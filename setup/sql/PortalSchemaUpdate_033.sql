alter table scrips add column GreenCount52W real;
alter table scrips add column RedCount52W real;

alter table scrips add column GreenTotalPercent52W real;
alter table scrips add column RedTotalPercent52W real;

alter table scrips add column raising_closeprice_count real;
alter table scrips add column avg_qtr_net_return_by_closeprice real;
alter table scrips add column last_qtr_closeprice real;

alter table scrips add column number_day_close_above30dEMA real;
alter table scrips add column number_day_close_below30dEMA real;

alter table scrips add column stochastic_value real;
alter table scrips add column is_raising_stochastic bool;
alter table scrips add column ema100day real;
alter table scrips add column day_bar_size_percent real;


alter table scrips drop column greencount52w;
alter table scrips drop column redcount52w;
alter table scrips drop column greentotalpercent52w;
alter table scrips drop column redtotalpercent52w;

INSERT INTO db_versions VALUES('0033', now(), 'Keshav', 'Additional columns scrips to store Red and Green count based on open and close','Schema' );