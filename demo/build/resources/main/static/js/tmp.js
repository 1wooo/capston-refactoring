var Target = document.getElementById("clock");
function clock() {
    // 비교시간
    /*<![CDATA[*/
    var now = new Date();

    // 현재시간
    var year = now.getFullYear();     // 연도
    var month = now.getMonth()+1;     // 월(+1해줘야됨)
    var day = now.getDate();          // 일
    var hours = now.getHours();       // 현재 시간
    var minutes = now.getMinutes();   // 현재 분

    var sttDt = [[${enteringTime}]];

    var nowday = sttDt.split("-");
    var tmp = nowday[2].split(" ");
    var realtime = tmp[1].split(":");

    var sttYear = nowday[0];
    var sttMonth = nowday[1];
    var sttDay = tmp[0];
    var sttHours = realtime[0];
    var sttMinutes = realtime[1];

    var date1 = new Date(year, month, day, hours, minutes);                    // 현재
    var date2 = new Date(sttYear, sttMonth, sttDay, sttHours, sttMinutes);     // 파라미터
    var elapsedMSec = date2.getTime() - date1.getTime();
    var elapsedMin = elapsedMSec / 1000 / 60;

    var outputHours = 0
    if (elapsedMin >= 60) {
        outputHours = parseInt(parseInt(elapsedMin)/60);
        elapsedMin = parseInt(elapsedMin)%60;
    }

    elapsedMin = parseInt(elapsedMin);    // 정수 표기를 위해 parseInt

    /*]]>*/
    Target.innerText = `${outputHours < 10 ? `0${outputHours}` : outputHours}시간
     ${elapsedMin < 10 ? `0${elapsedMin}` : elapsedMin}'
                + '분 소요되었습니다.`;
}
clock();
setInterval(clock, 1000); // 1초마다 실행