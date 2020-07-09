/**
 * exam.html
 */
	$(function() {
		

	//更新考试
	//console.log("0");
		//$(".update").click(function() {//只能应用于加载页面时绑定的事件动态增加的不能绑定
		$("#tb1").on("click", ".update", function() {//用on动态绑定事件，要在其父类上绑定
			var power = $(".power1").html();
			//alert(power);
			if(power<4){
				alert("权限不足,请联系管理员！");
				//$("button").attr("disabled", true);
			}else{
			
		
			//console.log("1");
			$("#updateModal").modal('show');
			var tr = $(this).parent().parent();
			$("#updateExamId").val(tr.find(":nth-child(2)").html());
			$("#updateExamId").attr("disabled", true);
			$("#updateExamPla").val(tr.find(":nth-child(3)").html());
			$("#updateExamDate").val(tr.find(":nth-child(4)").html());
			//console.log("2");
			//修改考试
			$("#updateExam").unbind('click').click(function() {//.unbind('click')解绑上一次修改时定义的事件
				//console.log("3");
				//$("#updateModal").modal('hide');
				var inds=$("#indx").html();
				//alert(inds);
				if(inds==null){
					inds="teacher";
				}
				
				
				$.post("/test/"+inds+"/addexam", {
					examId : $("#updateExamId").val(),
					examDate : $("#updateExamDate").val(),
					examPla : $("#updateExamPla").val()
				}, function(data) {
					tr.find(":nth-child(3)").html(data.examPla);
					//	console.log("4");
					tr.find(":nth-child(4)").html(data.examDate);
					alert("修改成功！");

				}).error(function(xhr,errorText,errorType){
					alert("操作失败！");
					window.location.reload();
				});

			});}

		});

		//新建考试
		$("#addexam").click(function() {
			//$("#addModal").modal('hide');
			//alert("1");
			$.post("/test/admin/addexam", {

				examId : $("#examId").val(),
				examPla : $("#examPla").val(),
				examDate : $("#examDate").val()
			}, function(data) {

				//alert("2");
				//获取最后一个记录的序号
				var ind = $("#tb1 tr:last-child").children().eq(0).html();
				ind = parseInt(ind) + 1;
				var tb = $("#tb1");
				var tr = $("<tr></tr>");
				//$("#sp").html(sp);
				//	alert(ind);
				tr.append("<td>" + ind + "</td>");
				tr.append("<td>" + data.examId + "</td>");
				tr.append("<td>" + data.examPla + "</td>");
				tr.append("<td>" + data.examDate + "</td>");
				tr.append("<td><button class='update'>修改</button></td>");
				tr.append("<td><button class='delete'>删除</button></td>");
				tb.append(tr);
				alert("增加成功！")
			}).error(function(xhr,errorText,errorType){
				alert("操作失败！");
				window.location.reload();
			});
		});

		//删除考试


	$("#tb1").on("click",".delete",function() {//用on动态绑定事件，要在其父类上绑定
			if (!window.confirm("确认删除？"))
				return;
			//var ind=$("#tb1 tr:last-child").children().eq(0).html()
			var tr1 = $(this).parent().parent();
			var id = tr1.children().eq(1).html();
			//alert(id);
			$.post("/test/admin/deleteexam", {
				examid : id
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
			});
		});
		//搜索考试
		$("#find").click(function() {
			
			

			var inds=$("#indx").html();
			//alert(inds);
			if(inds==null){
				inds="teacher";
			}
			
			
			//获取搜索id
			var examid = $("#findId").val();
			
			if(examid!=""){
			
				//清空当前数据	
			var s = $("#tb1").children().eq(0).children().eq(0);
			while (s.next()[0]) {
				s = s.next();
				s.empty();
			}
			
			//alert(teacherid);
			//从后端请求数据
			$.post("/test/"+inds+"/findbyexamid", {
				examid : examid
			}, function(data) {
				
			//alert(data);
				var tb = $("#tb1");
				var tr = $("<tr></tr>");
				var td1 = $("<td>1</td>");
				var td2 = $("<td>" + data.examId + "</td>");
				var td3 = $("<td>" + data.examPla + "</td>");
				var td4 = $("<td>" + data.examDate + "</td>");
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
				if(inds=="admin"){
					tr.append(td6);
				}
				
				
				tb.append(tr);
				$("ul").hide();
			}).error(function(xhr,errorText,errorType){
				alert("你输入的考试编号不存在！");
				window.location.reload();
			});};
		});
		//分页按钮
		var ss = $("#ii1").html();
		$("#ii1").html("共" + ss + "页");
		ss = $("#ii2").html();
		$("#ii2").html("第" + ss + "页");
	});