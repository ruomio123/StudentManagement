$(() => {
    $("#login-btn").click(() => {
        let username = $("#username").val();//用户名
        let password = $("#password").val();//密码
        let captcha = $("#captcha").val();//验证码

        //前端校验
        layui.use(() => {
            let layer = layui.layer;
            if (username === "") {
                layer.msg("用户名不可为空");
                return false;
            }

            if (password === "") {
                layer.msg("密码不可为空");
                return false;
            }

            if (captcha === "") {
                layer.msg("验证码不可为空");
                return false;
            }

            //对用户名进行正则表达式校验
            let pattern = /^\w{6,12}$/;
            if (!username.match(pattern)) {
                layer.msg("用户名必须是6到12位之间，且为合法的字符");
                return false;
            }

            //认证函数
            login(username, password, captcha);

        });
    });

    //刷新验证码
    $(".captcha").click(function () {
        $(this).attr("src", "/admin/captcha?seed=" + new Date().getTime());
    });
});

//认证函数
function login(username, password, captcha) {
    let url = "/api/v1/users/login";
    $.ajax({
        url,
        method: "get",
        dataType: "json",
        data: {
            username, password, captcha
        },
        success(resp) {
            if (resp.success) {
                location.href = "/admin";
            } else {
                layui.use(() => {
                    let layer = layui.layer;
                    layer.alert(resp.error);
                });
            }
        }
    });
}