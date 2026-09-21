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
    `名前：${formdata.name}<br>` +
    `Email:${formdata.email}<br>` +
    `学校：${formdata.school}<br>` +
    `学部：${formdata.faculty}<br>` +
    `学科：${formdata.department}<br>` +
    `年齢：${formdata.age}<br>` +
    `生年月日：${formdata.date}<br>`;

    // はいボタン
    yesButton.onclick = () => {
        console.log(formdata);
        fetch ("http://localhost:8081/user/newlogin", {
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
            // successPopup();
            window.location.href = "login.html";
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
document.getElementById("newlogin_form").addEventListener("submit",function(event){
    event.preventDefault();

    //ユーザーフォームの値を取得　
    const user_Name = document.getElementById("user_name").value;
    const user_Email = document.getElementById("user_email").value;
    const user_Pass = document.getElementById("user_pass").value;
    const user_School = document.getElementById("user_school").value;
    const user_Faculty = document.getElementById("user_faculty").value;
    const user_Department = document.getElementById("user_department").value;
    const user_Age = document.getElementById("user_age").value;
    const user_Date = document.getElementById("user_date").value;

    // オブジェクトに変換
    const newlogin_formdata = {
        name: user_Name,
        email: user_Email,
        password: user_Pass,
        school: user_School,
        faculty: user_Faculty,
        department: user_Department,
        age: user_Age === "" ? null : parseInt(user_Age,10),
        date: user_Date === "" ? null : user_Date,
    };

    // errorList = [];
    // 入力確認
    // if (!user_Name.trim()){
    //     errorList.push("名前を入力してください");
    // }
    // if (!user_Email.trim()){
    //     errorList.push("Emailを入力してください");
    // }
    // if (!user_Pass.trim()){
    //     errorList.push("パスワードを入力してください");
    // }
    // if (!user_School.trim()){
    //     errorList.push("所属学校を入力してください");
    // }
    // if (!user_Faculty.trim()){
    //     errorList.push("所属学部を入力して下さい");
    // }
    // if (!user_Department.trim()){
    //     errorList.push("学科を入力してください");
    // }
    // if (user_Age.trim() === '' || user_Age.trim() === null || user_Age.trim() === undefined || user_Age.trim() < 0){
    //     errorList.push("年齢を入力してください");
    // }
    // if (!user_Date.trim()){
    //     errorList.push("生年月日を入力してください");
    // }
    // if (errorList.length > 0){
    //     form_errorPopup(errorList)
    //     return;
    // }
    // 登録確認画面表示
    formConfirm(newlogin_formdata);
});