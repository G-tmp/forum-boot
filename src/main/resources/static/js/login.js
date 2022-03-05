function refresh_kaptcha() {
    const path = "/kaptcha?p=" + Math.random();
    $("#kaptcha").attr("src", path);
}

function login() {
    var email = $("#email").val();
    var password = $("#password").val();
    var verifyCode = $("#verifyCode").val();
    var rememberMe = $("#rememberMe").is(":checked");

    var data = {
        email: email,
        password: password,
        verifyCode: verifyCode,
        rememberMe: rememberMe
    };

    $.ajax({
        type: "post",
        url: "/login",
        data: JSON.stringify(data),
        contentType: "application/json; charset=UTF-8",
        dataType: "json",
        success: function (result) {
            if (result['code'] === 0) {
                window.location.href = "http://localhost:8080/";
                sessionStorage.setItem('status','loggedIn');
                sessionStorage.setItem('messageCounter',0);
            } else {
                alert(result['msg']);
            }
        },
        error: function () {
            alert("error");
        }
    });
}