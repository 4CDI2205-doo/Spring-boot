const head_script = `
<header>
    <div class="logo">
        <a href="Home.html">楽々ES</a>
    </div>
    <nav>
        <ul>
            <li><a href="register.html">登録</a></li>
            <li><a href="list.html">表示</a></li>
            <li><a href="edit.html">編集</a></li>
            <button id="logoutButton" class="logout-button">ログアウト</button>
        </ul>
    </nav>
</header>
`;

document.getElementById("headerplaceholder").innerHTML = head_script;