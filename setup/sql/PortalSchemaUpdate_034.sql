CREATE TABLE scrips_history
(  
  f_scrip bigint, 
  bse_cmp real,
  bse_todays_gain real,
  bse_previous_close real,
  face_value real,
  date_added timestamp without time zone,
  nse_cmp real,
  nse_todays_gain real,
  nse_previous_close real,
  bse_52week_high real,
  bse_52week_high_date date,
  bse_52week_low real,
  bse_52week_low_date date,
  nse_52week_high real,
  nse_52week_high_date date,
  nse_52week_low real,
  nse_52week_low_date date,
  last_updated date,
  eps_ttm real,
  pe real,
  bse_avg_volume bigint,
  bse_previous_volume bigint,
  bse_todays_volume bigint,
  book_value real,
  dividend_yield_percent real,
  nse_3month_high real,
  bse_3month_high real,
  nse_avg_volume bigint,
  nse_previous_volume bigint,
  nse_todays_volume bigint,
  result_date date,
  change_in_netprofit real,
  raising_profit_qtr_count real,
  average_four_qtr_netprofit real,
  profit_margin_percentage real,
  avg_volume bigint,
  average_turnover real,
  cmp real,
  todays_gain real,
  price_oneday_before real,
  price_fiveday_before real,
  price_onemonth_before real,
  price_threemonth_before real,
  raising_closeprice_count real,
  avg_qtr_net_return_by_closeprice real,
  last_qtr_closeprice real,
  number_day_close_above30dema real,
  number_day_close_below30dema real,
  stochastic_value real,
  is_raising_stochastic boolean,
  ema100day real,
  day_bar_size_percent real,
  CONSTRAINT scrips_history_pkey PRIMARY KEY (f_scrip, last_updated),
  CONSTRAINT fk_scrips_histort_f_scrip FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)
WITH (
  OIDS=FALSE
);

alter table scrips add column fixed_assets_turnover real;
alter table scrips add column return_on_equity real;
alter table scrips add column cash_ratio real;
alter table scrips add column operating_profit_margin real;
alter table scrips add column Net_Profit_Margin real;
alter table scrips add column Return_On_Capital_Employed real;
alter table scrips add column Gross_Profit_Margin real;
alter table scrips add column Debt_Equity_Ratio real;
alter table scrips add column Return_On_Assets real;
alter table scrips add column Cash_Flows_to_Long_Term_Debt real;
alter table scrips add column Quick_Ratio real;
alter table scrips add column Current_Ratio real;
alter table scrips add column cmp_on_data_read real;
alter table scrips add column nse_volume_vs_avg_volume real;
alter table scrips add column mean_price real;
alter table scrips add column raisingFourQtrProfitCount real;
alter table scrips add column raisingFourQtrClosePriceCount real;

alter table scrips_history add column fixed_assets_turnover real;
alter table scrips_history add column return_on_equity real;
alter table scrips_history add column cash_ratio real;
alter table scrips_history add column operating_profit_margin real;
alter table scrips_history add column Net_Profit_Margin real;
alter table scrips_history add column Return_On_Capital_Employed real;
alter table scrips_history add column Gross_Profit_Margin real;
alter table scrips_history add column Debt_Equity_Ratio real;
alter table scrips_history add column Return_On_Assets real;
alter table scrips_history add column Cash_Flows_to_Long_Term_Debt real;
alter table scrips_history add column Quick_Ratio real;
alter table scrips_history add column Current_Ratio real;
alter table scrips_history add column cmp_on_data_read real;
alter table scrips_history add column nse_volume_vs_avg_volume real;
alter table scrips_history add column mean_price real;
alter table scrips_history add column raisingFourQtrProfitCount real;
alter table scrips_history add column raisingFourQtrClosePriceCount real;


alter table scrips add column latest_financial_report_quarter_id int;
alter table scrips_history add column latest_financial_report_quarter_id int;

alter table scrips add column last_same_qtr_growth_percentage real;
alter table scrips_history add column last_same_qtr_growth_percentage real;


alter table scrips add column raisingSameQtrCount real;
alter table scrips_history add column raisingSameQtrCount real;

ALTER TABLE scrips add column avg_volume_onemonth bigint;
alter table scrips_history add column avg_volume_onemonth bigint;

alter table scrips add column averageFourSameQuarterNetprofit real;
alter table scrips_history add column averageFourSameQuarterNetprofit real;

alter table scrips add column support_price_3M real;
alter table scrips_history add column support_price_3M real;

alter table scrips add column support_volume_leftover_3M bigint;
alter table scrips_history add column support_volume_leftover_3M bigint;

alter table scrips add column cmp_on_mcap_read real;
alter table scrips_history add column cmp_on_mcap_read real;

alter table scrips add column change_in_epsttm real;
alter table scrips_history add column change_in_epsttm real;

alter table scrips add column eps_sqg real;
alter table scrips_history add column eps_sqg real;

create table shareholding_pattern
(
	f_scrip bigint, 
  	total_shares real,
 CONSTRAINT shareholding_pattern_pkey PRIMARY KEY (f_scrip),
 CONSTRAINT fk_shareholding_pattern_f_scrip FOREIGN KEY (f_scrip)
      REFERENCES scrips (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE
)
WITH (
  OIDS=FALSE
);

INSERT INTO db_versions VALUES('0034', now(), 'Keshav', 'Scrips History','Schema' );