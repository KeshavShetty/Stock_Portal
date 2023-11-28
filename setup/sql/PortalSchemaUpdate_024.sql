CREATE TABLE JqGrid_Table_View 
(
	id bigint NOT NULL,
	table_identifier character varying(100),
	display_header character varying(255),
	joining_query character varying(1000),
	default_filter_json character varying(1000),
	default_order_by character varying(100),
	CONSTRAINT JqGrid_Table_View_pkey PRIMARY KEY (id),
	CONSTRAINT JqGrid_Table_View_table_identifier_uk UNIQUE (table_identifier)
);

CREATE TABLE JqGrid_Table_columns 
(
  	id bigint NOT NULL,
	f_JqGrid_Table bigint NOT NULL,
	display_name character varying(100),
	view_to_db_identifier character varying(100),
	actual_column_name character varying(100),
	column_type character varying(20),
	column_fetch_order integer,
	data_formatter character varying(1000),
	is_visible boolean default true,
	is_searchable boolean default true,
	select_query character varying(1000),
	search_where_clause character varying(1000),
	ui_width integer default 100,
	CONSTRAINT JqGrid_Table_columns_pkey PRIMARY KEY (id),
  	CONSTRAINT fk_JqGrid_Table_columns_f_JqGrid_Table FOREIGN KEY (f_JqGrid_Table)
      REFERENCES JqGrid_Table_View (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY IMMEDIATE,
);

CREATE SEQUENCE JQGRID_SAVED_SEARCH_id_seq
	    START WITH 1
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	    
CREATE TABLE JQGRID_SAVED_SEARCH (
	   id bigint not null ,
	   name varchar(255),
		 f_jqgrid_table bigint,
	   f_user bigint,
	   filter_query character varying(1000)
);
ALTER TABLE ONLY JQGRID_SAVED_SEARCH ADD CONSTRAINT JQGRID_SAVED_SEARCH_pkey PRIMARY KEY (id);
ALTER TABLE ONLY JQGRID_SAVED_SEARCH ADD CONSTRAINT fk_JQGRID_SAVED_SEARCH_f_user FOREIGN KEY (f_user) REFERENCES users(id) MATCH FULL DEFERRABLE;	
ALTER TABLE ONLY JQGRID_SAVED_SEARCH ADD CONSTRAINT fk_JQGRID_SAVED_SEARCH_f_JqGrid_Table FOREIGN KEY (f_JqGrid_Table) REFERENCES JqGrid_Table_View (id);


INSERT INTO JqGrid_Table_View (id, table_identifier, joining_query) VALUES(1, 'NewsPage_NewsTable', 'from feed_posts fp, feed_source fs where fp.feed_source=fs.id');

INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(1, 1, 'Post ID', 'PostID', 'fp.id', 'Integer', 1, 'fp.id', false);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(2, 1, 'Source ID', 'SourceID', 'fs.id', 'Integer', 2, 'fs.id', false);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(3, 1, 'Headline', 'Headline', 'fp.post_title', 'String', 3, 'fp.post_title', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(4, 1, 'Details', 'ShortContent', 'fp.post_short_content', 'String', 4, 'fp.post_short_content', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(5, 1, 'Link', 'LinkURL', 'fp.unique_gui_id', 'String', 5, 'fp.unique_gui_id', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(6, 1, 'Publish Date', 'PublishDate', 'fp.publish_date', 'Date', 6, 'fp.publish_date', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(7, 1, 'Media', 'RSSSource', 'fs.source_name', 'String', 7, 'fs.source_name', true);

INSERT INTO JqGrid_Table_View (id, table_identifier, joining_query, display_header) VALUES(2, 'WatchlistPage_ScripwiseTable', 'from watchlist_item wlItem, watchlist wl, scrips scr WHERE wlItem.f_scrip = scr.id and wlItem.f_watchlist=wl.id', 'Watchlist Portfolio');

INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(8, 2, 'Scrip', 'ScripName', 'scr.name', 'String', 1, 'scr.name', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(9, 2, 'Stock In Hand', 'SIH', 'wlItem.sih_quantity', 'Integer', 2, 'wlItem.sih_quantity', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(10, 2, 'Average Buy Rate', 'AverageBuyRate', 'wlItem.average_buy_rate', 'Float', 3, 'wlItem.average_buy_rate', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(11, 2, 'CMP', 'CMP', 'scr.cmp', 'Float', 4, 'scr.cmp', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(12, 2, 'Todays Gain', 'TodaysGain', 'scr.todays_gain', 'Float', 5, 'scr.todays_gain', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(13, 2, 'SIH Worth', 'SIHWorth', 'wlItem.sih_quantity*scr.cmp', 'Float', 6, 'wlItem.sih_quantity*scr.cmp', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(14, 2, 'Invested Money', 'InvestedMoney', 'wlItem.sih_quantity*wlItem.average_buy_rate', 'Float', 7, 'wlItem.sih_quantity*wlItem.average_buy_rate', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(15, 2, 'Net Profit', 'NetProfit', 'wlItem.sih_quantity*scr.cmp-wlItem.sih_quantity*wlItem.average_buy_rate', 'Float', 8, 'wlItem.sih_quantity*scr.cmp-wlItem.sih_quantity*wlItem.average_buy_rate', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible, select_query)
VALUES(16, 2, 'Watchlist', 'Watchlist', 'wl.wl_name', 'DropDown', 9, 'wl.wl_name', true, 'select wl_name, wl_name from watchlist where f_owner=UserID order by wl_name');
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(17, 2, 'UserID', 'UserID', 'wl.f_owner', 'String', 11, 'wl.f_owner', false);

INSERT INTO JqGrid_Table_View (id, table_identifier, joining_query, display_header) 
VALUES(3, 'ScripsPage_ScripTable', 'from scrips scr', 'Scrips');

INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(18, 3, 'Scrip', 'ScripName', 'scr.name', 'String', 1, 'scr.name', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(19, 3, 'CMP', 'CMP', 'scr.cmp', 'Number', 2, 'scr.cmp', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(20, 3, 'EPS', 'EPS', 'scr.eps_ttm', 'Number', 3, 'scr.eps_ttm', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(21, 3, 'PE', 'PE', 'scr.pe', 'Number', 4, 'scr.pe', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(22, 3, 'PB', 'PB', 'scr.cmp/NULLIF(scr.book_value,0)', 'Number', 5, 'scr.cmp/NULLIF(scr.book_value,0)', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(23, 3, 'NetProfitPercentage', 'NetProfitPercentage', 'scr.change_in_netprofit', 'Number', 6, 'scr.change_in_netprofit', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(24, 3, 'AvgFourQuarterNet', 'AvgFourQuarter', 'scr.average_four_qtr_netprofit', 'Number', 7, 'scr.average_four_qtr_netprofit', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(25, 3, 'RaisingProfitQtr', 'RaisingProfitQtr', 'scr.raising_profit_qtr_count', 'Integer', 8, 'scr.raising_profit_qtr_count', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(26, 3, 'ProfitMargin', 'ProfitMargin', 'scr.profit_margin_percentage', 'Number', 9, 'scr.profit_margin_percentage', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(27, 3, 'AverageTurnoverPerDay', 'AverageTurnoverPerDay', 'scr.average_turnover', 'Integer', 10, 'scr.average_turnover', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible, select_query,  search_where_clause)
VALUES(28, 3, 'Watchlist', 'Watchlist', 'scr.id', 'SelectManyToMany', 11, '', false, 'select id as keyLabel, wl_name as valLabel from watchlist where f_owner=UserID order by wl_name' , '(select f_scrip from watchlist_item where f_watchlist=paramValue)');
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible, select_query,  search_where_clause)
VALUES(29, 3, 'Sector', 'Sector', 'scr.f_sector', 'SelectManyToMany', 12, '', false, 'select id as keyLabel, name as valLabel from sector order by name' , '(select id from sector where id=paramValue)');

INSERT INTO JqGrid_Table_View (id, table_identifier, joining_query, display_header) 
VALUES(4, 'ScreenerPage_AnalysisTable', 'from scrips scr, autoscan_study_result asr, autoscan_study_library asl where asr.f_scrip = scr.id and asr.f_study_master = asl.id', 'Screener');

INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(30, 4, 'Scrip', 'ScripName', 'scr.name', 'String', 1, 'scr.name', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(31, 4, 'CMP', 'CMP', 'scr.cmp', 'number', 2, 'scr.cmp', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(32, 4, 'SignalDate', 'SignalDate', 'asr.signal_date', 'date', 3, 'asr.signal_date', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(33, 4, 'StudyName', 'StudyName', 'asl.shortname', 'String', 4, 'asl.shortname', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(34, 4, 'Signal', 'Signal', 'asr.signal_code', 'Integer', 5, 'asr.signal_code', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible)
VALUES(35, 4, 'Price At Signal', 'PriceAtSignal', 'asr.previous_close', 'number', 6, 'asr.previous_close', true);
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible, select_query,  search_where_clause)
VALUES(36, 4, 'Watchlist', 'Watchlist', 'scr.id', 'SelectManyToMany',7, '', false, 'select id as keyLabel, wl_name as valLabel from watchlist where f_owner=UserID order by wl_name' , '(select f_scrip from watchlist_item where f_watchlist=paramValue)');
INSERT INTO JqGrid_Table_columns (id, f_JqGrid_Table, display_name, view_to_db_identifier, actual_column_name, column_type, column_fetch_order, data_formatter, is_visible, select_query,  search_where_clause)
VALUES(37, 4, 'Study', 'Study', 'asr.f_study_master', 'SelectManyToMany',8, '', false, '' , '(paramValue)');
INSERT INTO db_versions VALUES('0024', now(), 'Keshav', 'JqGrid_Table_View tables','Schema' );