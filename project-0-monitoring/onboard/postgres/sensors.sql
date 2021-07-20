--
-- PostgreSQL database dump
--

-- Dumped from database version 11.12
-- Dumped by pg_dump version 11.12 (Raspbian 11.12-0+deb10u1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: singleton; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.singleton (
    id integer DEFAULT 42 NOT NULL,
    "timestamp" timestamp without time zone,
    off_temperature integer,
    on_temperature integer,
    humidity integer,
    barometric_pressure integer,
    is_motion_detected boolean,
    CONSTRAINT only_one_row CHECK ((id = 42))
);


ALTER TABLE public.singleton OWNER TO postgres;

--
-- Data for Name: singleton; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.singleton (id, "timestamp", off_temperature, on_temperature, humidity, barometric_pressure, is_motion_detected) FROM stdin;
\.


--
-- Name: singleton singleton_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.singleton
    ADD CONSTRAINT singleton_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

