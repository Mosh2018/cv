CREATE TABLE users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username VARCHAR (50) NOT NULL,
  firstName VARCHAR (50) NOT NULL,
  lastName VARCHAR (50),
  email VARCHAR (50) NOT NULL,
  password VARCHAR (250) NOT NULL,
  created_at datetime DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_username (username),
  UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;