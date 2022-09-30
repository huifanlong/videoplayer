$(function(){
    var from = new Date();//登陆时间
    var to;//退出时间
    var time;

    /* 初始化右上角登录显示 */
    loadUserInfo();
    //从数据库获取并展示信息
    getAndLoadReflectionsFromDbs();
    /*每次到达这个页面都要判单是否需要初始化计时器，直接给后台一个GET请求就行*/
    window.onpageshow = function(){//onpageshow比最外层的document.ready可以放置back-force-cache：即实现点击浏览器左上角返回按钮时也触发该事件。但是IE好像不支持这个事件
        $.get("/trace/duration",function (json){
            if(json.state == 200){
                updateBegin("index");//开始记录此页面登录时间
            }
        });
        console.log("/trace/duration已访问？");
    }

    /* 点击退出登录按钮，进行退出登录操作*/
    $('#login-out').click(function () {
        logout();
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
            updateBegin("reflection");//进入笔记的轨迹
            getAndLoadReflectionsFromDbs();
        }else if(index == 0){//点击了课程页面
            updateBegin("index");//进入课程页面的轨迹
        }else{ //进入多元智能测验
            updateBegin("multiple")
        }
    })

    /* 给反思的提交按钮和内容输入框的回车键绑定事件，增加一条笔记 ，还要存储到数据库当中？ 还是最后离开页面再存储？---->解答 ： 先存储到浏览器的本地内存，当用户离开页面时 再统一从本地中进行存储？**/
    $(".reflections-submit").on("click",function (){
        if($(".write-reflections-content").val() === "" || $(".write-reflections-title").val() === ""){
            alert("没有内容");
        }else{
            $.post("/reflection/create",{"title":$(".write-reflections-title").val(),"reflection":$(".write-reflections-content").val()},function (json){
                if(json.state == 200){
                    console.log("创建反思成功");
                    getAndLoadReflectionsFromDbs();
                    $(".write-reflections-content").val("");//内容文本框清空
                    $(".write-reflections-title").val("");//主题文本框清空
                }
            })
        }
    })
    let database_id
    /*给所有的删除button注册点击事件*/
    $(".reflections").on("click",".btn-delete",function(){
        database_id = $(this).attr("data-database-id");
        $.get("/reflection/delete",{"id":database_id},function (json){
            if(json.state == 200){
                getAndLoadReflectionsFromDbs();
            }
        })
    })
    /**给所有的修改button绑定点击事件*/
    let index_update;
    $(".reflections").on("click",".btn-update",function(){
        index_update = $(this).attr("data-id");
        $(".update-reflections-content").val($(".reflection-content").eq(index_update).text());//给模态框里面的内容文本框之前的内容
        $(".update-reflections-title").val($(".reflection-title").eq(index_update).text());//给主题文本框之前的内容
        database_id = $(this).attr("data-database-id");
        /**事件里面可以有事件吗？要写一个模态框的点击事件*/
        $(".save-update").on("click",function(){
            $('#myModal').modal('hide');//关闭模态框
            time = new Date();
            $.post("/reflection/update",{"id":database_id,"title":$(".update-reflections-title").val(),"reflection":$(".update-reflections-content").val()},function (json){
                if(json.state == 200){
                    console.log("修改成功");
                    getAndLoadReflectionsFromDbs();
                }
            })
        })
        //比较奇怪的 存储和重新显示放到这里的话 就不会执行 是因为事件的嵌套？
    })
    /**页面加载好时方法1： 从数据库中调取笔记的内容；*/
    function getAndLoadReflectionsFromDbs(){
        console.log("进入加载删除方法了吗");
        $.get("/reflection/find_all",
            function(json){
                if(json.state==200) {
                    $(".reflections").empty();//先置空，再显示所有笔记;
                    console.log("已置空");
                    $.each(json.data, function (index, ele) {
                        index = json.data.length-index-1;//数组翻转
                        $(".reflections").prepend("<div class='panel panel-default col-md-8'><div class='panel-heading'><p class='reflection-title'>"+ele.title+"</p><div class='btn-group' role='group' ><button type='button' class='btn btn-default btn-delete' id='"+index+"' data-database-id='"+ele.id+"'>删除</button><button type='button' class='btn btn-default btn-update' data-toggle='modal' data-target='#myModal' data-id='"+index+"' data-database-id='"+ele.id+"'>修改</button></div></div><div class='panel-body'><p class='reflection-content'>"+ele.reflection+"</p></div></div>");
                    })
                    console.log("已重新显示");
                }else if(json.state == 7002){//提示这么视频下面没有笔记
                    $(".reflections").empty();
                }
            });
    }

})