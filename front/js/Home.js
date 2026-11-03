// ID取得
const closeButton = document.getElementById("closeButton");
const history = document.getElementById("history");
const searchHistory = document.getElementById("searchHistory");
const searchForm = document.getElementById("companyFormSearch");
const companyListMain = document.getElementById("companyListMain");

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
// 検索結果表示フロントエンド処理
function searchCompanyList(searchResults,place){
    searchResults.forEach((d) => {
        const companyCard = document.createElement("div");
        const companyName = document.createElement("h1");
        const companycorporateNumber = document.createElement("p");
        const companyLocation = document.createElement("p");
        const companyEmployeeCount = document.createElement("p");

        companyCard.classList.add("cardItem");

        companyName.textContent = d.name;
        companycorporateNumber.textContent = `企業番号：${d.corporate_number}`;
        companyLocation.textContent = `本社：${d.location}`;
        if (d.employee_number == null){
            companyEmployeeCount.textContent = `従業員数：非公開`
        }else{
            companyEmployeeCount.textContent = `従業員数：${d.employee_number}`;
        }

        companyCard.appendChild(companyName);
        companyCard.appendChild(companycorporateNumber);
        companyCard.appendChild(companyLocation);
        companyCard.appendChild(companyEmployeeCount);

        place.appendChild(companyCard);
    })
}

// 企業一覧表示フロントエンド処理
function companyList(companyList,place){
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

        place.appendChild(companyCard);
    })
}

closeButton.addEventListener("click",function(){
   popup.style.display = "none";
})


// 企業検索フォーム処理
searchForm.addEventListener("submit",function(event){
    event.preventDefault()
    const searchCompanyName = document.getElementById("companySearch").value;

    // オブジェクト変換
    const formSearchValue = {companyName:searchCompanyName}; 

    fetch ("http://127.0.0.1:8081/company/search",{
        method: "POST",
        headers: {
            "Content-type":"application/json",
        },
        credentials: "include",
        body: JSON.stringify(formSearchValue) 
    })
    .then((response) => {
        if (!response.ok){
            throw new Error("検索企業の受信が出来ませんでした。");
        }
        return response.json();
    })
    .then((data) => {
        console.log("検索企業の取得に成功しました。");
        companyListMain.style.display = "flex";
        searchHistory.replaceChildren();
        searchCompanyList(data,searchHistory);
    })
    .catch((error) => {
        errorPopup();
        console.log("Error:",error);
    })

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
        companyList(data,history);
    }
})
.catch((error) => {
    errorPopup();
    console.error("Error:",error);
})

