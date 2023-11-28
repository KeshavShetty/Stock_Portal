
CREATE SEQUENCE forthcoming_result_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;

create table forthcoming_result (
	id bigint not null ,
	f_scrip bigint,
	result_date date,
	exchange_code boolean
);

ALTER TABLE ONLY forthcoming_result ADD CONSTRAINT forthcoming_result_pkey PRIMARY KEY (id);
ALTER TABLE ONLY forthcoming_result ADD CONSTRAINT fk_forthcoming_result_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;


alter table watchlist add column max_capital_used real;
alter table watchlist add column available_capital real;
alter table watchlist add column sih_worth real;
alter table watchlist add column wl_score real;


INSERT INTO db_versions VALUES('0042', now(), 'Keshav', 'Forthcoming result','Schema' );