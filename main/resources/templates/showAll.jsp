<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>视频管理页面</title>
    <link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<table class="layui-hide" id="videoTable" lay-filter="videoFilter"></table>

<!-- 当script的type="text/html"时，可以在其中写html代码，但是这些标签不会显示在页面上 -->
<script type="text/html" id="editBar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-
danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.use(['table','layer','jquery','form'], function(){
        var table = layui.table;
        var layer = layui.layer;
        var $ = layui.jquery;
        var form = layui.form;

        table.render({//加载数据表格
            elem: '#videoTable',			//element，绑定页面table的id值
            url:'selectAllVideoByLimits',			//请求地址:数据的来源:数据必须是json格式
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.list //解析数据列表
                };
            },
            cols: [[
                {field:'vName',title: '视频称'},
                {field:'director',title: '导演'},
                {field:'typeName',title: '视频类别'},
                {field:'imgPath',title: '类别',templet: '<div><img src="{{ d.imgPath }}" style="width:80px; height:30px;"></div>'},
                {field:'isVip',title: '是否会员',
                    templet: function(d){   // 自定义属性
                        var str = "";
                        if(d.isVip == 0){
                            str = "会员";
                        }else{
                            str = "非会员";
                        }
                        return str;
                    }
                },
                {fixed: 'right', title:'操作', toolbar: '#editBar'}
            ]],
            page:true,
            limits:[5,10,15,20,25,30]
        });

        //监听行工具事件
        //tool(typeFilter)对应table表格中的lay-filter="typeFilter"
        table.on('tool(videoFilter)', function(obj){
            var data = obj.data; //选中的哪一行数据
            var vid = data.vid;	//视频id
            if(obj.event === 'del'){//点击删除按钮
                layer.confirm('确定删除么？', function(index){
                    $.post('deleteVideoById',{vid:vid},function(result){
                        if(result.status){
                            layer.close(index);//关闭弹框
                            //刷新表格
                            table.reload('videoTable',{});
                        }else{
                            layer.msg(result.message);
                        }
                    },"json");
                });
            }else if(obj.event === 'edit'){//点击修改按钮
                layer.open({
                    type:1,
                    title:"修改视频信息",
                    content: $("#editPanel").html(),//利用jquery选择器获取元素里面的html内容
                    area:['400px','600px'],
                    btn: ['提交', '重置', '取消'],
                    yes: function(index, layero){
                        var formObj = form.val("editFilter");	//这是js对象，无法发送给后台
                        var jsonStr = JSON.stringify(formObj);	//将js对象转换成json格式字符串
                        $.post('updateVideoByVid',{vid:vid,jsonStr:jsonStr},function(result){
                            if(result.status){
                                layer.close(index);//关闭弹框
                                table.reload('videoTable',{});
                            }else{
                                layer.msg(result.message);
                            }
                        },"json");
                    },btn2: function(index, layero){
                        form.val('editFilter',{//addFilter得值对应得是 form中带有class="layui-form"得元素上 lay-filter="addFilter"
                            vName:data.vName,
                            director:data.director,
                            tid:data.tid,
                            isVip:data.isVip
                        });
                        return false; //不关闭窗口
                    },btn3: function(index, layero){
                    },success: function(index, layero){
                        //打开修改按钮，显示的面板中应该有所有视频分类，并且默认选择当前视频的类别
                        //利用ajax请求数据，将数据动态写入到select标签中
                        $.ajaxSettings.async = false;//设置ajax为同步状态，只有ajax彻底执行结束，再加载其他内容
                        $.post('selectAllType',{},function(result){
                            if(result.status){
                                var html = '';
                                var list = result.list;
                                for(var i = 0;i<list.length;i++){
                                    html+='<option value="'+list[i].tid+'">'+list[i].typeName+'</option>';
                                }
                                $("select").append(html);
                            }else{
                                layer.msg(result.message);
                            }
                        },"json");
                        form.val('editFilter',{//addFilter得值对应得是 form中带有class="layui-form"得元素上 lay-filter="addFilter"
                            vName:data.vName,
                            director:data.director,
                            tid:data.tid,
                            isVip:data.isVip
                        });
                        //当form表单中出现: 单选框、多选框、下拉菜单、文本域等组件时，是不会显示出来的
                        form.render();
                    }
                });
            }
        });

    });
</script>

<script type="text/html" id="editPanel">
    <form class="layui-form" lay-filter="editFilter">
        <div class="layui-form-item">
            <label class="layui-form-label">视频名称</label>
            <div class="layui-input-inline">
                <input type="text" name="vName" placeholder="请输入视频名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">导演</label>
            <div class="layui-input-inline">
                <input type="text" name="director" placeholder="请输入导演" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">类别</label>
            <div class="layui-input-inline">
                <select name="tid" lay-verify="required"></select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否会员</label>
            <div class="layui-input-block">
                <input type="radio" name="isVip" value="0" title="会员">
                <input type="radio" name="isVip" value="1" title="非会员">
            </div>
        </div>
    </form>
</script>



</body>
</html>