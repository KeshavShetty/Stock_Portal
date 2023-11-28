<#if (parameters.renderAs?default('json') == 'html')>
 </tr><#t/>
<#else>
  ]<#t/>
 <#if ( parameters.last?if_exists != true ) >
  ,<#t/>
 </#if>
</#if>