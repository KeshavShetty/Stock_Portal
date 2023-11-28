CREATE SEQUENCE watchlist_sector_analysis_id_seq
	START WITH 1000
	INCREMENT BY 1
	NO MAXVALUE
	NO MINVALUE
	CACHE 1;
	
create table watchlist_sector_analysis (
   id bigint not null,
   f_watchlist bigint,
   f_sector bigint,
   data_date date,
   scrip_count integer,
   avgsqg real   
);

ALTER TABLE ONLY watchlist_sector_analysis ADD CONSTRAINT watchlist_sector_analysis_pkey PRIMARY KEY (id);
ALTER TABLE ONLY watchlist_sector_analysis ADD CONSTRAINT fk_watchlist_sector_analysis_f_watchlist FOREIGN KEY (f_watchlist) REFERENCES watchlist (id) MATCH FULL DEFERRABLE;	
ALTER TABLE ONLY watchlist_sector_analysis ADD CONSTRAINT fk_watchlist_sector_analysis_f_sector FOREIGN KEY (f_sector) REFERENCES sector (id) MATCH FULL DEFERRABLE;

ALTER TABLE watchlist_sector_analysis ADD CONSTRAINT watchlist_sector_analysisUK unique (f_watchlist,f_sector, data_date);


INSERT INTO db_versions VALUES('0044', now(), 'Keshav', 'watchlist_sector_analysis','Schema' );