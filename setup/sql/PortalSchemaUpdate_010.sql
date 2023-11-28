CREATE TABLE bse_intraday_summary_data
(
  id bigint NOT NULL,
  f_scrip bigint,
  data_date date,  
  open_price real DEFAULT 0,
  high_price real DEFAULT 0,
  low_price real DEFAULT 0,  
  close_price real DEFAULT 0,  
  last_price real DEFAULT 0,
  total_volume bigint DEFAULT 0, 
  highprice_time timestamp,
  lowprice_time timestamp,  
  CONSTRAINT bse_intraday_summary_data_pkey PRIMARY KEY (id),
  CONSTRAINT fk_bse_intraday_summary_data_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT bse_intraday_summary_data_uk UNIQUE (f_scrip, data_date)
)
WITH (
  OIDS=FALSE
);

CREATE INDEX bse_intraday_summary_data_idx1 ON bse_intraday_summary_data USING btree (f_scrip, data_date);

CREATE SEQUENCE bse_intraday_summary_data_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
INSERT INTO db_versions VALUES('0010', now(), 'Keshav', 'Scrips for bse intra day summary data','Schema' );