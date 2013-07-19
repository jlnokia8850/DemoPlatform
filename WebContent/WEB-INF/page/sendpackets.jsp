<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="Shortcut Icon" href="${base}/favicon.ico" />
<title>流量清洗演示系统</title>
<link type="text/css" rel="stylesheet" href="${base}/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/index.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/stat.css" />
<style type="text/css">
.seperator {
	height: 10px;
}
</style>
</head>
<body>
	<c:import url="/includes/header.jsp">
		<c:param name="currentNav">sendpackets</c:param>
	</c:import>



	<div id="wrap" class="container">
		<div class="row-fluid">
			<div class="span4">
				<p class="muted">&nbsp;</p>
				<p class="lead text-info">
					<b>配置参数</b>
				</p>
				<div class="well ">

					<form id="form1" name="form1" action="${base}/platform/savePacket"
						class="form-horizontal" method="post">
						<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发送次数</b>
							&nbsp; <input class="span6" type="text" id="charsetSize"
								placeholder="" name="packet.sendTimes" value="${curSendPacketsModel.sendTimes}" />
						</div>
						<div class="control-group">
							<div class="controls">
								<button type="submit" class="btn btn-primary">保存</button>

							</div>
						</div>
					</form>
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
			<div class="span8">
				<p class="muted">&nbsp;</p>
				<p class="lead text-info">
					<b>匹配速度</b>
				</p>
				<div id="amcharts_regex" style="text-align: center">You need
					to upgrade your Flash Player</div>
				<!--/row-->

				<!--/row-->
			</div>
			<!--/span=8-->
		</div>
		<!--/row-->
		<div style="height: 20px"></div>

		<div class="span12">
			<p>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" role="button" class="btn btn-large btn-primary"
					data-toggle="modal" onclick="submitForm1();">&nbsp;&nbsp;&nbsp;&nbsp;配置&nbsp;&nbsp;&nbsp;&nbsp;</a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			</p>
		</div>



		<hr>
	</div>
	<!--/.fluid-container-->

	<%@ include file="/includes/footer.jsp"%>
	<script type="text/javascript" src="${base}/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/amchart/swfobject.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			ChartHandler.init();

			//<c:if test="{$param.showDetail == 'true'}">
			//if()

			//</c:if>

		});

		function submitForm1() {
			//document.form1.target = "fm1";
			//document.form1.submit();
			//document.form2.target = "fm2";
			//document.form2.submit();

			//if ($('input[name="Filerule"]').val() == null
			//		|| $('input[name="Filerule"]').val() == '') {
			//	alert("请上传规则文件!");
			//	return;
			//}
			document.form1.submit();
		};
	</script>
	<script type="text/javascript">
		var ChartHandler = {
			swfPath : "${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf",
			setting_file : "${base}/common/amchart/stat/consumption_settings.xml",
			data_file : "${base}/common/amchart/stat/amcolumn_data.xml",
			so : null,
			init : function() {

				this.so = new SWFObject(this.swfPath, "amcolumn", "620", "290",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");
				this.setting_file = "${base}/common/amchart/stat/consumption_settings.xml";
				//data_file = this.getRegexResultDataFile();

				this.data_file = "${base}/common/amchart/stat/amcolumn_data.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));
				//alert('aaaa');

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_regex");

			},
			getRegexResultDataFile : function() {
				//var s = Math.random().toString();
				//var data_file = "${base}/platform/getAmchartsData?nocache=" + s;
				//return data_file;
			},
			reloadChart : function() {
				//var data_file = this.getRegexResultDataFile();
				//this.so.addVariable("data_file", encodeURIComponent(data_file));
				//this.so.write("amcharts_regex");
			}

		};
	</script>

</body>
</html>