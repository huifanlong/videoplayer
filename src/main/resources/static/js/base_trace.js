/* 发送进入页面的时间  参数是index_trace、reflection_trace...*/
function updateBegin(fromWhere){
    var begin = new Date();
    $.ajax({type: "POST", url: "/trace/update", data:"trace="+fromWhere+" "+begin.toLocaleString()+" ",dataType:"JSON", async: true,
        error: function (request) {
            console.log("轨迹error");
        },
        success: function (json) {
            if(json.state==200) {
                console.log("进入页面记录成功");
            }
            else{
                console.log("进入页面记录出错");
            }
        }
    })
}
/* 发送进入页面的时间  */
function updateLeaving(){
    var end = new Date();
    $.ajax({type: "POST", url: "/trace/leaving", data:"time= "+end.toLocaleString()+" ",dataType:"JSON", async: true,
        error: function (request) {
            console.log("轨迹error");
        },
        success: function (json) {
            if(json.state==200) {
                console.log("退出页面记录成功");
            }
            else{
                console.log("退出页面记录出错");
            }
        }
    })
}
/* 初始化右上角登录显示 */
function loadUserInfo(){
    $.ajax({type: "GET", url: "/users/find_by_id", dataType:"JSON", async: true,
        error: function (request) {
            //为啥没登陆直接跳转到这个页面会是error？
            $(".login-yes").prop("style","display:none");
        },
        success: function (json) {
            if(json.state==200) {
                // alert(json.data.userName);
                $(".login-no").prop("style","display:none");
                $(".login-span-yes").html("你好！"+json.data.userName+"&nbsp;&nbsp");
                // alert("首页初始化成功");
            }else {
                $(".login-yes").prop("style","display:none");
            }
        }
    })
}