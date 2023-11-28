ALTER TABLE nse_eq_eod_data add column autoscan_study_94 real;
ALTER TABLE bse_eq_eod_data add column autoscan_study_94 real;

INSERT INTO db_versions VALUES('0027', now(), 'Keshav', 'Added Trix to autoscan library','Schema' );