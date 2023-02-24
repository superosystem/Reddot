DROP TABLE IF EXISTS token;
CREATE TABLE public.token (
    id serial NOT NULL PRIMARY KEY,
    token character varying(255) NOT NULL,
    expiry_date timestamp(6) with time zone,
    user_id bigint NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES public.users(id)
);

ALTER TABLE ONLY public.token ADD CONSTRAINT u_token UNIQUE (token);
