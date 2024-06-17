CREATE TABLE clients (
                         id        bigserial PRIMARY KEY,
                         full_name varchar(255) NOT NULL,
                         login     varchar(255) NOT NULL UNIQUE,
                         password  varchar(255) NOT NULL
);

CREATE TABLE invents (
                         id       bigserial PRIMARY KEY,
                         category VARCHAR(255) NOT NULL,
                         client   VARCHAR(255) NOT NULL,
                         location VARCHAR(255) NOT NULL,
                         name     VARCHAR(255) NOT NULL,
                         picture  VARCHAR(255),
                         qr       VARCHAR(255) NOT NULL,
                         quality  VARCHAR(255) NOT NULL,
                         status   VARCHAR(255)
);

CREATE TABLE permission (
                            id          bigserial PRIMARY KEY,
                            description varchar(255) NOT NULL,
                            name        varchar(255) NOT NULL UNIQUE
);

CREATE TABLE roles (
                       id   serial PRIMARY KEY,
                       name varchar(50)
);

CREATE TABLE roles_permissions (
                                   role_id       integer NOT NULL REFERENCES roles,
                                   permission_id bigint NOT NULL REFERENCES permission,
                                   PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE users (
                       id       bigserial PRIMARY KEY,
                       email    varchar(50) UNIQUE,
                       password varchar(120),
                       username varchar(20) UNIQUE
);

CREATE TABLE users_roles (
                             user_id bigint  NOT NULL REFERENCES users,
                             role_id integer NOT NULL REFERENCES roles,
                             PRIMARY KEY (user_id, role_id)
);

CREATE TABLE category (
                          id            bigserial PRIMARY KEY,
                          category_name varchar(255)
);

CREATE TABLE location (
                          id            bigserial PRIMARY KEY,
                          location_name varchar(255)
);

CREATE TABLE quality (
                         id            bigserial PRIMARY KEY,
                         quality_name varchar(255)
);

/* ROLES and TEST info */
INSERT INTO roles(name) VALUES('ROLE_SUPER_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_MANAGER');

INSERT INTO clients(full_name, login, password)
VALUES ('super-admin', 'super-admin', '$2a$10$wScCezWbw0GlYa606P9GquBcXoaandeYIGQuLWRWJD4Lmn4p55oRy');

INSERT INTO users (email, password, username)
VALUES ('super-admin@gmail.com', '$2a$12$Qll7eJxTBpa5bjVNpSMOd.9USpQ9nDRYX8/j6IT2ZMARdqxg04sxG', 'super-admin');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);

INSERT INTO permission(description, name) VALUES('testPermissionDesc', 'testPermissionName');
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, 1);

INSERT INTO invents(name, location, quality, category, picture, qr, client, status)
VALUES('exampleInvent', 'Room 200', 'Low', 'Chair', '/test/test.png', 'test_qr', 'super-admin', 'testStatus');

-- Insert categories
INSERT INTO category (category_name) VALUES ('Chair');
INSERT INTO category (category_name) VALUES ('Table');
INSERT INTO category (category_name) VALUES ('Board');
INSERT INTO category (category_name) VALUES ('Computer');
INSERT INTO category (category_name) VALUES ('Keyboard');
INSERT INTO category (category_name) VALUES ('Mouse');
INSERT INTO category (category_name) VALUES ('Monitor');

-- Insert locations
INSERT INTO location (location_name) VALUES ('Room 200');
INSERT INTO location (location_name) VALUES ('Room 201');
INSERT INTO location (location_name) VALUES ('Room 202');
INSERT INTO location (location_name) VALUES ('Room 203');
INSERT INTO location (location_name) VALUES ('Room 204');
INSERT INTO location (location_name) VALUES ('Room 205');
INSERT INTO location (location_name) VALUES ('Room 206');
INSERT INTO location (location_name) VALUES ('Room 207');
INSERT INTO location (location_name) VALUES ('Room 208');
INSERT INTO location (location_name) VALUES ('Room 209');
INSERT INTO location (location_name) VALUES ('Room 210');
INSERT INTO location (location_name) VALUES ('Room 300');
INSERT INTO location (location_name) VALUES ('Room 301');
INSERT INTO location (location_name) VALUES ('Room 302');
INSERT INTO location (location_name) VALUES ('Room 303');
INSERT INTO location (location_name) VALUES ('Room 304');
INSERT INTO location (location_name) VALUES ('Room 305');
INSERT INTO location (location_name) VALUES ('Room 306');
INSERT INTO location (location_name) VALUES ('Room 307');
INSERT INTO location (location_name) VALUES ('Room 308');
INSERT INTO location (location_name) VALUES ('Room 309');
INSERT INTO location (location_name) VALUES ('Room 310');

-- Insert qualities
INSERT INTO quality (quality_name) VALUES ('Low');
INSERT INTO quality (quality_name) VALUES ('Medium');
INSERT INTO quality (quality_name) VALUES ('High');
