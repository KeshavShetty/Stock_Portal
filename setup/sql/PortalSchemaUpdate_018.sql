CREATE SEQUENCE icici_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
	    
CREATE TABLE icici_order
(
  id bigint NOT NULL,
  f_scrip bigint,
  order_date timestamp without time zone,
  exchange_code boolean,  
  order_type boolean,  
  transaction_type boolean,  
  quantity bigint DEFAULT 0,
  at_price real DEFAULT 0,
  maximum_budget real DEFAULT 0, 
  f_placed_by bigint, 
  order_status character varying(100),
  CONSTRAINT icici_order_pkey PRIMARY KEY (id),
  CONSTRAINT fk_icici_order_placed_by FOREIGN KEY (f_placed_by)
      REFERENCES users (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT fk_icici_order_f_scrip FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)
WITH (
  OIDS=FALSE
);
	
	    
INSERT INTO db_versions VALUES('0018', now(), 'Keshav', 'Order placed in Icici HttpClient','Schema' );