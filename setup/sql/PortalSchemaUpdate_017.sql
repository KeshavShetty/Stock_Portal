CREATE SEQUENCE intraday_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
	    
CREATE TABLE intraday_data
(
  id bigint NOT NULL,
  f_scrip bigint,
  data_time timestamp without time zone,
  exchange_code boolean,  
  price real DEFAULT 0,
  volume bigint DEFAULT 0,
  CONSTRAINT intraday_data_pkey PRIMARY KEY (id),
  CONSTRAINT fk_intraday_summary_data_f_scrip FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT intraday_data_uk UNIQUE (f_scrip, data_time, exchange_code)
)
WITH (
  OIDS=FALSE
);
	    
INSERT INTO db_versions VALUES('0017', now(), 'Keshav', 'Scrips for intra day data','Schema' );