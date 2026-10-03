function errorPopup(){
    popup.style.display = "flex";
    yesButton.style.display = "none";
    noButton.style.display = "none";
    closeButton.style.display = "flex";
    popupTitle.textContent = "会社情報の取得に失敗しました";
    popupMessage.innerHTML = "時間をおいて再度お試しください";
}

fetch ("http://127.0.0.1:8081/company/list",{
    method: "GET",
    credentials: "include"
})
.then((response) => {
    if(!response.ok){
        throw new Error("会社情報の取得に失敗しました");
    }
    return response.json();
})
.then((data) => {
    if (data){
        console.log("会社情報の取得に成功しました");
        console.log(data);
    }
})
.catch((error) => {
    errorPopup();
    console.error("Error:",error);
})
