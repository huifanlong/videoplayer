 $(function(){
        /**通过网页链接里面的参数列表来获取视频id的值，存储到vid中
         * 每次加载网页，都会执行一次ajax请求，通过视频id向后端请求视频的src值，该方法写在下面
         * 前端根据获取的值再设置<video>标签的src属性，这样就可以显示出相应的视频了*/
        var arr = location.search.substr(1).split('=');
        var vid = arr[1];

        // var vname = "";
        // console.log(src)
        // console.log(vid)
        // console.log(vname)
        var myVideo=document.getElementById("media");
        // myVideo.src = src;
        // document.getElementById("demo").innerHTML = "视频名："+vname;
        var currentTime = "";
        var timeString = "";
        var tol;
        var timeId;
        var qid;

        var rateSelect = document.getElementById("selRate"); //拿到select对象
        var rate;//用于记录每次改变速率后的速率，存储到rateString当中;
        var time;//用于记录每次改变速率时的时间，储存到rateString当中;
        var rateString="";
        var flag=true;

        var is_like=0;//默认当前视频没有点赞，点赞按钮是黑色？从数据库中请求得来的数据，点赞的话才改为1，按钮改为红色
        var origin_is_like = 0;//方便观察用户是否有修改点赞状态，离开页面是判断是否存储和怎么存储(添加还是删除)
        var is_collect=0;//同上
        var origin_is_collect = 0;//同上
        var likesNumbers;
        var originLikesNumbers;//该视频从数据中取出的点赞人数 通过比较这个和likeNumber的大小来决定 离开页面时视频的点赞数是增加还是减少
        var collectNumbers;
        var originCollectNumbers;//同上

        /**页面加载好时方法3+1:*/
        /** 从数据库中获取笔记数据 并加载*/
        getNotesFromDbs();
        /**展示笔记*/
        // loadNotes();上面的方法一起load 否则可能因为执行顺序 localstorage里面的内容没有load。
        /**点赞图标修改红色 如果点赞的话*/
        loadLikeBtnColor();
        /**收藏图标修改红色 如果收藏的话*/
        loadCollectBtnColor();
        updateBegin("video_trace");//开始记录此页面登录时间

        /**页面加载好时操作1： 找到用户名 页面右上角 欢迎的初始化*/
        $.ajax({type: "GET", url: "/users/find_by_id", dataType:"JSON", async: true,
            // data: $('#form').serialize(),// 序列化表单值LogLLLLLL
            // data:"userName="+user_name+"&userPass="+user_pass,
            // data:"",
            error: function (request) {
                //为啥没登陆直接跳转到这个页面会是error？
                $(".login-yes").prop("style","display:none");
                // alert("未登录，请先登录");
            },
            success: function (json) {
                if(json.state==200) {
                    // alert("成功");
                    $(".login-no").prop("style","display:none");
                    $(".login-span-yes").html("你好！"+json.data.userName+"&nbsp;&nbsp");
                }else if (json.state==2) {  //管理员账号
                    window.location.href = "{:U('Index/admin')}";
                }
                else {
                    $(".login-yes").prop("style","display:none");
                }
            }
        })
        /**页面加载好时操作2： 到视频对象 并且根据src初始化视频 ；根据videoName初始化视频名字；根据点赞人数初始化originLikesNumbers和likesNumbers两个变量 ；根据点赞人数初始化originCollectNumbers和collectNumbers两个变量*/
        $.ajax({type: "POST", url: "/videos/find_by_id", data: "id="+vid, dataType:"JSON", async: true,
            // data:"userName="+user_name+"&userPass="+user_pass,
            error: function (request) {
                alert("Connection error"+request.message+request.status);
                alert(vid);
            },
            success: function (json) {
                if(json.state==200) {
                    // alert("dd");
                    $("#media").prop("src",json.data.src);
                    $("#demo").text("视频名："+json.data.videoName);
                    likesNumbers = json.data.likeNumbers;
                    originLikesNumbers = json.data.likeNumbers;//初始化originLikeNumbers和likesNumbers
                    $(".like-btn ").html("<span class='glyphicon glyphicon-thumbs-up' aria-hidden='true';></span>"+likesNumbers);
                    collectNumbers = json.data.collectNumbers;
                    originCollectNumbers = json.data.collectNumbers;//同上
                    $(".collect-btn ").html("<span class='glyphicon glyphicon-heart' aria-hidden='true';></span>"+collectNumbers);
                    //glyphicon glyphicon-heart
                    // console.log(json.data.likeNumbers);
                    alert("likenumbers:"+originLikesNumbers+"collectnumbers"+originCollectNumbers);
                }else {
                    alert(json.message);
                }
            }
        });


        /**页面加载好时方法1： 从数据库中调取笔记的内容；*/
        function getNotesFromDbs(){
            $.ajax({
                type:"POST",
                url:"/notes/find_video_notes",
                data:"vid="+vid, dataType:"JSON", async: true,
                error: function (request) {
                    alert("Connection error"+request.message+request.status);
                    alert(vid);
                },
                success: function (json) {
                    if(json.state==200) {
                        var local = [];
                        $.each(json.data,function (index,ele){
                            // alert(ele.notes);
                            local.push({time:ele.secondTime,content:ele.notes,title:ele.title,state:"fromdbs"});
                        })
                        savaNotes(local);
                        loadNotes();
                    } else if(json.state == 7002){
                        //米有笔记就什么都不做吧
                        // alert(json.message);
                    }
                }
            })
        }
        /**页面加载好时方法2： 从数据库中调取用户对视频的点赞状态 调整变量is_like的值*/
        function loadLikeBtnColor(){
            $.ajax({
                type:"POST",
                url:"/likes/find_like_status",
                data:"vid="+vid, dataType:"JSON", async: true,
                error: function (request) {
                    alert("Connection error"+request.message+request.status);
                    alert(vid);
                },
                success: function (json) {
                    if(json.state==200) {//返回200表示已经点赞,初始化is_like为1，并调整点赞按钮样式为红色
                        is_like = 1;
                        origin_is_like = 1;
                        $(".like-btn").css("color","red");
                    }//如果是8001表示没有点赞 那就时默认样式
                }
            })
        }
        /**页面加载好时方法3： 从数据库中调取用户对视频的收藏状态 调整变量is_collect的值*/
        function loadCollectBtnColor(){
            $.ajax({
                type:"POST",
                url:"/collects/find_collection_status",
                data:"vid="+vid, dataType:"JSON", async: true,
                error: function (request) {
                    alert("Connection error"+request.message+request.status);
                    alert(vid);
                },
                success: function (json) {
                    if(json.state==200) {//返回200表示已经点赞,初始化is_like为1，并调整点赞按钮样式为红色
                        is_collect = 1;
                        origin_is_collect = 1;
                        $(".collect-btn").css("color","red");
                    }//如果是6001表示没有点赞 那就时默认样式
                }
            })
        }

        /**页面离开时方法1：存储视频的点赞人数 */
        function saveLikesNumbersToDbs(){
            if(originLikesNumbers < likesNumbers){//这个用户增加了一次点赞，该视频的点赞人数加1
                alert("点赞数+1存入数据库");
                $.ajax({
                    type:"POST", url:"/videos/add_like_numbers",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("点赞更新发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("点赞更新成功");
                        }
                    }
                })
            }else if(originLikesNumbers > likesNumbers){//这个用户取消了已有点赞，该视频的点赞人数减1
                alert("点赞数-1存入数据库");
                $.ajax({
                    type:"POST", url:"/videos/minus_like_numbers",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("点赞更新发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("点赞更新成功");
                        }
                    }
                })
            }//第三种情况二者相等 就不需要改变点赞人数
        }
        /**页面离开时方法2：存储视频的收藏人数 与上面基本相似*/
        function saveCollectLikesNumbersToDbs(){
            if(originCollectNumbers < collectNumbers){//这个用户增加了一次收藏，该视频的收藏人数加1
                $.ajax({
                    type:"POST", url:"/videos/add_collect_numbers",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("收藏更新发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("收藏更新成功");
                        }
                    }
                })
            }else if(originCollectNumbers > collectNumbers){//这个用户取消了已有收藏，该视频的收藏人数减1
                $.ajax({
                    type:"POST", url:"/videos/minus_like_numbers",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("收藏更新发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("收藏更新成功");
                        }
                    }
                })
            }//第三种情况二者相等 就不需要改变点赞人数
        }
        /**页面离开时方法3: 存储用户对这个视频的点赞状态*/
        function savaLikeStatusToDbs(){
            if( is_like === 0 && origin_is_like === 1){//说明用户本来是点赞的，离开时取消了点赞
                $.ajax({
                    type:"POST", url:"/likes/delete_like",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("修改用户对这个视频的点赞发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("修改用户对这个视频的点赞成功");
                        }
                    }
                })
            }else if( is_like === 1 && origin_is_like === 0){//说明用户本来没有点赞的，离开时已经点了赞
                $.ajax({
                    type:"POST", url:"/likes/creat_like",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("修改用户对这个视频的点赞发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("修改用户对这个视频的点赞成功");
                        }
                    }
                })
            }
        }
        /**页面离开时方法4: 存储用户对这个视频的点赞状态*/
        function savaCollectStatusToDbs(){
            if( is_collect === 0 && origin_is_collect === 1){//说明用户本来是收藏的，离开时取消了收藏
                $.ajax({
                    type:"POST", url:"/collects/delete_collection",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("修改用户对这个视频的点赞发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("修改用户对这个视频的点赞成功");
                        }
                    }
                })
            }else if( is_collect === 1 && origin_is_collect === 0){//说明用户本来没有收藏的，离开时已经点了收藏
                $.ajax({
                    type:"POST", url:"/collects/creat_collection",
                    data:"vid="+vid, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("修改用户对这个视频的点赞发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            console.log("修改用户对这个视频的点赞成功");
                        }
                    }
                })
            }
        }

        /** 改变播放速率*/
        rateSelect.addEventListener('change', function () {
            //调整视频播放速率
            myVideo.playbackRate = this.value;
            //获取改变之后的视频播放速率
            rate = $("#selRate").val();
            //获取改变速率的当前时间
            time = parseInt(myVideo.currentTime);  //秒
            rateString += (rate + " " + time + ";");

            /**rateString的格式如下：
             * rateString: 1.25 6;1.5 19;0.5 35;1.0 37;*/
        });

        myVideo.addEventListener('play',getCurTime);
        myVideo.addEventListener('pause',function(){//取消监听器是用来干嘛的？ ：是让它不会二次再执行getCurTime（）方法吧 所以暂停的时候 依然也是会记录进timeString里面的；但是其实不是有一个方法可以让play的监听器只执行一次吗
            myVideo.removeEventListener('play',getCurTime);
        });

        /**
         * 写这样一个方法 向后端存储观看记录， 因为三种清空下都会存储数据 避免重复代码 直接写个函数
         * @param vid
         * @param timeString
         * @param rateString
         */
        function savaRecord(v,t,r){
            $.ajax({
                type: "POST",
                url: "/users/create_record",
                // data: $('#form').serialize(),// 序列化表单值LogLLLLLL
                data:"vid="+v+"&time="+t+"&rate="+r,
                dataType:"JSON",
                async: true,
                error: function (request) {
                    alert("播放数据存储发生错误"+request.message+request.status);
                },
                success: function (json) {
                    if(json.state==200) {
                        // alert("退出成功");
                        console.log("播放数据存储成功");
                    }else if (json.state==2) {  //管理员账号
                        window.location.href = "{:U('Index/admin')}";
                    }
                    else {
                    }
                }
            })
        }

        /**当用户点击播放的时候，会出发这个方法curveTime，获取时间曲线
        * 这个方法体里面有个定时1秒回调一次的函数，正常一直播播放的话，每秒回调一次，会让timeString增加这一秒的内容
        * timeString里面的内容类似于：0 1 2 3 4 5 100 101 102 103.。。
        * 且 当播放完毕时 会调用ajax请求，当数据保存至数据库，并且跳转至练习题页面
        * 有个疑问 ：第一个if-else语句为什么要分这两种情况给tol赋不同的值？ 第一秒时tol为1 有什么特别的含义吗？*/
        function getCurTime() {
            currentTime = myVideo.currentTime;  //秒
            // console.log(currentTime);
            timeString += (parseInt(currentTime) + " ");
            if(parseInt(currentTime)==0) {
                tol = 1;
            } else {
                tol = myVideo.duration;
            }

            if (currentTime == tol) {
                clearTimeout(timeId);
                console.log("播放完毕");
                console.log(timeString);
                console.log(rateString);
                flag=false;
                savaRecord(vid,timeString,rateString);
                // $.ajax({
                //     type: "POST",
                //     url: "/users/create_record",
                //     // data: $('#form').serialize(),// 序列化表单值LogLLLLLL
                //     data:"vid="+vid+"&time="+timeString+"&rate="+rateString,
                //     dataType:"JSON",
                //     async: true,
                //     error: function (request) {
                //         alert("播放数据存储发生错误"+request.message+request.status);
                //     },
                //     success: function (json) {
                //         if(json.state==200) {
                //             // alert("退出成功");
                //             console.log("播放数据存储成功");
                //         }else if (json.state==2) {  //管理员账号
                //             window.location.href = "{:U('Index/admin')}";
                //         }
                //         else {
                //         }
                //     }
                // })

                // qid = vid - 1;
                // lianjie="<a href='http://etm2020.cn/index.php/Index/quiz_etm_2021?id="+qid+"'>测试链接</a>";
                // console.log(lianjie);
                // document.getElementById("showdiv1").innerHTML="<p>若未跳转成功点击此链接:</p>";
                // document.getElementById("showdiv2").innerHTML=lianjie;
                // alert('视频播放结束!请同学们完成相关测验题，本次测验题的成绩也会作为学生总成绩的一部分，点击确定按钮跳转至测验部分');
                //
                // console.log(vid)
                // window.location.href = "{:U('Index/quiz_etm_2021')}?id="+qid;
            }
            else
                timeId = setTimeout(getCurTime,1000);
        }


        /**onbeforeunload 事件在即将离开当前页面（刷新或关闭）时触发
         * 方法4+2操作*/
        window.onbeforeunload = function(){
            console.log("退出视频页面事件激活0");
            alert("退出视频页面事件激活0");
            updateLeaving();
            alert("退出视频页面事件激活");
            /**1.存储视频观看数据*/
            if(timeString!=""&&flag==true){
                flag=false;
                savaRecord(vid,timeString,rateString);
            }
            /**2.笔记存储：把笔记数据全部从浏览器内存中拿给后端，后端处理好了哪些笔记是增加 哪些笔记是删除
             * 2.1暂时前端没有做笔记修改的操作*/
            var local = getNotes();//从本地中获取数据
            $.each(local,function (index,ele){
                $.ajax({type:"POST", url:"/notes/create_notes",
                    data:"vid="+vid+"&secondTime="+ele.time+"&title="+ele.title+"&notes="+ele.content+"&state="+ele.state, dataType:"JSON", async: true,
                    error: function (request) {
                        alert("笔记存储发生错误"+request.message+request.status);
                    },
                    success: function (json) {
                        if(json.state==200) {
                            // alert("退出成功");
                            console.log("笔记存储成功");
                        }
                    }
                })
            })
            /**3.存储视频的点赞人数 + 存储用户对这个视频的点赞状态*/
            saveLikesNumbersToDbs();
            savaLikeStatusToDbs();
            alert("存储点赞数据成功");
            console.log("存储点赞数据成功");
            /**4.存储视频的收藏人数 + 存储用户对这个视频的点赞状态*/
            saveCollectLikesNumbersToDbs();
            savaCollectStatusToDbs();

            localStorage.removeItem("notes");
            // alert("清除数据");
            // localStorage.clear();
        }

        /**点击返回 也要存储相关的观看数据*/
        $('#back').click(function (){
            if(timeString!=""&&flag==true){
                flag=false;
                savaRecord(vid,timeString,rateString);
            }
        })
        /**点击退出，则执行一次ajax请求，到后端清除session数据*/
        $('#login-out').click(function () {
            $.ajax({
                type: "GET",
                url: "/users/login_out",
                // data: $('#form').serialize(),// 序列化表单值LogLLLLLL
                // data:"userName="+user_name+"&userPass="+user_pass,
                // data:"",
                dataType:"JSON",
                async: true,
                error: function (request) {
                    alert("Connection error"+request.message+request.status);
                },
                success: function (json) {
                    if(json.state==200) {
                        alert("退出成功");
                    }else if (json.state==2) {  //管理员账号
                        window.location.href = "{:U('Index/admin')}";
                    }
                }
            })
         });

        /**当点击记笔记的按钮时 才滑出一个输出框*/
        var video_flag;//控制的这个按钮播放/暂停视频的变量，效果还可以，如果没看视频就点击记笔记不会自己播放了；
        $(".write-notes-btn").on("click",function(){
            $(".write-notes").slideToggle();
            if(!myVideo.paused){//如果视频在播放
                video_flag=1;
                myVideo.pause();
                clearTimeout(timeId);//取消timeString的这个定时器，在笔记的暂停时间段内不进行记录
                myVideo.removeAttribute("controls");//取消视频上的控制按钮 播放视频只能再次点击 记笔记的按钮
                updateLeaving();
                updateBegin("notes_trace");//进入笔记的轨迹
            }else{
                updateLeaving();
                updateBegin("video_trace");//进入视频播放的轨迹
                if(video_flag === 1){
                    video_flag=0;
                    myVideo.play();
                    timeId = setTimeout(getCurTime,1000);//重新添加定时器
                    myVideo.setAttribute("controls","controls");
                }
            }
        })

        /** 给笔记的提交按钮和内容输入框的回车键绑定事件，增加一条笔记 ，还要存储到数据库当中？ 还是最后离开页面再存储？---->解答 ： 先存储到浏览器的本地内存，当用户离开页面时 再统一从本地中进行存储？*/
        $(".notes-submit").on("click",function (){
            if($(".write-notes-content").val() === "" || $(".write-notes-title").val() === ""){
                alert("没有内容");
            }else{
                //获取地中的所有笔记
                var local = getNotes();
                //增加这条新的笔记
                local.push({time:parseInt(myVideo.currentTime),content:$(".write-notes-content").val(),title:$(".write-notes-title").val(),state:"new"});
                //保存到内存中
                savaNotes(local);
                //重新加载
                loadNotes();
                $(".write-notes-content").val("");//内容文本框清空
                $(".write-notes-title").val("");//主题文本框清空
            }
        })

        /**给所有的播放button注册点击事件*/
        $(".notes").on("click",".btn-play",function(){
            // 下面这个可以获取所点击的button的data-time这个自定义属性的值，然后就可以让视频播放到相应的时间，但是我们需要在创建这样的media的时候去给它赋值；
            // $(this).attr("data-time");
            myVideo.currentTime = $(this).attr("data-time");
            myVideo.play();
            // alert($(this).attr("data-time"));
        })
        /**给所有的删除button注册点击事件*/
        $(".notes").on("click",".btn-delete",function(){
            var local = getNotes();
            // 数组的splice(从哪个索引号开始删,删几个)方法可以帮助去除localStorage里面的某条笔记信息
            // local.splice($(this).attr("id"),1);
            // alert(local[$(this).attr("id")].title);
            // alert(local[0].content);
            local[$(this).attr("id")].state+="-delete";
            savaNotes(local);
            loadNotes();
        })

        /** 给点赞按钮创建点击事件*/
        $(".like-btn").on("click",function(){
            alert("is_like="+is_like);
            if(is_like === 0){//没有点赞时 才可以修改其点赞样式为红色
                alert("成功进入islike=0");
                $(this).css("color","red");
                likesNumbers++;
                $(".like-btn").html("<span class='glyphicon glyphicon-thumbs-up' aria-hidden='true';></span>"+likesNumbers);//这个前面的span是固定的样式。主要是为了修改显示的点赞人数。
                is_like=1;
            }else{//点赞时，（再点击），那就修该样式为黑色
                alert("成功进入islike=1");
                $(this).css("color","black");
                likesNumbers--;
                $(".like-btn").html("<span class='glyphicon glyphicon-thumbs-up' aria-hidden='true';></span>"+likesNumbers);//同上
                is_like=0;
            }
        })
        /** 给收藏按钮创建点击事件 ；这个方法跟上面代码重叠率很高*/
        $(".collect-btn").on("click",function(){
            if(is_collect === 0){
                $(this).css("color","red");
                collectNumbers++;
                $(".collect-btn").html("<span class='glyphicon glyphicon-heart' aria-hidden='true';></span>"+collectNumbers);
                is_collect=1;
            }else{
                $(this).css("color","black");
                collectNumbers--;
                $(".collect-btn").html("<span class='glyphicon glyphicon-heart' aria-hidden='true';></span>"+collectNumbers);
                is_collect=0;
            }
        })

     /**读取本地存储的数据*/
     function getNotes(){
         var notes = localStorage.getItem("notes");
         if(notes !== null){
             return JSON.parse(notes); //本地存储的数据是字符串 需要转化成对象的格式
         }else{
             return [];
         }
     }
     /**保存笔记到本地*/
     function savaNotes(notes){
         localStorage.setItem("notes",JSON.stringify(notes));
     }
     /**渲染加载数据*/
     function loadNotes(){
         //读取本地存储的数据
         var notes = getNotes();
         //先把所有笔记内容清空
         $(".notes").empty();
         if(notes !== null){
             //再显示所有笔记
             $.each(notes,function(index,ele){
                 console.log(ele);
                 //判断这个元素的state属性，如果是new或者fromdbs就给他展示出来，否则就不展示；这样操作是方便最后将所有的这些数据传入数据库时 做不同的增删改查处理
                 //值得注意的是，删除元素只是修改其属性 让他在这里不显示，但是他依然在locaLStorage里面，在显示元素的时候，显示元素的index值是会被不显示的元素所占位的，所以在删除的方法里 通过他的index值还是可以定位到所显示的元素的？
                 if(ele.state === "new" || ele.state === "fromdbs"){
                     /* 下面这行是创建1个完整的<div>笔记*/
                     $(".notes").prepend("<div class='panel panel-default col-md-8'><div class='panel-heading'>"+ele.title+"<div class='btn-group' role='group' ><button type='button' class='btn btn-default btn-play' data-time='"+ele.time+"'>查看</button><button type='button' class='btn btn-default btn-delete' id='"+index+"'>删除</button></div></div><div class='panel-body'><p>"+ele.content+"</p></div></div>");
                 }else{

                 }

             })
         }
     }

    })