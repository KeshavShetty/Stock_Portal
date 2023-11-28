CREATE TABLE btst_autoscan_result (
	id bigint not null,
	f_scrip bigint not null,
	exchange boolean,
	signal_date date,
	signal_price real,
	f_study_master bigint not null
);
ALTER TABLE ONLY btst_autoscan_result ADD CONSTRAINT btst_autoscan_result_pk PRIMARY KEY (id);
ALTER TABLE ONLY btst_autoscan_result ADD CONSTRAINT fk_btst_autoscan_result_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;
ALTER TABLE ONLY btst_autoscan_result ADD CONSTRAINT fk_btst_autoscan_result_study_master FOREIGN KEY (f_study_master) REFERENCES autoscan_study_library (id) MATCH FULL DEFERRABLE;

CREATE SEQUENCE btst_autoscan_result_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

ALTER TABLE btst_autoscan_result ADD CONSTRAINT btst_autoscan_result_scripwiseUK unique (f_scrip,signal_date,f_study_master);

INSERT INTO db_versions VALUES('0021', now(), 'Keshav', 'BTST Autoscan tables','Schema' );