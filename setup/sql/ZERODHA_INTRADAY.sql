
CREATE SEQUENCE ZERODHA_INTRADAY_ORDERS_ID_SEQ
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;

create table ZERODHA_INTRADAY_ORDERS (
	id bigint not null,
	f_scrip bigint,
	order_type varchar(5),
	quantity integer,
	entry_rate real,
	stoploss_rate real,
	order_time timestamp default current_timestamp,
	order_status varchar(25),
	zerodha_order_id varchar(50),
	zerodha_comment_id varchar(100)
);

ALTER TABLE ONLY ZERODHA_INTRADAY_ORDERS ADD CONSTRAINT ZERODHA_INTRADAY_ORDERS_pkey PRIMARY KEY (id);
ALTER TABLE ONLY ZERODHA_INTRADAY_ORDERS ADD CONSTRAINT fk_ZERODHA_INTRADAY_ORDERS_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;

