CREATE SEQUENCE financial_result_xls_source_id_seq START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE financial_result_xls_source
(
  id bigint NOT NULL,
  f_scrip bigint NOT NULL,
  financial_report_quarter_id integer NOT NULL,
  revenue real,
  other_income real,
  total_income real,
  expenditure real,
  interest real,
  pbdt real,
  depreciation real,
  pbt real,
  tax real,
  net_profit real,
  eps real,
  qtr_close_price real,
  isConsolidated boolean,
  CONSTRAINT financial_result_xls_source_pk PRIMARY KEY (id),
  CONSTRAINT financial_result_xls_source_financial_report_quarter_f_scripuk UNIQUE (f_scrip, financial_report_quarter_id, isConsolidated)
);

-- Index: financial_result_period_scrip_idx

CREATE INDEX financial_result_xls_source_period_scrip_idx
  ON financial_result_xls_source
  USING btree
  (f_scrip, financial_report_quarter_id, isConsolidated);


INSERT INTO db_versions VALUES('0046', now(), 'Keshav', 'financial report table for XLS imported data','Schema' );