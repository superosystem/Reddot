DROP TABLE IF EXISTS refresh_token;
CREATE TABLE public.refresh_token (
    id uuid DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    token character varying(255) NOT NULL,
    create_date timestamp(6) with time zone
);

ALTER TABLE ONLY public.refresh_token ADD CONSTRAINT u_refresh_token UNIQUE (token);