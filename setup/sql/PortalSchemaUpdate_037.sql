CREATE TABLE split_n_bonus
(  
	id bigint not null ,
	f_scrip bigint,
	effect_date date,
	for_shares integer,
	bonus_shares integer,
	data_updated boolean,
  CONSTRAINT split_n_bonus_pkey PRIMARY KEY (id),
  CONSTRAINT fk_split_n_bonus_f_scrip FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE split_n_bonus_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

ALTER TABLE split_n_bonus ADD CONSTRAINT split_n_bonus_UK unique (f_scrip, effect_date);


INSERT INTO db_versions VALUES('0037', now(), 'Keshav', 'Split and bonus info','Schema' );