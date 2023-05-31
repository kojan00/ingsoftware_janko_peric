INSERT INTO contact_type (id, type)
VALUES
    (1, 'Personal'),
    (2, 'Work');

INSERT INTO users (id, first_name, last_name, email, password, role)
VALUES
    (1, 'John', 'Doe', 'john@example.com', 'password123', 'ADMIN'),
    (2, 'Robert', 'Johnson', 'robert@example.com', 'password456', 'USER');

INSERT INTO contacts (id, first_name, last_name, address, phone_number, contact_type, user_id)
VALUES
    (1, 'John', 'Doe', '123 Main St', '555-1234', 1, 1),
    (2, 'Jane', 'Smith', '456 Elm St', '555-5678', 2, 1),
    (3, 'Robert', 'Johnson', '789 Oak St', '555-9012', 1, 2);
