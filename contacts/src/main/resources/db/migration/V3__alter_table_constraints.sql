ALTER TABLE "contact" ADD CONSTRAINT "address_unique" UNIQUE ("address");
ALTER TABLE "contact" ADD CONSTRAINT "phone_number_unique" UNIQUE ("phone_number");
ALTER TABLE "user" ADD CONSTRAINT "email_unique" UNIQUE ("email");

ALTER TABLE "contact" ALTER COLUMN "first_name" SET NOT NULL;
ALTER TABLE "contact" ALTER COLUMN "last_name" SET NOT NULL;
ALTER TABLE "contact" ALTER COLUMN "phone_number" SET NOT NULL;
ALTER TABLE "contact" ALTER COLUMN "contact_type" SET NOT NULL;
ALTER TABLE "contact" ALTER COLUMN "user_id" SET NOT NULL;

ALTER TABLE "user" ALTER COLUMN "first_name" SET NOT NULL;
ALTER TABLE "user" ALTER COLUMN "last_name" SET NOT NULL;
ALTER TABLE "user" ALTER COLUMN "email" SET NOT NULL;
ALTER TABLE "user" ALTER COLUMN "password" SET NOT NULL;
ALTER TABLE "user" ALTER COLUMN "role" SET NOT NULL;