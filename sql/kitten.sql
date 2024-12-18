-- Table: public.kitten

-- DROP TABLE IF EXISTS public.kitten;

CREATE TABLE IF NOT EXISTS public.kitten
(
    id integer NOT NULL,
    customer_id integer NOT NULL,
    CONSTRAINT kitten_pkey PRIMARY KEY (id),
    CONSTRAINT kitten_to_customer FOREIGN KEY (customer_id)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.kitten
    OWNER to postgres;