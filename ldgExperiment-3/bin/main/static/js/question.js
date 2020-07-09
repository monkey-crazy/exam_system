/**
 * question.html
 */
$(function(){
	//新增试题
		$("#addquestion").click(function() {
			//$("#addModal").modal('hide');
			//alert("1");
			var power = $(".power1").html();
			//alert(power);
			if(power<3){
				alert("权限不足,请联系管理员！");
				//$("button").attr("disabled", true);
			}else{
			
			
			
			$.post("/test/teacher/addquestion", {

				major : $("#ma").val(),
				content : $("#context").val()
				
			}, function(data) {

				  
				alert("添加成功");
				window.location.href="http://localhost:8080/test/teacher/showquestion?page=0&size=10";
//				//获取最后一个记录的序号
//				
//				
//				//无法获得添加的题号的id
//				var ind = $("#tb1 tr:last-child").children().eq(0).html();
//				if(ind==null){
//					ind=0;
//				}
//				ind = parseInt(ind) + 1;
//				var tb = $("#tb1");
//				var tr = $("<tr></tr>");
//				//$("#sp").html(sp);
//					
//				tr.append("<td>" + ind + "</td>");
//				//alert(data.questionId);
//				tr.append("<td>" + data.questionId + "</td>");
//				tr.append("<td>" + data.content + "</td>");
//				tr.append("<td>" + data.major + "</td>");
//				tr.append("<td><button class='update'>修改</button></td>");
//				tr.append("<td><button class='delete'>删除</button></td>");
//				tb.append(tr);
			
			}).error(function(xhr,errorText,errorType){
				alert("操作失败！");
				window.location.reload();
			});
				}
		});
	//修改试题
		$("#tb1").on("click", ".update", function() {//用on动态绑定事件，要在其父类上绑定
			
			var power = $(".power1").html();
			//alert(power);
			if(power<3){
				alert("权限不足,请联系管理员！");
				//$("button").attr("disabled", true);
			}else{
			//console.log("1");
			$("#updateModal").modal('show');
			var tr = $(this).parent().parent();
			$("#th").val("题号："+tr.find(":nth-child(2)").html());
			$("#context2").val(tr.find(":nth-child(3)").html());
			//console.log("2");
			//修改考试
			$("#updatequestion").unbind('click').click(function() {//.unbind('click')解绑上一次修改时定义的事件
				//console.log("3");
				//$("#updateModal").modal('hide');
				$.post("/test/teacher/updatequestion", {
					questionId :tr.find(":nth-child(2)").html(),
					content : $("#context2").val()
				}, function(data) {
					
					//	console.log("4");
					tr.find(":nth-child(3)").html(data.content);

				}).error(function(xhr,errorText,errorType){
					alert("操作失败！");
					window.location.reload();
				});

			});}

		});

	//删除试题
	
	$("#tb1").on("click",".delete",function() {//用on动态绑定事件，要在其父类上绑定
		var power = $(".power1").html();
		//alert(power);
		if(power<4){
			alert("权限不足,请联系管理员！");
			//$("button").attr("disabled", true);
		}else{
		
		
			if (!window.confirm("确认删除？"))
				return;
			//var ind=$("#tb1 tr:last-child").children().eq(0).html()
			var tr1 = $(this).parent().parent();
			var id = tr1.children().eq(1).html();
			//alert(id);
			$.post("/test/teacher/deletequestion", {
				questionid : id
			}, function(data) {
				alert(data);
				tr1.empty();
				//删除后序号自动前补
				var nex = tr1;
				while (nex.next()[0]) {
					nex = nex.next();
					//alert(nex.children().eq(0).html());
					nex.children().eq(0).html(
							parseInt(nex.children().eq(0).html()) - 1);
					//alert(nex.children().eq(0).html());
					//alert(i.children().eq(0).html());
				}
			}).error(function(xhr,errorText,errorType){
				alert("操作失败！");
				window.location.reload();
			});}
		});
	//按id搜索题
		$("#find1").click(function() {
			
			
			//获取搜索id
			var qid = $("#findId").val();
			if(qid!=""){
			
			//清空当前数据
			var s = $("#tb1").children().eq(0).children().eq(0);
			while (s.next()[0]) {
				s = s.next();
				s.empty();
			}
			
			//alert(qid);
			//从后端请求数据
			$.post("/test/teacher/findById", {
				questionid : qid
			}, function(data) {
				var tb = $("#tb1");
				var tr = $("<tr></tr>");
				var td1 = $("<td>1</td>");
				var td2 = $("<td>" + data.questionId + "</td>");
				var td3 = $("<td>" + data.content + "</td>");
				var td4 = $("<td>" + data.major +"</td>");
				var td5 = $("<td></td>");
				var bu1 = $("<button>修改</button>");
				bu1.attr("class", "update");
				td5.append(bu1);
				var td6 = $("<td></td>");
				var bu2 = $("<button>删除</button>");
				bu2.attr("class", "delete");
				td6.append(bu2);
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				tb.append(tr);
				$("#fy").hide();
			}).error(function(xhr,errorText,errorType){
				alert("输入的试题id不存在！");
				window.location.reload();
			});};
		});
	//按试题内容查找
	$("#find2").click(function() {
			
			
			//获取搜索id
			var cont = $("#findCon").val();
			
			
			//清空当前数据
			var s = $("#tb1").children().eq(0).children().eq(0);
			while (s.next()[0]) {
				s = s.next();
				s.empty();
			}
			
			//alert(teacherid);
			//从后端请求数据
			$.post("/test/teacher/findByContent", {
				content : cont
			}, function(data) {
				//alert(data.length);
					if(data.length==0){
						alert("没有满足要求的试题！")
						window.location.reload();
					}
					else{
				for(var i=0 ; i<data.length;i++){
					var stu=data[i];
					var tb = $("#tb1");
					var tr = $("<tr></tr>");
					var td1 = $("<td>"+i+1+"</td>");
					var td2 = $("<td>" + stu.questionId + "</td>");
					var td3 = $("<td>" + stu.content + "</td>");
					var td4 = $("<td>" + stu.major +"</td>");
					var td5 = $("<td></td>");
					var bu1 = $("<button>修改</button>");
					bu1.attr("class", "update");
					td5.append(bu1);
					var td6 = $("<td></td>");
					var bu2 = $("<button>删除</button>");
					bu2.attr("class", "delete");
					td6.append(bu2);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tr.append(td4);
					tr.append(td5);
					tr.append(td6);
					tb.append(tr);
					$("#fy").hide();
				}			
			}}).error(function(xhr,errorText,errorType){
				alert("没有满足要求的试题！");
				window.location.reload();
			});
		}
			
	);
	
	
	
	//分页按钮
	var ss = $("#ii1").html();
	$("#ii1").html("共" + ss + "页");
	ss = $("#ii2").html();
	$("#ii2").html("第" + ss + "页");
});