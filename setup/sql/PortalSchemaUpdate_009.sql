ALTER TABLE scrips add column bse_avg_volume bigint;
ALTER TABLE scrips add column bse_previous_volume bigint;
ALTER TABLE scrips add column bse_todays_volume bigint;

ALTER TABLE scrips add column nse_avg_volume bigint;
ALTER TABLE scrips add column nse_previous_volume bigint;
ALTER TABLE scrips add column nse_todays_volume bigint;

ALTER TABLE scrips add column avg_volume bigint;
ALTER TABLE scrips add column average_turnover real;

alter table scrips add column price_oneday_before real;
alter table scrips add column price_fiveday_before real;
alter table scrips add column price_onemonth_before real;
alter table scrips add column price_threemonth_before real;


INSERT INTO db_versions VALUES('0009', now(), 'Keshav', 'Scrips volume related columns','Schema' );