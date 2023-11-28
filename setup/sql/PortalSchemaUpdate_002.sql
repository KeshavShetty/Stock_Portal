---- UName/Password admin/12345678 
INSERT INTO users (id, email_id, firstname, lastname, password, username, userstatus, usertype) 
VALUES (1,'keshavshetty@aim.com','Admin','Istrator','98ef8709c6e2657b12e7069dddd3b672','admin',true,true);

INSERT INTO account_transaction_types (id,name) values(1,'Deposits');
INSERT INTO account_transaction_types (id,name) values(2,'Income');
INSERT INTO account_transaction_types (id,name) values(3,'Withdrawals');
INSERT INTO account_transaction_types (id,name) values(4,'Expenses');

INSERT INTO db_versions VALUES('0002', now(), 'Keshav', 'Insert initial user account data','Data' );