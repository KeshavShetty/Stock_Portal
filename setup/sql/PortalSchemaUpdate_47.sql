CREATE SEQUENCE exchange_news_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE exchange_news
(
  id bigint NOT NULL,
  exchange_code boolean,
  news_type character varying(100),
  f_scrip bigint,
  exchange_announced_time timestamp,
  updated_in_system timestamp DEFAULT now(),
  news_title character varying(1024),
  news_hyper_link character varying(2048),
  
  CONSTRAINT exchange_news_pkey PRIMARY KEY (id),
  CONSTRAINT fk_exchange_news_f_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)WITH (
  OIDS=FALSE
);

CREATE SEQUENCE twitter_post_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE twitter_post
(
  id bigint NOT NULL,
  source_user character varying(100),
  twitter_post_id character varying(100),
  post_content character varying(2048),
  announced_time timestamp,
  updated_in_system timestamp DEFAULT now(),
  
  CONSTRAINT twitter_post_pkey PRIMARY KEY (id),
    CONSTRAINT twitter_post_id_uk UNIQUE (twitter_post_id)

)WITH (
  OIDS=FALSE
);




INSERT INTO db_versions VALUES('0047', now(), 'Keshav', 'Scrips for exchange news','Schema' );