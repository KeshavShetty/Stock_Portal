CREATE SEQUENCE mcsector_id_seq
	START WITH 1000
	INCREMENT BY 1
	NO MAXVALUE
	NO MINVALUE
	CACHE 1;
	    
create table mcsector (
   id bigint not null ,
   name varchar(255)
);

ALTER TABLE ONLY mcsector ADD CONSTRAINT mcsector_pkey PRIMARY KEY (id);
	
alter table scrips add column f_mcsector bigint;
ALTER TABLE ONLY scrips ADD CONSTRAINT fk_scrips_f_mcsector FOREIGN KEY (f_mcsector) REFERENCES mcsector (id) MATCH FULL DEFERRABLE;

INSERT INTO db_versions VALUES('0043', now(), 'Keshav', 'Sectors from MoneyControl','Schema' );