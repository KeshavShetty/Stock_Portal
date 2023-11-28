CREATE TABLE autoscan_study_type (
	id bigint not null,
	study_name varchar(100)
);

INSERT INTO autoscan_study_type (id, study_name) VALUES 
(1,'Price'),
(2,'Technical'),
(3,'Candle'),
(4,'Custom'),
(5,'Experiment');

ALTER TABLE ONLY autoscan_study_type ADD CONSTRAINT autoscan_study_type_pk PRIMARY KEY (id);
ALTER TABLE ONLY autoscan_study_library ADD CONSTRAINT fk_autoscan_study_type FOREIGN KEY (study_type) REFERENCES autoscan_study_type (id) MATCH FULL DEFERRABLE;

INSERT INTO db_versions VALUES('0028', now(), 'Keshav', 'Added master table for autoscan study Type','Schema' );