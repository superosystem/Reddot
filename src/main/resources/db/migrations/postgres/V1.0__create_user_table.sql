CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS users;
CREATE TABLE public.users (
    id uuid DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    name character varying(100) NOT NULL,
    username character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    role character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone
);

ALTER TABLE ONLY public.users ADD CONSTRAINT username UNIQUE (username);
