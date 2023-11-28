DROP TABLE watchlist_item cascade;
DROP SEQUENCE watchlist_item_id_seq;

DROP TABLE watchlist cascade;
DROP SEQUENCE watchlist_id_seq;

CREATE TABLE watchlist
(
  id bigint NOT NULL,
  wl_name character varying(255),
  f_owner bigint,
  description character varying(1000),
  default_watchlist boolean,
  shared boolean,
  CONSTRAINT watchlist_pkey PRIMARY KEY (id),  
  CONSTRAINT fk_watchlist_owner FOREIGN KEY (f_owner)
      REFERENCES users (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)WITH (
  OIDS=FALSE
);

CREATE SEQUENCE watchlist_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE watchlist_item
(
  id bigint NOT NULL,
  f_watchlist bigint,
  f_scrip bigint,
  sih_quantity bigint default 0,
  average_buy_rate real default 0,
  CONSTRAINT watchlist_item_pkey PRIMARY KEY (id),  
  CONSTRAINT fk_watchlist FOREIGN KEY (f_watchlist)
      REFERENCES watchlist (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT fk_scrips FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)WITH (
  OIDS=FALSE
);

CREATE SEQUENCE watchlist_item_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE watchlist_transaction
(  
  id bigint NOT NULL,
  f_watchlist_item bigint,
  transaction_type boolean,
  exchange boolean,
  transaction_date date,
  quantity bigint,
  rate real default 0,
  brokerage real default 0, 
  settlement_number character varying(25),
  CONSTRAINT fk_watchlist_item FOREIGN KEY (f_watchlist_item)
      REFERENCES watchlist_item (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE  
)WITH (
  OIDS=FALSE
);
CREATE SEQUENCE watchlist_transaction_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


INSERT INTO db_versions VALUES('0011', now(), 'Keshav', 'Scrips for watchlist','Schema' );