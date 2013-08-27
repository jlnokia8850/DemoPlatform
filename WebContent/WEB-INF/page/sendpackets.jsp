<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="Shortcut Icon" href="${base}/favicon.ico" />
<title>流量清洗演示系统</title>
<link type="text/css" rel="stylesheet" href="${base}/css/bootstrap.css" />
<link type="text/css" rel="stylesheet"
	href="${base}/css/bootstrap-select.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/index.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/stat.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/button.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/demo.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/wp-syntax.css" />
<style type="text/css">
.seperator {
	height: 10px;
}

div.x {
	position: absolute;
	left: 0px;
	top: 0px;
	z-index: -1
}
</style>
</head>
<body>
	<c:import url="/includes/header.jsp">
		<c:param name="currentNav">sendpackets</c:param>
	</c:import>

	<form id="form3" name="form3" action="${base}/platform/sendPacket"
		class="form-horizontal" method="post"></form>
	<form id="form5" name="form5" action="${base}/platform/resetPacket"
		class="form-horizontal" method="post"></form>

	<div id="wrap" class="container">
		<div class="row-fluid">
			<div class="span6">

				<div class="well ">

					<form class="demoform" id="form1" name="form1"
						action="${base}/platform/savePacket" class="form-horizontal"
						method="post">

						<div class="control-group">
							<b>数据包名&nbsp;:</b> &nbsp; <input class="span9" type="text"
								id="inputid_name" name="packet.name"
								value="${curSendPacketsModel.name}" plugin="datepicker"
								datatype="nullmsg" errormsg="dfwafsdfe" />
						</div>


						<div class="control-group">
							<b>源Mac&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b> &nbsp; <input
								class="span9" type="text" id="inputid_srcMac"
								name="packet.srcMac" value="${curSendPacketsModel.srcMac}"
								placeholder="mac" onBlur="isMac(value)" />
						</div>
						<div class="control-group">
							<b>目的Mac&nbsp;:</b> &nbsp; <input class="span9" type="text"
								id="inputid_dstMac" name="packet.dstMac"
								value="${curSendPacketsModel.dstMac}" onBlur="isMac(value)" />
						</div>

						<div class="control-group">
							<b>源IP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span3" type="text" id="inputid_srcIP"
								name="packet.srcIP" value="${curSendPacketsModel.srcIP}"
								onBlur="isIP(value)" />
							<!--</div>
						
				    	  <div class="control-group">-->
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目的IP&nbsp;&nbsp;&nbsp;&nbsp;:</b>
							&nbsp; <input class="span3" type="text" id="inputid_dstIP"
								name="packet.dstIP" value="${curSendPacketsModel.dstIP}"
								onBlur="isIP(value)" />
						</div>

						<div class="control-group">
							<b>源端口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b> &nbsp; <input
								class="span3" type="text" id="inputid_srcPort"
								name="packet.srcPort" value="${curSendPacketsModel.srcPort}"
								onBlur="isPort(value)" />
							<!--  </div>
				    	<div class="control-group">-->
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目的端口:</b>
							&nbsp; <input class="span3" type="text" id="inputid_dstPort"
								name="packet.dstPort" value="${curSendPacketsModel.dstPort}"
								onBlur="isPort(value)" />
						</div>
						<div class="control-group">
							<b>协议号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b> &nbsp; <input
								class="span3" type="text" id="inputid_ipPro" name="packet.ipPro"
								value="${curSendPacketsModel.ipPro}" onBlur="isIpPro(value)" />
							<!--  </div>
						<div class="control-group"> -->
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发送次数:</b>
							&nbsp; <input class="span3" type="text" id="inputid_sendTimes"
								name="packet.sendTimes" value="${curSendPacketsModel.sendTimes}"
								onBlur="isNum(value)" />
						</div>

						<div class="control-group">
							<b>内容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b> &nbsp;
							<textarea class="span9" name="packet.content" cols="50" rows="5">${curSendPacketsModel.content}</textarea>
						</div>
						<a href="#" type="button" class="button blue skew"
							onclick="save()">保存</a> <a href="#" type="button"
							class="button blue skew" onclick="reset()">重置</a> <a href="#"
							type="button" class="button orange shield glossy"
							onclick="send()">发送</a>
					</form>


				</div>
				<!--/.well -->
			</div>
			<!--/span-->


			<div class="span6">
				<div class="span6"
					style="height: 0px; position: absolute; z-index: -1"></div>
				<div id="amcharts_regex" style="position: relative; z-index: -1">请安装Adobe
					Flash控件</div>
				<div class="row-fluid">
					<div class="span12"></div>
					<div class="row-fluid">
						<div class="span6">
							<form id="form2" name="form2">

								<select id="select1" name="select1" class="selectpicker"
									onchange="selectOption(this[selectedIndex].innerHTML)">
									<option value=" ">---请选择数据包---</option>
									<c:forEach items="${curFileList}" var="file">
										<option value="${file}">${file}</option>
									</c:forEach>
									<!-- -->
								</select>
							</form>
						</div>
						<div class="span6">
							<form id="form4" name="form4" action="">
								<select id="select2" name="select2" class="selectpicker"
									onchange="deleteOption(this[selectedIndex].innerHTML)">
									<option value=" ">---请删除数据包---</option>
									<c:forEach items="${curFileList}" var="file">
										<option value="${file}">${file}</option>
									</c:forEach>
								</select>

							</form>
						</div>
					</div>
				</div>


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

	<script type="text/javascript"
		src="http://validform.rjboy.cn/wp-content/themes/validform/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="http://validform.rjboy.cn/Validform/v5.3/Validform_v5.3.2_min.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('.selectpicker').selectpicker({

			});
		});

		$(".demoform")
				.Validform(
						{
							btnSubmit : "#btn_sub",
							btnReset : ".btn_reset",
							tiptype : 1,
							ignoreHidden : false,
							dragonfly : false,
							tipSweep : true,
							label : ".label",
							showAllError : false,
							postonce : true,
							ajaxPost : true,
							datatype : {
								"*6-20" : /^[^\s]{6,20}$/,
								"z2-4" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/,
								"username" : function(gets, obj, curform, regxp) {
									var reg1 = /^[\w\.]{4,16}$/, reg2 = /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,8}$/;

									if (reg1.test(gets)) {
										return true;
									}
									if (reg2.test(gets)) {
										return true;
									}
									return false;

								},
								"phone" : function() {
								}
							},
							usePlugin : {
								swfupload : {},
								datepicker : {},
								passwordstrength : {},
								jqtransform : {
									selector : "select,input"
								}
							},
							beforeCheck : function(curform) {
							},
							beforeSubmit : function(curform) {
							},
							callback : function(data) {
							}
						});

		function save() {
			document.form1.submit();
			alert("保存完毕");
		}
		function reset() {
			document.form5.submit();
		}
		function send() {
			document.form1.submit();
			document.form3.submit();
			alert("发送完毕");
		}
		function selectOption(name) {
			window.location = '${base}/platform/loadpacket?filename=' + name;
		}
		function deleteOption(name) {
			window.location = '${base}/platform/deltpacket?filename=' + name;
		}

		function isMac(str) {
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = /[a-fA-F\d]{2}:[a-fA-F\d]{2}:[a-fA-F\d]{2}:[a-fA-F\d]{2}:[a-fA-F\d]{2}:[a-fA-F\d]{2}/;
			if (!re.test(str)) {
				alert("输入Mac地址格式错误");
				return false;
			}
			return true;
		}
		function isIPs(str) {
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = /^(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})$/g;
			if (!re.test(str)) {
				alert("输入IP地址格式错误");
				return false;
			}
			return true;
		}
		function isIP(str) {
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
			//var re = /^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$/g;
			if (re.test(str)) {
				if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
						&& RegExp.$4 < 256)
					return true;
			}
			alert("输入IP地址格式不正确");
			return false;
		}
		function isPort(str) {
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = new RegExp(/^[0-9]+$/);
			if (!re.test(str)) {
				alert("请输入数字");
				return false;
			}
			if (str > 65535) {
				alert("请不要超过65535");
				return false;
			}
			return true;
		}
		function isIpPro(str) {
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = new RegExp(/^[0-9]+$/);
			if (!re.test(str)) {
				alert("请输入数字");
				return false;
			}
			return true;
		}
		function isNum(str) {
			var re = new RegExp(/^[0-9]+$/);
			if (!re.test(str)) {
				alert("请输入数字");
				return false;
			}
			return true;
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
			setting_file : "${base}/common/amchart/stat/emission_settings.xml",
			so : null,
			init : function() {

				this.so = new SWFObject(this.swfPath, "amcolumn", "470", "290",
						"8", "#FFFFFF");
				this.so.addVariable("path", "${base}/amchart/");
				this.setting_file = "${base}/common/amchart/stat/emission_settings.xml";
				data_file = this.getSendDataFile();
				this.data_file = data_file;

				this.so.addVariable("settings_file",
						encodeURIComponent(this.setting_file));

				this.so.addVariable("data_file",
						encodeURIComponent(this.data_file));

				this.so.addVariable("preloader_color", "#999999");
				//this.so.addParam("wmode", "opaque");
				this.so.write("amcharts_regex");

			},
			getSendDataFile : function() {
				var s = Math.random().toString();
				var data_file = "${base}/platform/getSendData?nocache=" + s;
				//alert(data_file.toString());
				return data_file;
			},
			reloadChart : function() {
				var data_file = this.getSendDataFile();
				this.so.addVariable("data_file", encodeURIComponent(data_file));
				this.so.write("amcharts_regex");
			}
		};
	</script>

</body>
</html>