delete from db_versions where dbversion != 'DBVER'; 

drop table sector cascade;
drop SEQUENCE sector_id_seq;

drop table watchlist cascade;
drop SEQUENCE watchlist_id_seq;

drop table transaction cascade;
drop SEQUENCE transaction_id_seq;

drop table users cascade;
drop SEQUENCE users_id_seq;

drop table watchlist_item cascade;
drop SEQUENCE watchlist_item_id_seq;

drop table scrips cascade;
drop SEQUENCE scrips_id_seq;

drop table bse_eq_eod_data cascade;
drop SEQUENCE bse_eq_eod_data_id_seq;

drop table nse_eq_eod_data cascade;
drop SEQUENCE nse_eq_eod_data_id_seq;

drop table stock_in_hand cascade;
drop SEQUENCE stock_in_hand_id_seq;

drop table simple_file cascade;
drop SEQUENCE simple_file_id_seq;

drop table knowledgebase cascade;
drop SEQUENCE knowledgebase_id_seq;

drop table analysis_history cascade;
drop SEQUENCE analysis_history_id_seq;

drop table stoploss_order cascade;
drop SEQUENCE stoploss_order_id_seq;

drop table stoploss_order_transaction cascade;
drop SEQUENCE stoploss_order_transaction_id_seq;

drop table account_transaction cascade;
drop SEQUENCE account_transaction_id_seq;

drop table account_transaction_types cascade;
drop SEQUENCE account_transaction_types_id_seq;

drop table country cascade;
drop SEQUENCE country_id_seq;

	