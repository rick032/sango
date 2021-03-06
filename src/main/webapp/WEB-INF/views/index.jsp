<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<meta http-equiv="Access-Control-Allow-Origin"
	content="https://code.jquery.com,https://stackpath.bootstrapcdn.com">
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
		<table class="table" id='downloadTable'>
			<thead>
				<th scope="col">說明文件</th>
				<th scope="col">操作助手0804</th>
				<th scope="col">版本:20180722</th>
				<th scope="col">版本:20180823</th>
				<th scope="col">版本:20180909</th>
				<th scope="col">版本:20181109</th>
			</thead>
			<tbody>				
				<tr>
					<td scope="row"><a href="https://drive.google.com/open?id=1SDZ_Y9YCfXRnSJVm0PnHIY3HuJgkmag-" target="_blank">說明文件</a></td>															
					<td scope="row"><a href="https://drive.google.com/open?id=14sbnNkb0TxXEAy7lXid8FepEdpM-VCur" target="_blank">sango_0722.zip</a></td>
					<td scope="row"><a href="https://drive.google.com/open?id=1e-Vvo-QNSCHVU0qyd8SSxVADOxnYUwfm" target="_blank">sango_0823.zip</a></td>
					<td scope="row"><a href="https://drive.google.com/open?id=1WbEkPwonXiCm8TFVGospyR2R9ZuGyci6" target="_blank">sango_0909.zip</a></td>
					<td scope="row"><a href="https://drive.google.com/open?id=15wAsQiwRA5l5CYfEyWVHpAaQK9Fjl1CQ" target="_blank">sango_1109.zip</a></td>
				</tr>
				<tr>
					<td scope="row">					
					<ul>
						<li>說明文件</li>
					</ul>
					</td>
					</td>					
					<td scope="row">					
					<ul>
						<li>增加跨服自動招募及治療</li>
						<li>fix 第四隊方向會出錯問題</li>		
						<li>使用免戰時增加檢查是否已免戰</li>
						<li>增加檢查網路斷線時重新連線</li>
						<li>增加每隊可選方向</li>
						<li>增加每10分鐘檢查網路斷線</li>
						<li>增加採集資源選項增加不限</li>
						<li>增加每次10次找不到可用隊伍時會回主城</li>
						<li>更改認證方式</li>
						<li>修改等待時間為3秒</li>
						<li>更新1.6.0版</li>				
					</ul>
					</td>
					<td scope="row">					
					<ul>
						<li>更新1.8.0版</li>
						<li>修改兵力檢查位置</li>
						<li>修改可輸入等待時間</li>
						<li>刪除自動設定採礦等級</li>
						<li>增加自動執行內政</li>									
					</ul>
					<td scope="row">					
					<ul>						
						<li>修正被打礦無法召回</li>
						<li>增加可輸入多久時間檢查募兵、內政</li>																									
					</ul>
					</td>
					<td scope="row">					
					<ul>						
						<li>更新1.9.0版</li>																															
					</ul>
					</td>				
				</tr>				
			</tbody>
		</table>
		<br/>
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