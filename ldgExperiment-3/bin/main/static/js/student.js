/**
 * student.html
 */
$(function(){
	//修改学生信息
		$("#tb1").on("click", ".update", function() {//用on动态绑定事件，要在其父类上绑定
			//console.log("1");
			$("#updateModal").modal('show');
			var tr = $(this).parent().parent();
			$("#u_id").val(tr.find(":nth-child(2)").html());
			$("#u_name").val(tr.find(":nth-child(3)").html());
			

			//console.log("2"+$("#u_id").val());
			$("#updateStu").unbind('click').click(function() {//.unbind('click')解绑上一次修改时定义的事件
				//console.log("3");
				//alert("12");
				//$("#updateModal").modal('hide');
				if ($("#u_photo").val() != "" && $("#u_name").val() != "") {
					$.post("/test/admin/updatestu", {
						stuid : $("#u_id").val(),
						stuname : $("#u_name").val()}, 
						function(data) {
						console.log("4" + data.studentId);
						tr.find(":nth-child(3)").html(data.studentName);
						alert("修改成功");
					});
				} else {
					alert("不能为空！");
				}
			});
		});
	
	//删除学生
		$("#tb1").on("click",".delete",function() {//用on动态绑定事件，要在其父类上绑定
					if (!window.confirm("确认删除？"))
						return;
					//var ind=$("#tb1 tr:last-child").children().eq(0).html()
					var tr1 = $(this).parent().parent();
					var id = tr1.children().eq(1).html();
					//alert(id);
					$.post("/test/admin/deletestudent", {
						stuid : id
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
						
					});
				});
	
	//按学号查找
		$("#findid").click(function() {
			
			var inds=$("#indx").html();
			//alert(inds);
			if(inds==null){
				inds="teacher";
			}
			
			//获取搜索id
			var stuid = $("#findId").val();
			if(stuid!=""){
			
			//清空当前数据
			var s = $("#tb1").children().eq(0).children().eq(0);
			while (s.next()[0]) {
				s = s.next();
				s.empty();
			}
			
			
			//alert(teacherid);
			//从后端请求数据
			$.post("/test/"+inds+"/findstuid", {
				studentid : stuid
			}, function(data) {
				var tb = $("#tb1");
				var tr = $("<tr></tr>");
				var td1 = $("<td>1</td>");
				var td2 = $("<td>" + data.studentId + "</td>");
				var td3 = $("<td>" + data.studentName + "</td>");
				var td31 = $("<td>" + data.major + "</td>");
				var td4 = $("<td></td>");
				var im =$("<img  width='50px' height='80px'>");
				//用密吗的字段装字符串化的图片
				im.attr("src","data:image/png; base64,"+data.password);
				td4.append(im);
				
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
				tr.append(td31);
				tr.append(td4);
				if(inds=="admin"){
					tr.append(td5);
					tr.append(td6);
				}
				tb.append(tr);
				$("ul").hide();
			}).error(function(xhr,errorText,errorType){
				alert("输入的学生账号不存在！");
				window.location.reload();
			});
			
			
			};
		});
	
	
	//按姓名查找
	$("#findname").click(function() {
			
		var inds=$("#indx").html();
		//alert(inds);
		if(inds==null){
			inds="teacher";
		}
			//获取搜索id
			var stuname = $("#findName").val();
			//清空当前数据
			var s = $("#tb1").children().eq(0).children().eq(0);
			while (s.next()[0]) {
				s = s.next();
				s.empty();
			}
			
			//alert(teacherid);
			//从后端请求数据
			$.post("/test/"+inds+"/findstuname", {
				studentname : stuname
			}, function(data) {
				//alert(data.length);
				if(data.length==0){
					alert("没有符合条件的学生！");
					window.location.reload();
				}else{
					
				
				for(var i=0 ; i<data.length;i++){
					var stu=data[i];
					var tb = $("#tb1");
					var tr = $("<tr></tr>");
					var td1 = $("<td>"+(parseInt(i)+1)+"</td>");
					var td2 = $("<td>" + stu.studentId + "</td>");
					var td3 = $("<td>" + stu.studentName + "</td>");
					var td31 = $("<td>" + stu.major + "</td>");
					var td4 = $("<td></td>");
					var im =$("<img  width='50px' height='80px'>");
					//用密吗的字段装字符串化的图片
					im.attr("src","data:image/png; base64,"+stu.password);
					td4.append(im);
					
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
					tr.append(td31);
					tr.append(td4);
					if(inds=="admin"){
						tr.append(td5);
						tr.append(td6);
					}
					
					
					tb.append(tr);
					$("ul").hide();
				}
				}
			});
		}
			);
	
	
	
	//分页按钮
var ss=$("#ii1").html();
$("#ii1").html("共"+ss+"页");
 ss=$("#ii2").html();
$("#ii2").html("第"+ss+"页");
});
