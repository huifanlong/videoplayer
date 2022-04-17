$(function(){
    var from = new Date();//登陆时间
    var to;//退出时间

    /* 初始化右上角登录显示 */
    loadUserInfo();
    /*每次到达这个页面都要判单是否需要初始化计时器，直接给后台一个GET请求就行*/
    window.onpageshow = function(){//onpageshow比最外层的document.ready可以放置back-force-cache：即实现点击浏览器左上角返回按钮时也触发该事件。但是IE好像不支持这个事件
        updateBegin("index_trace");//开始记录此页面登录时间
        $.ajax({type: "GET", url: "/trace/duration", dataType:"JSON", async: true,
            error: function (request) {
                console.log(request.message);
            }
        })
        console.log("/trace/duration已访问？");
    }

    /* 点击退出登录按钮，进行退出登录操作*/
    $('#login-out').click(function () {
        $.ajax({type: "GET", url: "/users/login_out", dataType:"JSON", async: true,
            error: function (request) {
                alert("Connection error"+request.message+request.status);
            },
            success: function (json) {
                if(json.state==200) {
                    alert("退出成功");
                }else if (json.state==2) {  //管理员账号
                    window.location.href = "{:U('Index/admin')}";
                }
                else {
                }
            }
        })
    });

    /** 导航栏切换显示《课程》和《反思》*/
    $(".nav-items").click(function(ele){//对两个元素一起添加点击事件
        var index = $(this).attr("index");//获取所点击的导航的index，这个index值是在html中写好的，为了对应所显示的内容

        $(".nav-items").removeClass("active");//先把所有导航元素的class同时移除active。这两个都用到了jQuery的隐式迭代：会将所有的元素都做同样的操作，而不需要用到循环
        $(".nav-contents").css("display","none");//各自的内容也同样全部移除

        var navContents = document.querySelectorAll(".nav-contents");
        $(navContents[index]).slideDown();//显示所点击的部分
        $(this).addClass("active");//激活所点击的导航栏
        if(index == 1){//点击了反思页面
            updateLeaving();
            updateBegin("reflection_trace");//进入笔记的轨迹
        }else{//点击了课程页面
            updateLeaving();
            updateBegin("index_trace");//进入课程页面的轨迹
        }
    })

    /* 给反思的提交按钮和内容输入框的回车键绑定事件，增加一条笔记 ，还要存储到数据库当中？ 还是最后离开页面再存储？---->解答 ： 先存储到浏览器的本地内存，当用户离开页面时 再统一从本地中进行存储？**/
    $(".reflections-submit").on("click",function (){
        if($(".write-reflections-content").val() === "" || $(".write-reflections-title").val() === ""){
            alert("没有内容");
        }else{
            //获取地中的所有笔记
            var local = getReflections();
            //增加这条新的笔记
            local.push({content:$(".write-reflections-content").val(),title:$(".write-reflections-title").val(),state:"new"});
            //保存到内存中
            savaReflections(local);
            //重新加载
            loadReflections();
            $(".write-reflections-content").val("");//内容文本框清空
            $(".write-reflections-title").val("");//主题文本框清空
        }
    })
    /*给所有的删除button注册点击事件*/
    $(".reflections").on("click",".btn-delete",function(){
        var local = getReflections();
        // 数组的splice(从哪个索引号开始删,删几个)方法可以帮助去除localStorage里面的某条笔记信息
        // local.splice($(this).attr("id"),1);
        // alert(local[$(this).attr("id")].title);
        // alert(local[0].content);
        local[$(this).attr("id")].state+="-delete";
        savaReflections(local);
        loadReflections();
    })
    /**给所有的修改button绑定点击事件*/
    $(".reflections").on("click",".btn-update",function(){
        var local = getReflections();
        var id = $(this).attr("data-id");//获取其id 根据这样的id在localStorage中查找

        var title = local[id].title;
        var content = local[id].content;
        $(".update-reflections-content").val(content);//给模态框里面的内容文本框之前的内容
        $(".update-reflections-title").val(title);//给主题文本框之前的内容

        /**事件里面可以有事件吗？要写一个模态框的点击事件*/
        $(".save-update").on("click",function(){
            $('#myModal').modal('hide');//关闭模态框
            local[id].title = $(".update-reflections-title").val();//将修改的内容保存
            local[id].content = $(".update-reflections-content").val();
            local[id].state += "-update";
            savaReflections(local);//存储
            loadReflections();//重新显示
        })
        //比较奇怪的 存储和重新显示放到这里的话 就不会执行 是因为事件的嵌套？
    })


    /*读取本地存储的数据reflections*/
    function getReflections(){
        var reflections = localStorage.getItem("reflections");
        if(reflections !== null){
            return JSON.parse(reflections); //本地存储的数据是字符串 需要转化成对象的格式
        }else{
            return [];
        }
    }
    /*保存笔记到本地*/
    function savaReflections(reflections){
        localStorage.setItem("reflections",JSON.stringify(reflections));
    }
    /*渲染加载数据*/
    function loadReflections(){
        //读取本地存储的数据
        var reflections = getReflections();
        //先把所有笔记内容清空
        $(".reflections").empty();
        if(reflections !== null){
            //再显示所有笔记
            $.each(reflections,function(index,ele){
                console.log(ele);
                //判断这个元素的state属性，如果是new或者fromdbs就给他展示出来，否则就不展示；这样操作是方便最后将所有的这些数据传入数据库时 做不同的增删改查处理
                //值得注意的是，删除元素只是修改其属性 让他在这里不显示，但是他依然在locaLStorage里面，在显示元素的时候，显示元素的index值是会被不显示的元素所占位的，所以在删除的方法里 通过他的index值还是可以定位到所显示的元素的？
                if(ele.state === "new" || ele.state === "fromdbs"){
                    /* 下面这行是创建1个完整的<div>笔记*/
                    $(".reflections").prepend("<div class='panel panel-default col-md-8'><div class='panel-heading'>"+ele.title+"<div class='btn-group' role='group' ><button type='button' class='btn btn-default btn-delete' id='"+index+"'>删除</button><button type='button' class='btn btn-default btn-update' data-toggle='modal' data-target='#myModal' data-id='"+index+"'>修改</button></div></div><div class='panel-body'><p>"+ele.content+"</p></div></div>");
                }else{

                }

            })
        }
    }

    window.onbeforeunload = function (){
        updateLeaving();
    }
    window.onpagehide = function (){
        console.log("onpagehide执行")
    }

})