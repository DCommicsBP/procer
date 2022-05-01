

DROP TABLE IF EXISTS public.people;

DROP SEQUENCE IF EXISTS  people_id_seq;

CREATE SEQUENCE people_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.people
(
    id integer NOT NULL DEFAULT nextval('people_id_seq'::regclass),
    cpf character(11) COLLATE pg_catalog."default" NOT NULL UNIQUE,
    email character varying(30) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    is_activated boolean NOT NULL,
    CONSTRAINT people_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.people
    OWNER to teste;

ALTER TABLE people
ADD CONSTRAINT constraint_name UNIQUE (CPF, email);

INSERT INTO people(cpf, email, first_name, last_name, is_activated) values
('01078556075', 'facilisis@hotmail.edu','Ciara', 'Stein', true),
('91173533001', 'ipsum.dolor@outlook.ca','Sara', 'Volks', true),
('33053656094', 'iaculis@aol.net','Liara', 'Assis', true),
('03500951082', 'pellentesque.massa@outlook.ca','Pedro', 'Rabbit', true),
('36858928006', 'ipsum.non@google.ca','Bruna', 'Pavan', true)
