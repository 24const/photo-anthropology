CREATE TABLE IF NOT EXISTS groups (
	group_id SERIAL PRIMARY KEY,
	group_name VARCHAR(20) NOT NULL,
	max_number_of_tags integer,
	group_question text
);

CREATE TABLE IF NOT EXISTS files (
	file_id SERIAL PRIMARY KEY,
	file_name VARCHAR(30) NOT NULL,
columns VARCHAR ARRAY
);

CREATE TABLE IF NOT EXISTS tags (
	tag_id SERIAL PRIMARY KEY,
	tag_name VARCHAR(70) NOT NULL,
	group_id integer REFERENCES groups
);

CREATE TABLE IF NOT EXISTS images (
	image_id SERIAL PRIMARY KEY,
	file_id integer REFERENCES files,
	image_path text NOT NULL,
	other_information json
);

CREATE TABLE IF NOT EXISTS tagged_images (
	image_id integer REFERENCES images,
	tag_id integer REFERENCES tags
);
