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
				<div id="amcharts_num_rec" style="text-align: center">You need
					to upgrade your Flash Player</div>
			</div>
			<div class="span6">
				<p class="muted">&nbsp;</p>
				<div id="amcharts_num_drop" style="text-align: center">You
					need to upgrade your Flash Player</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<p class="muted">&nbsp;</p>
				<div id="amcharts_num_tcp" style="text-align: center">You need
					to upgrade your Flash Player</div>
			</div>
			<div class="span6">
				<p class="muted">&nbsp;</p>
				<div id="amcharts_num_udp" style="text-align: center">You
					need to upgrade your Flash Player</div>
			</div>
		</div>
	</div>

	<%@ include file="/includes/footer.jsp"%>
	<script type="text/javascript" src="${base}/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/amchart/swfobject.js"></script>

	<script type="text/javascript">
		//ajax方式，与nutz通讯 var
		var xmlHttp = false;
		xmlHttp = newActiveXObject("Microsoft.XMLHTTP");
		/*try
		{
			xmlHttp=newActiveXObject("Msxml2.XMLHTTP"); 
		}	catch (e) 
		{ 
			try
			{
				xmlHttp=newActiveXObject("Microsoft.XMLHTTP"); 
			}	catch (e2) { xmlHttp=false; } 
		} 
		if (!xmlHttp && typeof XMLHttpRequest !='undefined')
		{ 
			xmlHttp=newXMLHttpRequest();
		} */

		function getXMLHTTPData() {
			//alert(1);
			var url = "${base}/platform/getAmchartsData";
			xmlHttp.open("POST", url, true);//提交数据

		}
	</script>

	<script type="text/javascript"> //ajax方式，与nutz通讯 var
	var xmlHttp=false; 
	xmlHttp=newActiveXObject("Microsoft.XMLHTTP"); 
	/*try
	{
		xmlHttp=newActiveXObject("Msxml2.XMLHTTP"); 
	}	catch (e) 
	{ 
		try
		{
			xmlHttp=newActiveXObject("Microsoft.XMLHTTP"); 
		}	catch (e2) { xmlHttp=false; } 
	} 
	if (!xmlHttp && typeof XMLHttpRequest !='undefined')
	{ 
		xmlHttp=newXMLHttpRequest();
	} */
	
	function getXMLHTTPData() 
	{
		//alert(1);
		var url="${base}/platform/getAmchartsData"; 
		xmlHttp.open("POST",url,true);//提交数据
		
	}
	
	</script>
	
	<script type="text/javascript">
		$(document).ready(function() {

			ChartHandler.rec();
			ChartHandler.drop();
			ChartHandler.tcp();
			ChartHandler.udp();

			//document.getElementById("charsetSize").valueOf();
			//<c:if test="{$param.showDetail == 'true'}">
			//if()
			//</c:if>
		});

		//var timer_interval = setInterval("getXMLHTTPData()", 1000);

		var timer_interval = setInterval(
				"ChartHandler.getRegexResultDataFile()", 5000);
		//var timer_interval = setInterval("getXMLHTTPData()",1000);
		//		alert("1");
		
		var ChartHandler = {

			swfPath : "${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf",
			swfPathline : "${base}/amchart/amline_1.6.0.1/amline/amline.swf",
			//setting_file : "${base}/common/amchart/stat/amcolumn_settings.xml",
			//data_file_rec : "${base}/common/amchart/stat/amcolumn_data_rec.xml",
			//data_file_drop : "${base}/common/amchart/stat/amcolumn_data_drop.xml",
			so : null,
			rec : function() {
				//alert('aaaa');
				this.so = new SWFObject(this.swfPath, "amcolumn", "480", "400",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");

				this.setting_file = "${base}/amchartData/amcolumn_settings.xml";

				data_file = this.getRegexResultRecDataFile();
				this.data_file = data_file;
				//this.data_file = "${base}/amchartData/amcolumn_data_rec.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_num_rec");
			},
			drop : function() {

				this.so = new SWFObject(this.swfPathline, "amline", "480", "400",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");

				this.setting_file = "${base}/amchartData/amline_settings.xml";
				
				data_file = this.getRegexResultDropDataFile();
				this.data_file = data_file;
				//this.data_file = "${base}/amchartData/amcolumn_data_rec.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_num_drop");

			},
			tcp : function() {

				this.so = new SWFObject(this.swfPath, "amcolumn", "480", "400",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");

				this.setting_file = "${base}/amchartData/amcolumn_settings.xml";
				
				data_file = this.getRegexResultTcpDataFile();
				this.data_file = data_file;
				//this.data_file = "${base}/amchartData/amcolumn_data_rec.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_num_tcp");

			},
			udp : function() {

				this.so = new SWFObject(this.swfPath, "amcolumn", "480", "400",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");

				this.setting_file = "${base}/amchartData/amcolumn_settings.xml";
				
				data_file = this.getRegexResultUdpDataFile();
				this.data_file = data_file;
				//this.data_file = "${base}/amchartData/amcolumn_data_rec.xml";

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				//this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_num_udp");

			},
			getRegexResultDropDataFile : function() {
				var s = Math.random().toString();

				//window.location = "${base}/platform/getAmchartsDatarec?nocache=" + s;
				//window.location = "${base}/platform/getAmchartsDatadrop?nocache=" + s;

				 var data_file = "${base}/platform/getAmchartsDatadrop?nocache=" + s;

				return data_file;
			},
			getRegexResultRecDataFile : function() {
				var s = Math.random().toString();

				//window.location = "${base}/platform/getAmchartsDatarec?nocache=" + s;
				//window.location = "${base}/platform/getAmchartsDatadrop?nocache=" + s;

				 var data_file = "${base}/platform/getAmchartsDatarec?nocache=" + s;

				return data_file;
			},
			getRegexResultTcpDataFile : function() {
				var s = Math.random().toString();

				//window.location = "${base}/platform/getAmchartsDatarec?nocache=" + s;
				//window.location = "${base}/platform/getAmchartsDatadrop?nocache=" + s;

				 var data_file = "${base}/platform/getAmchartsDatatcp?nocache=" + s;

				return data_file;
			},
			getRegexResultUdpDataFile : function() {
				var s = Math.random().toString();

				//window.location = "${base}/platform/getAmchartsDatarec?nocache=" + s;
				//window.location = "${base}/platform/getAmchartsDatadrop?nocache=" + s;

				 var data_file = "${base}/platform/getAmchartsDataudp?nocache=" + s;

				return data_file;
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