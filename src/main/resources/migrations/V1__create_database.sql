create table clients
(
    id       bigserial
        primary key,
    login    varchar(255) not null
            unique,
    logo     varchar(255),
    password varchar(255) not null
);

create table invents
(
    id        bigserial
        primary key,
    category  varchar(255) not null,
    location  varchar(255) not null,
    name      varchar(255) not null
            unique,
    picture   varchar(255),
    qr        varchar(255) not null,
    quality   varchar(255) not null,
    client_id bigint       not null
            references clients
);

create table permission
(
    id          bigserial
        primary key,
    description varchar(255),
    name        varchar(255)
);

create table roles
(
    id   bigserial
        primary key,
    name varchar(50)
);

create table roles_permissions
(
    role_id       bigint not null
            references roles,
    permission_id bigint not null
            references permission,
    primary key (role_id, permission_id)
);

create table users
(
    id       bigserial
        primary key,
    email    varchar(50),
    password varchar(120),
    username varchar(20)
);

create table users_roles
(
    user_id bigint not null
            references users,
    role_id bigint not null
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
INSERT INTO roles(name) VALUES('ROLE_MANAGER');
INSERT INTO permission(description, name) VALUES('testPermissionDesc', 'testPermissionName');
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, 1);
INSERT INTO users(email, password, username) VALUES('exampleUser@example.com', '$2a$10$fJWr5Od5AWWgEARKrq7jaeh/Q4tUhHAyhvghkWxgbWpMgfAGCFZ.O', 'exampleUser');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);

INSERT INTO clients(login, logo, password) VALUES('exampleUser@example.com', '/test/test.png', '$2a$10$fJWr5Od5AWWgEARKrq7jaeh/Q4tUhHAyhvghkWxgbWpMgfAGCFZ.O');
INSERT INTO invents(name, location, quality, category, picture, qr, client_id) VALUES('exampleInvent', 'test', 'test', 'test', '/test/test.png', 'test_qr', 1);
INSERT INTO location(location_name) values ('test');
INSERT INTO quality(quality_name) values ('test');
INSERT INTO category(category_name) values ('test');

