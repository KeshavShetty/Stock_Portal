alter table jqgrid_table_columns add column date_gap_days integer;

INSERT INTO db_versions VALUES('0031', now(), 'Keshav', 'Row select function for JqGrid columns','Schema' );