# --- !Ups
CREATE TABLE "account" (
  "id" SERIAL PRIMARY KEY,
  "email" TEXT NOT NULL,
  "password" TEXT NOT NULL,
  "name" TEXT NOT NULL,
  "role" TEXT NOT NULL
);

INSERT INTO "account" (email, password, name, role) VALUES ('alice@example.com', 'secret', 'Alice', 'Administrator');
INSERT INTO "account" (email, password, name, role) VALUES ('bob@example.com', 'secret', 'Bob', 'NormalUser');
INSERT INTO "account" (email, password, name, role) VALUES ('chris@example.com', 'secret', 'Chris', 'NormalUser');

# --- !Downs
DROP TABLE "account";