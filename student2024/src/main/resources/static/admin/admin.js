$(() => {
    //给左侧导航菜单添加事件
    $("aside>.nav>li>a").click(function (e) {
        e.preventDefault();//阻止超链接的默认点击事件

        let id = $(this).attr("id");//唯一编号
        let title = $(this).text();//标题
        let url = $(this).attr("href");//地址

        layui.use(() => {
            let element = layui.element;
            //新建选项卡
            if (url !== "#") {
                let $li = $(".layui-tab-title>[lay-id=" + id + "]");
                if ($li.length > 0) {//如果已存在对应id的选项卡
                    element.tabChange('tabs', id);//切换到指定id的选项卡
                } else {//如果没有，则新建选项卡
                    element.tabAdd('tabs', {
                        id,
                        title,
                        content: "<iframe src='" + url + "'></iframe>",
                        change: true //是否添加完毕后即自动切换
                    });
                }
            }
        });
    });

    //注销按钮事件
    $("#logout-btn").click(e => {
        e.preventDefault();
        location.href = "/admin/logout";
    });
});