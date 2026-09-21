fetch("http://127.0.0.1:8081/user/session-check", {
    method: "GET",
    credentials: "include"
})
.then((response) => {
    if (!response.ok) {
        window.location.href = "login.html";
        return null;
    }

    return response.json();
})
.then((data) => {

    if (data) {
        console.log("ログイン中のユーザーID:", data.userId);
    }
})
.catch((error) => {
    console.error("Error:", error);
    window.location.href = "login.html";
});



// ログアウトボタン
const logoutButton = document.getElementById("logoutButton");
logoutButton.addEventListener("click", function() {

    fetch("http://127.0.0.1:8081/user/logout", {
        method: "POST",
        credentials: "include"
    })
    .then((response) => {

        if (!response.ok) {
            throw new Error("ログアウトに失敗しました");
        }
        // ログアウト成功
        window.location.href = "login.html";
    })
    .catch((error) => {
        console.error("Error:", error);
    });
});
