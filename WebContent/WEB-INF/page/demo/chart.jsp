<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ include file="/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>chart demo</title>
<script type="text/javascript" src="${base}/amchart/swfobject.js"></script>

<style type="text/css">
	body > div{
		float : left;
		margin : 20px;
	}
</style>

</head>
<body>
	<div id="amcharts_line">You need to upgrade your Flash Player</div>
	
	<div id="amcharts_column">You need to upgrade your Flash Player</div>
	
	<div id="amcharts_stackcolumn">You need to upgrade your Flash Player</div>
	
	<div id="amcharts_column_electric">You need to upgrade your Flash Player</div>
	
	<script type="text/javascript">
		var so = new SWFObject("${base}/amchart/amline_1.6.0.1/amline/amline.swf", "amline", "500", "400", "8", "#FFFFFF");
		so.addVariable("path", "${base}/amchart/");
		so.addVariable("settings_file", encodeURIComponent("${base}/common/demo/amline_settings.xml"));
		so.addVariable("data_file", encodeURIComponent("${base}/common/demo/amline_data.xml"));
		so.write("amcharts_line");
	
		var so = new SWFObject("${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf", "amcolumn", "500", "400", "8", "#FFFFFF");
		so.addVariable("path", "${base}/amchart/");
		so.addVariable("settings_file", encodeURIComponent("${base}/common/demo/amcolumn_settings.xml"));
		so.addVariable("data_file", encodeURIComponent("${base}/common/demo/amcolumn_data.xml"));
		so.write("amcharts_column");
		
		var so = new SWFObject("${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf", "stackcolumn", "520", "380", "8", "#FFFFFF");
		so.addVariable("path", "${base}/amchart/");
		so.addVariable("settings_file", encodeURIComponent("${base}/common/demo/stackcolumn_settings.xml"));
		so.addVariable("data_file", encodeURIComponent("${base}/common/demo/stackcolumn_data.xml"));
		so.write("amcharts_stackcolumn");
		
		var so = new SWFObject("${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf", "stackcolumn", "520", "380", "8", "#FFFFFF");
		so.addVariable("path", "${base}/amchart/");
		so.addVariable("settings_file", encodeURIComponent("${base}/common/amchart/stat/consumption_settings.xml"));
		so.addVariable("data_file", encodeURIComponent("${base}/common/amchart/stat/amcolumn_data.xml"));
		so.write("amcharts_column_electric");
		
		
	</script>
</body>
</html>