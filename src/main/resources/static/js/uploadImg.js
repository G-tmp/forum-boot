// $('input[type="file"]').change(function(e){
//     var fileName = e.target.files[0].name;
//     // var filenames = $(e.target).siblings('.custom-file-label').html(fileName);
//     alert(fileName);
//     $('.custom-file-label').html(fileName);
// });

$('#selectedFile').on('change',function(){
    //get the file name
    var fileName = $(this).val();
    alert(fileName);
    //replace the "Choose a file" label
    $(this).next('.custom-file-label').html(fileName);
});

function uploadImg() {

    if (!$("#selectedFile").val()){
        alert("image null");
        return;
    }

    // var formData = new FormData($("#uploadImgForm")[0]);   //创建一个forData

    var formData = new FormData();
    var img = $("#selectedFile")[0].files[0];
    formData.append("img",img);

    // alert(JSON.stringify(formData));

    $.ajax({
        type : "POST",
        url : "/user/upload",
        data : formData,
        dataType : "json",
        encrypt : "multipart/form-data",
        contentType : false,
        processData : false,
        cache : false,
        success : function (result) {

            if (result['success'] == "success"){
                alert("success");
                $("#avatar").attr("src",result.path);
                console.log(result.path);
            }else {
                alert("failed");
            }
        },
        error : function (err) {
            alert(JSON.stringify(err))
        }

    });

}
