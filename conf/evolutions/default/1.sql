# User schema

# --- !Ups
CREATE TABLE "user" (
  "id" SERIAL PRIMARY KEY,
  "first_name" TEXT NOT NULL,
  "last_name" TEXT NOT NULL,
  "mobile" BIGINT NOT NULL,
  "email" TEXT NOT NULL
);

# --- !Downs
DROP TABLE "user"