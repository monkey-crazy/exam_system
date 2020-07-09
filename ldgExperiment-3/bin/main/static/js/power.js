/**
 * power.html
 */

$(function(){
	//学生权限管理(显示)
	$("#students").click(function(){
		//alert("123");
		var tb=$("#tb1");
		tb.empty();
		var tr0 =$("<tr></tr>");
		
		var td01=$("<th>student</th>");
		var td02=$("<th>学生id</th>");
		var td03=$("<th>学生姓名</th>");
		var td04=$("<th>权限级别</th>");
		var td05=$("<th>操作</th>");
		tr0.append(td01);
		tr0.append(td02);
		tr0.append(td03);
		tr0.append(td04);
		tr0.append(td05);
		tb.append(tr0);
		
//		var td0=$("th");
//		
//		tb.empty();
//		var tr0 =$("<tr></tr>");
////		tr0.append(td0);
//		tb.append(tr0);
//		//修改表头信息
//		var tr=tb.children().eq(0).children().eq(0);
//			tr.children().eq(2).html("学生姓名");
//			tr.children().eq(1).html("学生id");
		$.post("/test/admin/showstup",function(data){
			
			//alert(data.length);
			//alert(data[0].data[i].power.powerId);
			for(var i = 0 ;i < data.length ; i++){
				var tr1 =$("<tr></tr>");
				var td1=$("<td>student</td>");
				var td2=$("<td>"+data[i].studentId+"</td>");
				var td3=$("<td>"+data[i].studentName+"</td>");
				var td4=$("<td></td>");
				var select =$("<select   title="+data[i].power.powerId
 			+"><option value='0'  >禁止</option>"+
			"<option value='1'  >一级权限</option>"+
			"<option value='2'  >二级权限</option>"+
			"<option value='3'  >三级权限</option>"+
			"<option value='4' >四级权限</option>"+
			"</select>")
			td4.append(select);
				
			var td5=$("<td><button class='stus  btn btn-primary'> 确认</button></td>");
			tr1.append(td1);
			tr1.append(td2);
			tr1.append(td3);
			tr1.append(td4);
			tr1.append(td5);
			tb.append(tr1);
			
			
			}
			var lists=$("select");
			
			for (var i=0;i<lists.length;i++){
				
			var ind= $(lists[i]);
			var tit=ind.attr("title");
			//console.log(tit);
				//console.log(ind.children().eq(tit));
				ind.children().eq(tit).attr("selected","selected");
				
			}
			
			
			
			
			//alert(tr);
			
		}).error(function(xhr,errorText,errorType){
			alert("操作失败！");
			window.location.reload();
		});
		//alert(tb.children().eq(1).html());
	
		
		
		
		
	});
	
	//提交权限
	$("#tb1").on("click",".stus",function(){
		var tr = $(this).parent().parent();
		//console.dirxml(tr);
		var sel= tr.children().eq(3);
		//获取选择的权限
		var pows=sel.find("option:selected").val();
		var stuid = tr.children().eq(1).html();
		//alert(tid);
		$.post("/test/admin/studentpower",{
			studentid:stuid,
			powid : pows
			
		},function(data){
			if(data==pows){
				alert("修改成功！");
			}else{
				alert("修改失败！");
			}
			
		}).error(function(xhr,errorText,errorType){
			alert("操作失败！");
			window.location.reload();
		});
		
		
		
	});
	
	
	
	//老师的权限管理
	
		$("#tb1").on("click",".ok",function() {
	//console.log($(this).parent().parent());
	var tr = $(this).parent().parent();
	//console.dirxml(tr);
	var sel= tr.children().eq(3);
	//获取选择的权限
	var pows=sel.find("option:selected").val();
	var tid = tr.children().eq(1).html();
	//alert(tid);
	$.post("/test/admin/teacherpower",{
		teacherid:tid,
		powid : pows
		
	},function(data){
		if(data==pows){
			alert("修改成功！");
		}else{
			alert("修改失败！");
		}
	}).error(function(xhr,errorText,errorType){
		alert("操作失败！");
		window.location.reload();
	});
	 //alert(pows);
	
	
	//console.dirxml(sel.val());
	
	//console.dirxml(sel);
	//var se=sel[0];
	
//	console.log(sel.prop('selectedIndex'));
	//var index = se.selectedIndex; // 选中索引
	 // alert(index);
	   //var al= se.options[index].value;

	//alert(al)
	
	
	
	});

	
	
	var lists=$("select");
	
	for (var i=0;i<lists.length;i++){
		
	var ind= $(lists[i]);
	var tit=ind.attr("title");
	//console.log(tit);
		//console.log(ind.children().eq(tit));
		ind.children().eq(tit).attr("selected","selected");
		
	}
	
	
});
