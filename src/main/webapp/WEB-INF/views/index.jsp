<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Sango</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<style type="text/css">
.selector-for-some-widget {
	box-sizing: content-box;
}
</style>
<link
	href="http://bootstrap.hexschool.com/docs/4.0/examples/grid/grid.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-2">
				<b>Game Name</b>
			</div>
			<div class="col-md-2">
				<b>User Name</b>
			</div>
			<div class="col-md-2">
				<b>IMEI</b>
			</div>
			<div class="col-md-2">
				<b>Mac Addr</b>
			</div>
			<div class="col-md-2">
				<b>Device Id</b>
			</div>
			<div class="col-md-2">
				<b>Enable</b>
			</div>

		</div>
		<c:forEach var="c" items="${devices}">
			<div class="row">
				<div class="col-md-2">${c.gamename}</div>
				<div class="col-md-2">${c.username}</div>
				<div class="col-md-2">${c.imei}</div>
				<div class="col-md-2">${c.macAddr}</div>
				<div class="col-md-2">${c.deviceID}</div>
				<div class="col-md-2">${c.enabled}</div>
			</div>
		</c:forEach>
		<br />


		<form:form action="/device/add" method="post" id="addForm"
			modelAttribute="Device">
			<table align="center">

				<tr>

					<td><form:label path="username">Username</form:label></td>

					<td><form:input path="username" name="username" id="username" />

					</td>

				</tr>

				<tr>

					<td><form:label path="gamename">Game Name</form:label></td>

					<td><form:password path="gamename" name="gamename"
							id="gamename" /></td>

				</tr>

				<tr>

					<td><form:label path="imei">IMEI</form:label></td>

					<td><form:input path="imei" name="imei" id="imei" /></td>

				</tr>

				<tr>

					<td><form:label path="deviceID">device ID</form:label></td>

					<td><form:input path="deviceID" name="deviceID" id="deviceID" />

					</td>

				</tr>

				<tr>

					<td><form:label path="macAddr">Mac Addr</form:label></td>

					<td><form:input path="macAddr" name="macAddr" id="macAddr" />

					</td>

				</tr>
				<tr>

					<td><form:label path="enabled">enabled</form:label></td>

					<td><form:checkbox path="enabled" name="enabled" id="enabled"
							value="enabled" /></td>

				</tr>


				<tr>

					<td></td>

					<td><form:button id="submit" name="submit">submit</form:button>

					</td>

				</tr>

				<tr>
				</tr>


			</table>
		</form:form>
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