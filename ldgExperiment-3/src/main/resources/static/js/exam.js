/**
 * exam.html
 */
$(function() {
		$(".baoming").bind("click", function() {
			var power = $(".power1").html();
			if (power < 3) {
				$("#body").empty();
				alert("您没有权限登陆，请联系管理员！");
			} else {
			var that = $(this);
			$("#addModal").modal('show');

			$("#addexam").unbind("click").bind("click", function() {
				$.post("/test/student/baoming", {
					examId : that.parent().attr("id")
				}, function(data) {
					if (data == "failsure")
						alert("请登陆! ");
					else if(data=="success"){
						alert("报名成功! ");
						that.attr("disabled", true);
					}else {
						alert("你已经报过这场考试了!! ");
						that.attr("disabled", true);
					}
				});

			});}

		});
	});