<#if (parameters.renderAs?default('json') == 'html')>
 <!-- START HTML Table Content-->
<#else>	
{<#t/>
 sEcho: ${parameters.pagingObject.pagingCall},<#t/>
 iTotalRecords: ${parameters.queryList.resultsTotalSize},<#t/>
 iTotalDisplayRecords: ${parameters.queryList.resultsTotalSize},<#t/>
 aaData:<#t/>
 [<#t/>
</#if>