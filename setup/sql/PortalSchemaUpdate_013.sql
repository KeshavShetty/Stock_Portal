alter TABLE autoscan_study_result add column signal_code int;
alter TABLE autoscan_study_result add CONSTRAINT autoscan_study_resultuk UNIQUE (f_scrip, signal_date, f_study_master, signal_code);
alter TABLE autoscan_study_result add column previous_close real;

alter table autoscan_study_library add column tutorial_url varchar (255);
alter table autoscan_study_result add column exchange_code boolean default false;
alter table autoscan_study_result drop CONSTRAINT autoscan_study_resultuk;
alter table autoscan_study_result add CONSTRAINT autoscan_study_resultuk UNIQUE (f_scrip, signal_date, f_study_master, exchange_code);

alter table autoscan_study_result drop CONSTRAINT autoscan_study_result_scripwiseuk;

ALTER TABLE bse_eq_eod_data ADD COLUMN previous_volume bigint DEFAULT 0;
ALTER TABLE nse_eq_eod_data ADD COLUMN previous_volume bigint DEFAULT 0;

ALTER TABLE bse_eq_eod_data add column autoscan_study_3 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_4 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_70 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_71 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_72 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_73 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_74 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_75 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_76 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_77 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_78 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_79 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_80 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_84 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_85 real;

ALTER TABLE nse_eq_eod_data add column autoscan_study_3 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_4 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_70 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_71 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_72 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_73 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_74 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_75 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_76 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_77 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_78 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_79 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_80 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_84 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_85 real;

//New EMA columns
ALTER TABLE bse_eq_eod_data add column autoscan_study_88 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_89 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_90 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_91 real;

ALTER TABLE nse_eq_eod_data add column autoscan_study_88 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_89 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_90 real;
ALTER TABLE nse_eq_eod_data add column autoscan_study_91 real;

CREATE INDEX bse_eq_eod_datadate_idx ON bse_eq_eod_data USING btree (data_date);
CREATE INDEX nse_eq_eod_datadate_idx ON nse_eq_eod_data USING btree (data_date);
  
INSERT INTO db_versions VALUES('0013', now(), 'Keshav', 'signal_code column added','Schema' );