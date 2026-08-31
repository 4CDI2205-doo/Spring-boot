document.getElementById("newlogin_form").addEventListener("submit",() => {
    event.preventDefault();
    const user_Name = document.getElementById("user_name").value;
    const user_Email = document.getElementById("user_email").value;
    const user_Pass = document.getElementById("user_pass").value;
    const user_School = document.getElementById("user_school").value;
    const user_Faculty = document.getElementById("user_faculty").value;
    const user_Department = document.getElementById("user_department").value;
    const user_Age = document.getElementById("user_age").value;
    const user_Date = document.getElementById("user_date").value;

    const newlogin_formdata = {
        name: user_Name,
        email: user_Email,
        pass: user_Pass,
        school: user_School,
        faculty: user_Faculty,
        department: user_Department,
        age: parseInt(user_Age,10),
        date: user_Date,
    };

    fetch ("http://localhost:8080/api/entry", {
        method: "POST",
        headers: {
            "Content-type": "application/json",
        },
        body: JSON.stringify(newlogin_formdata)     
    })
})