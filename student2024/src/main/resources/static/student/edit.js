$(() => {
    layui.use(() => {
        let laydate = layui.laydate;
        laydate.render({
            elem: "#birthday"
        });
    });

    if (error) {//如果error不为空
        layui.use(() => {
            layer.msg("您要修改的学生不存在");
        });
    }

    //让性别选中
    if (stu) {
        $(":radio[name=sex][value=" + stu.sex + "]").prop("checked", true);
    }
});

//修改学生
function edit(cb) {
    let id = stu.id;//主键
    let studentId=$("#studentId").val();
    let name = $("#name").val();
    let sex = $(":radio[name=sex]:checked").val();
    let college = $("#college").val();
    let major = $("#major").val();

    //向后台发出ajax请求，微服务
    let url = "/api/v1/student";
    $.ajax({
        url,
        method: "patch",//部分修改
        dataType: "json",
        data: {
            id,studentId, name,sex,college,major
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