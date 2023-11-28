<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<div class="spui-frame">
	<div class="c2">
		<JqGridTable:addTable tableIdentifier="ScripPage_PeerTable" formRequired="true"/>
	</div>
</div>
<div class="spui-frame">
	<div class="c2">
		<JqGridTable:addTable tableIdentifier="ScripPage_Peer_CoRelationTable" formRequired="true"/>
	</div>
</div>