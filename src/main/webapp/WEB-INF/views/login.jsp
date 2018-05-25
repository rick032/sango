<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Access-Control-Allow-Origin" content="https://stackpath.bootstrapcdn.com">
<title>Sango</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">

<title>Sango</title>
<link href="/css/singin.css" rel="stylesheet">
</head>
<body>
	<div class="container">		
		<form class="form-signin" action='<spring:url value="/loginAction"/>' method="post">
		<h2 class="form-signin-heading">Please sign in</h2>
			<table>
				<tr>
					<label for="userName" class="sr-only">User Name</label>
					<td><input type="text" name="username" class="form-control" placeholder="userName" required autofocus></td>
				</tr>
				<tr>
					<label for="inputPassword" class="sr-only">Password</label>
					<td><input type="password" name="password" class="form-control" placeholder="Password" required></td>
				</tr>
				<tr>
					<td><button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button></td>
				</tr>
			</table>
		</form>
		<br />
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
		integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
		integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
		crossorigin="anonymous"></script>
</body>
</html>