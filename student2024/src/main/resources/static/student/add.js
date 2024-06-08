$(() => {
    layui.use(() => {
        let laydate = layui.laydate;
        laydate.render({
            elem: "#birthday"
        });
    });
});

//保存学生
function save(cb) {

    let studentId=$("#studentId").val();
    let name = $("#name").val();
    let sex = $(":radio[name=sex]:checked").val();
    let college = $("#college").val();
    let major = $("#major").val();

    //向后台发出ajax请求，微服务
    let url = "/api/v1/student";
    $.ajax({
        url,
        method: "post",
        dataType: "json",
        data: {
            studentId, name,sex,college,major
        },
        success(resp) {
            if (resp.success) {//如果后台保存数据成功
                if (cb) {
                    cb();//调用回调函数
                }
            }
        }
    });
}