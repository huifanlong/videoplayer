$(function(){
    var timeId;
    var starttime;
    var endtime;
    var time;
    var qid;
    var answer;
    var right_questionID = " ";
    var error_questionID = " ";
    var num_of_right;
    var count;
    var answer_list = new Array();

    var arr = location.search.substr(1).split('=');
    qid = arr[1];  //视频id
    loadUserInfo();  //加载右上角个人信息（通过ajax请求后端查看是否有session信息）
    time_fun(); //启动页面做题计时器（每次刷新时都重新计时）
    //展示题目
    loadQuestions();
    $("#button1").on("click",function (){  //提交按钮绑定点击事件，执行main函数
        main();
    })
    $("#button2").on("click",function (){  //返回课程页面按钮
        window.location.href = "../web/course_edutec.html";
    })
    updateBegin("quiz"+qid);//开始记录此页面登录时间
    window.onbeforeunload = function (){
        // updateLeaving();
    }
    window.onpagehide = function (){
        console.log("onpagehide执行")
    }
    function two_char(n) {
        return n >= 10 ? n : "0" + n;
    }

    function time_fun() {
        var sec=0;
        timeId = setInterval(function () {
            sec++;
            var date = new Date(0, 0)
            date.setSeconds(sec);
            var h = date.getHours(), m = date.getMinutes(), s = date.getSeconds();
            document.getElementById("mytime").innerText = two_char(h) + ":" + two_char(m) + ":" + two_char(s);
        }, 1000);

        var myDate = new Date();//获取系统当前时间
        starttime = myDate.toLocaleString( ); //获取日期与时间
        // console.log(starttime);
    }

    function loadQuestions(){
        $.get("/questions/get_quiz_questions",
            {"quizId":qid},
            function (data) {
                // console.log(data); //注意这里的data并不是传回来的数据，而是{state: 200, message: null, data: Array(4)}
                count = data.data.length; //题目数量
                $.each(data.data,function (index, value) {
                    answer_list.push(value.correctAnswer);//构建answer_list
                    var initial = '<div id=question_' + index + '><p>' + value.questionId + value.title + '<br></p>' + '<p><label for="optionA'+value.questionId+'"><input type="radio" name="Q'+value.questionId+'" id="optionA'+value.questionId+'" value="A">A.' + value.optionA + '<br></label><label for="optionB'+value.questionId+'"><input type="radio" name="Q'+value.questionId+'" id="optionB'+value.questionId+'" value="B">B.' + value.optionB + '<br></label>';
                    if(value.optionC != '' && value.optionD != ''){//四个选择
                        var ele = '<label for="optionC'+value.questionId+'"><input type="radio" name="Q'+value.questionId+'" id="optionC'+value.questionId+'" value="C">C.' + value.optionC + ' <br></label><label for="optionD'+value.questionId+'"><input type="radio" name="Q'+value.questionId+'" id="optionD'+value.questionId+'" value="D">D.' + value.optionD + ' <br></p></div>';
                    }else if(value.optionC != ''){//三个选项
                        var ele = '<label for="optionD'+value.questionId+'"><input type="radio" name="Q'+value.questionId+'" id="optionD'+value.questionId+'" value="D">D.' + value.optionD + ' <br></label></p></div>';
                    }else {//两个选项
                        var ele = '</p></div>';
                    }
                    $("#questions_list").append($(initial+ele));
                })
                // console.log(answer_list);
            });
    }

    //检查单选题的选项是否被选上，若选上则返回被选选项对应的value(A/B/C/D)，若为选上则返回10
    function Name(name) {
        var temp = document.getElementsByName(name);  //得到一个拥有相同name的元素数组。我们让每个题目的每个选择具有相同的name，如Q1表示四个选项都是第一题
        var intHot = 9;
        for(var i=0;i<temp.length;i++) {
            if(temp[i].checked)
                intHot = temp[i].value;
        }
        if (intHot==9) {
            return 10;
        }
        return intHot;
    };

    function main() {
        var myDate = new Date();//获取系统当前时间
        endtime = myDate.toLocaleString( ); //获取日期与时间
        // console.log(endtime);

        //-------------questionArray，看测验有几个问题--------------
        // var questionArray = new Array("Q1","Q2","Q3","Q4","Q5");
        var j;
        // count = "{$num}";   //num_mcq为选择题个数
        // console.log(count);
        var questionArray = new Array(count);
        for (var i = 0; i < count; i++) {
            j = i+1;
            questionArray[i] = "Q"+j;   //questionArray为Q1 Q2...
        }

        //检查单选题是否已作答，若已作答则将答案存到resultArray数组中
        var resultArray = new Array();
        for (var i = 0; i < questionArray.length; i++) {
            if (Name(questionArray[i])!=10) {
                resultArray[i] = Name(questionArray[i]); //resultArray[i]存的是用户所有单选题的答案
            }else{
                alert("第"+(i+1)+"题,您未作答!!");
                return false;
            }
        }

        //-------------aryAns，存储正确答案的数组--------------
        //aryAns[]是从后端返回的数组,当点击交卷的时候,向后端请求正确答案的数组,赋值给aryAns[]即可;
        // var aryAns = new Array("D","C","D","C","B");
        var aryAns = answer_list;
        // console.log(aryAns);
        var rightArray = new Array();
        //计算答对的题数；
        num_of_right = 0;
        for (var i = 0; i < questionArray.length; i++) {
            if (aryAns[i]==resultArray[i]) {
                num_of_right++;
                rightArray[i] = 1;
            }else{
                rightArray[i] = 0;
            }
        }
        // console.log(resultArray);
        answer = resultArray.join(",");
        // console.log(answer);

        //显示答对和答错的题号
        for (var i = 0; i < rightArray.length; i++) {
            if (rightArray[i] ==1 ) {
                right_questionID += i+1+",";
            }else{
                error_questionID += i+1+",";
            }
        }
        console.log(right_questionID);
        console.log(error_questionID);
        console.log(num_of_right);
        time=document.getElementById("mytime").innerHTML;
        document.getElementById("num_of_right").innerText = num_of_right;
        document.getElementById("time").innerText = time;
        // if (right_questionID!=" ") {
        //     document.getElementById("right_questionID").innerText = right_questionID;
        // }
        if (error_questionID!=" ") {
            document.getElementById("error_questionID").innerText = error_questionID;
        }
        // -------------------------修改正确答案-----------------------
        // document.getElementById("correct_answer").innerText = "C,C,B";
        document.getElementById("correct_answer").innerText = answer_list.join(",");

        postData();

        clearInterval(timeId);

        document.getElementById("answer_check").style.visibility='visible';
        $("#button1").attr('disabled',true);
        $("#button2").attr('disabled',false);
        $("#button3").attr('disabled',false);
    };

    function postData() {

        $.post("/quiz_record/create",
            {"vid": qid, "answer": answer, "rightQuestionID": right_questionID, "errorQuestionID": error_questionID, "startTime": starttime, "endTime": endtime, "usedTime": time,"score":Math.round(num_of_right/count*100),"numOfRight": num_of_right},
            function (json) {
                if(json.state!=200){
                    alert(json.message);
                }else{
                    console.log("成绩记录成功，vid="+vid);
                }
            });
    }

})
