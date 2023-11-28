alter table watchlist add column watchlist_type VARCHAR(10);
alter table watchlist add column scrip_fecth_sql VARCHAR(500);

update watchlist set watchlist_type = 'NORMAL';
update watchlist set scrip_fecth_sql = 'select f_scrip from watchlist_item where f_watchlist = ' || id::text;


alter table watchlist add column f_parent_watchlist bigint;
ALTER TABLE ONLY watchlist ADD CONSTRAINT fk_watchlist_f_parent_watchlist FOREIGN KEY (f_parent_watchlist) REFERENCES watchlist (id) MATCH FULL DEFERRABLE;


INSERT INTO db_versions VALUES('0034', now(), 'Keshav', 'Scrips History','Schema' );