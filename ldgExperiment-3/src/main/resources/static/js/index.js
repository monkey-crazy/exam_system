/**
 * index.html
 */
$(function() {
	// 清空显示的地方
	// $("#consol").empty();
	// $("#consol").append("欢迎使用考试系统！！");

	var power = $(".power1").html();
	if (power < 1) {
		$("#body").empty();
		alert("您没有权限登陆，请联系管理员！");
	} else {

		// 查看个人信息
		$("#grxx").bind("click", function() {
			$("#consol").empty();
			$("#ckgrxx").css('display', 'inline');
		});

		// 查看报名信息

		$("#bmxx")
				.unbind("click")
				.bind(
						"click",
						function() {
							$("#consol").empty();
							// 获取权限信息
							var power = $(".power1").html();
							// alert(power);
							if (power < 3) {
								alert("权限不足,请联系管理员！");
								$("#consol").css('display', 'none');
							} else {
								$("#ckgrxx").css('display', 'none');
								$("#consol")
										.append(
												"<table id='tb2' class='table'></table>");
								$
										.post(
												"/test/student/showAllStudentExam",
												function(data) {

													if (typeof (data) == "string") {
														alert("没有报名信息!");
														return;
													} else {
														var tab = $("#tb2");
														var tr1 = $("<th>姓名</th><th>考试时间</th><th>考试地点</th><th>考试编号</th><th>操作</th>");
														tab.append(tr1);
														$
																.each(
																		data,
																		function(
																				seindex,
																				sevalue) {
																			let tr = $("<tr></tr>");
																			tr
																					.append("<td>"
																							+ sevalue.studentName
																							+ "</td>");
																			tr
																					.append("<td>"
																							+ sevalue.examDate
																							+ "</td>");
																			tr
																					.append("<td>"
																							+ sevalue.examPla
																							+ "</td>");
																			tr
																					.append("<td>"
																							+ sevalue.examId
																							+ "</td>");
																			var tb = $("<td></td>");

																			if (sevalue.flag == 0) {
																				let ta = $("<a class='btn btn-primary' >去考试 </a>");
																				ta
																						.attr(
																								"href",
																								"/test/student/toExam?eid="
																										+ sevalue.examId);
																				tb
																						.append(ta);
																			} else {

																				ta = $("<button class='findScore btn btn-primary' >查成绩 </button>");
																				tb
																						.append(ta);
																				// ta.attr("href","/test/student/getScore?eid="+sevalue.examId);
																			}
																			// alert(sevalue.examId);

																			tr
																					.append(tb);
																			tab
																					.append(tr);

																		});
													} // 查询成绩
													$(".findScore")
															.unbind("click")
															.bind(
																	"click",
																	function() {

																		var that = $(this);
																		var tr = that
																				.parent()
																				.parent();
																		var id = tr
																				.find(
																						":nth-child(4)")
																				.html();
																		console
																				.log(id)
																		$
																				.get(
																						"/test/student/getScore",
																						{
																							eid : id
																						},
																						function(
																								data) {
																							if (data == -1) {
																								alert("老师还未批改，请耐心等候。");
																							} else {
																								alert("你的成绩为： "
																										+ data);
																							}

																						});
																	});
												});
							}

						});
		var i = 0;
		$("#qx").click(function() {
			if (i % 2 == 0) {
				$("#xs").css('display', 'inline');
			} else {
				$("#xs").css('display', 'none');
			}
			i = i + 1;
		});

		// 阅卷
		$("#markpaper")
				.click(
						function() {

							var power = $(".power1").html();
							// alert(power);
							if (power < 3) {
								alert("权限不足,请联系管理员！");

							} else {

								// alert("q");
								$("#consol").empty();
								$("#ckgrxx").css('display', 'none');
								// alert("123");
								var tab = $("<table class='table'></table>");
								$
										.post(
												"/test/teacher/querymarkpaper",
												function(data) {

													// alert(data.length);
													// alert(data[0]);
													// alert(data.get(0));
													var tr = $("<tr></tr>");
													var td1 = $("<td>专业</td>");
													var td2 = $("<td>操作</td>");
													tr.append(td1);
													tr.append(td2);
													tab.append(tr);

													for (var i = 0; i < data.length; i++) {
														var tr = $("<tr></tr>");
														var td1 = $("<td>"
																+ data[i]
																+ "</td>");
														var ma = $("#major1")
																.html();
														// alert(ma);
														// alert(data[i]);
														if (ma == data[i]) {

															var td2 = $("<td><a href='/test/teacher/markpaper?major="
																	+ data[i]
																	+ " class='btn btn-primary'>阅卷</a></td>");
														} else {
															var td2 = $("<td><a th:href='@{/teacher/markpaper(major="
																	+ data[i]
																	+ ")}'  style='pointer-events: none;'>阅卷</a></td>");
														}
														// alert(td2)
														tr.append(td1);
														tr.append(td2);
														tab.append(tr);
													}

												}).error(
												function(xhr, errorText,
														errorType) {
													alert("操作失败！");
													window.location.reload();
												});

								$("#consol").append(tab);
							}
						});

		// 修改个人信息
		$("#updatepw").click(function() {
			var power = $(".power1").html();
			// alert(power);
			if (power < 2) {
				alert("权限不足,请联系管理员！");

			} else {

				// console.log("1");
				$("#updateModal").modal('show');
				var tab = $("#tb3 tr:last-child").children();
				$("#uid").val(tab.eq(0).html());
				$("#name").val(tab.eq(1).html());
				$("#major").val(tab.eq(2).html());
				if (power < 4) {
					$("#name").attr("readonly", "readonly");
					$("#major").attr("readonly", "readonly");
				}

				// alert(tab.eq(0).html());
				$("#updatexx").unbind('click').click(function() {// .unbind('click')解绑上一次修改时定义的事件
					// console.log("3");
					// $("#updateModal").modal('hide');
					var indss = $(".major2").html();

					$.post("/test/" + indss + "/updatexx", {
						uid : $("#uid").val(),
						name : $("#name").val(),
						major : $("#major").val(),
						pwd : $("#pwd").val()

					}, function(data) {

						// console.log("4");
						alert("修改成功")

					}).error(function(xhr, errorText, errorType) {
						alert("修改失败！");
						window.location.reload();
					});

				});
			}

		});

	}
});
