DROP TABLE IF EXISTS subreddit;
CREATE TABLE public.subreddit (
    id uuid DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    name character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    user_id uuid DEFAULT uuid_generate_v4() NOT NULL
);

ALTER TABLE ONLY public.subreddit ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);