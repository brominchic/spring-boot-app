CREATE TABLE IF NOT EXISTS public.roles
(
    id bigint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
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