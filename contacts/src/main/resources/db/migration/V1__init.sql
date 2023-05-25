CREATE TABLE "contact" (
                           "id" integer PRIMARY KEY,
                           "first_name" varchar,
                           "last_name" varchar,
                           "address" varchar,
                           "phone_number" varchar,
                           "contact_type" integer,
                           "user_id" integer
);

CREATE TABLE "user" (
                        "id" integer PRIMARY KEY,
                        "first_name" varchar,
                        "last_name" varchar,
                        "email" varchar,
                        "password" varchar,
                        "role" varchar
);

CREATE TABLE "contact_type" (
                                "id" integer PRIMARY KEY,
                                "type" varchar
);

ALTER TABLE "contact" ADD FOREIGN KEY ("contact_type") REFERENCES "contact_type" ("id");

ALTER TABLE "contact" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
