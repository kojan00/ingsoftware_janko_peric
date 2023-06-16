CREATE TYPE role AS ENUM ('USER', 'ADMIN');
CREATE TABLE contacts (
                           id serial PRIMARY KEY,
                           tsid bigint unique,
                           first_name varchar(25) NOT NULL,
                           last_name varchar(25) NOT NULL,
                           address varchar(40),
                           phone_number varchar(15) NOT NULL,
                           contact_type bigint NOT NULL,
                           user_id bigint NOT NULL
);

CREATE TABLE users (
                        id serial PRIMARY KEY,
                        tsid bigint unique,
                        first_name varchar(25) NOT NULL,
                        last_name varchar(25) NOT NULL,
                        email varchar(25) NOT NULL,
                        password varchar(100) NOT NULL,
                        role role NOT NULL
);

CREATE TABLE contact_type (
                                id serial PRIMARY KEY,
                                tsid bigint unique,
                                type varchar(25)
);

ALTER TABLE contacts ADD FOREIGN KEY (contact_type) REFERENCES contact_type (id);

ALTER TABLE contacts ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);