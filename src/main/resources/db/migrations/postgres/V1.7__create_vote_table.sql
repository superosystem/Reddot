DROP TABLE IF EXISTS vote;
CREATE TABLE public.vote (
    id bigint NOT NULL PRIMARY KEY,
    vote_type smallint NOT NULL,
    updated_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone,
    post_id bigint NOT NULL,
    user_id bigint NOT NULL
);

ALTER TABLE ONLY public.vote ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.vote ADD CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES public.post(id);
