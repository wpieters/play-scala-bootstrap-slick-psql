# --- !Ups
CREATE TABLE "account" (
  "id" SERIAL PRIMARY KEY,
  "email" TEXT NOT NULL,
  "password" TEXT NOT NULL,
  "name" TEXT NOT NULL,
  "role" TEXT NOT NULL
);

INSERT INTO "account" (email, password, name, role) VALUES ('alice@example.com', '$2a$10$.7WiVwnfmROXvphMK64c9uz9.na1zulVszM0Z1v9O3UhuV.lj.Cs2', 'Alice', 'Administrator');
INSERT INTO "account" (email, password, name, role) VALUES ('bob@example.com', '$2a$10$Mx5edZdtteQdosaMQ1j9MeLoDO0lRPwky3FnHrJlt.qS4yW1hD6Le', 'Bob', 'NormalUser');
INSERT INTO "account" (email, password, name, role) VALUES ('chris@example.com', '$2a$10$6yzsMAEH2Wfr91usSwVfiOBh0URls2YyNFH1kwrrA6JuR1RQOkj9q', 'Chris', 'NormalUser');

# --- !Downs
DROP TABLE "account";