drop table nse_intraday_summary_data;
drop table bse_intraday_summary_data;
drop SEQUENCE nse_intraday_summary_data_id_seq;
drop SEQUENCE bse_intraday_summary_data_id_seq;

CREATE TABLE intraday_summary_data
(
  id bigint NOT NULL,
  f_scrip bigint,
  data_date date,  
  exchange_code boolean, 
  open_price real DEFAULT 0,
  high_price real DEFAULT 0,
  low_price real DEFAULT 0,  
  close_price real DEFAULT 0,  
  last_price real DEFAULT 0,
  total_volume bigint DEFAULT 0, 
  highprice_time timestamp,
  lowprice_time timestamp,  
  CONSTRAINT intraday_summary_data_pkey PRIMARY KEY (id),
  CONSTRAINT fk_intraday_summary_data_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT intraday_summary_data_uk UNIQUE (f_scrip, data_date)
)
WITH (
  OIDS=FALSE
);

CREATE INDEX intraday_summary_data_idx1 ON intraday_summary_data USING btree (f_scrip, data_date);

CREATE SEQUENCE intraday_summary_data_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
INSERT INTO db_versions VALUES('0016', now(), 'Keshav', 'Scrips for intra day summary data','Schema' );