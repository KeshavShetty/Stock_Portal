ALTER TABLE nse_eq_eod_data ADD CONSTRAINT nse_eod_Scrip_uk unique (f_scrip, data_date);
CREATE INDEX nse_eq_eodcloseproce_idx ON nse_eq_eod_data USING btree (close_price);

ALTER TABLE scrips DROP CONSTRAINT scrips_nse_codeUK;
ALTER TABLE scrips ADD CONSTRAINT scrips_nse_codeUK unique (nse_code,series_type);

ALTER TABLE scrips add column last_updated date;
ALTER TABLE scrips drop column extra_column;

ALTER TABLE scrips ALTER COLUMN nse_code type varchar(50);


INSERT INTO db_versions VALUES('0005', now(), 'Keshav', 'NSE EOD data table changes','Schema' );