
alter table autoscan_study_library add column retrieve_order int;


INSERT INTO db_versions VALUES('0039', now(), 'Keshav', 'retrieve_order added for Scrip Summary page','Schema' );