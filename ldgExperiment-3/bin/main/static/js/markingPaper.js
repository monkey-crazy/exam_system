/**
 * markingPaper.html
 */
	$(function() {
		$("#submit").bind("click", function() {
		
			if (!window.confirm("确认提交？"))
				return;
			var giveScore = [];
			var tds = $("td");
			//alert(tds.length);
			for (var i = 0; i < tds.length; i++) {
				var td = $(tds.get(i));
				var scores =td.find("input").val();
				//alert(scores+"---1");
				if(scores==""){
					
					alert("您还有第"+(i+1)+"题没有评阅！");
					break;
				}
				//alert(scores+"---2")
				var scoreJson = {
					stuId :td.find(":nth-child(1)").html(),
					examId:td.find(":nth-child(2)").html(),
					queId :td.find(":nth-child(3)").html(),
					score :scores
				}
				giveScore[i] = scoreJson;
			}
			
			$.ajax({
				url : "/test/teacher/submitscore",
				type : 'post',
				data : JSON.stringify(giveScore),
				contentType : "application/json",
				success : function(data) {
					alert(data);
					$("input").attr("disabled", true);

				}
			});

		});
		
	});