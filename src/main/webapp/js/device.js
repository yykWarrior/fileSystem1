
//设备管理

//发放记录动态表格
layui.use(['table','layer','laydate'], function(){
    var table = layui.table;
    table.render({
        elem: '#device_table'
        ,height: 600
        ,width:1200
        ,url: '/dis/device/selectall' //数据接口
        ,field:'devicetable'
        ,skin:'line'
        ,toolbar:'#devicetitlebtn'
        ,page: true //开启分页
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left',align:'center'}
            ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
            , {field: 'id', title: 'id', sort: true,align:'center',hide:true}
            ,{field: 'process_name', title: '工序名称',align:'center'}
            ,{field: 'd_type', title: '设备名称', sort: true,align:'center'}
            ,{field: 'name', title: '所属生产线', sort: true,align:'center'}
            ,{field: 'sex', title: '操作',toolbar:'#optdevicebtn',align:'center'}
        ]]
    });


    //table表格重新加载
    function reloadDeviceTable() {
        layui.use(['table', 'layer', 'laydate'], function () {
            var table = layui.table;
            table.reload('device_table', {
                where: {
                    a: 1,
                    b: 2
                },
                page: {
                    curr: 1
                }
            });
        })
    }

    //监听头工具栏
    table.on('toolbar(device_on)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        var ids='';
        if(data.length>0){
            for(var i=0;i<data.length;i++){
                var m=data[i].id+',';
                ids+=m;
            }
        }
        switch(obj.event){
            case 'delete1':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除行么',{title:'删除'}, function () {
                        $.ajax({
                            url: "/dis/device/muldel",
                            type: "post",
                            data: {"ids": ids},
                            dataType: "json",
                            success: function (data) {
                                if (data.retCode === 200) {
                                    layer.msg('删除成功');
                                    reloadDeviceTable()
                                } else {
                                    layer.msg('删除失败')
                                }
                            }
                        })

                    });
                }
                break;
            case 'ser1':
                // 搜索条件
                var processname = $('#processname').val();
                var devicename = $('#devicename').val();
                var lineName = $('#byLine').val();
                table.reload('device_table', {
                    method: 'post'
                    , where: {
                        'processname': processname,
                        'devicename':devicename,
                        'lineName':lineName
                    }
                    , page: {
                        curr: 1
                    }
                });
                break;
        }
    });
    //监听行工具事件
    table.on('tool(device_on)', function(obj){
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'del1'){
            layer.confirm('真的删除行么', {title:'删除'}, function (index) {
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    url: "/dis/device/muldel",
                    type: "post",
                    data:{"ids":data.id},
                    dataType: "json",
                    success: function (data) {
                        if(data.retCode === 200){
                            layer.msg('删除成功');
                            reloadDeviceTable()
                        }else{
                            layer.msg('删除失败')

                        }
                    }
                });

            });
        }
    });


});