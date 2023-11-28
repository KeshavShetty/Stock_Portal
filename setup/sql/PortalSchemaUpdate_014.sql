CREATE SEQUENCE feed_source_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE feed_source
(
  id bigint NOT NULL,
  source_name character varying(255),
  feed_type character varying(10),
  feed_link character varying(250),
  feed_main_url character varying(250),
  last_updated_on timestamp with time zone,
  registered_date date,
  registered_by bigint,
  status character varying(10) DEFAULT 'Active',
  post_frequency integer,
  html_selector character varying(255),
  CONSTRAINT rss_feeds_source_pkey PRIMARY KEY (id),  
  CONSTRAINT fk_feed_source_f_registered_by FOREIGN KEY (registered_by) REFERENCES users (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)WITH (
  OIDS=FALSE
);

CREATE SEQUENCE feed_posts_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE feed_posts
(
  id bigint NOT NULL,
  feed_source bigint,
  post_title character varying(250),
  post_short_content character varying(500),
  publish_date timestamp,
  author character varying(100),
  unique_gui_id character varying(250),  
  solr_entry_deleted boolean DEFAULT false,
  date_added date DEFAULT now(),
  CONSTRAINT feed_posts_pkey PRIMARY KEY (id),  
  CONSTRAINT fk_feed_posts_feed_source FOREIGN KEY (feed_source) REFERENCES feed_source (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)WITH (
  OIDS=FALSE
);
CREATE INDEX feed_posts_idx1 on feed_posts USING btree (publish_date); 

CREATE SEQUENCE feed_scrip_map_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE feed_scrip_map
(
  id bigint NOT NULL,
  feed_post bigint,
  scrip bigint, 
  CONSTRAINT feed_scrip_map_pkey PRIMARY KEY (id),  
  CONSTRAINT fk_feed_scrip_map_feed_post FOREIGN KEY (feed_post) REFERENCES feed_posts (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
  CONSTRAINT fk_feed_scrip_map_scrip FOREIGN KEY (scrip) REFERENCES scrips (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)WITH (
  OIDS=FALSE
);
CREATE INDEX feed_scrip_map_idx1 on feed_scrip_map USING btree (scrip); 

INSERT INTO db_versions VALUES('0014', now(), 'Keshav', 'Scrips for rss/atom feeds/posts','Schema' );