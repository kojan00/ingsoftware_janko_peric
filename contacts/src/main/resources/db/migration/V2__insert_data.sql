INSERT INTO "contact_type" ("type")
VALUES
    ('Personal'),
    ('Work');

INSERT INTO "user" ("first_name", "last_name", "email", "password", "role")
VALUES
    ('John', 'Doe', 'john@example.com', 'password123', 'admin'),
    ('Robert', 'Johnson', 'robert@example.com', 'password456', 'user');

INSERT INTO "contact" ("first_name", "last_name", "address", "phone_number", "contact_type", "user_id")
VALUES
    ('John', 'Doe', '123 Main St', '555-1234', 1, 1),
    ('Jane', 'Smith', '456 Elm St', '555-5678', 2, 1),
    ('Robert', 'Johnson', '789 Oak St', '555-9012', 1, 2);
