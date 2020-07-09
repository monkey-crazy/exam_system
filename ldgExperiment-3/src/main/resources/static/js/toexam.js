/**
 * toexam.html
 */
$(function() {
		$("#submitquestion").click(submitPaper);

		function submitPaper() {
			if (!window.confirm("你确定要交卷吗"))
				return;
			var answers = [];
			var tds = $("td");
			for (let i = 0; i < tds.length; i++) {
				var td = $(tds.get(i));
				var ansJson = {};
				ansJson.queNo = td.find(":nth-child(1)").html();
				ansJson.queBaseNo = td.find(":nth-child(2)").html();
				ansJson.queAnswer = td.find(":nth-child(4)").val();
				answers[i] = ansJson; //answers. push( ansJosn)|
			}

			$.ajax({

				url : "/test/student/submitPaper",
				type : 'post',
				data : JSON.stringify(answers),
				contentType : 'application/json',
				success : function(data) {
					alert(data);
					$("textarea").attr("disables", true);
				}
			});
		}

		// {no:1, answer:...}

	});