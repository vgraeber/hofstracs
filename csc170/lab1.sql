-- public.schedule definition

-- Drop table

-- DROP TABLE public.schedule;

CREATE TABLE public.schedule (
	id int4 NOT NULL,
	course_type text NULL,
	course_code text NULL,
	course_name text NULL,
	teacher text NULL,
	semester text NULL,
	start_time time NULL,
	end_time time NULL,
	CONSTRAINT schedule_id PRIMARY KEY (id),
	CONSTRAINT schedule_id_not_null1 NOT NULL id
);