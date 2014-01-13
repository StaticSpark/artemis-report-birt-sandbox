<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript"	src="${ctx}/js/component/artemis-chart-cookie.js"></script>
<script type="text/javascript"	src="${ctx}/js/component/chart.js"></script>

<script src="${ctx}/js/jquery-iphone/jquery.iphone-switch.js" type="text/javascript"></script>
              <div id="chart_area" style="margin-left: 0px;margin-bottom: 5px;position:absolute">
			      <hr id="chart_line_split" style="display:none" color='#ADADAD' size="1"/>
			      <br/>
			      <span>
			      <table>
			          <tr>
			              <td>
			              		 <span id="chart_control" style="display:none;">
						       		<table>
						       		    <tr>
						       		        <td>
						       		           <span id="switch_label"><b>图表控制：</b></span> 
						       		        </td>
						       		        <td id="switch_chart_td">
						       		            <div id="switch_chart" style="display:inline"></div>
						       		        </td>
						       		    </tr>
						       		</table>
						         </span>
			              </td>
			              <td>
			                   <span id="chart_types" style="display:none; margin-left: 20px;">
							       <a name="chart_type"  rel="nofollow" class= "chart_type" id="line">折线图 </a>  <span style="margin-left: 5px;color:gray;"> | </span>
							       <a name="chart_type" rel="nofollow" class= "chart_type" id="column">柱状图 </a><span style="margin-left: 5px;color:gray;"> | </span>
							       <a name="chart_type" rel="nofollow" class= "chart_type" id="area">阴影图  </a>
						       </span>
						       
						       <span id="tips" style = "display : none;color:#613030;margin-left: 20px;">
						       </span>
			              </td>
			          </tr>
			      </table>
				       
			       </span>
<!-- 			       <br/> -->
<!-- 			       &nbsp; -->
			       <div id="chart" style="margin-top:10px;width:100%; margin: 0 auto;"></div>
			   </div>
