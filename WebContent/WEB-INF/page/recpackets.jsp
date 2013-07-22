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
<!--<meta http-equiv="refresh" content="5">-->
</head>
<body>
	<c:import url="/includes/header.jsp">
		<c:param name="currentNav">recpackets</c:param>
	</c:import>

	<div id="wrap" class="container">
		<div class="row-fluid">
			<div class="span6">
				<p class="muted">&nbsp;</p>
				<p class="muted">&nbsp;</p>
				<p class="muted">&nbsp;</p>
				<div id="amcharts_num_rec" style="text-align: center">You need
					to upgrade your Flash Player</div>
			</div>
			<div class="span6">
				<p class="muted">&nbsp;</p>
				<p class="muted">&nbsp;</p>
				<p class="muted">&nbsp;</p>
				<div id="amcharts_num_drop" style="text-align: center">You
					need to upgrade your Flash Player</div>
			</div>
		</div>
	</div>





	<div id="footer">
		<div class="container">
			<p class="muted credit">
				信息工程研究所二室硬件开发组 <a href="http://www.iie.ac.cn" target="_blank">iie</a>
				&nbsp;&nbsp;版权所有 Copyright 2013
			</p>
		</div>
	</div>


	<%@ include file="/includes/footer.jsp"%>
	<script type="text/javascript" src="${base}/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/amchart/swfobject.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			ChartHandler.rec();
			ChartHandler.drop();

			//document.getElementById("charsetSize").valueOf();

			//<c:if test="{$param.showDetail == 'true'}">
			//if()

			//</c:if>

		});
		var ChartHandler = {

			swfPath : "${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf",
			setting_file : "${base}/common/amchart/stat/consumption_settings.xml",
			data_file : "${base}/common/amchart/stat/amcolumn_data.xml",
			so : null,
			rec : function() {
				//alert('aaaa');
				this.so = new SWFObject(this.swfPath, "amcolumn", "420", "350",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");

				this.setting_file = "${base}/common/amchart/stat/consumption_settings.xml";
				//data_file = this.getRegexResultDataFile();

				this.data_file = "${base}/common/amchart/stat/amcolumn_data.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_num_rec");

			},
			drop : function() {
				//alert('aaaa');
				this.so = new SWFObject(this.swfPath, "amcolumn", "420", "350",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");

				this.setting_file = "${base}/common/amchart/stat/consumption_settings.xml";
				//data_file = this.getRegexResultDataFile();

				this.data_file = "${base}/common/amchart/stat/amcolumn_data.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_num_drop");

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