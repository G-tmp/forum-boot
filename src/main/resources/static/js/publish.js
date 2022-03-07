function publish_post() {
    var title = $("#title").val().trim();
    var content = $("#content").val();
    var boardId = $("#boardId").val();

    if (title == null || title === "") {
        alert("标题不能为空");
        return;
    }

    var data = {
        title: title,
        content: content,
        boardId: boardId
    };

    $.ajax({
        type: "post",
        url: "/post/insert",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result['code'] === 0) {
                location.reload();
            } else {
                alert(result['msg']);
            }
        },
        error: function (result) {
            alert("error")
        }
    });
}


function publish_reply() {
    var postid = $("#postid").val();
    var content = $("#reply_textarea").val().trim();

    if (content == null || content === "") {
        alert("内容不能为空");
        return;
    }

    var data = {
        postId: postid,
        content: content
    };
    // alert(JSON.stringify(reply));


    $.ajax({
        type: "POST",
        url: "/reply/insert",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result['code'] === 0) {
                location.reload();
            } else {
                alert(result['msg']);
            }
        },
        error: function (result) {
            alert("error");
        }
    });
}