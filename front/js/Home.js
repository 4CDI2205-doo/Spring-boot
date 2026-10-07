// ID取得
const closeButton = document.getElementById("closeButton");
const history = document.getElementById("history");

console.log(history);

// エラー時ポップアップ
function errorPopup(){
    popup.style.display = "flex";
    yesButton.style.display = "none";
    noButton.style.display = "none";
    closeButton.style.display = "flex";
    popupTitle.textContent = "会社情報の取得に失敗しました";
    popupMessage.innerHTML = "時間をおいて再度お試しください";
}

// 企業一覧表示フロントエンド処理
function companyList(companyList){
    companyList.forEach((d) => {
        const companyCard = document.createElement("div");
        const companyName = document.createElement("h1");
        const companyEmployeeCount = document.createElement("p");
        const companyStartingSalary = document.createElement("p");
        const companyAnnualHolidays = document.createElement("p");

        companyCard.classList.add("cardItem");

        companyName.textContent = d.companyName;
        companyEmployeeCount.textContent = `従業員数：${d.employeeCount}人`;
        companyStartingSalary.textContent = `初任給：${d.startingSalary}円`;
        companyAnnualHolidays.textContent = `年間休日：${d.annualHolidays}日`;

        companyCard.appendChild(companyName);
        companyCard.appendChild(companyEmployeeCount);
        companyCard.appendChild(companyStartingSalary);
        companyCard.appendChild(companyAnnualHolidays);

        history.appendChild(companyCard);
    })
}

closeButton.addEventListener("click",function(){
   popup.style.display = "none";
})

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
    if (data.length > 0){
        console.log("会社情報の取得に成功しました");
        console.log(data);
        companyList(data);
    }
})
.catch((error) => {
    errorPopup();
    console.error("Error:",error);
})
