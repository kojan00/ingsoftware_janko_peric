INSERT INTO contact_type (tsid, type)
VALUES (451742692751587704, 'Personal'),
       (452025544751916770, 'Work');

INSERT INTO users (tsid, first_name, last_name, email, password, role)
VALUES (452025627400677091, 'Janko', 'Peric', 'admin@example.com', '$2a$04$jai46npMwPqeaft4fEkjmeYHtr6q7MF5dQdvJhAfAiZK7Bj.xxRwm', 'ADMIN'), /*SIFRA JE admin123*/
       (454198670929971100, 'Petar', 'Petrovic', 'user@example.com', '$2a$04$9cvLDwJhls2EtfAL3GsviuhUOcTwSIKQ27E28lthfxFV36VkXKsv2', 'USER'); /*SIFRA JE user123*/

INSERT INTO contacts (tsid, first_name, last_name, address, phone_number, contact_type, user_id)
VALUES (454650124721979072, 'Aca', 'Ilic', 'Kosovska', '555-1234', 1, 1),
       (456107062874326716, 'Misa', 'Gajovic', 'Kneza Mihajla', '555-5678', 2, 1);
