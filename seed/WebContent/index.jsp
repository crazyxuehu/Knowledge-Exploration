<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test spring mvc</title>
<script type="text/javascript" src="jquery-1.11.1.min.js"></script>
</head>
<body>
			<div style="width:300px;height:200px;">
				<form name="myform" action="/seed/test" method="get">
					<input id="name" name="name" value="" maxlength=10>
					<input type="button" value="提交" onClick="fact()">
				</form>
				<div id="test"></div>
			</div>
			<script type="text/javascript">
	
				function fact(){
					var nn=$('#name').val();
					var list=new Array();
					//list.push("do you wanna touch me");
					list.push("bryan gordon");
					list.push("aibou: the movie iii");
					//list.push("trying again (tv series)");
					//list.push("lucrezia borgia (1922 film)");
					var head='bryan gordon';
					var tail='palimos ng pag-ibig (tv series)';
					$.ajax({
						url:"/seed/test",
						data:JSON.stringify({mylist:list,head:head,tail:tail}),
						type:"POST",
						dataType:'json',
						contentType:"application/json",
						success:function(result,status){
							for(var i=0;i<result.length;i++){
								console.log(result[i]);	
							}
						},
						error:function(er,status){
							alert(er);
						}
					})
					
				}
			</script>
</body>
</html>