<%-----------------------------------------------------------------------------
	Copyright (c) 2004 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation. 
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" buffer="none" %>
<%@ page import="org.eclipse.birt.report.resource.BirtResources,
				 org.eclipse.birt.report.utility.ParameterAccessor" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />
<!-- to do 中文化配置 -->
<%-----------------------------------------------------------------------------
	Navigation bar fragment
-----------------------------------------------------------------------------%>
<TR 
	<%	
		String imagesPath = "birt/images/";
	
		if( attributeBean.isShowNavigationbar( ) )
		{
	%>
		HEIGHT="25px"
	<%
		}
		else
		{
	%>
		style="display:none"
	<%
		}
	%>	
>
	<TD>
		<DIV id="navigationBar">
			<TABLE CELLSPACING="0" CELLPADDING="0" WIDTH="100%" HEIGHT="25px" CLASS="birtviewer_navbar">
				<TR><TD></TD></TR>
				<TR>
				   <TD WIDTH="1%"/>
					<TD WIDTH="1%">
                        <INPUT TYPE="image" NAME='exportReport' onclick="updateIframe(300)" SRC="birt/images/ExportReport.gif" id="exportReport"
                               TITLE="导出报告"
                               ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.exportreport" )%>" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="1%"/>
                    <%
                        String reportName = attributeBean.getReportDesignName();
                        if(reportName.contains("mc_imp_report.rptdesign") || reportName.contains("mc_imp_sum_report.rptdesign")) {
                    %>
                    <%}else { %>
                    <%} %>
					<% 
						String rptName = attributeBean.getReportDesignName();
					    if (rptName.contains("mc_imp_report.rptdesign")){
					%>
					<TD WIDTH="1%">
						<img title="一键导出imp时段报告" height="15" onclick="parent.__imp.oneKeyExport();" style="cursor:pointer; margin-top:3px;" width="17" src="birt/images/one-key.png">
					</TD>
					<%} %>
					<TD WIDTH="1%"/>
					<TD WIDTH="1%">
					  <INPUT TYPE="image" NAME='export' SRC="birt/images/Export.gif" onclick="updateIframe(400);"
					   		TITLE="导出数据"
					   		ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.export" )%>" CLASS="birtviewer_clickable" style="display: none">
					</TD>
					<TD WIDTH="1%"/>
					<TD WIDTH="1%">
                        <INPUT TYPE="image" NAME='toc' SRC="birt/images/Toc.gif"
                               TITLE="目录"
                               ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.toc" )%>" CLASS="birtviewer_clickable" style="display: none">
					</TD>
					<script type="text/javascript">
					    function updateIframe(height) {
					    	var tableLayout = document.getElementById( "layout" );
							rowHeight = tableLayout.offsetHeight;
							
							if(document.getElementById("imp_notes")) {
								rowHeight = rowHeight + 70;
							}
							
							if(height > rowHeight) {
								parent.__common.changeIframeHeight(height);
							}
							
							//setTimeout(locationDialog, 100);
					    }
					    
					    function locationDialog () {
					    	var exportDialog = document.getElementById("exportReportDialog");
					    	exportDialog.style.top = "0px";
					    }
					    
					</script>
					<TD WIDTH="1%"/>
					<TD WIDTH="1%">
					   		<INPUT TYPE="image" NAME='print' SRC="birt/images/Print.gif"
					   		TITLE="打印"
					   		ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.print" )%>" CLASS="birtviewer_clickable" style="display: none">
					</TD>
					<%
					if( ParameterAccessor.isSupportedPrintOnServer )
					{
					%>					
					<TD WIDTH="1%"/>
					<TD WIDTH="1%">
					   <INPUT TYPE="image" NAME='printServer' SRC="birt/images/PrintServer.gif"
					   		TITLE="打印到服务器上"
					   		ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.printserver" )%>" CLASS="birtviewer_clickable" style="display: none">
					</TD>
					<%
					}
					%>
                    <TD WIDTH="1%">&nbsp;</TD>
					<td WIDTH="64%" >
                        <INPUT TYPE="image" NAME='parameter' SRC="birt/images/Report_parameters.gif" onclick="updateIframe(500);"
                               TITLE="运行报告"
                               ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.parameter" )%>" CLASS="birtviewer_clickable" style="display: none">
					</td>
					<TD  WIDTH="1%">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"LastPage":"FirstPage") + "_disabled.gif" %>" NAME='first'
							ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.navbar.first" )%>" 
							TITLE="第一页" CLASS="birtviewer_clickable">
					</TD>
					<TD  WIDTH="1%">
							<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"NextPage":"PreviousPage") + "_disabled.gif" %>" NAME='previous' 
							ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.navbar.previous" )%>" 
							TITLE="上一页" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="1%">&nbsp;</TD>
					<TD  NOWRAP>
						<B>
						<%
							if ( attributeBean.getBookmark( ) != null )
							{
						%>
							<SPAN style="display: none" ID='pageNumber'></SPAN>&nbsp;
						<%
							}
							else
							{
						%>
							<SPAN  style="display: none" ID='pageNumber'><%= ""+attributeBean.getReportPage( ) %></SPAN>&nbsp;
						<%
							}
						%>
						</B>
					</TD>
					
					<TD ALIGN="right" width="1%">
						<INPUT ID='gotoPage' TYPE='text' VALUE='' style="text-align:center;" MAXLENGTH="8" SIZE='3' CLASS="birtviewer_navbar_input">
					</TD>
					<td WIDTH="3%" style="text-align:center;">	<b>共
							<SPAN ID='totalPage'></SPAN>页</b></td>
					<td WIDTH="1%">&nbsp;</td>
					<TD WIDTH="1%">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"PreviousPage":"NextPage") + "_disabled.gif" %>" NAME='next'
						    ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.navbar.next" )%>" 
							TITLE="下一页" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="1%">
							<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"FirstPage":"LastPage") + "_disabled.gif" %>" NAME='last'
						    ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.navbar.last" )%>"
							TITLE="最后一页" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="1%">&nbsp;</TD>
					<td width = "2%">
					    <select id="select_page">
					         <option> 10 </option>
					         <option selected = "selected"> 20 </option>
					         <option> 30 </option>
					         <option> 50 </option>
					    </select>
					</td>
					<TD WIDTH="1%">&nbsp;</TD>
					<TD WIDTH="7%"><div id="load_time_part" style="display: none;">加载 ( <span id="rpt_load_time"></span> ) 秒.</div></TD>
				</TR>
			</TABLE>
		</DIV>
	</TD>
</TR>
	<script type="text/javascript">
	 <%
	    String recordNum = request.getParameter("recordNum");
	 %>
	 var select_page = document.getElementById("select_page");
	 var recordNum = <%=recordNum%>;
	 
	 for (var i = 0; i < select_page.options.length; i++) {        
	        if (select_page.options[i].text == recordNum) {        
	        	select_page.options[i].selected = true;        
	        }        
	  }  
	
	  function exportExcel() {
		  <%
		     String url = request.getRequestURL() + "?" + request.getQueryString();
		  %>
		 parent.location = "<%=url%>&__format=xls&__asattachment=true&__overwrite=false";
	  }
	</script>
