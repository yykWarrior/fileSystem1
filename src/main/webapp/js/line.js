

//线位管理
// alert('看得到我吗');

var line_table = 'line_table';
var typeShow_table = 'typeShow_table';
var equipShow_Table = 'equipShow_Table';
var fileShow_Table = 'fileShow_Table';
var recordShow_Table = 'recordShow_Table';
layui.use(['table','layer','laydate'], function(){
    var table = layui.table;
    table.render({
        elem: '#line_table'
        ,height: 600
        ,width:1200
        ,url: '/dis/line/selectLine' //数据接口
        ,field:'Linetable'
        ,title: '线位表'
        ,skin:'line'
        ,toolbar:'#linetitlebtn'
        ,page: true //开启分页
        ,cols: [[ //表头
            {type: 'checkbox', align:'center',width:'5%'}
            ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
            ,{field: 'id', align:'center',title: 'id', sort: true,hide:true}
            ,{field: 'name', align:'center',title: '线名称',width:'20%'}
            ,{field: 'creatdate',align:'center' ,title: '提交时间', sort: true,width:'25%'}
            ,{field: '#', align:'center',title: '操作',toolbar:'#optline1',width:'15%' }
            ,{field: '#', align:'center',title: '添加',toolbar:'#linefill',width:'15%' }
            ,{fixed: 'right', align:'center',title: '型号分配',toolbar:'#moddistr',width:'15%' }
        ]]
    });

    //双击行查看发放记录
    table.on('rowDouble(line_table)',function (obj) {
        var lineName = obj.data.name;
        showRecord(lineName)
    });

    //监听头工具栏
    table.on('toolbar(line_table)', function(obj){
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
            case 'add':
                showAdd_line();
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('同时删除线位下的设备和型号，确认删除吗？', {title:"删除生产线"},function () {
                        $.ajax({
                            url: "/dis/line/muldel",
                            type: "post",
                            data: {"ids": ids},
                            dataType: "json",
                            success: function (data) {
                                if (data.retCode === 200) {
                                    layer.msg('删除成功');
                                    reloadTable(line_table)
                                } else {
                                    layer.msg('删除失败')
                                }
                            }
                        })
                    });

                }
                break;
            case 'ser':
                // 执行搜索，表格重载

                // 搜索条件
                var send_name = $('#linename').val();
                table.reload('line_table', {
                    method: 'post'
                    , where: {
                        'name': send_name
                    }
                    , page: {
                        curr: 1
                    }
                });
                break;
            case 'str_type':
                //发放
                if(data.length === 0){
                    layer.msg('请选择一条生产线')
                }else{
                    strType(data[0].id);
                }
                break;
        }
    });

    //监听行工具事件
    table.on('tool(line_table)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            showType(data.id)
        }else if(layEvent === 'del') {
            layer.confirm('同时删除线位下的设备和型号，确认删除吗？', {title: "删除生产线"}, function (index) {
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    url: "/dis/line/muldel",
                    type: "post",
                    data: {"ids": data.id},
                    dataType: "json",
                    success: function (data) {
                        if (data.retCode === 200 ) {
                            layer.msg('删除成功');
                            reloadTable(line_table)
                        } else {
                            layer.msg('删除失败')

                        }
                    }
                })
            });
        }else if(layEvent === 'detail') {        //  调用：线-型号表
            reloadTypeTable();
            showType(data.id);
        }else if(layEvent === 'add_mod') {         //    调用：添加型号
            addType(data.id);
        }else if(layEvent === 'add_equip') {       //调用：添加设备
            addEquip(data.id);
        }else if(layEvent === 'str_type') {         //调用：分配型号，发放文件
            strType(data.id);
        }else if(layEvent === 'detail10'){          //调用： 线-设备表
            showEquip(data.id);
        }else if(layEvent === "detail12"){           //调用：线-文件表
            showFile(data.id);
        }

    });

});


//表格重载
function reloadTable(tableName){
    layui.use(['table','layer','laydate'], function() {
        var table = layui.table;
        table.reload(tableName, {
            where: {
                a: 1,
                b: 2
            },
            page: {
                curr: 1
            }
        });
    });
}



// 产线增加弹出窗
function showAdd_line(){
    layer.open({
        type: 1,
        title:'产线添加',
        id:'addLine',
        btn:['提交','添加线'],
        btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '500px'], //宽高
        content: '<div id=\'ty1\' align=\'center\'>' +
            '<label for=\'spot_title\' style="margin:5px;font-size:medium"">生产线名称:</label>' +
            '<input type=\'text\' id=\'a3\' name=\'spot_title\' style="margin:5px;">' +
            '</div>'

        //提交添加的生产线
        ,yes: function(index){
            // $("#ty1").load('#ty1');
            // data.preventDefault();
            var names='';
            $("input[name='spot_title']").each(function(){
                names+=$(this).val();
                names+=',';
            });
            var a1 = names.split(',');
            // console.log(names);
            // console.log(a1);
            if(!a1[0] && !a1[a1.length-1]){
                layer.msg('第一行与最后一行不能为空')
            } else{
                $.ajax({
                    url:"/dis/line/batchadd",
                    type:"post",
                    data:{"names":names},
                    dataType:"json",
                    success: function (data) {
                        if(data.retCode === 200){
                            layer.msg("添加成功！");
                            reloadTable(line_table);
                            layer.close(index);
                            // $('#ty1 label,input,br').remove();
                        }else{
                            layer.msg("添加失败！")
                        }
                    }
                })
            }
        }

        //添加生产线输入框
        ,btn2: function(){
            $('#ty1').append(
                '<br/>' +
                '<label for=\'spot_title\' style="margin:5px;">生产线名称:</label>' +
                '<input type="text" name="spot_title" / style="margin:5px;">'
                /* '<input type="button" class="remove" value="Delete" />'*/
            );
            //return false 开启该代码可禁止点击该按钮关闭
            return false
        }

    });
}

// 弹出增加型号窗
function addType(lineid) {
    layer.open({
        type: 1,
        title: '型号添加',
        btn:['提交','添加型号'],
        btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['800px', '500px'], //宽高
        content: '<div id="ty2" align="center">' +
            ' <label for=\'spot_title1\' style="margin:5px;">轴承类型:</label>' +
            '<input type="text" name="spot_title1" / style="margin:5px;"> ' +
            '<br/>' +
            ' <label for=\'spot_title2\' style="margin:5px;">轴承型号:</label>' +
            '<input type="text" name="spot_title2" / style="margin:5px;"> ' +
            '<hr/>' +
            '</div>'

        ,yes: function(index){
            var name1='';
            var name2='';
            $("input[name='spot_title1']").each(function(){
                name1+=$(this).val();
                name1+=',';
            });
            $("input[name='spot_title2']").each(function(){
                name2+=$(this).val();
                name2+=',';
            });

            var a1 = name2.split(',');
            for (var i=a1.length-1;i>0;i--){
                if(!a1[i-1]){
                    break;
                }
            }
            if(i<=0){

                var valBoo=  a1[i];
            }else{

                var valBoo= a1[i-1];
            }
            if(!valBoo){
                layer.msg('必须输入轴承型号')
            }else {
                $.ajax({
                    url:"/dis/model/batchadd",              //在这里添加后端数据接口
                    type:"post",
                    data:{"modelNames":name1,"modelType":name2,"lineid":lineid},   // 在这行需后端进行数据定义
                    dataType:"json",
                    success: function (data) {
                        if(data.retCode === 200){
                            layer.msg("添加成功！");
                            reloadTable(typeShow_table);
                            layer.close(index);
                        }else{
                            layer.msg("添加失败！")
                        }
                    }
                });
            }
        }
        //添加型号输入框
        ,btn2: function(){
            $("#ty2").append(
                '<br/>' +
                ' <label for=\'spot_title1\' style="margin:5px;">轴承类型:</label>' +
                '<input type="text" name="spot_title1" / style="margin:5px;">' +
                '<br/>' +
                ' <label for=\'spot_title2\' style="margin:5px;">轴承型号:</label>' +
                ' <input type="text" name="spot_title2" / style="margin:5px;">' +
                '<hr/>'
            );
            //return false 开启该代码可禁止点击该按钮关闭
            return false
        }
    });
}

//弹出增加设备窗口
function addEquip(lineid) {
    layer.open({
        type: 1,
        title: '设备添加',
        btn:['提交','添加设备'],
        btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['800px', '500px'], //宽高
        content: '<div id="ty3" align="center">' +
            ' <label for=\'spot_title3\' style="margin:5px;">工序名称:</label>' +
            '<input type="text" name="spot_title3" / style="margin:5px;"> ' +
            '<br/>' +
            ' <label for=\'spot_title4\' style="margin:5px;">设备型号:</label>' +
            '<input type="text" name="spot_title4" / style="margin:5px;"> ' +
            '<hr/>' +
            '</div>'
        //点击提交按钮
        ,yes: function(index){
            var name3 = '';
            var name4 = '';
            $("input[name='spot_title3']").each(function(){
                name3+=$(this).val();
                name3+=',';
            });
            $("input[name='spot_title4']").each(function(){
                name4+=$(this).val();
                name4+=',';
            });
            var a1 = name3.split(',');
            for (var i=a1.length-1;i>0;i--){
                if(!a1[i-1]){

                    break;
                }
            }
            if(i<=0){
                // console.log(i);
                // console.log(!a1[i])
                var valBoo=  a1[i];
            }else{
                // console.log(i-1);
                // console.log(!a1[i-1]);
                var valBoo= a1[i-1];
            }
            if(!valBoo){
                layer.msg('必须输入工序名称')
            }else{
                $.ajax({
                    url:"/dis/device/batchadd",              //在这里添加后端数据接口
                    type:"post",
                    data:{"processNames":name3,"deviceNames":name4,"lineid":lineid},   // 在这行需后端进行数据定义
                    dataType:"json",
                    success: function (data) {
                        if(data.retCode === 200){
                            layer.msg("添加成功！");
                            layui.use(['table','layer','laydate'], function() {
                                var table = layui.table;
                                var device_table = 'device_table';
                                reloadTable(device_table);
                            });
                            layer.close(index);
                            // $('#ty3 label,input,br').remove();
                        }else{
                            layer.msg("添加失败！")
                        }
                    }
                });
            }

        }
        //添加设备输入框
        ,btn2: function() {
            $("#ty3").append(
                '<br/>' +
                ' <label for=\'spot_title3\' style="margin:5px;">工序名称:</label>' +
                '<input type="text" name="spot_title3" / style="margin:5px;"> ' +
                '<br/>' +
                ' <label for=\'spot_title4\' style="margin:5px;">设备型号:</label>' +
                '<input type="text" name="spot_title4" / style="margin:5px;"> ' +
                '<hr/>'
            );
            //return false 开启该代码可禁止点击该按钮关闭
            return false
        }
    })
}
// 弹出查看型号窗口
function  showType(id) {
    layer.open({
        type: 1,
        title: '型号查看',
        maxmin: 'ture',
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '500px'], //宽高
        content: '<div align="center" aria-relevant="additions">' +
            '<table id=\'typeShow_table\' lay-filter="typeShow_table"></table>' +
            '</div>',
        success: function (index4) {
            layui.use(['table', 'layer', 'laydate'], function () {
                var table = layui.table;
                table.render({
                    elem: '#typeShow_table'
                    , url: '/dis/model/selectpage'             //产线-型号接口
                    , where:{'lineId':id}
                    // , field: 'typetable'
                    , title: '线位-型号表'
                    , skin: 'line'
                    , toolbar: '#typetitlebtn'
                    , page: true //开启分页
                    , cols: [[ //表头
                        {type: 'checkbox', align: 'center', fixed: 'left', width: '10%'}
                        ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'15%'}
                        , {field: 'id', align: 'center', title: 'id',  sort: true,hide:true}
                        , {field: 'name', align: 'center', title: '轴承类型', width: '30%'}
                        , {field: 'type', align: 'center', title: '轴承型号', sort: true, width: '30%'}
                        , {field: '#', align:'center',title: '操作',toolbar:'#optTypeBtn',width:'15%' }

                    ]]
                });
                    //监听头工具栏
                    table.on('toolbar(typeShow_table)', function (obj) {
                        var checkStatus = table.checkStatus(obj.config.id)
                            , data = checkStatus.data; //获取选中的数据
                        var ids = '';
                        if (data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                var m = data[i].id + ',';
                                ids += m;
                            }
                        }
                        switch (obj.event) {
                            case 'add4':
                                addType(id);
                                break;
                            case 'delete4':
                                if (data.length === 0) {
                                    layer.msg('请选择一行');
                                } else {
                                    var layer = layui.layer;
                                    layer.confirm('确认删除吗？', {title: "删除型号"}, function () {
                                        $.ajax({
                                            url: "/dis/model/muldel",
                                            type: "post",
                                            data: {"ids": ids},
                                            dataType: "json",
                                            success: function (data) {
                                                if (data.retCode === 200) {
                                                    layer.msg('删除成功');
                                                    reloadTable(typeShow_table)
                                                } else {
                                                    layer.msg('删除失败')
                                                }
                                            }

                                        });
                                    });

                                }
                                break;
                            case 'ser4':
                                // 搜索条件
                                var modelname = $('#modename').val();
                                var typename = $('#typename').val();
                                /*alert(modelname)
                                alert(typename)*/
                                table.reload('typeShow_table', {
                                    method: 'post'
                                    , where: {
                                        'modelname': modelname,
                                        'typename': typename
                                    }
                                    , page: {
                                        curr: 1
                                    }
                                });
                                break;
                        }
                    });

            });

        }
    })
}

// 弹出查看设备窗口
function  showEquip(id) {
    layer.open({
        type: 1,
        title: '设备查看',
        // btn: ['查看', '返回'],
        // btnAlign: 'c',
        maxmin: 'ture',
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '500px'], //宽高
        content: '<div align="center" aria-relevant="additions">' +
            '<table id=\'equipShow_Table\' lay-filter="equipShow_Table"></table>' +
            '</div>',
        success: function (index4) {
            layui.use(['table', 'layer', 'laydate'], function () {
                var table = layui.table;
                table.render({
                    elem: '#equipShow_Table'
                    , url: '/dis/device/getDeviceByLine'           //产线-设备数据接口
                    , where:{'lineId':id}
                    , title: '产线-设备表'
                    , skin: 'line'
                    , toolbar: '#deviceTitleBtn1'
                    , page: true //开启分页
                    ,cols: [[ //表头
                        {type: 'checkbox', fixed: 'left',align:'center'}
                        ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
                        , {field: 'id', title: 'id', sort: true,align:'center',hide:true}
                        ,{field: 'process_name', title: '工序名称',align:'center'}
                        ,{field: 'd_type', title: '设备名称', sort: true,align:'center'}
                    ]]
                });

                //监听头工具栏
                table.on('toolbar(equipShow_Table)', function (obj) {
                    var checkStatus = table.checkStatus(obj.config.id)
                        , data = checkStatus.data; //获取选中的数据
                    var ids = '';
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            var m = data[i].id + ',';
                            ids += m;
                        }
                    }
                    switch (obj.event) {
                        case 'add11':
                            addEquip(id);
                            break;
                        case 'delete11':
                            if (data.length === 0) {
                                layer.msg('请选择一行');
                            } else {
                                var layer = layui.layer;
                                layer.confirm('确认删除吗？', {title: "删除工序"}, function () {
                                    $.ajax({
                                        url: "/dis/device/muldel",               //删除接口
                                        type: "post",
                                        data: {"ids": ids},
                                        dataType: "json",
                                        success: function (data) {
                                            if (data.retCode === 200) {
                                                layer.msg('删除成功');
                                                reloadTable(equipShow_Table);
                                            } else {
                                                layer.msg('删除失败')
                                            }
                                        }

                                    });
                                });

                            }
                            break;
                    }
                });

            });

        }
    })

}

// 弹出查看文件窗口
function  showFile(id) {
    layer.open({
        type: 1,
        title: '文件查看',
        // btn: ['查看', '返回'],
        // btnAlign: 'c',
        maxmin: 'ture',
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '500px'], //宽高
        content: '<div align="center" aria-relevant="additions">' +
            '<table id=\'fileShow_Table\' lay-filter="fileShow_Table"></table>' +
            '</div>',
        success: function (index12) {
            layui.use(['table', 'layer', 'laydate'], function () {
                var table = layui.table;
                table.render({
                    elem: '#fileShow_Table'
                    , url: '/dis/fileType/getFileByLine'       //产线-文件数据接口
                    , where:{'lineId':id}
                    , title: '产线-文件表'
                    , skin: 'line'
                    ,toolbar: '#fileTitleBtn1'
                    ,page: true //开启分页
                    ,limit:10
                    ,cols: [[ //表头
                        {type: 'checkbox', fixed: 'left',align:'center'}
                        ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
                        , {field: 'id', title: 'id', sort: true,align:'center',hide:true}
                        , {field: 'dname', title: '工序', sort: true,align:'center'}
                        , {field: 'tname', title: '型号', sort: true,align:'center'}
                        ,{field: 'fid', title: '文件名',align:'center'}
                        // ,{field: 'code', title: '文件编号', sort: true,align:'center'}
                        // ,{field: 'version', title: '版本', sort: true,align:'center'}
                        ,{field:'url',title:'文件地址',align:'center',hide:true}
                    ]]
                });

                //监听头工具栏
                table.on('toolbar(fileShow_Table)', function (obj) {
                    var checkStatus = table.checkStatus(obj.config.id)
                        , data = checkStatus.data; //获取选中的数据
                    var ids = '';
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            var m = data[i].id + ',';
                            ids += m;
                        }
                    }
                    switch (obj.event) {
                        case 'delete12':
                            if (data.length === 0) {
                                layer.msg('请选择一行');
                            } else {
                                var layer = layui.layer;
                                layer.confirm('确认删除文件和产线关系吗？', {title: "删除文件-产线关系"}, function () {
                                    $.ajax({
                                        url: "/dis/fileType/mulDel",               //删除接口
                                        type: "post",
                                        data: {"ids": ids},
                                        dataType: "json",
                                        success: function (data) {
                                            if (data.retCode === 200) {
                                                layer.msg('删除成功');
                                                reloadTable(fileShow_Table)
                                            } else {
                                                layer.msg('删除失败')
                                            }
                                        }

                                    });
                                });

                            }
                            break;
                    }
                });

                //双击查看文件
                table.on('rowDouble(fileShow_Table)',function (obj) {
                    var data = obj.data;
                    var fileUrl = data.url;
                    window.open('pdfjs/web/viewer.html?file=../..'+ fileUrl +'')
                })

            });

        }
    })

}


// 弹出查看记录窗口
function  showRecord(name) {
    layer.open({
        type: 1,
        title: '发放记录',
        maxmin: 'ture',
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '500px'], //宽高
        content: '<div align="center" aria-relevant="additions">' +
            '<table id=\'recordShow_Table\' lay-filter="recordShow_Table"></table>' +
            '</div>',
        success: function (index13) {
            layui.use(['table', 'layer', 'laydate'], function () {
                var table = layui.table;
                table.render({
                    elem: '#recordShow_Table'
                    , url: '/dis/dis/multiquery'       //产线-记录数据接口
                    , where:{'proline':name}
                    // , field: 'typetable'
                    , title: '产线-记录表'
                    , skin: 'line'
                    , toolbar: '#recordTitleBtn1'
                    , page: true //开启分页
                    ,cols: [[ //表头
                        {type: 'checkbox', fixed: 'left' ,align:'center'}
                        ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
                        , {field: 'id', title: 'id', sort: true ,align:'center',hide:true}
                        ,{field: 'creatdate', title: '发放时间',align:'center'}
                        ,{field: 'startdate', title: '开始时间',align:'center'}
                        ,{field: 'enddate', title: '结束时间',align:'center'}
                        ,{field: 'prolineName', title: '生产线', sort: true ,align:'center',hide:true}
                        ,{field: 'tname', title: '轴承型号', sort: true ,align:'center'}
                        ,{field: 'dname', title: '工序名称' ,align:'center'}
                        ,{field: 'fid', title: '文件名称' ,align:'center'}
                        ,{fixed: 'right', title: '操作',toolbar:'#recordInnerBtn1',align:'center'}
                    ]]
                });

                //监听头工具栏
                table.on('toolbar(recordShow_Table)', function (obj) {
                    var checkStatus = table.checkStatus(obj.config.id)
                        , data = checkStatus.data; //获取选中的数据
                    var ids = '';
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            var m = data[i].id + ',';
                            ids += m;
                        }
                    }
                    //回收文件记录
                    switch (obj.event) {

                        case 'ser312':
                            // 搜索条件
                            var c1 = $('#beartype1').val();
                            var d1 = $('#dename1').val();
                            // var e1 = $('#filname').val();
                            table.reload('recordShow_Table', {
                                method: 'post'
                                , where: {
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
                //文件回收
                table.on('tool(recordShow_Table)',function (obj) {
                    var recordId = obj.data.id;
                    if(obj.event === 'recycle1'){
                        $.ajax({
                            url: "/dis/dis/updateEffect",               //回收2--接口
                            type: "post",
                            data: {"id": recordId},
                            dataType: "json",
                            success: function (data) {
                                if (data.retCode === 200) {
                                    layer.msg('回收成功');
                                    reloadTable(recordShow_Table)
                                } else {
                                    layer.msg('回收失败')
                                }
                            }

                        });
                    }
                })

            });

        }
    })

}


//弹出发放文件框
function strType(lineId) {
    layer.open({
        type: 1,
        title: '型号分配',
        btn: '确认分配',
        btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '500px'], //宽高
        content:
            '<div id=\'file_div\' style="font-size: medium;line-height: 25px">' +
            '<label for="typ_Cho" style="font-size: medium">选择型号:</label>' +
            '        <select name="ber_Cho" id="ber_Cho" xm-select="ber_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-direction="down">' +
            '        </select>' +
            '<label for="pro_Cho" style="font-size: medium">选择工序:</label>' +
            '        <select name="pro_Cho" id="pro_Cho" xm-select="pro_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-direction="down">' +
            '        </select>' +
            '<label for="date1" style="font-size: medium">生产时间:</label>' +
            '<input type="text" id="date1"/>' +
            '<script src="formSelects/dist/formSelects-v4.js"></script>' +
            '<link rel="stylesheet" href="formSelects/dist/formSelects-v4.css">' +
            '<hr/>' +
            '</div>'
        ,success:function(index){
            $('#ber_Cho #pro_Cho').remove();
            //时间选择器
            layui.use('laydate',function (laydate) {
                // var laydata = layui.laydata;
                laydate.render({
                    elem: '#date1'
                    , type: 'datetime'
                    , range: true
                });
            });
            var formSelects = layui.formSelects;
            formSelects.render();
            //型号
            $.get("/dis/model/selectByLinId",          //型号选择接口
                {lineId:lineId},function (res2) {
                    var ber_arr = new Array();        //定义个数字装远程数据
                    for (var i = 0; i < res2.data.length; i++) {      //遍历远程数据
                        ber_arr.push({
                            name: res2.data[i].type,     //下拉框各选项要显示的名称
                            value: res2.data[i].id        //下拉框各选项实际值
                        })
                    }
                    formSelects.data('ber_Cho', 'local', {arr: ber_arr});        //渲染第三个下拉框数据
                });
            //工序
            $.get("/dis/device/selectByLineId",     //工序选择接口
                {lineId: lineId}, function (res1) {     //第二个下拉框型号的请求地址 有参数 name是后台controller对应的参数，val.value为当前选择的第一个下拉框值
                    var pro_arr = new Array();        //定义个数字装远程数据
                    for (var i = 0; i < res1.data.length; i++) {      //遍历远程数据
                        pro_arr.push({
                            name: res1.data[i].process_name,     //下拉框各选项要显示的名称
                            value: res1.data[i].id        //下拉框各选项实际值
                            // selected:'selected'                 //默认全选
                        })
                    }
                    formSelects.data('pro_Cho', 'local', {arr: pro_arr});  //渲染第二个下拉框数据
                })
        }
        ,yes:function (index) {
          var manDate = $('#date1').val(),
              formSelects = layui.formSelects,
              berType1 = formSelects.value('ber_Cho','val'),
              proName1 = formSelects.value('pro_Cho','val'),
              //型号
              berType = (berType1).toString(),
              //设备
              proName = (proName1).toString();

            // manDate = $('#date1').data;
            // console.log(berType);
            // console.log(proName);
            // console.log(manDate);

            $.post('/dis/dis/disLine',   //分配数据接口
                {lineId:lineId,berType:berType,proName:proName,manDate:manDate},function (res) {
                    if(res.retCode === 200){
                        layer.confirm('确认分配吗',{title:'生产分配'},function () {
                            layer.msg('分配成功');
                            layer.close(index);
                            // layui.use('table', function() {
                                var table = layui.table;
                                table.reload('record_table', {
                                    where: {
                                        a: 1,
                                        b: 2
                                    },
                                    page: {
                                        curr: 1
                                    }
                                });
                            // });

                        });

                    }else {
                        layer.msg('分配失败')
                    }
                })

        }

    });



}


