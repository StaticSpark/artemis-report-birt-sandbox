<%@ page language="java" pageEncoding="UTF-8" %>

<div id="footer">
   
   <%
		 String isTestValFoot = (String) session.getAttribute("isTest");
		 if("true".equals(isTestValFoot)) {
		%>
		   <div id="qunit_result">
			  <h1 id="qunit-header">QUnit example</h1>
			  <h2 id="qunit-banner"></h2>
			  <h2 id="qunit-userAgent"></h2>
			  <ol id="qunit-tests"></ol>
		   </div>
		<%
		 }
		%>
	<p>如果您有任何建议和意见请联系 <a href="mailto:project-artemis@funshion.com">project-artemis@funshion.com</a></p>
	<p>Visit <a href="http://redmine.funshion.com/redmine/projects/ad/wiki">Redmine project</a>,
	<a href="http://builds.funshion.com:8080/jenkins/job/Atlas_Parent/">Jenkins job</a>
	or <a href="http://builds.funshion.com:8080/jenkins/job/Atlas_Parent/site/">maven generated documentation site</a>.
	</p>
	<p>Microlens Artemis 2013 Funshion</p>
</div>