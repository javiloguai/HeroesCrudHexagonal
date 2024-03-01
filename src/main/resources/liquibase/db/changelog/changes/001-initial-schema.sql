CREATE TABLE IF NOT EXISTS "SUPER_HERO" (
  "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,
  "NAME" VARCHAR(50) NOT NULL,
  "DESCRIPTION" VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "HERO_SUPER_POWER" (
  "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,
  "SUPERHERO_ID" BIGINT NOT NULL,
  "SUPER_POWER" VARCHAR(50) NOT NULL,
  FOREIGN KEY ("SUPERHERO_ID") REFERENCES "SUPER_HERO"("ID")
);

CREATE TABLE IF NOT EXISTS "AUTH_USER"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,
    "PASSWORD" VARCHAR(512),
    "ROLE" VARCHAR(50),
    "USERNAME" VARCHAR(50) NOT NULL UNIQUE
);