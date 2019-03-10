DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE users (
  id bigint(20) AUTO_INCREMENT NOT NULL,
  username VARCHAR (40) NOT NULL,
  first_name VARCHAR (40) NOT NULL,
  last_name VARCHAR (40),
  email VARCHAR (40) NOT NULL,
  password VARCHAR(255) NOT NULL,
  security_key VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_username (username),
  UNIQUE KEY uk_users_email (email)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE roles (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  role_name VARCHAR (20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_name (role_name)
  ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE user_roles (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
  ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO roles(role_name) VALUES('ROLE_USER');
INSERT INTO roles(role_name) VALUES('ROLE_ADMIN');