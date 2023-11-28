CREATE SEQUENCE tree_performance_id_seq
	    START WITH 1
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
		
CREATE TABLE tree_performance (
id bigint NOT NULL,
f_source_scrip bigint,
f_target_scrip bigint,
joint_increment integer,
total_source_gain real,
total_target_gain real,
CONSTRAINT tree_performance_pkey PRIMARY KEY (id),
CONSTRAINT fk_tree_performance_f_source_scrip FOREIGN KEY (f_source_scrip) REFERENCES scrips (id) ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE,
CONSTRAINT fk_tree_performance_f_target_scrip FOREIGN KEY (f_target_scrip) REFERENCES scrips (id) ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE
);

INSERT INTO db_versions VALUES('0023', now(), 'Keshav', 'Tree performance tables','Schema' );