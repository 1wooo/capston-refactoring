function selectAll(selectAll)  {
  const checkboxes
     = document.querySelectorAll("input[name='reportChkRow']");

  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked
  })
}
function boardDelete(){
    //체크박스 체크된 항목
    const query = 'input[name="reportChkRow"]:checked'
    const selectedElements = document.querySelectorAll(query)

    //체크박스 체크된 항목의 개수
    const selectedElementsCnt = selectedElements.length;

    if(selectedElementsCnt == 0){
        alert("삭제할 항목을 선택해주세요.");
        return false;
    }

    else{
        if (confirm("정말로 삭제하시겠습니까?")) {
            //배열생성
            const arr = new Array(selectedElementsCnt);

            document.querySelectorAll('input[name="reportChkRow"]:checked').forEach(function(v, i) {
                arr[i] = v.value;
            });

            const form = document.createElement('form');
            form.setAttribute('method', 'post');        //Post 메소드 적용
            form.setAttribute('action', '/carRemove');

            var input1 = document.createElement('input');
            input1.setAttribute("type", "hidden");
            input1.setAttribute("name", "illegalCarNumberTableID");
            input1.setAttribute("value", arr);
            form.appendChild(input1);
            console.log(form);
            document.body.appendChild(form);
            form.submit();
        }
    }
}
