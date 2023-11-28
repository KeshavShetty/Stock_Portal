CREATE TABLE javachart_trendlines
(  
	id bigint not null ,
	f_user bigint,
	symbol VARCHAR(20) NULL,
	trendline_value VARCHAR(1000) NULL,
  CONSTRAINT javachart_trendlines_pkey PRIMARY KEY (id),
  CONSTRAINT fk_javachart_trendlines_f_user FOREIGN KEY (f_user)
      REFERENCES users (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)
WITH (
  OIDS=FALSE
);

CREATE INDEX javachart_trendlines_symbol_idx1 on javachart_trendlines USING btree (f_user,symbol); 

CREATE SEQUENCE javachart_trendlines_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


INSERT INTO db_versions VALUES('0036', now(), 'Keshav', 'Javachart trendlines','Schema' );