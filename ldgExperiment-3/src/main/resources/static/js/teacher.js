/**
 * teacher.html
 */
$(function() {
		
	//添加老师
		$("#addteacher").click(function() {
				// $("#addModal").modal('hide');
				if ($("#ids").val() != "" && $("#name").val() != ""&& $("#major").val() != ""&& $("#pwd").val() != "") {
					//alert("5555");		
					$.post("/test/admin/addteacher",{
												teacherId : $("#ids").val(),
												teacherName : $("#name").val(),
												major : $("#major").val(),
												password : $("#pwd").val()
											},function(data) {
												//alert("123");
												// 返回的参数是添加的信息
												var tb = $("#tb1");
												var tr = $("<tr></tr>");
												// 获取最后一个记录的序号
												var ind = $("#tb1 tr:last-child").children().eq(0).html();
												ind = parseInt(ind) + 1;
												tr.append("<td>" + ind
														+ "</td>");
												tr.append("<td>"
														+ data.teacherId
														+ "</td>");
												tr.append("<td>"
														+ data.teacherName
														+ "</td>");
												tr.append("<td>" + data.major
														+ "</td>");
												tr.append("<td><button class='update'>修改</button></td>");
												tr.append("<td><button class='delete'>删除</button></td>");
												tb.append(tr);
											});
						} else {
							alert("不能为空！");
						}
						;
						});
		// 修改老师信息
		$("#tb1").on("click", ".update", function() {// 用on动态绑定事件，要在其父类上绑定
			//console.log("1");
			$("#updateModal").modal('show');
			var tr = $(this).parent().parent();
			$("#u_id").val(tr.find(":nth-child(2)").html());
			$("#u_name").val(tr.find(":nth-child(3)").html());
			$("#u_major").val(tr.find(":nth-child(4)").html());

			//console.log("2"+$("#u_id").val());
			$("#updateteacher").unbind('click').click(function() {//.unbind('click')解绑上一次修改时定义的事件
				//console.log("3");
				//alert("12");
				//$("#updateModal").modal('hide');
				if ($("#u_major").val() != "" && $("#u_name").val() != "") {
					$.post("/test/admin/updateteacher", {
						teacherId : $("#u_id").val(),
						teacherName : $("#u_name").val(),
						major : $("#u_major").val()
					}, function(data) {
						alert("修改成功");
						console.log("4" + data.teacherId);
						tr.find(":nth-child(3)").html(data.teacherName);
						tr.find(":nth-child(4)").html(data.major);
					}).error(function(xhr,errorText,errorType){
						alert("操作失败！");
						window.location.reload();
					});
				} else {
					alert("不能为空！");
				}
			});
		});
		//删除
		$("#tb1").on(
				"click",
				".delete",
				function() {//用on动态绑定事件，要在其父类上绑定
					if (!window.confirm("确认删除？"))
						return;
					//var ind=$("#tb1 tr:last-child").children().eq(0).html()
					var tr1 = $(this).parent().parent();
					var id = tr1.children().eq(1).html();
					//alert(id);
					$.post("/test/admin/deleteteacher", {
						teacherid : id
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
		//搜索
		$("#find").click(function() {
			
			
			//获取搜索id
			var teacherid = $("#findId").val();
			if(teacherid!=""){
			//	window.location.href="http://localhost:8080/test/admin/showteacher?page=0&size=10";  
			//清空当前数据
			var s = $("#tb1").children().eq(0).children().eq(0);
			while (s.next()[0]) {
				s = s.next();
				s.empty();
			}
			
			//alert(teacherid);
			//从后端请求数据
			$.post("/test/admin/findbyid", {
				teacherid : teacherid
			}, function(data) {
				//alert(data);
				var tb = $("#tb1");
				var tr = $("<tr></tr>");
				var td1 = $("<td>1</td>");
				var td2 = $("<td>" + data.teacherId + "</td>");
				var td3 = $("<td>" + data.teacherName + "</td>");
				var td4 = $("<td>" + data.major + "</td>");
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
				$("ul").hide();
			}).error(function(xhr,errorText,errorType){
				alert("该老师不存在！");
				window.location.reload();
			});
			
			};
		});
		//分页按钮
		var ss = $("#ii1").html();
		$("#ii1").html("共" + ss + "页");
		ss = $("#ii2").html();
		$("#ii2").html("第" + ss + "页");
	});