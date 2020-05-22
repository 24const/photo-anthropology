CREATE TABLE IF NOT EXISTS groups (
	id SERIAL PRIMARY KEY,
	group_name VARCHAR(50) NOT NULL,
	group_question VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS files (
	id SERIAL PRIMARY KEY,
	file_name VARCHAR(255) NOT NULL,
    column_names json,
    date_created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tags (
	id SERIAL PRIMARY KEY,
	tag_name VARCHAR(70) NOT NULL,
	group_id integer,
	FOREIGN KEY (group_id) REFERENCES groups
);

CREATE TABLE IF NOT EXISTS images (
	id SERIAL PRIMARY KEY,
	file_id integer,
	image_path text NOT NULL,
	other_information json,
    FOREIGN KEY (file_id) REFERENCES files
);

CREATE TABLE IF NOT EXISTS tagged_images (
	image_id integer,
	tag_id integer,
	FOREIGN KEY (image_id) REFERENCES images,
	FOREIGN KEY (tag_id) REFERENCES tags
);

CREATE TABLE IF NOT EXISTS groups_in_file (
	file_id integer,
	group_id integer,
	FOREIGN KEY (file_id) REFERENCES files,
	FOREIGN KEY (group_id) REFERENCES groups
);