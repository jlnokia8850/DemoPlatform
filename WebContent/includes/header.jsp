
<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<!-- Fixed navbar -->

<div class="navbar navbar-fixed-top" id="nav">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="brand" id="logo_bar" href="#"><b>流量清洗演示系统</b></a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					<a href="#"><b>关于我们</b></a>
				</p>
				<div class=tabbable>
					<ul class="nav">
						<li class="active"><a href="${base}/platform/init"><b>发包演示</b></a></li>
						<li><a href="${base}/platform/recpackets"><b>收包演示</b></a></li>
						<li><a href="#"><b>阻断演示</b></a></li>
						<li><a href="#"><b>分流演示</b></a></li>
					</ul>
				</div>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>

<script language="javascript">
	function chk() {
		var s = fr.file.value;
		if (fr.file.value == "") {
			alert("请输入图片地址!");
			fr.file.focus();
			return false;
		}
		ss = s.substr(s.lastIndexOf(" . ") + 1, 3);
		// if (s != "gif" && s != "jpg" ){  
		//   alert( " 请选择正确的图片格式! " )  
		//  return   false ;  
		//}  

		var img = new Image();
		var FileMaxSize = 100; // 限制上传的文件大小，单位(k)  

		img.src = fr.file.value;
		if (img.fileSize > FileMaxSize * 1024) {
			alert("请上传小于100K的图片");
			fr.file.focus();
			return false;
		}
	}
</script>

