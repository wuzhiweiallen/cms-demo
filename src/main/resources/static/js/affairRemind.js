$(function() {
    initDatagrid();
    /*$.getJSON("/moneyGift/getMoneyGiftByCondition",function(json){
     $('#mTb').datagrid('loadData', json);
     });*/
});

function initDatagrid() {
    /*$('#remindTime').datetimebox({
        closeText:'关闭',
        onSelect:function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            var h = date.getHours()
            m = (m < 10 ? '0' : '') + m;
            return y+'-'+m+'-'+ d + '' + h +':00:00';
        },
    });*/
    /*$('#remindTimeSearch').datetimebox({
        closeText:'关闭',
        onSelect:function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            var h = date.getHours()
            m = (m < 10 ? '0' : '') + m;
            return y+'-'+m+'-'+ d + '' + h +':00:00';
        },
    });*/
    $('#mTb').datagrid({
        loadMsg : '正在加载数据...',
        method : 'GET',
        url: "/affairRemind/getAffairRemindByCondition",
        striped : true,
        fitColumns : true,
        checkOnSelect : true,
        // 指明id为标识字段 跨页选择删除时不会取消上一页的选择
        idField : 'id',
        pagination : true,
        checkbox : true,
        fitColumns : true,
        pageSize : 10,
        pageList : [ 10, 20, 30],
        pageNumber : 1,
        columns : [ [ {
            field : 'subject',
            title : '主题',
            width : 200
        }, {
            field : 'content',
            title : '内容',
            width : 200
        }, {
            field : 'remindTime',
            title : '提醒时间',
            width : 200
        }, {
            field : 'cron',
            title : '定时器规则',
            width : 200
        }] ],
        toolbar : '#tb'

    });
}
var url;
function newAffairRemind(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
    url = '/affairRemind/saveAffairRemind';
}
function editAffairRemind(){
    var row = $('#mTb').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','修改');
        $('#fm').form('load',row);
        console.log(row.givenDate);
        /*$('#remindTime').datetimebox('setValue', row.remindTime);*/
        url = '/affairRemind/updateAffairRemind';
    }
}
function saveAffairRemind(){
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            if (result != null){
                var resobj = eval("(" + result + ")");
                var desc = resobj.desc;
                $.messager.show({    // show error message
                    title: '提示',
                    msg: desc
                });
                $('#dlg').dialog('close');        // close the dialog
                $('#mTb').datagrid('reload');    // reload the user data
            } else {
                $.messager.show({    // show error message
                    title: '提示',
                    msg: '未知错误'
                });
            }
        }
    });
}
function removeAffairRemind(){
    var row = $('#mTb').datagrid('getSelections');
    var idString = "";
    for(var i=0 ; i < row.length; i++){
        if(i == 0){
            idString = row[i].id;
        } else {
            idString = idString + "," + row[i].id;
        }
    }
    var id = {id:idString};
    if (row){
        $.messager.confirm('确认','您确定要删除吗？',function(r){
            if (r){
                $.ajax({
                    method : 'POST',
                    url: "/affairRemind/deleteAffairRemind/",
                    dataType:"json",
                    data: id,
                    //async: false, //true:异步，false:同步
                    //contentType: false,
                    //processData: false,
                    success: function (data) {
                        $.messager.show({    // show error message
                            title: '提示',
                            msg: data.desc
                        });
                        $('#mTb').datagrid('reload');    // reload the user data
                    },
                    error: function (err) {
                        alert("error");

                    }});
                $.post('/affairRemind/deleteAffairRemind/', id, function(result){
                    if (result){
                        var resobj = JSON.parse(result);

                    } else {
                        $.messager.show({    // show error message
                            title: '提示',
                            msg: '位置错误'
                        });
                    }
                },'json');
            }
        });
    }
}

function doSearch(){
    var subject = $("#subject").val();
    var remindTime = $("#remindTimeSearch").val();
    /*var remindTime = $('#remindTimeSearch').datetimebox('getValue');*/
    var url = "/affairRemind/getAffairRemindByCondition";
    if(subject){
        url = url + "?subject=" + subject;
        if(remindTime){
            url = url + "&remindTime=" + remindTime;
        }
    } else {
        if(remindTime){
            url = url + "?remindTime=" + remindTime;
        }
    }
    $.ajax({
        method : 'GET',
        url: url,
        dataType:"json",
        //async: false, //true:异步，false:同步
        //contentType: false,
        //processData: false,
        success: function (data) {
            $("#mTb").datagrid("loadData", data);
        },
        error: function (err) {
            alert("error");
        }
    });
}