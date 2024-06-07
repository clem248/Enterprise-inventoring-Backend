create table clients
(
    id        bigserial
        primary key,
    full_name varchar(255) not null,
    login     varchar(255) not null
            unique,
    password  varchar(255) not null
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

create table permission
(
    id          bigserial
        primary key,
    description varchar(255) not null,
    name        varchar(255) not null
            unique
);

create table roles
(
    id   serial
        primary key,
    name varchar(50)
);

create table roles_permissions
(
    role_id       integer not null
            references roles,
    permission_id bigint not null
            references permission,
    primary key (role_id, permission_id)
);

create table users
(
    id       bigserial
        primary key,
    email    varchar(50)
            unique,
    password varchar(120),
    username varchar(20)
            unique
);


create table users_roles
(
    user_id bigint  not null
            references users,
    role_id integer not null
            references roles,
    primary key (user_id, role_id)
);

create table category
(
    id            bigserial
        primary key,
    category_name varchar(255)
);
create table location
(
    id   bigserial
        primary key,
    location_name varchar(255)
);

create table quality
(
    id           bigserial
        primary key,
    quality_name varchar(255)
);


/* ROLES and TEST info*/
INSERT INTO roles(name) VALUES('ROLE_SUPER_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_CLIENT');

INSERT INTO clients(full_name, login, password)
VALUES ('super-admin', 'super-admin', '$2a$10$wScCezWbw0GlYa606P9GquBcXoaandeYIGQuLWRWJD4Lmn4p55oRy');

INSERT INTO users (email, password, username)
VALUES ('super-admin@gmail.com', '$2a$12$Qll7eJxTBpa5bjVNpSMOd.9USpQ9nDRYX8/j6IT2ZMARdqxg04sxG','super-admin');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);



INSERT INTO permission(description, name) VALUES('testPermissionDesc', 'testPermissionName');
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, 1);


INSERT INTO invents(name, location, quality, category, picture, qr, client,status)
VALUES('exampleInvent', 'test', 'test', 'test', '/test/test.png', 'test_qr', 'testClient','testStatus');
INSERT INTO location(location_name) values ('test');
INSERT INTO quality(quality_name) values ('test');
INSERT INTO category(category_name) values ('test');

