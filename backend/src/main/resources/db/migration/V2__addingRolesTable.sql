/* adding new table roles to database */

DROP TABLE IF EXISTS roles_users;
DROP TABLE IF EXISTS roles;


CREATE TABLE roles (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR (50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_roles_name (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');


CREATE TABLE roles_users (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY (user_id, role_id),
  KEY `fk_user_roles_role_id` (`role_id`),
  CONSTRAINT fk_users_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_users_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id)


) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*
INSERT INTO users(username, firstName, lastName, email, password, created_at, updated_at)
values ('moha', 'moha', 'joka', 'w@w.fi', '1234', '2008-01-01 00:00:01', '2008-01-01 00:00:01');
INSERT INTO roles_users(user_id, role_id) values (1,4);
INSERT INTO roles_users(user_id, role_id) values (2,4);
*/