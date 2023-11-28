CREATE OR REPLACE FUNCTION datediff(type VARCHAR, date_from DATE, date_to DATE) RETURNS INTEGER LANGUAGE plpgsql
AS
$$
DECLARE age INTERVAL;
BEGIN
    age := age(date_to, date_from);
    CASE type
        WHEN 'month' THEN
            RETURN date_part('year', age) * 12 + date_part('month', age);
        WHEN 'year' THEN
            RETURN date_part('year', age);
        ELSE
            RETURN (date_to - date_from)::int; 
    END CASE;
END;
$$;

INSERT INTO db_versions VALUES('0045', now(), 'Keshav', 'Postgres DB dateDif function','Schema' );