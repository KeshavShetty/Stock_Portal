alter table intraday_snapshot_data add column cf_rating_5day real;
alter table intraday_snapshot_data add column lowest_low_5day boolean default false;
alter table intraday_snapshot_data add column highest_high_5day boolean default false;


alter table intraday_snapshot_data add column cf_rating_15day real;
alter table intraday_snapshot_data add column lowest_low_15day boolean default false;
alter table intraday_snapshot_data add column highest_high_15day boolean default false;

alter table intraday_snapshot_data add column cf_rating_30day real;
alter table intraday_snapshot_data add column lowest_low_30day boolean default false;
alter table intraday_snapshot_data add column highest_high_30day boolean default false;

alter table intraday_snapshot_data rename column cf_rating to cf_rating_90day;
alter table intraday_snapshot_data rename column lowest_low to lowest_low_90day;
alter table intraday_snapshot_data rename column highest_high to highest_high_90day;

alter table intraday_snapshot_data add column highest_onemin_volume bigint DEFAULT 0;
alter table intraday_snapshot_data add column highest_onemin_turnover real DEFAULT 0;

INSERT INTO db_versions VALUES('0029', now(), 'Keshav', 'intraday_snapshot_data additional columns','Schema' );