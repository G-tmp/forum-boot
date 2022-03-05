$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");

    // 获取标题和内容
    let title = $("#recipient-name").val();
    let content = $("#message-text").val();
    // 发送异步请求
    data = {
        "title": title,
        "content": content
    };
    $.ajax({
        type: "post",
        url: "/post/insert",
        data: JSON.stringify(data),
        contentType: "application/json; charset=UTF-8",
        dataType: "json",
        success: function (result) {
            if (result['code'] === 0) {
                window.location.reload();
            } else {
                alert(result['msg']);
            }
        },
        error: function () {
            alert("error");
        }
    });

    // $.post(
    //     "/post/insert",
    //     {"title": title, "content": content},
    //     function (result) {
    //         result = $.parseJSON(result);
    //         console.log(result);
    //         // 在提示框中显示返回的信息
    //         $("#hintBody").text(result.msg);
    //         // 显示提示框
    //         $("#hintModal").modal("show");
    //         // 2 秒后自动隐藏
    //         setTimeout(function () {
    //             $("#hintModal").modal("hide");
    //             // 发布成功，刷新页面显示
    //             if (result.code === 0) {
    //                 window.location.reload();
    //             }
    //         }, 2000);
    //     }
    // );
}