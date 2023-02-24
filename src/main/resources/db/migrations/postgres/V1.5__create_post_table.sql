DROP TABLE IF EXISTS post;
CREATE TABLE public.post (
    id serial NOT NULL PRIMARY KEY,
    post_name character varying(255) NOT NULL,
    url character varying(255),
    description character varying(255) NOT NULL,
    vote_count integer,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    user_id bigint NOT NULL,
    subreddit_id bigint NOT NULL,
    posts bigint,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT fk_subreddit_id FOREIGN KEY (subreddit_id) REFERENCES public.subreddit(id),
    CONSTRAINT fk_posts FOREIGN KEY (posts) REFERENCES public.subreddit(id)
);