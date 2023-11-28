CREATE TABLE distribution_list (
id int8 NOT NULL,
name varchar(255),
CONSTRAINT distribution_list_pk PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE distribution_list OWNER TO postgres;

CREATE TABLE distribution_list_members (
id int8 NOT NULL,
member_name varchar(50),
f_distribution_list int8,
email varchar(50),
mobile varchar(15),
CONSTRAINT f_distribution_list_members_pk PRIMARY KEY (id),
CONSTRAINT fk_distribution_list FOREIGN KEY (f_distribution_list) REFERENCES distribution_list (id) ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE
)
WITH (OIDS=FALSE);
ALTER TABLE distribution_list_members OWNER TO postgres;

INSERT INTO distribution_list (id, name) values(1,'Dalal Street WhatsApp');

insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(1,'Keshav Shetty', 1, '9886730220');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(2,'Abhilash Nagraju', 1, '9845465377');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(3,'Amitesh Ranjan', 1, '9582209884');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(4,'Basant Mohta', 1, '9886428814');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(5,'Debasis', 1, '9980007462');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(6,'Mangalam Sunghania', 1, '9740192021');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(7,'Muralidhar Teppala', 1, '9740031929');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(8,'Nitin', 1, '9880464846');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(9,'Rohit Bhagade', 1, '9980540682');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(10,'Roopesh Maheshwari', 1, '9901137851');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(11,'Sanjeev K B', 1, '9731357700');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(12,'Siddarth Bahadur', 1, '8197278265');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(13,'Sudarshan Shahpurkar', 1, '9845678911');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(14,'Sunil Kulkarni', 1, '9980670531');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(15,'Vikram Sainuche', 1, '9880100255');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(16,'Vishal Mittal', 1, '9611896082');
insert into distribution_list_members (id, member_name, f_distribution_list, mobile) values(17,'Wasif Mohammed', 1, '9900223232');

INSERT INTO db_versions VALUES('0020', now(), 'Keshav', 'SMS and email distribution list','Schema' );