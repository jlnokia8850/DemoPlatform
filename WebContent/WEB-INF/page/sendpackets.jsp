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

<form id="form3" name="form3" action="${base}/platform/sendPacket"
						class="form-horizontal" method="post">
						</form>

	<div id="wrap" class="container">
		<div class="row-fluid">
			<div class="span4">
				<p class="muted">&nbsp;</p>
				<p class="lead text-info">
					<b>配置参数</b>
				</p>
				<div class="well ">

					<form id="form1" name="form1"  action="${base}/platform/savePacket"
						class="form-horizontal" method="post">
						
						<div class="control-group">
							<b>数据包名</b>
							&nbsp; <input class="span6" type="text" id="inputid_name"
								placeholder="" name="packet.name"
								value="${curSendPacketsModel.name}" />
						<!--   </div>
						<div class="control-group"> -->
							<b>发送次数</b>
							&nbsp; <input class="span6" type="text" id="inputid_sendTimes"
								placeholder="" name="packet.sendTimes"
								value="${curSendPacketsModel.sendTimes}" />
						</div>
						
						<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源Mac</b>
							&nbsp; <input class="span6" type="text" id="inputid_srcMac"
								placeholder="" name="packet.srcMac"
								value="${curSendPacketsModel.srcMac}" />
						</div>
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目的Mac</b>
							&nbsp; <input class="span6" type="text" id="inputid_dstMac"
								placeholder="" name="packet.dstMac"
								value="${curSendPacketsModel.dstMac}" />
						</div>
					
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源IP</b>
							&nbsp; <input class="span6" type="text" id="inputid_srcIP"
								placeholder="" name="packet.srcIP"
								value="${curSendPacketsModel.srcIP}" />
						</div>
						
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目 的IP</b>
							&nbsp; <input class="span6" type="text" id="inputid_dstIP"
								placeholder="" name="packet.dstIP"
								value="${curSendPacketsModel.dstIP}" />
						</div>
						
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;协议号</b>
							&nbsp; <input class="span6" type="text" id="inputid_ipPro"
								placeholder="" name="packet.ipPro"
								value="${curSendPacketsModel.ipPro}" />
						</div>
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源端口</b>
							&nbsp; <input class="span6" type="text" id="inputid_srcPort"
								placeholder="" name="packet.srcPort"
								value="${curSendPacketsModel.srcPort}" />
						</div>
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目的端口</b>
							&nbsp; <input class="span6" type="text" id="inputid_dstPort"
								placeholder="" name="packet.dstPort"
								value="${curSendPacketsModel.dstPort}" />
						</div>
				    	<div class="control-group">
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容</b>
							&nbsp; <!-- input class="span6" type="text" id="inputid_content" style="word-break: break-all;padding-top:15px;width:180px;height:100px;"
								name="packet.content"
								value="${curSendPacketsModel.content}" /-->
							<textarea name="content" cols="40" rows="4" value="${curSendPacketsModel.content}"></textarea>
						</div>
						<div class="control-group">
						
						 <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
								<button type="button" class="btn btn-primary" onclick="save()">保存</button>
								<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
								<button type="button" class="btn btn-primary" onclick="send()">发送</button> 
						</div>
						
						
					</form>
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
			
		
			<div class="span8">
			
				<p class="muted">&nbsp;</p>
				<p class="lead text-info">
					<b>快速选择</b>
				</p>
			<form id="form2" name="form2" action="">
			    
				<select id="select1" name="select1" class="selectpicker" onchange="selectOption(this[selectedIndex].innerHTML)">
				<c:forEach items="${curFileList}" var="file">
		    		<option value="${file}">${file}</option>
 				</c:forEach>
				<!-- -->
				</select>
			
			</form>
				
				<!-- form id="form2" name="form2" action=""
			    class="form-horizontal" method="post">
				<p>
		
				<select multiple="multiple">  
				<option>QQ</option>  
				<option>163</option>  
				<option>UDP</option>  
				<option>TCP</option>  
				<option>Tcent</option>
				</select>
				</p>
				<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
				<button type="submit" class="btn btn-primary" >确定</button>
				
				</form-->
			</div>
			<!--/span=8-->
		</div>
		<!--/row-->
		<div style="height: 20px"></div>


		<hr>
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
    	document.form2.submit();
      }
     function send(){
    	 document.form3.submit();
         alert("发送完毕");
     	document.form2.submit();
      }
     function selectOption(name){
    	
    	 window.location='${base}/platform/loadpacket?filename='+name;
    	 document.form2.submit();
      }
     </script>



	<script type="text/javascript">
		$(document).ready(function() {

			//ChartHandler.init();
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
	 
	 <!--  
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
	</script>-->

</body>
</html>