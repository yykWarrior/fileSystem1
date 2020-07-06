
//文件管理js

//发放记录动态表格
layui.use(['table','layer','laydate'], function(){
    var table = layui.table;

    table.render({
        elem: '#record_table'
        ,height: 500
        ,width:1400
        ,url: '/dis/dis/multiquery' //数据接口
        ,title: '记录表'
        ,align:'center'
        ,skin:'line'
        ,toolbar:'#recordtitlebtn'
        ,page: true //开启分页
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left' ,align:'center'}
            ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
            , {field: 'id', title: 'id', sort: true ,align:'center',hide:true}
            ,{field: 'creatdate', title: '发放时间',align:'center'}
            ,{field: 'startdate', title: '开始时间',align:'center'}
            ,{field: 'enddate', title: '结束时间',align:'center'}
            ,{field: 'prolineName', title: '生产线', sort: true ,align:'center'}
            ,{field: 'tname', title: '轴承型号', sort: true ,align:'center'}
            ,{field: 'dname', title: '工序名称' ,align:'center'}
            ,{field: 'fid', title: '文件名称' ,align:'center'}
            ,{fixed: 'right', title: '操作',toolbar:'#recordoptbtn',width:'15%',align:'center'}
            ,{field: 'effect', title: '有效性1/0' ,align:'center'}
        ]]
    });

    //时间选择器
    laydate.render({
        elem: '#dateser'
        , type: 'datetime'
    });

    //监听头工具栏
    table.on('toolbar(record_on)', function(obj){
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
            case 'delete3':
                // var datetime=$('#effect').val();
                // console.log(datetime);
                if(data.length === 0){
                    layer.msg('请选择一行');
                }
                // else if ( datetime === 0)  {
                //     $('.record_delete').addClass('layui-btn layui-btn-disabled')
                //
                // }
                else {
                    layer.confirm('删除后对应的文件分配也被删除了，真的删除吗', {title:'删除分配'},function () {
                        $.ajax({
                            url: "/dis/dis/multidel",
                            type: "post",
                            data: {"ids": ids},
                            dataType: "json",
                            success: function (data) {
                                if (data.retCode === 200) {
                                    layer.msg('删除成功');
                                    reloadRecordTable();
                                } else {
                                    layer.msg('删除失败')
                                }
                            }
                        })
                    });
                }
                break;
            case 'ser3':
                // 搜索条件
                var a1 = $('#dateser').val();
                table.reload('record_table', {
                    method: 'post'
                    , where: {
                        'dateser': a1
                    }
                    , page: {
                        curr: 1
                    }
                });
                break;
            case 'ser31':
                // 搜索条件
                var b1 = $('#proline').val();
                var c1 = $('#beartype').val();
                var d1 = $('#dename').val();
                // var e1 = $('#filname').val();
                table.reload('record_table', {
                    method: 'post'
                    , where: {
                        'proline': b1,
                        'beartype': c1,
                        'dename': d1
                        // 'filname': e1
                    }
                    , page: {
                        curr: 1
                    }
                });
                break;

        }
    });
    //监听行工具事件
    table.on('tool(record_on)', function(obj){     //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        // var datetime1=$('#effect').val();
        // if(datetime1 === 0){
        //     $('#del3').addClass('layui-btn layui-btn-disabled')
        // }
        if(layEvent === 'del3'){
            layer.confirm('删除后对应的文件分配也被删除了，真的删除吗', {title:"删除分配"},function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    url: "/dis/dis/multidel",
                    type: "post",
                    data:{"ids":data.id},
                    dataType: "json",
                    success: function (data) {
                        if(data.retCode === 200){
                            layer.msg('删除成功');
                            var table = layui.table;
                            reloadRecordTable();
                        }else{
                            layer.msg('删除失败')

                        }
                    }
                })
            });
        }
    });


});

function reloadRecordTable(data) {
    layui.use('table',function () {
        var table = layui.table;
        table.reload('record_table',{
            where:{
                a:1,
                b:2
            },
            page:{
                curr:1
            }
        })
    })
}