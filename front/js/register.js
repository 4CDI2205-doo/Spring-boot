// ボタンのID取得
const closeButton = document.getElementById("closeButton");
const noButton = document.getElementById("noButton");
const yesButton = document.getElementById("yesButton");

//入力エラー時のポップアップ表示関数
function form_errorPopup(errorList){
    popup.style.display = "flex";
    yesButton.style.display = "none";
    noButton.style.display = "none";
    closeButton.style.display = "flex";
    popupTitle.textContent = "エラーメッセージ";
    popupMessage.innerHTML = errorList.join("<br>");
}

// 送信成功時ポップアップ表示
function successPopup(){
    popup.style.display = "flex";
    yesButton.style.display = "none";
    noButton.style.display = "none";
    closeButton.style.display = "flex";
    popupTitle.textContent = "登録完了";
    popupMessage.innerHTML = "入力された情報を登録しました。";
}

// 送信時エラー時
function errorPopup(){
    popup.style.display = "flex";
    yesButton.style.display = "none";
    noButton.style.display = "none";
    closeButton.style.display = "flex";
    popupTitle.textContent = "登録に失敗しました";
    popupMessage.innerHTML = "時間をおいて再度お試しください";
}

// 登録確認ポップアップ
function formConfirm(formdata){
    popup.style.display = "flex";
    yesButton.style.display = "flex";
    noButton.style.display = "flex";
    closeButton.style.display = "none";
    popupTitle.textContent = "登録確認";
    popupMessage.innerHTML = 
    `企業名：${formdata.companyName}<br>` +
    `従業員数:${formdata.employeeCount}<br>人` +
    `初任給：${formdata.startingSalary}<br>円` +
    `年間休日：${formdata.annualHolidays}<br>日`
    ;

    // はいボタン
    yesButton.onclick = () => {
        console.log(formdata);
        fetch ("http://127.0.0.1:8081/company/register", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify(formdata)     
        })
        .then((response) => {
            if(!response.ok){
                return response.json();
            }
            successPopup();
            // window.location.href = "login.html";
            return null;
        })
        .then((data) => {
            if (data){
                const errorList = Object.values(data);
                form_errorPopup(errorList);
                // 確認ログ
                console.log(errorList);
                console.log(data);
            }
        })
        .catch((error) => {
            // サーバーエラー時
            errorPopup();
            console.error("Error:", error);
        });
    }
}

// 閉じるボタン
closeButton.addEventListener("click",function(){
    popup.style.display = "none";
});

// いいえボタン
noButton.addEventListener("click",function(){
    popup.style.display = "none";
});

// 新規登録ユーザー
document.getElementById("register_form").addEventListener("submit",function(event){
    event.preventDefault();

    //ユーザーフォームの値を取得　
    const company_Name = document.getElementById("company_name").value;
    const company_EmployeeCounter = document.getElementById("employee_counter").value;
    const company_StartingSalary = document.getElementById("starting_salary").value;
    const company_AnnualHolidays = document.getElementById("annual_holidays").value;

    // オブジェクトに変換
    const newregister_formdata = {
        companyName: company_Name,
        employeeCount: Number(company_EmployeeCounter),
        startingSalary: Number(company_StartingSalary),
        annualHolidays: Number(company_AnnualHolidays),
    };

    // 登録確認画面表示
    formConfirm(newregister_formdata);
});