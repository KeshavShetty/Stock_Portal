CREATE TABLE autoscan_study_library (
 	id bigint not null,
 	study_type int,
 	shortname varchar(50),
 	study_name varchar(100),
 	study_parameter varchar(100), 
 	description varchar(100)
);

ALTER TABLE ONLY autoscan_study_library ADD CONSTRAINT autoscan_study_library_pk PRIMARY KEY (id);

-- Below are fundamental scan (i.e type is 1)
INSERT INTO autoscan_study_library (id, study_type, shortname, study_name, study_parameter, description)
VALUES (1,1,'52WH','Near 52 Week high','8','Stocks which are near 52 week high by 8 percentage');
INSERT INTO autoscan_study_library (id, study_type, shortname, study_name, study_parameter, description)
VALUES (2,1,'52WL','Near 52 Week low','8','Stocks which are near 52 week low by 8 percentage');

-- Below 2 are technical scan (i.e type is 2)
INSERT INTO autoscan_study_library (id, study_type, shortname, study_name, study_parameter, description)
VALUES (3,2,'MA','Moving average','200','Simple moving average of 200 days');
INSERT INTO autoscan_study_library (id, study_type, shortname, study_name, study_parameter, description)
VALUES (4,2,'MACD','MA Convergence/Divergence','12,26,9','MACD');

CREATE TABLE autoscan_study_result (
	id bigint not null,
	f_scrip bigint not null,
	signal_date date,
	f_study_master bigint not null,
	notes varchar(100)
);
ALTER TABLE ONLY autoscan_study_result ADD CONSTRAINT autoscan_study_result_pk PRIMARY KEY (id);
ALTER TABLE ONLY autoscan_study_result ADD CONSTRAINT fk_autoscan_study_result_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;
ALTER TABLE ONLY autoscan_study_result ADD CONSTRAINT fk_autoscan_study_result_study_master FOREIGN KEY (f_study_master) REFERENCES autoscan_study_library (id) MATCH FULL DEFERRABLE;

--Some scanner can be calculated in single transaction like 52WH, some need individual scrip level like MACD
ALTER table autoscan_study_library ADD COLUMN is_individual int;  
update autoscan_study_library set is_individual = 0 where id=1;
update autoscan_study_library set is_individual = 0 where id=2;
update autoscan_study_library set is_individual = 1 where id=3;
update autoscan_study_library set is_individual = 1 where id=4;

CREATE SEQUENCE autoscan_study_result_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

ALTER TABLE autoscan_study_result ADD CONSTRAINT autoscan_study_result_scripwiseUK unique (f_scrip,signal_date,f_study_master);

INSERT INTO db_versions VALUES('0007', now(), 'Keshav', 'Autoscan tables','Schema' );