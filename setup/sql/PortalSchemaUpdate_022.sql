CREATE SEQUENCE index_scrips_id_seq
	    START WITH 1
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
		
CREATE TABLE index_scrips (
id bigint NOT NULL,
f_index_scrip bigint,
f_scrip bigint,
weightage real DEFAULT 0,
CONSTRAINT index_scrips_pkey PRIMARY KEY (id),
CONSTRAINT fk_index_scrips_f_index_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE,
CONSTRAINT fk_index_scrips_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE
);

alter table bse_eq_eod_data add column stochastic_value real;
alter table nse_eq_eod_data add column stochastic_value real;

alter table bse_eq_eod_data add column price_move_trend bool;
alter table nse_eq_eod_data add column price_move_trend bool;

INSERT INTO db_versions VALUES('0022', now(), 'Keshav', 'Index Scrips tables','Schema' );