
//现场账号管理


layui.use(['table','layer','laydate'], function(){
    var table = layui.table;
    var layer = layui.layer;
    table.render({
        elem: '#siteAccount_table'
        ,height: 600
        ,width:1000
        ,url: '/dis/user/findByAllUser' //数据接口
        ,field:'siteAccountTable'
        ,title: '账号管理表'
        ,skin:'line'
        ,limit:20
        ,toolbar:'#accountTableTitle'
        ,page: true //开启分页
        ,cols: [[ //表头
            {type: 'checkbox',align:'center',width:'10%'}
            ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'20%'}
            ,{field: 'id', align:'center',title: 'id', sort: true,hide:true}
            // ,{field: 'name', align:'center',title: '团队',width:'16%'}
            // ,{field: 'owner',align:'center' ,title: '责任人', sort: true,width:'16%'}
            ,{field: 'name',align:'center' ,title: '账号', sort: true,width:'25%'}
            ,{field: 'proline',align:'center' ,title: '责任区域', sort: true,width:'25%'}
            ,{field: '#', align:'center',title: '操作',toolbar:'#optAccount6',width:'20%' }
        ]]
    });


    //监听头工具栏
    table.on('toolbar(siteAccount_on)', function(obj){
        var id = $('siteAccount_table').id;
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        var ids='';
        if(data.length>0){
            for(var i=0;i<data.length;i++){
                var m=data[i].id+',';
                ids+=m;
            }
        }
        switch(obj.event) {
            case 'add6':
                addSiteAccount();
                break;
            case 'mod6':
                if(data.length === 0){
                    layer.msg('请选择一行')
                }else{
                    // console.log(data);
                    // console.log(data[0].id);
                    modAccountInf(data[0].id);
                }
                break;
            case 'delete6':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.prompt({
                        formType: 1,
                        title: '请输入验证口令'
                    },function(value,index){
                            if(value === "123"){
                                layer.close(index);
                                layer.confirm('确认删除该账号吗？', {title:"删除账号"},function () {
                                    $.ajax({
                                        url: "/dis/user/mulDel",     //数据接口
                                        type: "post",
                                        data: {"ids": ids},
                                        dataType: "json",
                                        success: function (data) {
                                            if (data.retCode === 200) {
                                                layer.msg('删除成功');
                                                reloadSiteTable()
                                            } else {
                                                layer.msg('删除失败')
                                            }
                                        }
                                    })
                                });
                            }else{
                                layer.msg('口令错误');
                            }

                        });

                }
                break;
        }
    });
    table.on('tool(siteAccount_on)', function(ob){
        var data = ob.data //获得当前行数据
            ,layEvent = ob.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            showType(data.id)
        }else if(layEvent === 'del6') {
            layer.prompt({
                formType: 1,
                title: '请输入验证口令'
            },function(value,index){
                if(value === "123"){
                    layer.close(index);
                    layer.confirm('确认删除该账号吗？', {title:"删除账号"},function () {
                        $.ajax({
                            url: "/dis/user/mulDel",     //数据接口
                            type: "post",
                            data: {"ids": data.id},
                            dataType: "json",
                            success: function (data) {
                                if (data.retCode === 200) {
                                    layer.msg('删除成功');
                                    reloadSiteTable()
                                } else {
                                    layer.msg('删除失败')
                                }
                            }
                        })
                    });
                }else{
                    layer.msg('口令错误');
                }

            });
        }

    });

});

//现场账号管理表格重载
function reloadSiteTable(value) {
    layui.use('table',function () {
        var table = layui.table;
        table.reload('siteAccount_table', {
            where: {
                a: 1,
                b: 2
            }
            , page: {
                curr: 1
            }
        })
    })
}


// 账号添加
function addSiteAccount() {
    layer.open({
        type: 1,
        title: '账号添加',
        btn: '确认添加',
        // btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '600px'], //宽高
        content:'<form>' +
            // '<br>' +
            // '          <label for="team_Cho" id="forTeam">选择团队：</label>' +
            // '          <select id="team_Cho" xm-select="team_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-radio xm-select-search-type="title"></select>' +
            // '<br/>' +
            // '          <label for="owner_Cho" id="forOwner">选择责任人：</label>' +
            // '          <select id="owner_Cho" xm-select="owner_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-radio xm-select-search-type="title"></select>' +
            // '<br/>' +
            '          <label for="area_Cho" id="forArea">选择责任区域：</label>' +
            '          <select id="area_Cho" xm-select="area_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-max="2" xm-select-search-type="title" xm-select-direction="down"></select>' +
            '<br/>' +
            '<br/>' +
            '          <label for="accountNumber1">填写账号：</label>' +
            '          <input id="accountNumber1" type="text" class="layui-input" required style="width:100%" >' +
            '<br/>' +
            '<br/>' +
            '          <label for="passwordNumber1">输入密码：</label>' +
            '          <input id="passwordNumber1" type="password" class="layui-input" required style="width:100%;">' +
            // '<img src="image/hide.png" style="box-sizing:border-box;width:30px;height:30px;position:absolute"/>' +
            '<br/>' +
            '          <label for="passwordNumber2">请再次输入密码：</label>' +
            '          <input id="passwordNumber2" type="password" class="layui-input" style="width:100%">' +
            '    </form>' +
            '    <script src="formSelects/src/formSelects-v4.js" ></script>' +
            '    <link rel="stylesheet" href="formSelects/dist/formSelects-v4.css">' +
            '<script>$("#passwordNumber1").click(function() {$("#passwordNumber1").css("background-color","#fff")})</script>' +
            '<script>$("#passwordNumber2").click(function() {$("#passwordNumber2").css("background-color","#fff")})</script>'
        ,success:function () {
            var formSelects = layui.formSelects;
            formSelects.render();

            //团队选择下拉框
            // $.get("/dis/",                                       //团队下拉框地址
            //     {},function(res){
            //     var team_arr = new Array();
            //     for (var i = 0; i < res.data.length; i++) {
            //         // alert(res.data[i].id);
            //         team_arr.push({
            //             name: res.data[i].name,                  //团队名
            //             value: res.data[i].id                     //团队Id
            //         })
            //     }
            //     formSelects.data('team_Cho', 'local', {arr: team_arr});
            // });
            //    formSelects.value('team_Cho', 'val');
            // formSelects.on('team_Cho', function(id, val){
            //     $.get("/dis/",                                          //责任人选择接口
            //         {teamId:val.value},function (res2) {
            //             var owner_arr = new Array();
            //             for (var i = 0; i < res2.data.length; i++) {
            //                 owner_arr.push({
            //                     name: res2.data[i].name,     //下拉框各选项要显示的名称
            //                     value: res2.data[i].id        //下拉框各选项实际值
            //                 })
            //             }
            //             formSelects.data('owner_Cho', 'local', {arr: owner_arr});        //渲染第三个下拉框数据
            //         });
            //     return false;
            // });

            //责任区域下拉框
            $.get("/dis/line/selectall",                                     //团队下拉框地址
                {},function(res){
                var area_arr = new Array();
                for (var i = 0; i < res.data.length; i++) {
                    // alert(res.data[i].id);
                    area_arr.push({
                        name: res.data[i].name,              //区域名
                        value: res.data[i].id                //区域Id
                    })
                }
                formSelects.data('area_Cho', 'local', {arr: area_arr});
            });

        }
        ,yes:function (index3){
            // var aa = document.getElementById('passwordNumber1').type;
            // console.dir(aa);
            var formSelects = layui.formSelects;
            var layer = layui.layer;
            // //团队获取
            // var t = formSelects.value('team_Cho','val'),
            //     team = t.toString();
            // //责任人获取
            // var o = formSelects.value('owner_Cho','val'),
            //     owner = o.toString();

            //责任区域获取
            var a = formSelects.value('area_Cho','val'),
                area = a.toString();
            console.log(area);
            //账号获取值
            var account = $("#accountNumber1").val();
            //密码第一遍值
            var password1 = $("#passwordNumber1").val();
            //密码第二遍值
            var password2 = $("#passwordNumber2").val();
            // if(!team){
            //     layer.tips('团队不能为空', '#forTeam', {
            //         tips: [1, '#33ccff'],
            //         time: 3000
            //     });
            // }
            // else if(!owner){
            //     layer.tips('责任人不能为空', '#forOwner', {
            //         tips: [1, '#33ccff'],
            //         time: 3000
            //     });
            // }
            // else
            if (!area){
                layer.tips('责任区域不能为空', '#forArea', {
                    tips: [1, '#33CCFF'],
                    time: 3000
                });
            }else if(!account){
                layer.tips('账号不能为空', '#accountNumber1', {
                    tips: [1, '#33CCFF'],
                    time: 3000
                });
            }else if ( password1 !== password2){
                layer.tips('两遍输入的密码不一致', '#passwordNumber2', {
                    tips: [1, '#33CCFF'],
                    time: 3000
                });
                $('#passwordNumber1').css("background-color","#FFFF99");
                $('#passwordNumber2').css("background-color","#FFFF99")
            }
            else{
                $.post('/dis/user/add',        //数据发送接口
                    {proLine:area,name:account,password:password1},
                    function (res) {
                        if (res.retCode === 200){
                            layer.msg('账号创建成功');
                            // layui.use('table',function () {
                            reloadSiteTable();
                            layer.close(index3);
                            // })
                        }else{
                            layer.msg('账号创建失败')
                        }
                    })
            }

        }
    })
}

// function showPassword1(){
//     $("#passwordNumber1").type("text")
// }






//修改密码
function modAccountInf(accountId) {
    layer.open({
        type: 1,
        title: '修改密码',
        btn: '确认修改',
        // btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '500px'], //宽高
        content:'<form>' +
            '          <label for="passwordNumber1">请输入新密码：</label>' +
            '          <input id="passwordNumber1" type="password" class="layui-input" style="width:100%"></input>' +
            '<br/>' +
            '          <label for="passwordNumber2">请再次输入密码：</label>' +
            '          <input id="passwordNumber2" type="password" class="layui-input" style="width:100%"></input>' +
            '    </form>\n' +
            '    <script src="layui-formSelects-master/src/formSelects-v4.js" ></script>' +
            '    <link rel="stylesheet" href="layui-formSelects-master/dist/formSelects-v4.css">' +
            '<script>$("#passwordNumber1").click(function() {$("#passwordNumber1").css("background-color","#fff")})</script>' +
            '<script>$("#passwordNumber2").click(function() {$("#passwordNumber2").css("background-color","#fff")})</script>'
        ,success:function () {
            var formSelects = layui.formSelects;
            formSelects.render();

            //责任区域下拉框
           /* $.get('/dis/line/selectall',                                     //团队下拉框地址
                {},function(res){
                    var area_arr = new Array();
                    for (var i = 0; i < res.data.length; i++) {
                        // alert(res.data[i].id);
                        area_arr.push({
                            name: res.data[i].name,              //区域名
                            value: res.data[i].id                //区域Id
                        })
                    }
                    formSelects.data('area_Cho', 'local', {arr: area_arr});
                });
*/
        }
        ,yes:function (index3){
            // var formSelects = layui.formSelects;
            // var layer = layui.layer;
            // //责任区域获取
            // var a = formSelects.value('area_Cho','val'),
            //     area = a.toString();
            // console.log(area);
            // //账号获取值
            // var account = $("#accountNumber1").val();
            //密码第一遍值
            var password1 = $('#passwordNumber1').val();
            //密码第二遍值
            var password2 = $('#passwordNumber2').val();
            if ( password1 !== password2){
                layer.tips('两遍输入的密码不一致', '#passwordNumber2', {
                    tips: [1, '#3366ff'],
                    time: 3000
                });
                $('#passwordNumber1').css("background-color","#FFFF99");
                $('#passwordNumber2').css("background-color","#FFFF99")
            } else{
                $.post('/dis/user/update',        //数据发送接口
                    {id:accountId,password:password1},
                    function (res) {
                        if (res.retCode === 200){
                            layer.msg('密码修改成功');
                            // layui.use('table',function () {
                            reloadSiteTable();
                            layer.close(index3);
                            // })
                        }else{
                            layer.msg('密码修改失败')
                        }
                    })
            }

        }
    })
}





