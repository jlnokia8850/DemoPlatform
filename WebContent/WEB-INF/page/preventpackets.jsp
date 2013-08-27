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
<style type="text/css">
.seperator {
	height: 10px;
}
</style>
</head>
<body>
	<c:import url="/includes/header.jsp">
		<c:param name="currentNav">preventpackets</c:param>
	</c:import>

	<form id="form3" name="form3" action="${base}/platform/recoverPacket"
		class="form-horizontal" method="post"></form>
	<form id="form5" name="form5" action="${base}/platform/resetprePacket"
		class="form-horizontal" method="post"></form>

	<div id="wrap" class="container">
		<div class="row-fluid">
			<div class="span6">
				<div class="well ">
					<form class="demoform" id="form1" name="form1"
						action="${base}/platform/preventPacket" class="form-horizontal"
						method="post">

						<div class="control-group">
							<b>请输入阻断源IP地址:</b> <input class="span9" type="text"
								id="inputid_SrcIP" name="packet.SrcIP" value="" />
						</div>
						<div class="control-group">
							<b>请输入阻断目的IP地址:</b> <input class="span9" type="text"
								id="inputid_IP" name="packet.IP"
								value="${curPreventPacketsModel.IP}" onBlur="isIP(value)" />
						</div>
						<div class="control-group">
							<b>请输入阻断源端口:</b> <input class="span10" type="text"
								id="inputid_SrcPort" name="packet.SrcPort" value="" />
						</div>
						<div class="control-group">
							<b>请输入阻断目的端口:</b> <input class="uneditable-input span5"
								type="text" id="inputid_DstPort" name="packet.DstPort"
								value="dfije" />
						</div>
						<div class="control-group">
							<b>请输入阻断协议号:</b> <span class="uneditable-input span5">阻断协议号</span>
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" type="button" class="btn btn-large btn-primary"
							onclick="resetpre()">开始</a>&nbsp;&nbsp;<a
							href="#" type="button" class="btn btn-large btn-primary"
							onclick="prevent()">阻断</a>&nbsp;&nbsp; <a
							href="#" type="button" class="btn btn-large btn-primary"
							onclick="recover()">恢复</a> &nbsp;&nbsp;<a
							href="#" type="button" class="btn btn-large btn-primary"
							onclick="resetpre()">重置</a>
					</form>
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
			<div class="span6">
				<div class="span6"
					style="height: 0px; position: relative; z-index: -1"></div>
				<div id="amcharts_regex" style="position: relative; z-index: -1">请安装Adobe
					Flash控件</div>
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

		function prevents() {
			document.form1.submit();
			alert("已阻断");
		}
		function prevent() {
			str = document.form1.inputid_IP.value;
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
			if (re.test(str)) {
				if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
						&& RegExp.$4 < 256) {
					document.form1.submit();
					alert("已阻断");
					return true;
				}
			}
			alert("输入IP地址格式不正确");
			return false;
		}
		function resetpre() {
			document.form5.submit();
		}
		function recover() {
			document.form1.submit();
			document.form3.submit();
			alert("已恢复");
		}

		function isIP(str) {
			if (str == null || str == "") {
				alert("输入内容不能为空");
				return false;
			}
			var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
			if (re.test(str)) {
				if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
						&& RegExp.$4 < 256)
					return true;
			}
			alert("输入IP地址格式不正确");
			return false;
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

				this.so = new SWFObject(this.swfPath, "amcolumn", "470", "450",
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
				var data_file = "${base}/platform/getAmchartsDatarec?nocache=" + s;
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