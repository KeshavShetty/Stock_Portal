CREATE SEQUENCE zerodha_intraday_streaming_data_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE zerodha_intraday_streaming_data
(
  id bigint NOT NULL,
  instrument_token character varying(25),
  quote_time timestamp, 
  avgTP real, 
  last_traded_quantity real, 
  last_traded_price real,
  open_price real, 
  high_price real, 
  low_price real, 
  close_price real, 
  netpricechange_from_closingPrice real,
  total_buyQuantity real, 
  total_sellQuantity real,
  
  CONSTRAINT zerodha_intraday_streaming_data_pkey PRIMARY KEY (id)
 
)WITH (
  OIDS=FALSE
);

CREATE INDEX zerodha_intraday_streaming_data_idx1 on zerodha_intraday_streaming_data 
USING btree (instrument_token,quote_time); 

INSERT INTO db_versions VALUES('0048', now(), 'Keshav', 'Tables for Zerodha Intraday Streaming date using Websocket','Schema' );