 <%@ page contentType="text/html;charset=utf-8" language="java" %>
 <%@ include file="/includes/taglibs.jsp"%>
 <!-- Fixed navbar -->
 	  <c:if test="${param.cycletype == null }">
 	  	<c:set var="cycletype" value="${session_cycletype_list[0].code}"></c:set>
 	  </c:if>
 	  <c:if test="${param.target == null }">
 	  	<c:set var="target" value="consumption"></c:set>
 	  </c:if>
 	  
      <div class="navbar navbar-fixed-top" id="nav">
        <div class="navbar-inner">
          <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="brand" id="logo_bar" href="${base}">大规模特征串匹配演示系统</a>
            <div class="nav-collapse collapse">
              <ul class="nav">
                <li <c:if test="${param.currentNav == 'statistic'}">class="active"</c:if>>
                	<c:if test="${param.target == null }">
                	<a href="${base}/cycle/stat?cycletype=${param.cycletype}&target=consumption&statBy=generator">
                	<img src="${base}/images/stat.png" width="16" height="16"/>&nbsp;行业数据分析</a>
                	</c:if>
                	<c:if test="${param.target != null }">
                	<a href="${base}/cycle/stat?cycletype=${param.cycletype}&target=${param.target}&statBy=generator">
                	<img src="${base}/images/stat.png" width="16" height="16"/>&nbsp;行业数据分析</a>
                	</c:if>
                </li>
                <li <c:if test="${param.currentNav == 'config'}">class="active"</c:if>>
                	<a href="${base}/cycle/config?cycletype=${param.cycletype}"><img src="${base}/images/config_16.png" width="16" height="16"/>&nbsp;自定义项目</a></li>
              </ul>
              
            <div style="float:right;width:100px;height:40px;line-height:40px;padding-left:10px;"><b>${curCycleType.name}</b></div>
			<div class="btn-group" style="float:right">
				  <a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
				    	生命周期选择
				    <span class="caret"></span>
				  </a>
				  <ul class="dropdown-menu">
				  	<c:forEach items="${session_cycletype_list}" var="cycletype">
				  		<c:if test="${param.currentNav == 'statistic' }">
					  	   <li>
					  	   <a href="${base}/cycle/stat?&cycletype=${cycletype.code}&target=${param.target}&statBy=generator">${cycletype.name}</a>
					  	   </li>
				  		</c:if>
				  		<c:if test="${param.currentNav == 'config' }">
					  	   <li><a href="${base}/cycle/config?&cycletype=${cycletype.code}">${cycletype.name}</a></li>
				  		</c:if>
				  	</c:forEach>
	              </ul>
			</div>
            </div><!--/.nav-collapse -->
          </div>
        </div>
      </div>
      
      