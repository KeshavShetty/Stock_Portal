alter table JqGrid_Table_View add column on_select_row_function character varying(1000);

update JqGrid_Table_View set on_select_row_function='showFloatingColorBox(''/portal/scripInfo.do'', ''jqIndex=''+id , 800, 600)'
where table_identifier not like 'Scrip%';

INSERT INTO db_versions VALUES('0030', now(), 'Keshav', 'Row select function for JqGrid columns','Schema' );