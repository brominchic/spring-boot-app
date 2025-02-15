CREATE TABLE IF NOT EXISTS public.roles
(
    id bigint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.currencies
(
    code character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    id bigint NOT NULL,
    CONSTRAINT currencies_pkey PRIMARY KEY (id),
    CONSTRAINT uniq_code UNIQUE (code)
);
CREATE TABLE IF NOT EXISTS public.operations
(
    id bigint NOT NULL,
    sum bigint NOT NULL,
    cur_code character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT operations_pkey PRIMARY KEY (id),
    CONSTRAINT cur_fk FOREIGN KEY (cur_code)
        REFERENCES public.currencies (code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL,
    role bigint NOT NULL,
    login character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uniq_login UNIQUE (login),
    CONSTRAINT fk_roles FOREIGN KEY (role)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
;
CREATE SEQUENCE public.id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;