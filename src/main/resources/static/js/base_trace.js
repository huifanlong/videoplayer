/* 发送进入页面的时间  参数是index_trace、reflection_trace...*/
function updateBegin(fromWhere){
    // var begin = new Date();
    $.post("/trace/create",
        {"where":fromWhere},
        function(json){
            if(json.state==200) {
                console.log("进入页面记录成功");
            }else{
                console.log("进入页面记录出错");
            }
        })
}
/* 发送进入页面的时间  */
// function updateLeaving(){
//     var end = new Date();
//     $.post("/trace/leaving",
//         {"time":end.toLocaleString()+" "},
//         function(json){
//             if(json.state==200) {
//                 console.log("退出页面记录成功");
//             }
//         })
// }
/* 初始化右上角登录显示 */
function loadUserInfo(){
    $.get("/users/find_by_id",
        function(json){
            if(json.state==200) {
                // alert(json.data.userName);
                $(".login-no").prop("style","display:none");
                $(".login-span-yes").html("你好！"+json.data.name+"&nbsp;&nbsp");
                // alert("首页初始化成功");
                console.log("已登录");
            }else {
                $(".login-yes").prop("style","display:none");
                console.log("未登录");
            }
    })
}