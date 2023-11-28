drop SEQUENCE nse_bulk_deal_id_seq;
drop table nse_bulk_deal;

CREATE SEQUENCE bulk_deal_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;

create table bulk_deal (
	id bigint not null ,
	f_scrip bigint,
	data_date date,
	transaction_type boolean,
  price real,
	volume bigint,
	client_name varchar(255)
);

ALTER TABLE ONLY bulk_deal ADD CONSTRAINT bulk_deal_pkey PRIMARY KEY (id);
ALTER TABLE ONLY bulk_deal ADD CONSTRAINT fk_bulk_deal_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;

INSERT INTO db_versions VALUES('0040', now(), 'Keshav', 'Bulk deal','Schema' );