alter table jqgrid_table_columns add column filter_form_tab_order integer;
update jqgrid_table_columns set filter_form_tab_order = column_fetch_order;

alter table jqgrid_table_columns add column default_minimum_filter_value character varying(100);
alter table jqgrid_table_columns add column default_maximum_filter_value character varying(100);

alter table jqgrid_table_view add column grid_height integer default 400;

INSERT INTO db_versions VALUES('0026', now(), 'Keshav', 'New columns for jqGrid','Schema' );