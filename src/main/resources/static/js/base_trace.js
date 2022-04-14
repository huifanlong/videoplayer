/* 发送进入页面的时间  参数是index_trace、reflection_trace...*/
function updateBegin(fromWhere){
    var begin = new Date();
    $.ajax({type: "POST", url: "/trace/update", data:"trace="+fromWhere+" "+begin.toLocaleString()+" ",dataType:"JSON", async: true,
        error: function (request) {
            alert("轨迹error");
        },
        success: function (json) {
            if(json.state==200) {
                alert("进入页面记录成功");
            }
            else{
                alert("进入页面记录出错");
            }
        }
    })
}
/* 发送进入页面的时间  */
function updateLeaving(){
    var end = new Date();
    $.ajax({type: "POST", url: "/trace/leaving", data:"time= "+end.toLocaleString()+" ",dataType:"JSON", async: true,
        error: function (request) {
            alert("轨迹error");
        },
        success: function (json) {
            if(json.state==200) {
                alert("退出页面记录成功");
            }
            else{
                alert("退出页面记录出错");
            }
        }
    })
}