// ボタンのID取得
const closeButton = document.getElementById("closeButton");
const noButton = document.getElementById("noButton");
const yesButton = document.getElementById("yesButton");

//ログインエラーポップアップ
function form_errorPopup(errorList){
    popup.style.display = "flex";
    yesButton.style.display = "none";
    noButton.style.display = "none";
    closeButton.style.display = "flex";
    popupTitle.textContent = "ログインに失敗しました";
    popupMessage.innerHTML = "emailまたはパスワードが異なります";
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

closeButton.addEventListener("click",function(){
    popup.style.display = "none";
})

// ログインフォーム情報
document.getElementById("login_form").addEventListener("submit",function(event){
    event.preventDefault();

    // ログインフォームの値を取得
    const login_Email = document.getElementById("user_email").value;
    const login_Password = document.getElementById("user_pass").value;

    const login_formdata = {
        email: login_Email,
        password: login_Password
    }

    fetch ("http://localhost:8081/user/login", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify(login_formdata)
    })
    .then((response) => {
            if(!response.ok){
                return response.json();
            }
            // successPopup();
            window.location.href = "Home.html";
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
)