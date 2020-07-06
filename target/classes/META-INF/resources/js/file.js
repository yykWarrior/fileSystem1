
//文件管理js

//文件管理动态表格
layui.use(['table','layer','laydate'], function(){
    var table = layui.table;
    var layer = layui.layer;
    table.render({
        elem: '#file_table'
        ,height: 420
        ,width:1200
        ,url: '/dis/file/selectall' //数据接口
        ,field:'devicetable'
        ,title: '文件表'
        ,skin:'line'
        ,toolbar:'#filetitlebtn'
        ,page: true //开启分页
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left',align:'center'}
            ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
            , {field: 'id', title: 'id', sort: true,align:'center',hide:true}
            ,{field: 'name', title: '文件名',align:'center'}
            ,{field: 'code', title: '文件编号', sort: true,align:'center'}
            ,{field: 'version', title: '版本', sort: true,align:'center'}
            ,{field: 'sex', title: '操作',toolbar:'#optfilebtn',align:'center'}
            ,{field:'path',title:'文件地址',align:'center',hide:true}
        ]]
    });

    //监听头工具栏
    table.on('toolbar(file_on)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        var ids='';
        var idS = new Array();
        if(data.length>0){
            for(var i=0;i<data.length;i++){
                var m=data[i].id+',';
                ids+=m;
                idS.push(data[i].id)
            }
        }
        switch(obj.event){
            case 'add2':
                addFile();
                break;
            case 'mod2':
                if(data.length===0){
                    layer.msg('请选择要修改信息的文件')
                }else{
                    modFil_inf(data[0].id);
                    // console.dir(ids)
                }
                break;
            case 'def2':
                if(data.length === 0){
                    layer.msg('请选择要定义属性的文件')
                }else{
                    var fileId = idS.toString();
                    defFile_Pro(fileId);
                }
                break;
            case 'delete2':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除行么', function () {
                        $.ajax({
                            url: "/dis/file/muldel",
                            type: "post",
                            data: {"ids": ids},
                            dataType: "json",
                            success: function (data) {
                                if (data.retCode === 200) {
                                    layer.msg('删除成功');
                                    loadFileTable();

                                } else {
                                    layer.msg('删除失败')
                                }
                            }
                        })
                    })

                }
               break;
            case 'ser2':
                // 搜索条件
                var filNam = $('#filNam').val(),
                    filNum = $('#filNum').val(),
                    verNum = $('#verNum').val();
                table.reload('file_table', {
                    method: 'post'
                    , where: {
                        'filename': filNam,
                        'fileNumber': filNum,
                        'versionNumber': verNum
                    }
                    , page: {
                        curr: 1
                    }
                });
                break;
        }
    });


    //监听行工具事件
    table.on('tool(file_on)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail2'){
            showFileDetail(data.id)
        }
       //行内删除
        if(layEvent === 'del2'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    url: "/dis/file/muldel",
                    type: "post",
                    data:{"ids":data.id},
                    dataType: "json",
                    success: function (data) {
                        if(data.retCode === 200){
                            layer.msg('删除成功');
                            // layui.use(['table','layer','laydate'], function() {
                            //     var table = layui.table;
                            loadFileTable();
                            // })
                        }else{
                            layer.msg('删除失败')

                        }
                    }
                })
            });
        }
    });

    table.on('rowDouble(file_on)', function(obj){
        var data = obj.data;
        var fileUrl = data.path;
        console.log(fileUrl);
        window.open('pdfjs/web/viewer.html?file=../..'+ fileUrl +'')
    });


});

//上传文件窗口
function  addFile() {
    layer.open({
        type: 1,
        title: '文件上传',
        // btn: ['查看', '返回'],
        // btnAlign: 'c',
        maxmin: 'ture',
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '500px'], //宽高
        content: '<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">' +
            '</fieldset>' +
            '<div class="layui-upload">' +
            '  <button class="layui-btn layui-btn-normal" id="fileUpload">选择多文件</button>' +
            '  <div class="layui-upload-list">' +
            '    <table class="layui-table">' +
            '      <thead>' +
            '        <tr><th>文件名</th>' +
            '        <th>大小</th>' +
            '        <th>状态</th>' +
            '        <th>操作</th>' +
            '      </tr></thead>' +
            '      <tbody id="filInfoView"></tbody>' +
            '    </table>' +
            '  </div>' +
            '  <button type="button" class="layui-btn" id="testListAction">开始上传</button>' +
            '</div>' +
            ''
        // Cancel:function(){
        //     layui.use('table',function () {
        //         var table = layui.table;
        //         table.reload('file_table',{
        //             where:{
        //                 a:1,
        //                 b:2
        //             },
        //             page:{
        //                 curr:1
        //             }
        //         })
        //     });
        //
        // }
        ,success: function () {
            layui.use('upload', function () {
             var upload = layui.upload;
                var demoListView = $('#filInfoView')
                    ,uploadListIns = upload.render({
                    elem: '#fileUpload'
                    ,url: '/dis/file/multiupload'     //在这里定义上传文件的接口
                    ,accept: 'file'
                    ,id:'fileUpload'
                    ,exts:'pdf'
                    ,multiple: true
                    ,auto: false
                    ,bindAction: '#testListAction'
                    ,choose: function(obj){
                        var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                        //读取本地文件
                        obj.preview(function(index, file, result){
                            var tr = $(['<tr id="upload-'+ index +'">'
                                ,'<td>'+ file.name +'</td>'
                                ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                                ,'<td>等待上传</td>'
                                ,'<td>'
                                ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                                ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                                ,'</td>'
                                ,'</tr>'].join(''));

                            //单个重传
                            tr.find('.demo-reload').on('click', function(){
                                obj.upload(index, file);
                                loadFileTable('#fileUpload');
                            });

                            //删除
                            tr.find('.demo-delete').on('click', function(){
                                delete files[index]; //删除对应的文件
                                tr.remove();
                                uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                            });

                            demoListView.append(tr);
                        });
                    }
                    ,done: function(res, index, upload){
                        if(res.retCode === 200){ //上传成功
                            layer.msg("上传成功");
                            var tr = demoListView.find('tr#upload-'+ index)
                                ,tds = tr.children();
                            tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                            tds.eq(3).html(''); //清空操作
                            loadFileTable('#fileUpload');
                            return delete this.files[index]; //删除文件队列已经上传成功的文件
                        }
                        this.error(index, upload);
                    }
                    ,error: function(index, upload){
                        var tr = demoListView.find('tr#upload-'+ index)
                            ,tds = tr.children();
                        tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                        tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
                    }
                });

            });

        }
    })

}



//文件属性定义
function defFile_Pro(file_Id) {
    layer.open({
        type: 1,
        title: '文件属性',
        btn: ['确认定义', '清除定义'],
        // btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '500px'], //宽高
        content:' <form>\n' +
            // '<button  type="button" id="cloCho" class="layui-btn layui-btn-sm ">关闭多选</button>' +
            '<br>' +
            '          <label for="lin_Cho">选择产线：</label>\n' +
            '          <select name="lin_Cho" id="lin_Cho" xm-select="lin_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-search-type="title" xm-select-direction="down"></select>\n' +
            '<button  type="button" id="defCho" class="layui-btn layui-btn-sm ">确认选择</button>' +
            '<br/>' +
            '          <label for="ber_Cho">选择型号：</label>\n' +
            '          <select name="ber_Cho" id="ber_Cho" xm-select="ber_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-search-type="title" xm-select-direction="down"></select>\n' +
            '          <label for="pro_Cho">选择工序：</label>\n' +
            '          <select name="pro_Cho" id="pro_Cho" xm-select="pro_Cho" xm-select-search="" xm-select-search-type="dl" xm-select-search-type="title" xm-select-direction="down"></select>\n' +
            '    </form>\n' +
            '    <script src="formSelects/src/formSelects-v4.js" ></script>\n' +
            '    <link rel="stylesheet" href="formSelects/dist/formSelects-v4.css">'
        ,success:function () {
            // $('#defCho').bind('click',onCho)
            var formSelects = layui.formSelects;
            // $("#cloCho").click(function(){
            //     alert('已关闭')
            //     formSelects.closed(null,function () {
            //     });
            // });
            // $("#defCho").click(function(){
            //     alert('拉取型号/工序数据中')
            //     formSelects.opened(null, function(id){
            //         console.log('打开了');
            //     });
            // });
            formSelects.render();
            $.get('/dis/line/selectall',{},function(res){     //第一个下拉框生产线请求地址 无参数请求

                var line_arr = new Array();        //定义个数字装远程数据
                for (var i = 0; i < res.data.length; i++) {      //遍历远程数据
                    // alert(res.data[i].id);
                    line_arr.push({
                        name: res.data[i].name,  //生产线
                        value: res.data[i].id//生产线序号

                    })
                }
                formSelects.data('lin_Cho', 'local', {arr: line_arr});
            });
            $('#defCho').click(function () {
                var a = formSelects.value('lin_Cho', 'val'),
                    lineId = a.toString();
                $('#ber_Cho #pro_Cho').remove();
                // console.log(lineId);
                // console.log(typeof lineId);
                // console.log(lineId);
                $.get('/dis/model/selectByLinId',          //型号选择接口
                    {lineId:lineId},function (res2) {
                        var pro_arr = new Array();        //定义个数字装远程数据
                        for (var i = 0; i < res2.data.length; i++) {      //遍历远程数据
                            pro_arr.push({
                                name: res2.data[i].type,     //下拉框各选项要显示的名称
                                value: res2.data[i].id        //下拉框各选项实际值
                            })
                        }
                        formSelects.data('ber_Cho', 'local', {arr: pro_arr});        //渲染第三个下拉框数据
                    });
                //工序
                $.get('/dis/device/selectByLineId',     //工序选择接口
                    {lineId: lineId}, function (res1) {     //第二个下拉框型号的请求地址 有参数 name是后台controller对应的参数，val.value为当前选择的第一个下拉框值
                        var type_arr = new Array();        //定义个数字装远程数据
                        for (var i = 0; i < res1.data.length; i++) {      //遍历远程数据
                            type_arr.push({
                                name: res1.data[i].process_name,     //下拉框各选项要显示的名称
                                value: res1.data[i].id        //下拉框各选项实际值
                            })
                            // console.log(res1.data[i].id)
                        }
                        formSelects.data('pro_Cho', 'local', {arr: type_arr});        //渲染第二个下拉框数据
                    })
            });
        }

        ,yes:function (index3){
            var layer = layui.layer;
            var formSelects = layui.formSelects;

                //轴承id
               var  a1 = formSelects.value('ber_Cho','val'),
                   berId = a1.toString();
               console.log(a1);
               console.log(berId);
                //设备id
                var a2 = formSelects.value('pro_Cho','val'),
                    proId = a2.toString();
                console.log(a2);
                console.log(proId);
            $.post('/dis/file/makerelate',        //数据发送接口
                {fileId:file_Id,berIds:berId,dids:proId},
                function (res) {
                    if (res.retCode === 200){
                        layer.msg('属性定义成功');
                        // layui.use('table',function () {
                        loadAttTable();
                        layer.close(index3);
                        // })
                    }else{
                        layer.msg('属性定义失败')
                    }
                })

        }
    })
}

//文件信息修改窗口
function modFil_inf(file_id){
    layer.open({
        type: 1,
        title: '文件信息修改',
        btn:'确认修改',
        btnAlign: 'c',
        skin: 'layui-layer-rim', //加上边框
        area: ['800px', '500px'], //宽高
        content: '<div id="modFil_inf" align="center">' +
          /*  ' <label for=\'fil_nam\' style="margin:5px;">文件名称:</label>' +
            '<input type="text" id="fil_nam" name="fil_nam"  style="margin:5px;"> ' +
            '<br/>' +*/
            ' <label for=\'fil_num\' style="margin:5px;">文件编号:</label>' +
            '<input type="text" name="fil_num" id="fil_num" / style="margin:5px;"> ' +
            '<br/>' +
            ' <label for=\'spot_title2\' style="margin:5px;">文件版本:</label>' +
            '<input type="text" name="ver_num" id="ver_num" style="margin:5px;"> ' +
            '</div>'
        ,yes: function(index4){
            layui.use(['table','layer','laydate'], function(){
                layer.confirm('确认修改或定义？',{title:'修改定义'},function(){
                    // var $=layui.jquery;
                   // var fil_nam=$('#fil_nam').val();
                    var fil_num=$('#fil_num').val();
                    var ver_num=$('#ver_num').val();

                    $.post('/dis/file/update',
                        {'id':file_id,'fil_num':fil_num,'ver_num':ver_num},
                        function (inf){
                            if(inf.retCode === 200){
                                layer.msg('信息修改成功！');
                                // layui.use(['table','layer','laydate'], function() {
                                    var table = layui.table;
                                    table.reload('file_table', {
                                        where: {
                                            a: 1,
                                            b: 2
                                        },
                                        page: {
                                            curr: 1
                                        }
                                    });
                                // });
                                layer.close(index4);
                            } else{
                                layer.msg('信息修改失败')
                            }
                        }
                    )
                })
            })

        }
    });
}

//文件属性查看弹出框
function showFileDetail(file_id) {
    layer.open({
        type: 1,
        title: '文件属性',
        // btn: ['查看', '返回'],
        // btnAlign: 'c',
        maxmin: 'ture',
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '500px'], //宽高
        content: '<div align="center" aria-relevant="additions">' +
            '<table id=\'fileAttribute\' lay-filter="fileAttribute"></table>' +
            '</div>',
        success: function () {
            layui.use(['table', 'layer', 'laydate'], function () {
                var table = layui.table;
                table.render({
                    elem: '#fileAttribute'
                    , height: 420
                    , width: 1000
                    , url: '/dis/fileType/selectAllByFid' //文件属性查看表数据接口
                    , where:{'file_Id':file_id}
                    , title: '线位-型号表'
                    , skin: 'line'
                    , toolbar: '#detailTitleBtn'
                    , page: true //开启分页
                    , cols: [[ //表头
                        {type: 'checkbox', align: 'center', width: '10%'}
                        ,{type: 'numbers',title:'序号',sort:true,align: "center",width:'10%'}
                        , {field: 'id', align: 'center', title: 'id',  sort: true,hide:true}
                        , {field: 'prolineName', align: 'center', title: '产线', width: '20%',sort:true}
                        , {field: 'tname', align: 'center', title: '型号', width: '20%',sort:true}
                        , {field: 'dname', align: 'center', title: '工序', width: '20%'}
                        , {field: '', align: 'center', title: '操作', width: '10%'}

                    ]]
                });

                //监听头工具栏
                table.on('toolbar(fileAttribute)', function (obj) {
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
                        case 'add5':
                            defFile_Pro(file_id);
                            break;

                        case 'delete5':
                            if (data.length === 0) {
                                layer.msg('请选择一行');
                            } else {
                                var layer = layui.layer;
                                layer.confirm('确认删除吗？', {title: "删除属性"}, function () {
                                    $.ajax({
                                        url: "/dis/fileType/mulDel",    ///文件属性表删除数据接口
                                        type: "post",
                                        data: {"ids": ids},
                                        dataType: "json",
                                        success: function (data) {
                                            if (data.retCode === 200) {
                                                layer.msg('删除成功');
                                                loadAttTable();
                                            } else {
                                                layer.msg('删除失败')
                                            }
                                        }

                                    });
                                });

                            }
                            break;
                        case 'ser5':
                            // 搜索条件
                            var typeName = $('#type1Name').val();
                            table.reload('fileAttribute', {
                                method: 'post'
                                , where: {
                                    'typeName': typeName
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

//重载文件表格
function loadFileTable(vale) {
        var table = layui.table;
        table.reload('file_table',{
            where:{
                a:1,
                b:2
            },
            page:{
                curr:1
            }
        })

}

//重载文件属性表格
function loadAttTable(vale) {
    var table = layui.table;
    table.reload('fileAttribute',{
        where:{
            a:1,
            b:2
        },
        page:{
            curr:1
        }
    })
}