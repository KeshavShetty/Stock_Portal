ALTER TABLE scrips add column status character varying(25);
ALTER TABLE scrips add column et_code character varying(25);

ALTER TABLE scrips add column mc_code character varying(25);

ALTER TABLE scrips add column isin_code character varying(25);


ALTER TABLE scrips ADD CONSTRAINT scrips_bsecodeUK unique (bse_code);
ALTER TABLE scrips ADD CONSTRAINT scrips_iccodeUK unique (icici_code);
ALTER TABLE scrips ADD CONSTRAINT scrips_nse_codeUK unique (nse_code,series_type);
ALTER TABLE scrips ADD CONSTRAINT scrips_bse_nameUK unique (bse_name);

ALTER TABLE bse_eq_eod_data ADD CONSTRAINT eod_Scrip_uk unique (f_scrip, data_date);
CREATE INDEX bse_eq_eodcloseproce_idx on bse_eq_eod_data USING btree (close_price); 

ALTER TABLE scrips add column bse_group character varying(5); 
ALTER TABLE scrips add column bse_index character varying(25);
ALTER TABLE scrips add column face_value real;
ALTER TABLE scrips add column date_added timestamp;

ALTER TABLE scrips RENAME cmp to bse_cmp;
ALTER TABLE scrips RENAME todays_gain to bse_todays_gain; 
ALTER TABLE scrips RENAME previous_close to bse_previous_close;

ALTER TABLE scrips ADD nse_cmp real;
ALTER TABLE scrips ADD nse_todays_gain real; 
ALTER TABLE scrips ADD nse_previous_close real;

ALTER TABLE scrips ADD bse_52week_high real;
ALTER TABLE scrips ADD bse_52week_high_date date;
ALTER TABLE scrips ADD bse_52week_low real;
ALTER TABLE scrips ADD bse_52week_low_date date;

ALTER TABLE scrips ADD nse_52week_high real;
ALTER TABLE scrips ADD nse_52week_high_date date;
ALTER TABLE scrips ADD nse_52week_low real;
ALTER TABLE scrips ADD nse_52week_low_date date;

ALTER TABLE scrips ADD nse_3month_high real;
ALTER TABLE scrips ADD bse_3month_high real;

INSERT INTO db_versions VALUES('0004', now(), 'Keshav', 'New columns and indexes for scrips','Data' );