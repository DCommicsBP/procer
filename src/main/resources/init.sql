    DROP TABLE IF EXISTS procer.people;

    CREATE SCHEMA IF NOT EXISTS procer;

    CREATE SEQUENCE IF NOT EXISTS procer.people_id_seq START 1;

    CREATE TABLE IF NOT EXISTS procer.people
    (
        id integer NOT NULL,
        cpf character(11) COLLATE pg_catalog."default" NOT NULL UNIQUE,
        email character varying(30) COLLATE pg_catalog."default" NOT NULL,
        first_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
        last_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
        is_activated boolean NOT NULL,
        CONSTRAINT people_pkey PRIMARY KEY (id)
    );

    --TABLESPACE pg_default;

    ALTER TABLE IF EXISTS procer.people
        OWNER to teste;

	ALTER TABLE procer.people ALTER "id" SET DEFAULT nextval('procer.people_id_seq');

    ALTER TABLE procer.people ADD CONSTRAINT constraint_name UNIQUE (CPF, email);

    INSERT INTO procer.people(cpf, email, first_name, last_name, is_activated) values
    ('01078556075', 'facilisis@hotmail.edu','Ciara', 'Stein', true),
    ('91173533001', 'ipsum.dolor@outlook.ca','Sara', 'Volks', true),
    ('33053656094', 'iaculis@aol.net','Liara', 'Assis', true),
    ('03500951082', 'pellentesque.massa@outlook.ca','Pedro', 'Rabbit', true),
    ('36858928006', 'ipsum.non@google.ca','Bruna', 'Pavan', true);