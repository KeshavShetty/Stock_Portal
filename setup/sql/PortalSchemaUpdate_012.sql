ALTER TABLE bse_intraday_summary_data ADD COLUMN previousday_close_price real;
ALTER TABLE bse_intraday_summary_data ADD COLUMN previousday_volume bigint;

INSERT INTO db_versions VALUES('0012', now(), 'Keshav', 'Previous day close added to intraday data','Schema' );