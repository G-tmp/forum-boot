// $(function () {
//     $(".follow-btn").click(follow);
// });

function follow(btn, entityType, entityId) {

    count = parseInt($("#followerCount").text());
    // var btn = $("#followBtn");
    data = {
        entityType: entityType, // user
        entityId: entityId
    };

    $.ajax({
        url: "/follow",
        data: JSON.stringify(data),
        type: "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 0) {
                if (result.isFollowed) {
                    $(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
                    $("#followerCount").text(count + 1);
                }else {
                    $(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");
                    $("#followerCount").text(count - 1);
                }
            } else {
                alert(result.msg);
            }
        },
        error: function (err) {
            alert(JSON.stringify(err));
        }
    });

}