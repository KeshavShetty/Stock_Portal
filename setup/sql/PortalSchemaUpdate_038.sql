
alter table scrips add column ema15day real;
alter table scrips_history add column ema15day real;

alter table scrips add column ema30day real;
alter table scrips_history add column ema30day real;

alter table scrips add column ema50day real;
alter table scrips_history add column ema50day real;


INSERT INTO db_versions VALUES('0038', now(), 'Keshav', 'Scrips History info for EMA','Schema' );