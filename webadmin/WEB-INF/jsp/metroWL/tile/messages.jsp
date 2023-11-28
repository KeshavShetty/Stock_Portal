<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="((fieldErrors != null && !fieldErrors.isEmpty()) || (actionErrors !=null && !actionErrors.isEmpty()))">
	<div id="WorkpaceMessagesBook" class="none">
		<div class="spui-book-content">
			<div id="WorkpaceMessagesPage" class="spui-page">
				<div id="portlet_messages" class="spui-window  ">
					<div class="spui-window-content">
						<!--messages-region-start-->
						<div id="asyncmessages">
							<div class="messagesbox tbframe">
								<div class="tbframeTop">
									<div>
										<div>
											&nbsp;
										</div>
									</div>
								</div>
								<div class="tbframeContent">
									<div class="spui-titlebar-button-panel">
										&nbsp;<a href="javascript:void(0)" onclick="$(this).parent().parent().parent().parent().parent().parent().hide();"><img src="<%=request.getContextPath()%>/metroWL/images/close.png" class="" title="close" alt="close "></a>
									</div>
									<h1 class="messagestitle"><img src="<%=request.getContextPath()%>/metroWL/images/error.png" alt="Message icon - Error ">Messages</h1>
									<s:if test="fieldErrors != null && !fieldErrors.isEmpty()" >
										<s:iterator value="fieldErrors" id="ferror" >
											<s:iterator value="%{value}" id="error">
												<div class="message">
													<span class="message_ERROR"><s:property value="error"/></span>
												</div>
											</s:iterator>
										</s:iterator>
									</s:if>
									<s:if test="actionErrors !=null && !actionErrors.isEmpty()" >
										<s:iterator value="actionErrors" id="error">
											<div class="message">
												<span class="message_ERROR"><s:text name="%{error}"/></span>
											</div>
										</s:iterator>
									</s:if>
								</div>
								<div class="tbframeBottom">
									<div>
										<div>
											&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:if>

<s:if test="actionMessages != null && !actionMessages.isEmpty()">
	<div id="WorkpaceMessagesBook" class="none">
		<div class="spui-book-content">
			<div id="WorkpaceMessagesPage" class="spui-page">
				<div id="portlet_messages" class="spui-window  ">
					<div class="spui-window-content">
						<!--messages-region-start-->
						<div id="asyncmessages">
							<div class="messagesbox tbframe">
								<div class="tbframeTop">
									<div>
										<div>
											&nbsp;
										</div>
									</div>
								</div>
	
								<div class="tbframeContent">
									<div class="spui-titlebar-button-panel">
										&nbsp;<a href="javascript:void(0)" onclick="$(this).parent().parent().parent().parent().parent().parent().hide();"><img src="<%=request.getContextPath()%>/metroWL/images/close.png" class="" title="close" alt="close "></a>
									</div>
									<h1 class="messagestitle"><img src="<%=request.getContextPath()%>/metroWL/images/success.png" alt="Message icon - Success ">Messages</h1>
									<s:iterator value="actionMessages" id="msg">
										<div class="message">
											<span class="message_SUCCESS"><s:text name="%{msg}"/></span>
										</div>
									</s:iterator>								
								</div>
	
								<div class="tbframeBottom">
									<div>
										<div>
											&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div><!--messages-region-end-->
					</div>
				</div>
			</div>
		</div>
	</div>
</s:if>

<% 	// Todo: Use this list to iterate to display the info message
	if (request.getAttribute("InfoMessageList")!=null) { %> 
<div id="WorkpaceMessagesBook" class="none">
	<div class="spui-book-content">
		<div id="WorkpaceMessagesPage" class="spui-page">
			<div id="portlet_messages" class="spui-window  ">
				<div class="spui-window-content">
					<!--messages-region-start-->
					<div id="asyncmessages">
						<div class="messagesbox tbframe">
							<div class="tbframeTop">
								<div>
									<div>
										&nbsp;
									</div>
								</div>
							</div>
							<div class="tbframeContent">
								<div class="spui-titlebar-button-panel">
									&nbsp;<a href="javascript:void(0)" onclick="$(this).parent().parent().parent().parent().parent().parent().hide();"><img src="<%=request.getContextPath()%>/metroWL/images/close.png" class="" title="Close" alt="Close "></a>
								</div>
								<h1 class="messagestitle"><img src="<%=request.getContextPath()%>/metroWL/images/info.png" alt="Message icon - Error ">Messages</h1>
								<div class="message">
									<span class="message_INFO">Yet to find a way to display Info from Strtus 2</span>
								</div>
							</div>
							<div class="tbframeBottom">
								<div>
									<div>
										&nbsp;
									</div>
								</div>
							</div>
						</div>
					</div><!--messages-region-end-->
				</div>
			</div>
		</div>
	</div>
</div>
<% } %>