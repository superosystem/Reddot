DROP TABLE IF EXISTS vote;
CREATE TABLE public.vote (
    id serial NOT NULL PRIMARY KEY,
    vote_type smallint NOT NULL,
    updated_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone,
    post_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES public.post(id)
);
