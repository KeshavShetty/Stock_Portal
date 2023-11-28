CREATE SEQUENCE intraday_snapshot_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE intraday_snapshot_data
(
  id bigint NOT NULL,
  f_scrip bigint,
  data_date date,
  exchange_code boolean default true,  
  open_price real DEFAULT 0,
  high_price real DEFAULT 0,
  low_price real DEFAULT 0,
  close_price real DEFAULT 0,
  last_price real DEFAULT 0,
  mean_price real DEFAULT 0,
  total_volume bigint DEFAULT 0,
  highprice_time timestamp without time zone,
  lowprice_time timestamp without time zone,
  cf_weightage real,
  mf_aboveclose_days integer,
  CONSTRAINT intraday_snapshot_data_pkey PRIMARY KEY (id),
  CONSTRAINT fk_intraday_snapshot_data_f_scrip FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT intraday_snapshot_data_uk UNIQUE (f_scrip, data_date)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE intraday_snapshot_data OWNER TO postgres;

CREATE INDEX intraday_snapshot_data_idx1
  ON intraday_snapshot_data
  USING btree
  (f_scrip, data_date);

alter table intraday_snapshot_data
add COLUMN volume_b4_3 bigint DEFAULT 0,
add column volume_a3_b4_315 bigint DEFAULT 0,
add COLUMN cf_rating real;
	    

alter table intraday_snapshot_data
add column avg_price_before2 real,
add column avg_price_after2 real,
add COLUMN total_volume_before2 bigint,
add COLUMN total_volume_after2 bigint,
add column orb_high_at_10 real,
add column orb_low_at_10 real
;

alter table intraday_snapshot_data
add column first_10min_High real,
add column first_10min_Low real;

INSERT INTO db_versions VALUES('0019', now(), 'Keshav', 'Snapshot data from intraday','Schema' );