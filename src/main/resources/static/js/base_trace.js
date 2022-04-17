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