
CREATE INDEX bse_eq_eod_idx1 on bse_eq_eod_data USING btree (f_scrip,data_date); 
CREATE INDEX nse_eq_eod_idx1 on nse_eq_eod_data USING btree (f_scrip,data_date); 

INSERT INTO db_versions VALUES('0003', now(), 'Keshav', 'Indexes in EOD','Data' );