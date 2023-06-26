ALTER TABLE contacts
    ADD CONSTRAINT phone_number_contacts_unique UNIQUE (phone_number);

ALTER TABLE users
    ADD phone_number varchar(25);

ALTER TABLE users
    ADD phone_verified bool DEFAULT false;

ALTER TABLE users
    ADD CONSTRAINT phone_number_user_unique UNIQUE (phone_number);


UPDATE users
SET phone_number = '325-625'
WHERE first_name = 'Janko';
UPDATE users
SET phone_number = '325-650'
WHERE first_name = 'Petar';


INSERT INTO users (tsid, first_name, last_name, email, password, role, phone_number)
VALUES (461183131431582433, 'Marko', 'Markovic', 'marko@example.com', '$2a$04$u2BQGNUDy7PZ4P96xFmIN.3JKVWzm3NDVRnyWppuFzD3SIwHecyXG
', 'USER', '555-002'),
       (454198673922971100, 'Pavle', 'Pavlovic', 'pavle@example.com',
        '$2a$04$/NB1W/Kl6yJFzRenCHdWtOJLX5Bouj0UsRDrNvTsFALJdae8QJNke', 'USER', '302-302');

INSERT INTO contacts (tsid, first_name, last_name, address, phone_number, contact_type, user_id)
VALUES (401183131931582473, 'Aleksa', 'Ilic', 'Kosovska', '555-902', 1, 2),
       (426107062874826719, 'Misa', 'Gajovic', 'Kneza Mihajla', '333-003', 2, 3);