function register() {
    var username = $("#username").val();
    var email = $("#email").val();
    var password = $("#password").val();
    var password2 = $("#password2").val();

    if (password == null || password == "") {
        alert("密码不能为空");
        return;
    }

    if (hasBlank(password)) {
        alert("密码不能含有空格");
        return;
    }

    if (password !== password2) {
        alert("两次密码不一致");
        return;
    }

    var user = {
        username: username,
        email: email,
        password: password
    };

    // alert(JSON.stringify(user));

    $.ajax({
        data: JSON.stringify(user),
        type: "post",
        dataType: "json",
        url: "/register",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result['code'] === 0){
                window.location.href = location.protocol + '//' + location.host;
            }else {
                alert(result['msg']);
            }
        },
        error: function () {
            alert("error")
        }
    });
}


function disableButton() {
    // alert(flagNick);
    alert("email : " + flagEmail + "\n" + "nick : " + flagNick);
    // if (flagNick){
    //     $("#confirmButton").removeAttr("disabled");
    // }else{
    //     $("#confirmButton").attr("disabled","disabled");
    // }

    if (flagNick && flagEmail)
        $("#confirmButton").removeAttr("disabled");
}


// 判断字符串是否有空格
function hasBlank(str) {
    // 無
    if (str.indexOf(" ") == -1) {
        return false;
    } else {
        return true;
    }
}

