 $(function(){
        /**通过网页链接里面的参数列表来获取视频id的值，存储到vid中
         * 每次加载网页，都会执行一次ajax请求，通过视频id向后端请求视频的src值，该方法写在下面
         * 前端根据获取的值再设置<video>标签的src属性，这样就可以显示出相应的视频了*/
        var arr = location.search.substr(1).split('=');
        var vid = arr[1];
        //如果是第一个视频，就把上一个（视频）按钮禁用
        if(vid==1){
            $("#playLast").attr('disabled',true);
        }
        var myVideo=document.getElementById("media");
        // myVideo.src = src;
        // document.getElementById("demo").innerHTML = "视频名："+vname;
        var currentTime = "";
        var timeString = "";
        var tol;
        var timeId;
        // var qid;

        var rateSelect = document.getElementById("selRate"); //拿到select对象
        var rate;//用于记录每次改变速率后的速率，存储到rateString当中;
        var time;//用于记录每次改变速率时的时间，储存到rateString当中;
        var rateString="";
        var flag=true;

        var is_like=0;//默认当前视频没有点赞，点赞按钮是黑色？从数据库中请求得来的数据，点赞的话才改为1，按钮改为红色
        // var origin_is_like = 0;//方便观察用户是否有修改点赞状态，离开页面是判断是否存储和怎么存储(添加还是删除)
        var is_collect=0;//同上
        // var origin_is_collect = 0;//同上
        var likesNumbers;
        // var originLikesNumbers;//该视频从数据中取出的点赞人数 通过比较这个和likeNumber的大小来决定 离开页面时视频的点赞数是增加还是减少
        var collectNumbers;
        // var originCollectNumbers;//同上
        var idNext;
        /**页面加载好时方法:*/
        /* 找到用户名 页面右上角 欢迎的初始化*/
        loadUserInfo();
        /* 加载视频信息：视频文件、名称、点赞数、收藏数，以及用户是否点赞或收藏*/
        window.onpageshow = function(){
            console.log("loadVideoInfo");
            loadVideoInfo();
        }
        /* 从数据库中获取笔记数据 并加载*/
        getAndLoadNotesFromDbs();
        // loadNotes();上面的方法一起load 否则可能因为执行顺序 localstorage里面的内容没有load。
        updateBegin("video"+vid);//开始记录此页面登录时间
        var vidNew
        //上一个（视频）按钮
        $("#playLast").on("click",function (){
            window.location.href = "../web/videoplayer_etm2021.html?id="+(parseInt(vid) - 1);
        })
        //下一个（视频）按钮
        var idNext;
        $("#playNext").on("click",function (){
            if(parseInt(vid) === 14){
                idNext = 100;
            }else{
                idNext = parseInt(vid) + 1;
            }
            $.post("/videos/find_by_id",
                {"id":(idNext)},
                function (json){
                    if (json.state == 200){ //如果有下一个视频
                        window.location.href = "../web/videoplayer_etm2021.html?id="+(idNext);
                    }
                    else{ //如果没有下一个视频，则返回到第一个视频
                        if(window.confirm('该视频是最后一个视频，点击确实将播放第一个视频')){
                            window.location.href = "../web/videoplayer_etm2021.html?id="+1;
                        }else{
                            return false;
                        }
                    }
                })
        })
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
         /** 事件编码 play、pause、skip、ratechange*/
         let event_time;//记录事件发生的事件
         let is_paused = true; //用该变量替代myVideo.paused，能够帮助skip事件获得发生时正真的视频状态（应该播放时发生skip事件也会在该事件前伴随一个pause事件）
         let is_skipped = false; //该变量帮助skip事件判断其之前是不是有一个skip事件，有的化该次的play事件不记录。（因为是skip事件引起的）
         /** play事件*/
         $(myVideo).on("play",function (){
             event_time = new Date();
             is_paused = false;
             // console.log("进入play事件,is_skipped"+is_skipped);
             if(!is_skipped){
                 // console.log("play事件激活");
                 $.post("/click_event/create",
                     {"vid":vid,"event":"play","position":parseInt(myVideo.currentTime),"time":event_time.toLocaleString(),"state":"playing","rate":myVideo.playbackRate},
                     function(json){
                         if(json.state==200) {
                             console.log("play事件存储成功");
                         }
                     } )
             }
             is_skipped = false;
         })
         /** pause事件*/
         $(myVideo).on("pause",function (){
             event_time = new Date();
             // console.log("pause事件激活,seeking:"+myVideo.seeking);
             if(!myVideo.seeking){
                 is_paused = true
                 // console.log("pause事件激活");
                 $.post("/click_event/create",
                     {"vid":vid,"event":"pause","position":parseInt(myVideo.currentTime),"time":event_time.toLocaleString(),"state":"paused","rate":myVideo.playbackRate},
                     function(json){
                         if(json.state==200) {
                             console.log("pause事件存储成功");
                         }
                     } )
             }
         })
         /** ratechange事件*/
         let event_state;
         $(rateSelect).on("change",function(){
             event_time = new Date();
             event_state = myVideo.paused ? "paused":"playing";
             // console.log("ratechange事件激活，state:"+event_state);
             $.post("/click_event/create",
                 {"vid":vid,"event":"ratechange","position":parseInt(myVideo.currentTime),"time":event_time.toLocaleString(),"state":event_state,"rate":myVideo.playbackRate},
                 function(json){
                     if(json.state==200) {
                         console.log("ratechange事件存储成功");
                     }
                 } )
         })
         /** skip事件
          * play的时候快进会触发三个事件：pause事件激活,seeking:true；skip事件激活，state:paused currentTime:26.322523；play事件激活,seeking:false
          * pause的时候快进会触发一个事件：skip事件激活，state:paused currentTime:68.843523
          * pause的时候快退会触发一个事件：skip事件激活，state:paused currentTime:22.779107
          * play的时候快退会触发三个事件：pause事件激活,seeking:true；skip事件激活，state:paused currentTime:8.60544；play事件激活,seeking:false
          * 所以对于快进快退，需要在pause和play事件中进行额外噪音判断*/
         $(myVideo).on("seeking",function (){
             is_skipped = is_paused ? false:true; //playing的时候的is_skip需要设置为true，paused的时候的is_skip不需要设置为true
             event_time = new Date();
             event_state = is_paused ? "paused":"playing";
             // console.log("skip事件激活，state:"+event_state);
             $.post("/click_event/create",
                 {"vid":vid,"event":"skip","position":parseInt(myVideo.currentTime),"time":event_time.toLocaleString(),"state":event_state,"rate":myVideo.playbackRate},
                 function(json){
                     if(json.state==200) {
                         console.log("skip事件存储成功");
                     }
                 } )
         })

        /**
         * 写这样一个方法 向后端存储观看记录， 因为三种清空下都会存储数据 避免重复代码 直接写个函数
         * @param v vid
         * @param t timeString
         * @param r rateString
         */
        function savaRecord(v,t,r){
            $.post("/users/create_record",
                {"vid":v,"time":t,"rate":r},
                function(json){
                    if(json.state==200) {
                        console.log("播放数据存储成功");
                    }
            });
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
                qid = vid;
                //没有答过题的记录才存储
                if(qid > 0){
                    $.get("/quiz_record/is_done",{"quizId":qid},function(json){
                        if(json.state != 200){ //查询不到内容(200状态码说明已经做过)
                            savaRecord(vid,timeString,rateString);
                            //做题
                            // lianjie="<a href='http://etm2020.cn/index.php/Index/quiz_etm_2021?id="+qid+"'>测试链接</a>";
                            // console.log(lianjie);
                            // document.getElementById("showdiv1").innerHTML="<p>若未跳转成功点击此链接:</p>";
                            // document.getElementById("showdiv2").innerHTML=lianjie;
                            alert('视频播放结束!请同学们完成相关测验题，本次测验题的成绩也会作为学生总成绩的一部分，点击确定按钮跳转至测验部分');

                            console.log(vid);
                            console.log(qid);
                            window.location.href = "../web/quiz.html?id="+qid;
                        }
                    })
                }
            }
            else
                timeId = setTimeout(getCurTime,1000);
        }


        /**onbeforeunload 事件在即将离开当前页面（刷新或关闭）时触发
         * 方法4+2操作*/
        window.onbeforeunload = function(){
            // alert("退出视频页面事件激活0");
            // updateLeaving();
            // alert("退出视频页面事件激活");
            /**1.存储视频观看数据*/
            if(timeString!=""&&flag==true){
                flag=false;
                if((parseInt(vid)-1)>0){
                    $.get("/quiz_record/is_done",{"quizId":(parseInt(vid)-1)},function(json){
                        if(json.state != 200){ //查询不到内容(200状态码说明已经做过)
                            savaRecord(vid,timeString,rateString);
                        }
                    })
                }
            }
        }

        /**点击返回 也要存储相关的观看数据*/
        $('#back').click(function (){
            if(timeString!=""&&flag==true){
                flag=false;
                savaRecord(vid,timeString,rateString);
            }
        });
        /**点击退出，则执行一次ajax请求，到后端清除session数据*/
        $('#login-out').click(function () {
            logout();
         });

        /**当点击记笔记的按钮时 才滑出一个输出框*/
        var video_flag;//控制的这个按钮播放/暂停视频的变量，效果还可以，如果没看视频就点击记笔记不会自己播放了；
        var is_write_notes_show = false;
        $(".write-notes-btn").on("click",function(){
            $(".write-notes").slideToggle();
            is_write_notes_show = !is_write_notes_show;
            if(is_write_notes_show){//如果视频在播放,且记笔记页面即将展示
                if(!myVideo.paused){////如果视频在播放
                    video_flag=1;
                    myVideo.pause();
                }
                clearTimeout(timeId);//取消timeString的这个定时器，在笔记的暂停时间段内不进行记录
                $(".write-notes-time").text("笔记对应视频时间："+parseInt(myVideo.currentTime)+"秒");
                // console.log("执行禁用");
                myVideo.removeAttribute("controls");//取消视频上的控制按钮 播放视频只能再次点击 记笔记的按钮
                updateBegin("notes");//进入笔记的轨迹
            }else{//如果记笔记页面即将收回
                updateBegin("video"+vid);//进入视频播放的轨迹
                timeId = setTimeout(getCurTime,1000);//重新添加定时器
                myVideo.setAttribute("controls","controls");
                if(video_flag === 1){
                    video_flag=0;
                    myVideo.play();
                }
            }
        });

        /** 给笔记的提交按钮和内容输入框的回车键绑定事件，增加一条笔记 ，还要存储到数据库当中？ 还是最后离开页面再存储？---->解答 ： 先存储到浏览器的本地内存，当用户离开页面时 再统一从本地中进行存储？*/
        $(".notes-submit").on("click",function (){
            if($(".write-notes-content").val() === "" || $(".write-notes-title").val() === ""){
                alert("请将内容填写完整哦！");
            }else{
                $.post("/notes/create_notes",
                    {"vid":vid,"secondTime":parseInt(myVideo.currentTime),"title":$(".write-notes-title").val(),"notes":$(".write-notes-content").val(),"state":"new"},
                    function(json){
                        if(json.state==200) {
                            console.log("笔记存储成功");
                            getAndLoadNotesFromDbs();
                        }
                    });
                $(".write-notes-content").val("");//内容文本框清空
                $(".write-notes-title").val("");//主题文本框清空
            }
        });

        /**给所有的播放button注册点击事件*/
        $(".notes").on("click",".btn-play",function(){
            // 下面这个可以获取所点击的button的data-time这个自定义属性的值，然后就可以让视频播放到相应的时间，但是我们需要在创建这样的media的时候去给它赋值；
            // $(this).attr("data-time");
            myVideo.currentTime = $(this).attr("data-time");
            myVideo.play();
            // alert($(this).attr("data-time"));
        });
        /**给所有的删除button注册点击事件*/
        let index_delete;
        $(".notes").on("click",".btn-delete",function(){
            index_delete = $(this).attr("id");
            $.get("/notes/delete",{"vid":vid,"secondTime":$(".btn-play").eq(index_delete).attr("data-time")},function(json){
                if(json.state == 200){
                    // console.log("删除笔记成功");
                    getAndLoadNotesFromDbs();
                }
            })
            // console.log($(".note-title").eq(index_delete).text());
            // console.log($(".note-content").eq(index_delete).text());
            // console.log($(".btn-play").eq(index_delete).attr("data-time"));
        });

        /** 给点赞按钮创建点击事件*/
        $(".like-btn").on("click",function(){
            // alert("is_like="+is_like);
            if(is_like === 0){//没有点赞时 才可以修改其点赞样式为红色
                userAddLike();//向数据库增加视频点赞数，存储用户点赞状态
                console.log("成功进入islike=0");
                $(this).css("color","red");
                likesNumbers++;
                $(".like-btn").html("<img src=\"../icon/hand-thumbs-up-red.svg\" alt=\"\" width=\"20\" height=\"20\" style='color: red;fill:currentColor'>"+likesNumbers);//这个前面的span是固定的样式。主要是为了修改显示的点赞人数。
                is_like=1;
            }else{//点赞时，（再点击），那就修该样式为黑色
                userDeleteLike();//向数据库减少视频点赞数，存储用户点赞状态
                console.log("成功进入islike=1");
                $(this).css("color","black");
                likesNumbers--;
                $(".like-btn").html("<img src=\"../icon/hand-thumbs-up.svg\" alt=\"\" width=\"20\" height=\"20\">"+likesNumbers);//同上
                is_like=0;
            }
        })
        /** 给收藏按钮创建点击事件 ；这个方法跟上面代码重叠率很高*/
        $(".collect-btn").on("click",function(){
            if(is_collect === 0){
                userAddCollection();
                $(this).css("color","red");
                collectNumbers++;
                $(".collect-btn").html("<img src=\"../icon/heart-red.svg\" alt=\"\" width=\"20\" height=\"20\" style='color: red;fill:currentColor'>"+collectNumbers);
                is_collect=1;
            }else{
                userDeleteCollection();
                $(this).css("color","black");
                collectNumbers--;
                $(".collect-btn").html("<img src=\"../icon/heart.svg\" alt=\"\" width=\"20\" height=\"20\">"+collectNumbers);
                is_collect=0;
            }
        })
     /** 加载视频信息：视频文件、名称、点赞数、收藏数，以及用户是否点赞或收藏*/
     function loadVideoInfo(){
         /*页面加载好时操作2： 到视频对象 并且根据src初始化视频 ；根据videoName初始化视频名字；根据点赞人数初始化originLikesNumbers和likesNumbers两个变量 ；根据点赞人数初始化originCollectNumbers和collectNumbers两个变量*/
         $.post("/videos/find_by_id",
             {"id":vid},
             function(json){
                 if(json.state==200) {
                     $("#media").prop("src",json.data.src);
                     $("#demo").text("视频名："+json.data.videoName);
                     likesNumbers = json.data.likeNumbers;
                     // originLikesNumbers = json.data.likeNumbers;//初始化originLikeNumbers和likesNumbers
                     $(".like-btn ").html("<img src=\"../icon/hand-thumbs-up.svg\" alt=\"赞\" width=\"20\" height=\"20\">"+likesNumbers);
                     collectNumbers = json.data.collectNumbers;
                     // originCollectNumbers = json.data.collectNumbers;//同上
                     $(".collect-btn ").html("<img src=\"../icon/heart.svg\" alt=\"收藏\" width=\"20\" height=\"20\">"+collectNumbers);
                     // console.log(json.data.likeNumbers);
                     // alert("likenumbers:"+originLikesNumbers+"collectnumbers"+originCollectNumbers);
                 }else {
                     console.log(json.message);
                 }
             })
         /*页面加载好时方法2： 从数据库中调取用户对视频的点赞状态 调整变量is_like的值*/
         $.post("/likes/find_like_status",
             {"vid":vid},
             function(json){
                 if(json.state==200) {//返回200表示已经点赞,初始化is_like为1，并调整点赞按钮样式为红色
                     is_like = 1;
                     // origin_is_like = 1;
                     $(".like-btn").css("color","red");
                     $(".like-btn svg").css("color","red");
                     $(".like-btn svg").css("src","../icon/hand-thumbs-up-red.svg");
                 }//如果是8001表示没有点赞 那就时默认样式
             });
         /*页面加载好时方法3： 从数据库中调取用户对视频的收藏状态 调整变量is_collect的值*/
         $.post("/collects/find_collection_status",
             {"vid":vid},
             function(json){
                 if(json.state==200) {
                     is_collect = 1;
                     // origin_is_collect = 1;
                     $(".collect-btn").css("color","red");
                     $(".collect-btn svg").css("color","red");
                     $(".collect-btn svg").css("src","../icon/heart-red.svg");
                 }//如果是6001表示没有点赞 那就时默认样式
             });
     }
     /**页面加载好时方法1： 从数据库中调取笔记的内容；*/
     function getAndLoadNotesFromDbs(){
         $.post("/notes/find_video_notes",
             {"vid":vid},
             function(json){
                 if(json.state==200) {
                     $(".notes").empty();//先置空，再显示所有笔记
                     $.each(json.data, function (index, ele) {
                         index = json.data.length-index-1;//数组翻转
                         $(".notes").prepend("<div class='panel panel-default col-md-8'><div class='panel-heading'><p class='note-title'>"+ele.title+"</p></p><div class='btn-group' role='group' ><button type='button' class='btn btn-default btn-play' data-time='"+ele.secondTime+"'>播放</button><button type='button' class='btn btn-default btn-delete' id='"+index+"'>删除</button></div></div><div class='panel-body'><p class='note-content'>"+ele.notes+"</p></div><p style='float: right;padding-right: 10px;font-size: 8px;color: #6f6f6f'>对应视频位置："+ele.secondTime+"s</p></div>");
                     })
                 }else if(json.state == 7002){
                     $(".notes").empty();
                     //米有笔记就什么都不做吧
                     // alert(json.message);
                 }
             });
     }
     function likeAndCollectManage(url){
         $.post(url,
             {"vid":vid},
             function(json){
                 if(json.state==200) {
                     console.log(json.message);
                 }
             });
     }
     function userAddLike(){
         /*视频点赞数+1*/
         likeAndCollectManage("/videos/add_like_numbers");
         /*更改点赞状态，在另一张表*/
         likeAndCollectManage("/likes/creat_like");
     }
     function userDeleteLike(){
         /*视频点赞数-1*/
         likeAndCollectManage("/videos/minus_like_numbers");
         /*更改点赞状态，在另一张表*/
         likeAndCollectManage("/likes/delete_like");
     }

     function userAddCollection(){
         likeAndCollectManage("/videos/add_collect_numbers");
         likeAndCollectManage("/collects/creat_collection");
     }
     function userDeleteCollection(){
         likeAndCollectManage("/videos/minus_collect_numbers");
         likeAndCollectManage("/collects/delete_collection");
     }

    })