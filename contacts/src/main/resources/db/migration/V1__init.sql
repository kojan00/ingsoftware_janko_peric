CREATE TYPE role AS ENUM ('USER', 'ADMIN');
CREATE TABLE contacts (
                           id bigint PRIMARY KEY,
                           first_name varchar NOT NULL,
                           last_name varchar NOT NULL,
                           address varchar,
                           phone_number varchar NOT NULL,
                           contact_type bigint NOT NULL,
                           user_id bigint NOT NULL
);

CREATE TABLE users (
                        id bigint PRIMARY KEY,
                        first_name varchar NOT NULL,
                        last_name varchar NOT NULL,
                        email varchar NOT NULL,
                        password varchar NOT NULL,
                        role role NOT NULL
);

CREATE TABLE contact_type (
                                id bigint PRIMARY KEY,
                                type varchar
);

ALTER TABLE contacts ADD FOREIGN KEY (contact_type) REFERENCES contact_type (id);

ALTER TABLE contacts ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);