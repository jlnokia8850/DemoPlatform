<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="Shortcut Icon" href="${base}/favicon.ico" />
<title>流量清洗演示系统</title>
<link type="text/css" rel="stylesheet" href="${base}/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/bootstrap-select.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/bootstrap-select.min.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/index.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/stat.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/button.css" />
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

<form id="form3" name="form3" action="${base}/platform/sendPacket" class="form-horizontal" method="post"></form>
<form id="form5" name="form5" action="${base}/platform/resetPacket" class="form-horizontal" method="post"></form>

	<div id="wrap" class="container">
		<div class="row-fluid">
			<div class="span6">
				
				<div class="well ">

					<form id="form1" name="form1"  action="${base}/platform/savePacket"
						class="form-horizontal" method="post">
						
						<div class="control-group">
							<b>数据包名&nbsp;:</b>
							&nbsp; <input class="span9" type="text" id="inputid_name"
								 name="packet.name"
								value="${curSendPacketsModel.name}" />
						</div>

						
						<div class="control-group">
							<b>源Mac&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span9" type="text" id="inputid_srcMac"
								 name="packet.srcMac"
								value="${curSendPacketsModel.srcMac}" />
						 </div>
				    	<div class="control-group">
							<b>目的Mac&nbsp;:</b>
							&nbsp; <input class="span9" type="text" id="inputid_dstMac"
								 name="packet.dstMac"
								value="${curSendPacketsModel.dstMac}" />
						</div>
					
				    	<div class="control-group">
							<b>源IP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span3" type="text" id="inputid_srcIP"
								 name="packet.srcIP"
								value="${curSendPacketsModel.srcIP}" />
						<!--</div>
						
				    	  <div class="control-group">-->
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目的IP&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span3" type="text" id="inputid_dstIP"
								 name="packet.dstIP"
								value="${curSendPacketsModel.dstIP}" />
						</div>
						
						<div class="control-group">
							<b>源端口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span3" type="text" id="inputid_srcPort"
								name="packet.srcPort"
								value="${curSendPacketsModel.srcPort}" />
						<!--  </div>
				    	<div class="control-group">-->
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目的端口:</b>
							&nbsp; <input class="span3" type="text" id="inputid_dstPort"
								name="packet.dstPort"
								value="${curSendPacketsModel.dstPort}" />
						</div>
				    	<div class="control-group">
							<b>协议号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span3" type="text" id="inputid_ipPro"
								 name="packet.ipPro"
								value="${curSendPacketsModel.ipPro}" />
						<!--  </div>
						<div class="control-group"> -->
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发送次数:</b>
							&nbsp; <input class="span3" type="text" id="inputid_sendTimes"
								name="packet.sendTimes"
								value="${curSendPacketsModel.sendTimes}" />
						</div>

				    	<div class="control-group">
							<b>内容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp;
							<textarea class="span9" name="packet.content" cols="50" rows="5"  >${curSendPacketsModel.content}</textarea>
						</div>
						<a href="#" type="button" class="button blue skew" onclick="save()">保存</a>
						<a href="#" type="button" class="button blue skew" onclick="reset()">重置</a>
						<a href="#" type="button" class="button orange shield glossy" onclick="send()">发送</a>
						<!--  <div class="control-group">
						
						 
								<button type="button" class="btn btn-large btn-primary" onclick="save()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
								<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
								<button type="reset" class="btn btn-large btn-primary" onclick="reset()">&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;重置&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button> </div>
								<div><button type="button" class="btn btn-large btn-success" onclick="send()">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发送&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</button></div>
						-->
						
						
					</form>

				
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
			
		
			<div class="span6">
			   <div class="span6">
			<form  id="form2" name="form2" action="">
			    
				<select id="select1" name="select1" class="selectpicker" onchange="selectOption(this[selectedIndex].innerHTML)">
				<option value=" "> ---请选择数据包---</option>
				<c:forEach items="${curFileList}" var="file">
		    		<option value="${file}">${file}</option>
 				</c:forEach>
				<!-- -->
				</select>
			</form>
			</div>
			    <div class="span6">
			<form  id="form4" name="form4" action="">
			<select id="select2" name="select2" class="selectpicker" onchange="deleteOption(this[selectedIndex].innerHTML)">
			<option value=" "> ---请删除数据包---</option>
			<c:forEach items="${curFileList}" var="file">
		    	<option value="${file}">${file}</option>
 			</c:forEach>
			</select>
				
			</form></div>
			<div class="span6" style="height: 80px"></div>
			<div id="amcharts_regex">请安装Adobe Flash控件</div>
			
		</div>
		
		
		</div>
		<!--/row-->
		<div style="height: 20px"></div>


	
	</div>
	<!--/.fluid-container-->

	<%@ include file="/includes/footer.jsp"%>
	<script type="text/javascript" src="${base}/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/js/bootstrap-select.js"></script>
	<script type="text/javascript" src="${base}/amchart/swfobject.js"></script>

	<script type="text/javascript">
	$(document).ready(function() {
		$('.selectpicker').selectpicker({

		});
	});

     function save(){
     document.form1.submit();
 	 alert("保存完毕");
      }
     function reset(){
         document.form5.submit();
          }
     function send(){
    	 document.form3.submit();
         alert("发送完毕");
      }
     function selectOption(name){
    	
    	 window.location='${base}/platform/loadpacket?filename='+name;
    	 document.form2.submit();
    	// document.form4.submit();
      }
     function deleteOption(name){
     	
    	 window.location='${base}/platform/deltpacket?filename='+name;
      }
     </script>



	<script type="text/javascript">
		$(document).ready(function() {

			ChartHandler.init();
			//document.getElementById("charsetSize").valueOf();

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
		function submitForm2() {
			document.form2.submit();
		};
	</script>
	 
	
	<script type="text/javascript">
		var ChartHandler = {

			swfPath : "${base}/amchart/amcolumn_1.6.0.1/amcolumn/amcolumn.swf",
			setting_file : "${base}/common/amchart/stat/consumption_settings.xml",
			data_file : "${base}/common/amchart/stat/amcolumn_data.xml",
			so : null,
			init : function() {

				this.so = new SWFObject(this.swfPath, "amcolumn", "470", "290",
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