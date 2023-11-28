alter table scrips add column marketcap real;
alter table scrips_history add column marketcap real;

alter table scrips add column revenuechangepercent real;
alter table scrips_history add column revenuechangepercent real;

alter table scrips add column revenueSameQtrchangepercent real;
alter table scrips_history add column revenueSameQtrchangepercent real;

alter table scrips add column lastQtrNetProfitAmount real;
alter table scrips_history add column lastQtrNetProfitAmount real;

alter table scrips add column oneyearNetProfitAmount real;
alter table scrips_history add column oneyearNetProfitAmount real;

alter table scrips add column yoy_growth real;
alter table scrips_history add column yoy_growth real;

alter table scrips add column yoy_balancegrowth real;
alter table scrips_history add column yoy_balancegrowth real;

alter table scrips add column wlcount integer;
alter table scrips_history add column wlcount integer;

alter table scrips add column wl_roi_score integer;
alter table scrips_history add column wl_roi_score integer;

alter table scrips add column wl_score_rank integer;
alter table scrips_history add column wl_score_rank integer;

alter table scrips add column growth_promise_percentage integer;
alter table scrips_history add column growth_promise_percentage integer;

alter table scrips add column growth_promise_percentage_consolidated integer;
alter table scrips_history add column growth_promise_percentage_consolidated integer;

alter table scrips add column next_result_date date;
alter table scrips_history add column next_result_date date;

alter table scrips add column growth_rank integer;
alter table scrips_history add column growth_rank integer;

alter table scrips add column growth_qtr_count integer;
alter table scrips_history add column growth_qtr_count integer;

alter table scrips add column lastQtrInterestAmount real;
alter table scrips_history add column lastQtrInterestAmount real;

alter table scrips add column oneyearInterestAmount real;
alter table scrips_history add column oneyearInterestAmount real;

alter table scrips add column ema200day real;
alter table scrips_history add column ema200day real;

alter table scrips add column estimated_next_qtr_sqg real;
alter table scrips_history add column estimated_next_qtr_sqg real;

alter table scrips add column profit_margin_lastqtr real;
alter table scrips_history add column profit_margin_lastqtr real;

alter table scrips add column tax_slab_percent real;
alter table scrips_history add column tax_slab_percent real;

INSERT INTO db_versions VALUES('0041', now(), 'Keshav', 'Market cap and Revenue change percent added','Schema' );