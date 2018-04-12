<#include "../macros/constant_output_macro.ftl">
<#assign staticName="login" title="登陆"/>
<@htmlCommonHead/>
</head>
<body id="login_bg"  onload="outOfFrame()">
	<div class="login_wrapper">
		<div class="login_header">
			<a><img src="resources/css/m_images/last.png" alt=""/></a>
			<!--div id="cloud_s"><img src="img/cloud_s.png" alt="" width="81" height="52"/></div>
			<div id="cloud_m"><img src="img/cloud_m.png" alt="" width="136" height="95"/></div-->
		</div>
		<div class="login_box">
			<form id="loginForm" method="post" action="#">
				<input type="text" id="username" name="username" tabindex="1" placeholder="用户名" value="<#if loginName??>${loginName}</#if>" />
				<input type="password" id="password" name="password" tabindex="2" placeholder="密码" />
				<input type="button" id="submitLogin" value="登&nbsp;&nbsp;&nbsp;&nbsp;录" />
				<p class="error" id="beError"></p>
			</form>
		</div>
		<div class="copyright">
			<p>© <#if versionYear??>${versionYear}</#if> 证大财富 All Rights Reserved</p>
		</div>
	</div>
</body>
</html>