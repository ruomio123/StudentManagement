$(() => {
    search();//查询数据

    //给分页条添加事件，事件委托和事件冒泡
    $(".paginate>ul").on("click", "li>a", function () {
        let pageNo = $(".paginate").data("pageNo");
        pageNo = parseInt(pageNo);//当前页
        let pages = $(".paginate").data("pages");
        pages = parseInt(pages);//总页数

        let $li = $(this).parent();
        if ($li.is(".first")) {
            search();
        } else if ($li.is(".last")) {//尾页
            search(pages);
        } else if ($li.is(".prev")) {//上一页
            pageNo--;
            if (pageNo < 1) {
                pageNo = 1;
            }
            search(pageNo);
        } else if ($li.is(".next")) {//下一页
            pageNo++;
            if (pageNo > pages) {
                pageNo = pages;
            }
            search(pageNo);
        } else {//数字页码
            let pageNo = parseInt($(this).text());
            search(pageNo);
        }
    });

    //给查询按钮添加事件
    $(".search #search-btn").click(() => search());

    //表格全选与取消全选功能
    $("#check-all").click(function () {
        let checked = $(this).prop("checked");//获取选中状态
        $("#tbl tr>td:first-child>:checkbox").prop("checked", checked);
    });

    //添加行选中功能，事件委托
    $("#tbl>tbody").on("click", ">tr", function () {
        //选中行的复选框
        let $checkbox = $(this).find(">td:first-child>:checkbox");
        //获取原状态
        let checked = $checkbox.prop("checked");
        //设置新状态
        $checkbox.prop("checked", !checked);
    });

    //给每一行的复选框添加事件，避免无法通过复选框选中。事件委托
    $("#tbl>tbody").on("click", "tr>td:first-child>:checkbox", function (e) {
        e.stopPropagation();//停止向上冒泡
    });

    //给删除按钮添加事件
    $(".action #del-btn").click(() => {
        //集合
        let $allChecked = $("#tbl tr>td:first-child>:checked");//获取所有选中的复选框
        let ids = [];//存储所有选中的id
        //对集合进行遍历迭代
        $allChecked.each(function () {
            let id = parseInt($(this).val());
            ids.push(id);
        });

        //使用弹窗插件
        layui.use(() => {
            let layer = layui.layer;

            if (ids.length === 0) {//如果用户未选中
                layer.msg("请选中您要删除的行");//信息提示
            } else {
                layer.confirm("是否确认删除所有选中的行?", function () {
                    //向后台发出请求，执行删除操作
                    deleteByIds(ids);
                });
            }
        });
    });

    //添加按钮事件
    $(".action #add-btn").click(() => {
        //location.href = "/student/add";

        //弹窗
        layui.use(() => {
            //弹出一个新窗口
            layer.open({
                type: 2,//表示iframe
                title: '新增学生',
                shade: 0.6,//模态窗口背景透明度
                area: ['500px', '570px'],
                content: '/student/add', // iframe 的 url
                btn: ["确定", "取消"],
                yes(index, layero) {//点击确定时调用的函数
                    //取得内嵌子窗口的window对象
                    let win = layero.find("iframe")[0].contentWindow;
                    //调用子窗口的save函数
                    win.save(function () {
                        layer.msg("新增学生操作成功");
                        layer.close(index);//关闭窗口
                        search();
                    });
                }
            });
        });
    });

    //查询按钮事件
    $(".action #query-btn").click(() => search());

    /*行操作按钮，修改和删除*/
    $("#tbl>tbody").on("click", "tr > td:last-child button", function (e) {
        e.stopPropagation();
        let $checkbox = $(this).closest("tr").find(">td:first-child>:checkbox");
        let id = $checkbox.val();

        if ($(this).is(".edit")) {
            $("#tbl tr>td:first-child>:checkbox").prop("checked", false);
            $checkbox.prop("checked", true);
            //触发click事件
            $(".action #edit-btn").click();
        } else if ($(this).is(".del")) {
            layui.use(() => {
                let layer = layui.layer;
                layer.confirm("是否确认删除选中的行?", () => deleteByIds([id]));
            });
        }
    });

//修改按钮事件
    $(".action #edit-btn").click(() => {
        //集合
        let $allChecked = $("#tbl tr>td:first-child>:checked");//获取所有选中的复选框
        layui.use(() => {
            let layer = layui.layer;
            if ($allChecked.length === 0) {//用户未选择
                layer.msg("请选中您要修改的记录");
            } else if ($allChecked.length > 1) {//用户选中多行
                layer.msg("您一次只能修改一行记录");
            } else {//只选中一行
                let id = $allChecked.first().val();//取出选中行的主键
                //location.href = "/student/edit?id=" + id;
                //弹窗
                layui.use(() => {
                    //弹出一个新窗口
                    layer.open({
                        type: 2,//表示iframe
                        title: '修改',
                        shade: 0.6,//模态窗口背景透明度
                        area: ['500px', '570px'],
                        content: '/student/edit?id=' + id, // iframe 的 url
                        btn: ["确定", "取消"],
                        yes(index, layero) {//点击确定时调用的函数
                            //取得内嵌子窗口的window对象
                            let win = layero.find("iframe")[0].contentWindow;
                            //调用子窗口的save函数
                            win.edit(function () {
                                layer.msg("修改学生操作成功");
                                layer.close(index);//关闭窗口
                                search();
                            });
                        }
                    });
                });
            }
        });
    });
});

//查询所有学生
function search(pageNo = 1, pageSize = 15) {
    //取出查询条件
    let id = $(".search #id").val();
    let name = $(".search #name").val();
    let studentId=$(".search #studentId").val();
    let sex = $(".search #sex").val();
    let college = $(".search #college").val();

    //使用ajax向后台微服务发出请求
    let url = "/api/v1/student";
    $.ajax({
        url,
        method: "get",
        dataType: "json",
        data: {//向后台提交的参数
            pageNo,
            pageSize,
            id,
            name,
            sex,
            studentId,
            college,
        },
        success(resp) {//回调函数，resp即后台studentApi响应的json对象
            //将后台响应的数据展示到页面上，dom操作
            if (resp.success) {
                let data = resp.data;//员工数据数组，遍历，迭代
                let $tbody = $("#tbl>tbody");

                $tbody.empty();//清空内容
                data.forEach(stu => {
                    let $tr = $("<tr>");//创建一个tr对象

                    /*添加一列复选框*/
                    $tr.append("<td><input type='checkbox' value='" + stu.id + "'></td>");

                    $tr.append("<td>" + stu.id + "</td>");
                    $tr.append("<td>" + stu.studentId + "</td>");
                    $tr.append("<td>" + stu.name + "</td>");
                    $tr.append("<td>" + stu.sex + "</td>");
                    $tr.append("<td>" + stu.college + "</td>");
                    $tr.append("<td>" + stu.major + "</td>");
                    $tr.append("<td><div><button class='edit'>修改</button><button class='del'>删除</button></div></td>");

                    $tbody.append($tr);
                });

                //将分页数据缓存起来
                let pi = resp.pi;
                $(".paginate").data("pageNo", pi.pageNum);//当前页
                $(".paginate").data("pages", pi.pages);//总页数

                //重置导航页码
                $(".paginate>ul>li:not(.fixed)").remove();//移除非固定的li
                for (let i = pi.navigateFirstPage; i <= pi.navigateLastPage; i++) {
                    let $li = $("<li><a href='javascript:void(0)'>" + i + "</a></li>");
                    $(".paginate>ul>li.next").before($li);
                    if (i === pi.pageNum) {//当前页
                        $li.addClass("active");
                    }
                }

            }
        }
    });
}

//批量删除，参数为id数组
function deleteByIds(ids) {
    //向后台发出一个请求
    let url = "/api/v1/student";
    $.ajax({
        url,
        method: "delete",
        dataType: "json",
        data: {
            ids
        },
        traditional: true,//提交数组参数则必须
        success(resp) {//回调函数
            if (resp.success) {
                layer.msg("删除操作成功，共删除" + resp.rows + "条记录");
                search();
            }
        }
    });
}