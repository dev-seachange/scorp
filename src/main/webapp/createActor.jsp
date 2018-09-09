<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Actor</title>

<script type='text/javascript' src="js/test.js"></script>
</head>
<body>
	<form onsubmit="myFunction()">
		<table width="100%" border="1" cellspacing="0" cellpadding="2">
			<tr>
				<td>Actor Name</td>
				<td><input type="text" name="actorName" id="actorName" /></td>
			</tr>
			<tr>
				<td>Actor Age</td>
				<td><input type="text" name="actorAge"	id="actorAge"  /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="createButton" id="createButton" value="Create Actor" /></td>
			</tr>
			<!-- input type="hidden" value="< % = rolePlayId % >" -->
		</table>
	</form>
</body>

