DROP TABLE IF EXISTS token;
CREATE TABLE public.token (
    id bigint NOT NULL PRIMARY KEY,
    token character varying(255) NOT NULL,
    expiry_date timestamp(6) with time zone,
    user_id bigint NOT NULL
);

ALTER TABLE ONLY public.token ADD CONSTRAINT u_token UNIQUE (token);

ALTER TABLE ONLY public.token ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
