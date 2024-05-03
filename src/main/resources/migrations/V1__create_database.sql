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
    name      varchar(255) not null
            unique,
    picture   varchar(255),
    qr        varchar(255),
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

/* ROLES and TEST info*/
INSERT INTO roles(name) VALUES('ROLE_SUPER_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_MANAGER');
INSERT INTO permission(description, name) VALUES('testPermissionDesc', 'testPermissionName');
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, 1);
INSERT INTO users(email, password, username) VALUES('exampleUser@example.com', '$2a$10$fJWr5Od5AWWgEARKrq7jaeh/Q4tUhHAyhvghkWxgbWpMgfAGCFZ.O', 'exampleUser');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);

INSERT INTO clients(login, logo, password) VALUES('exampleUser@example.com', '/test/test.png', '$2a$10$fJWr5Od5AWWgEARKrq7jaeh/Q4tUhHAyhvghkWxgbWpMgfAGCFZ.O');
INSERT INTO invents(name, picture, qr, client_id) VALUES('exampleInvent', '/test/test.png', 'test_qr', 1);
