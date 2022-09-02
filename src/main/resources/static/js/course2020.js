$(function(){
    var from = new Date();//登陆时间
    var to;//退出时间
    var time;

    /* 初始化右上角登录显示 */
    loadUserInfo();
    //从数据库获取信息
    getReflectionsFromDbs();
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
        $.get("/users/login_out",
            function(json){
                if(json.state==200) {
                    alert("退出成功");
                }
            });
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
            // updateLeaving();
            updateBegin("reflection");//进入笔记的轨迹
            getReflectionsFromDbs();
        }else{//点击了课程页面
            // updateLeaving();
            updateBegin("index");//进入课程页面的轨迹
            /**2.反思存储：把笔记数据全部从浏览器内存中拿给后端，后端处理好了哪些笔记是增加 哪些笔记是删除,那些笔记是修改
             * 2.1暂时前端没有做笔记修改的操作*/
            var local = getReflections();//从本地中获取数据
            $.each(local,function (index,ele){
                $.post("/reflection/create",
                    {"time":ele.time,"title":ele.title,"reflection":ele.content,"state":ele.state,"fromDbsTime":ele.fromDbsTime},
                    function(json){
                        if(json.state==200) {
                            // alert("退出成功");
                            console.log("笔记存储成功");
                        }
                    });
            })
            localStorage.removeItem("reflections");
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
                }
            })
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
    let index_update;
    let database_id
    $(".reflections").on("click",".btn-update",function(){
        // console.log("点击了删除按钮");
        // var local = getReflections();
        index_update = $(this).attr("data-id");
        // var id = $(this).attr("data-id");//获取其id 根据这样的id在localStorage中查找

        // var title = local[id].title;
        // var content = local[id].content;
        $(".update-reflections-content").val($(".reflection-title").eq(index_update).text());//给模态框里面的内容文本框之前的内容
        $(".update-reflections-title").val($(".reflection-content").eq(index_update).text());//给主题文本框之前的内容
        database_id = $(this).attr("data-database-id");
        /**事件里面可以有事件吗？要写一个模态框的点击事件*/
        $(".save-update").on("click",function(){
            $('#myModal').modal('hide');//关闭模态框
            time = new Date();
            $.post("/reflection/update",{"id":database_id,"title":$(".update-reflections-title").val(),"reflection":$(".update-reflections-content").val()},function (json){
                if(json.state == 200){
                    console.log("修改成功");
                    getAndLoadReflectionsFromDbs()
                }
            })
            // local[id].time = time.toLocaleString();
            // local[id].title = $(".update-reflections-title").val();//将修改的内容保存
            // local[id].content = $(".update-reflections-content").val();
            // local[id].state += "-update";
            // savaReflections(local);//存储
            // loadReflections();//重新显示
        })
        //比较奇怪的 存储和重新显示放到这里的话 就不会执行 是因为事件的嵌套？
    })
    /**页面加载好时方法1： 从数据库中调取笔记的内容；*/
    function getAndLoadReflectionsFromDbs(){
        $.post("/reflection/find_all",
            function(json){
                if(json.state==200) {
                    // var local = [];
                    $.each(json.data, function (index, ele) {
                        // alert(ele.notes);
                        // local.push({fromDbsTime: ele.time, content: ele.reflection, title: ele.title, state: "fromdbs",time:""});
                        index = json.data.length-index-1;//数组翻转
                        $(".reflections").prepend("<div class='panel panel-default col-md-8'><div class='panel-heading'><p class='reflection-title'>"+ele.title+"</p><div class='btn-group' role='group' ><button type='button' class='btn btn-default btn-delete' id='"+index+"'>删除</button><button type='button' class='btn btn-default btn-update' data-toggle='modal' data-target='#myModal' data-id='"+index+"' data-database-id='"+ele.id+"'>修改</button></div></div><div class='panel-body'><p class='reflection-content'>"+ele.reflection+"</p></div></div>");
                    })
                    // savaReflections(local);
                    // loadReflections();
                }else if(json.state == 7002){
                    //米有笔记就什么都不做吧
                    // alert(json.message);
                }
            });
    }

    // /*读取本地存储的数据reflections*/
    // function getReflections(){
    //     var reflections = localStorage.getItem("reflections");
    //     if(reflections !== null){
    //         return JSON.parse(reflections); //本地存储的数据是字符串 需要转化成对象的格式
    //     }else{
    //         return [];
    //     }
    // }
    // /*保存笔记到本地*/
    // function savaReflections(reflections){
    //     localStorage.setItem("reflections",JSON.stringify(reflections));
    // }
    // /*渲染加载数据*/
    // function loadReflections(){
    //     // console.log("加载展示笔记内容");
    //     //读取本地存储的数据
    //     var reflections = getReflections();
    //     //先把所有笔记内容清空
    //     $(".reflections").empty();
    //     if(reflections !== null){
    //         //再显示所有笔记
    //         $.each(reflections,function(index,ele){
    //             console.log(ele);
    //             //判断这个元素的state属性，如果是new或者fromdbs就给他展示出来，否则就不展示；这样操作是方便最后将所有的这些数据传入数据库时 做不同的增删改查处理
    //             //值得注意的是，删除元素只是修改其属性 让他在这里不显示，但是他依然在locaLStorage里面，在显示元素的时候，显示元素的index值是会被不显示的元素所占位的，所以在删除的方法里 通过他的index值还是可以定位到所显示的元素的？
    //             // ele.state === "new" || ele.state === "fromdbs" ||ele.state.endsWith("update")
    //             if(!ele.state.endsWith("delete")){
    //                 /* 下面这行是创建1个完整的<div>笔记*/
    //                 $(".reflections").prepend("<div class='panel panel-default col-md-8'><div class='panel-heading'>"+ele.title+"<div class='btn-group' role='group' ><button type='button' class='btn btn-default btn-delete' id='"+index+"'>删除</button><button type='button' class='btn btn-default btn-update' data-toggle='modal' data-target='#myModal' data-id='"+index+"'>修改</button></div></div><div class='panel-body'><p>"+ele.content+"</p></div></div>");
    //             }else{
    //             }
    //
    //         })
    //     }
    // }

    window.onbeforeunload = function (){
        // updateLeaving();
        /**2.反思存储：把笔记数据全部从浏览器内存中拿给后端，后端处理好了哪些笔记是增加 哪些笔记是删除,那些笔记是修改
         * 2.1暂时前端没有做笔记修改的操作*/
        var local = getReflections();//从本地中获取数据
        $.each(local,function (index,ele){
            $.post("/reflection/create",
                {"time":ele.time,"title":ele.title,"reflection":ele.content,"state":ele.state,"fromDbsTime":ele.fromDbsTime},
                function(json){
                    if(json.state==200) {
                        // alert("退出成功");
                        console.log("笔记存储成功");
                    }
                });
        })
        localStorage.removeItem("reflections");
    }
    window.onpagehide = function (){
        console.log("onpagehide执行");
    }

})