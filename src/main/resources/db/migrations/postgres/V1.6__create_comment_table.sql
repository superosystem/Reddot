DROP TABLE IF EXISTS comment;
CREATE TABLE public.comment (
    id serial NOT NULL PRIMARY KEY,
    text character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    post_id bigint NOT NULL,
    user_id bigint NOT NULL
);

ALTER TABLE ONLY public.comment ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.comment ADD CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES public.post(id);
