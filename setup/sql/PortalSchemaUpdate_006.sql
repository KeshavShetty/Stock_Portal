CREATE TABLE financial_result (
 	id bigint not null ,
 	f_scrip bigint not null,
 	financial_report_quarter_id int not null,
 	revenue real,
 	other_income real,
 	total_income real,
 	expenditure real, 
	interest real,
	PBDT real,
	depreciation real,
	PBT real,
	tax real,
	net_profit real,
	Equity real,
	EPS real,
	CEPS real,
	OPM_percentage real,
	NPM_percentage real 	
);

ALTER TABLE ONLY financial_result ADD CONSTRAINT financial_result_pk PRIMARY KEY (id);
ALTER TABLE ONLY financial_result ADD CONSTRAINT fk_financial_result_scrip FOREIGN KEY (f_scrip) REFERENCES scrips (id) MATCH FULL DEFERRABLE;
CREATE SEQUENCE financial_result_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE INDEX financial_result_period_scrip_idx ON financial_result USING btree (f_scrip,financial_report_quarter_id);

alter table scrips add column eps_ttm real;
alter table scrips add column pe real;
alter table scrips add column cmp real;
alter table scrips add column todays_gain real;

alter table financial_result add column car_percentage real;

alter table financial_result add column qtr_close_price real; 

alter table scrips add column change_in_netprofit real;
alter table scrips add column raising_profit_qtr_count integer;
alter table scrips add column average_four_qtr_netprofit real;
alter table scrips add column profit_margin_percentage real;

ALTER TABLE financial_result ADD CONSTRAINT financial_result_financial_report_quarter_f_scripUK unique (f_scrip,financial_report_quarter_id);
ALTER TABLE consolidated_financial_result ADD CONSTRAINT consolidated_financial_result_financial_report_quarter_f_scripUK unique (f_scrip,financial_report_quarter_id);

INSERT INTO db_versions VALUES('0006', now(), 'Keshav', 'financial report table','Schema' );