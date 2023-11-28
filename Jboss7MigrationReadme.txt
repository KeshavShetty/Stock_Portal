1. Download and Install Jboss 7 from http://www.jboss.org/jbossas/downloads/

2. Database driver configuration as a module. (module name configured as org.postgresql in module.xml)
   Copy etc/jboss7/module/org to D:\programs\jboss-as-7.0.0.Final\modules\org 
   
3. Configure datasource
   Edit standalone.xml inside D:\programs\jboss-as-7.0.0.Final\standalone\configuration add following xml section inside <datasources> 
   
		<datasource jndi-name="jdbc/portalDS" pool-name="portalDatasource" enabled="true" jta="true" use-java-context="true" use-ccm="true">
        	<connection-url>jdbc:postgresql://localhost:5432/stockPortal_database</connection-url>
			<driver-class>org.postgresql.Driver</driver-class>
			<driver>postgresql</driver>
			<transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
			<pool>
				<min-pool-size>10</min-pool-size>
				<max-pool-size>20</max-pool-size>
                <prefill>true</prefill>
                <use-strict-min>false</use-strict-min>
				<flush-strategy>FailingConnectionOnly</flush-strategy>
			</pool>
            <security>
            	<user-name>postgres</user-name>
				<password>jijikos</password>
			</security>
            <validation>
            	<validate-on-match>false</validate-on-match>
				<background-validation>false</background-validation>
				<useFastFail>false</useFastFail>
			</validation>
            <statement>
            	<prepared-statement-cache-size>100</prepared-statement-cache-size><share-prepared-statements/>
            </statement>
		</datasource>

	Also add driver section inside <drivers> as below
	
	<driver name="postgresql" module="org.postgresql"/>


4. Use ant target ant deploy-ear which will generate compressed ear and deploys in jboss 7 standalone deployments folder.
   Note: Update dev_build.properties with proper value for jboss7.deployment.folder

5. Start Jboss 7 by running standalone.bat inside D:\programs\jboss-as-7.0.0.Final\bin folder.

6. For session related issue (From Apache to Jboss)
	Upgrade Apache to 2.2 and include in httpd.conf the below line (at the end or after ProxyPassreverse)
	ProxyPassReverseCookiePath / / 
