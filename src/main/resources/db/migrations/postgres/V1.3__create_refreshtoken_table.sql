DROP TABLE IF EXISTS refresh_token;
CREATE TABLE public.refresh_token (
    id serial NOT NULL PRIMARY KEY,
    token character varying(255) NOT NULL,
    create_date timestamp(6) with time zone
);

ALTER TABLE ONLY public.refresh_token ADD CONSTRAINT u_refresh_token UNIQUE (token);