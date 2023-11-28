alter table watchlist_item add column last_updated date;
alter table watchlist_item add column hold_suggestion VARCHAR(100);

INSERT INTO db_versions VALUES('0032', now(), 'Keshav', 'Additional columns watchlist_item to store buy, hold or sell sugestions','Schema' );