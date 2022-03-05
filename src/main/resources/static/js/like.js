function like(btn, entityType, entityId, toUserId) {
    data = {
        "entityType": entityType,  // 1
        "entityId": entityId,
        "toUserId": toUserId
    };

    $.ajax({
        url: "/like",
        data: JSON.stringify(data),
        type: "post",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        success: function (result) {
            if (result.code === 0) {
                $(btn).children("i").text(result.likeCount);
                $(btn).children("b").text(result.isLiked ? '已赞' : '赞');
            } else {
                alert(result.msg);
            }
        },
        error: function (err) {
            alert(JSON.stringify(err));
        }
    });

    // $.post(
    //     "/like",
    //     data,
    //     function (data) {
    //         data = $.parseJSON(data);
    //         if (data.code === 0) {
    //             $(btn).children("i").text(data.likeCount);
    //             $(btn).children("b").text(data.ifLike ? '已赞' : '赞');
    //         } else {
    //             alert(data.msg);
    //         }
    //     }
    // )
}