	create table sector (
	   id bigint not null ,
	   name varchar(255)
	);
	ALTER TABLE ONLY sector ADD CONSTRAINT sector_pkey PRIMARY KEY (id);
	CREATE SEQUENCE sector_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	
	create table watchlist (
	   id bigint not null ,
	   name varchar(255),
	   owner bigint,
	   f_bse_scrip bigint,
	   description character varying(1000),
	   default_watchlist boolean,
	   shared boolean
	);
	ALTER TABLE ONLY watchlist ADD CONSTRAINT watchlist_pkey PRIMARY KEY (id);
	CREATE SEQUENCE watchlist_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	
	create table transaction (
	   id bigint not null,
	   brokerage real,
	   quantity integer,
	   rate real,
	   transaction_date timestamp,
	   exchange_code boolean,
	   transaction_type boolean,
	   comment varchar(255),
	   f_scrip bigint,
	   f_owner bigint
	);
	ALTER TABLE ONLY transaction ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);
	CREATE SEQUENCE transaction_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table users (
	   id bigint not null ,
	   email_id varchar(50),
	   firstname varchar(25),
	   lastname varchar(25),
	   password varchar(255),
	   username varchar(12),
	   userstatus boolean,
	   captcha varchar(50),
	   mobile_number varchar(25),
	   notification_number varchar(25),
	   usertype boolean
	);
	ALTER TABLE ONLY users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
	CREATE SEQUENCE users_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;    
	    
	create table watchlist_item (
	   id bigint not null ,
	   f_watchlist bigint,
	   f_scrip bigint
	);
	ALTER TABLE ONLY watchlist_item ADD CONSTRAINT watchlist_item_pkey PRIMARY KEY (id);
	CREATE SEQUENCE watchlist_item_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	
	create table scrips (
	   id bigint not null ,
	   bse_code varchar(25),
	   icici_code varchar(25),
	   name varchar(100),
	   nse_code varchar(25),
	   f_sector bigint,
	   cmp real,
	   bse_name varchar(25),
	   todays_gain real,
	   previous_close real,
	   icici_name varchar(100),
	   strong_buy_rec integer,
	   strong_sell_rec integer,
	   symbol_type varchar(5),
	   series_type varchar(5),
	   book_value real,
  	   dividend_yield_percent real
	);
	ALTER TABLE ONLY scrips ADD CONSTRAINT scrips_pkey PRIMARY KEY (id);
	CREATE SEQUENCE scrips_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	
	create table bse_eq_eod_data (
	   id bigint not null ,
	   close_price real,
	   data_date date,
	   high_price real,
	   low_price real,
	   open_interest bigint,
	   open_price real,
	   previous_close real,
	   f_scrip bigint,
	   volume bigint
	);
	ALTER TABLE ONLY bse_eq_eod_data ADD CONSTRAINT bse_eq_eod_data_pkey PRIMARY KEY (id);
	
	create table nse_eq_eod_data (
	   id bigint not null ,
	   close_price real,
	   data_date date,
	   high_price real,
	   low_price real,
	   open_interest bigint,
	   open_price real,
	   previous_close real,
	   f_scrip bigint,
	   volume bigint
	);
	ALTER TABLE ONLY nse_eq_eod_data ADD CONSTRAINT nse_eq_eod_data_pkey PRIMARY KEY (id);
	
	CREATE SEQUENCE bse_eq_eod_data_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	  
	CREATE SEQUENCE nse_eq_eod_data_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	
	create table stock_in_hand (
		id bigint not null , 
		quantity integer, 
		f_scrip bigint, 
		average_buy_rate real,
		modify_date date,
		f_owner bigint
	);
	ALTER TABLE ONLY stock_in_hand ADD CONSTRAINT stock_in_hand_pkey PRIMARY KEY (id);
	CREATE SEQUENCE stock_in_hand_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
    
    create table simple_file (
    	id bigint not null , 
    	contentType varchar(100), 
    	data oid, 
    	filename varchar(255), 
    	size bigint not null
    );
    ALTER TABLE ONLY simple_file ADD CONSTRAINT simple_file_pkey PRIMARY KEY (id);
    CREATE SEQUENCE simple_file_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table knowledgebase (
		id bigint not null , 
		description text, 
		resource_date date, 
		f_file bigint, 
		f_uploader bigint,
		title varchar(255)
	);
	ALTER TABLE ONLY knowledgebase ADD CONSTRAINT knowledgebase_pkey PRIMARY KEY (id);
    CREATE SEQUENCE knowledgebase_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table analysis_history (
		id bigint not null , 
		analysis_type integer, 
		buy_description text, 
		buy_indicators integer, 
		cmp real, 
		data_date date, 
		exchange_code boolean, 
		scrip_id bigint, 
		sell_description text, 
		sell_indicators integer
	);
	ALTER TABLE ONLY analysis_history ADD CONSTRAINT analysis_history_pkey PRIMARY KEY (id);
    CREATE SEQUENCE analysis_history_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table stoploss_order (
		id bigint not null , 
		description text, 
		f_bse_scrip bigint, 
		name varchar(255), 
		owner bigint,
		target_date date,
		target_rate real,
		effective_date date,
		status boolean,
		order_rate real
	);
	ALTER TABLE ONLY stoploss_order ADD CONSTRAINT stoploss_order_pkey PRIMARY KEY (id);
    CREATE SEQUENCE stoploss_order_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table stoploss_order_transaction (
		id bigint not null , 
		f_transaction bigint, 
		f_stoploss bigint
	);
	ALTER TABLE ONLY stoploss_order_transaction ADD CONSTRAINT stoploss_order_transaction_pkey PRIMARY KEY (id);
    CREATE SEQUENCE stoploss_order_transaction_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table account_transaction (
		id bigint not null , 
		description varchar(255), 
		f_owner bigint, 
		transaction_date date, 
		transaction_type integer,
		amount real
	);
	ALTER TABLE ONLY account_transaction ADD CONSTRAINT account_transaction_pkey PRIMARY KEY (id);
    CREATE SEQUENCE account_transaction_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	create table account_transaction_types (
		id bigint not null , 
		name varchar(255)
	);
	ALTER TABLE ONLY account_transaction_types ADD CONSTRAINT account_transaction_types_pkey PRIMARY KEY (id);
    CREATE SEQUENCE account_transaction_types_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	CREATE TABLE country (
		id bigint NOT NULL,
		isd_code character varying(5),
  		name character varying(255)  
	);
	ALTER TABLE ONLY country ADD CONSTRAINT country_id_pkey PRIMARY KEY (id);
    CREATE SEQUENCE country_id_seq
	    START WITH 1000
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
	ALTER TABLE ONLY watchlist ADD CONSTRAINT fk_watchlist_owner FOREIGN KEY (owner) REFERENCES users(id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY watchlist_item ADD CONSTRAINT fk_watchlist_item_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips(id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY watchlist_item ADD CONSTRAINT fk_watchlist_item_f_watchlist FOREIGN KEY (f_watchlist) REFERENCES watchlist (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY scrips ADD CONSTRAINT fk_scrips_f_sector FOREIGN KEY (f_sector) REFERENCES sector (id) MATCH FULL DEFERRABLE;
	ALTER TABLE ONLY bse_eq_eod_data ADD CONSTRAINT fk_bse_eq_eod_data_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;
	ALTER TABLE ONLY nse_eq_eod_data ADD CONSTRAINT fk_nse_eq_eod_data_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;
	ALTER TABLE ONLY transaction ADD CONSTRAINT fk_transaction_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;
	ALTER TABLE ONLY stock_in_hand ADD CONSTRAINT fk_stock_in_hand_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY transaction ADD CONSTRAINT fk_transaction_f_owner FOREIGN KEY (f_owner) REFERENCES users (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY stock_in_hand ADD CONSTRAINT fk_stock_in_hand_f_owner FOREIGN KEY (f_owner) REFERENCES users(id) MATCH FULL DEFERRABLE;
	ALTER TABLE ONLY knowledgebase ADD CONSTRAINT fk_knowledgebase_f_file FOREIGN KEY (f_file) REFERENCES simple_file (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY knowledgebase ADD CONSTRAINT fk_knowledgebase_f_uploader FOREIGN KEY (f_uploader) REFERENCES users(id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY analysis_history ADD CONSTRAINT fk_analysis_history_scrip_id FOREIGN KEY (scrip_id) REFERENCES scrips (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY watchlist ADD CONSTRAINT fk_watchlist_f_bse_scrip FOREIGN KEY (f_bse_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY stoploss_order ADD CONSTRAINT fk_stoploss_order_f_bse_scrip FOREIGN KEY (f_bse_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY stoploss_order ADD CONSTRAINT fk_stoploss_order_owner FOREIGN KEY (owner) REFERENCES users (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY stoploss_order_transaction ADD CONSTRAINT fk_stoploss_order_transaction_f_transaction FOREIGN KEY (f_transaction) REFERENCES transaction (id) MATCH FULL DEFERRABLE;
	ALTER TABLE ONLY stoploss_order_transaction ADD CONSTRAINT fk_stoploss_order_transaction_f_stoploss FOREIGN KEY (f_stoploss) REFERENCES stoploss_order (id) MATCH FULL DEFERRABLE;	
	ALTER TABLE ONLY account_transaction ADD CONSTRAINT fk_account_transaction_f_owner FOREIGN KEY (f_owner) REFERENCES users(id) MATCH FULL DEFERRABLE;
	

INSERT INTO db_versions VALUES('0001', now(), 'Keshav', 'Insert initial schema','Schema' );