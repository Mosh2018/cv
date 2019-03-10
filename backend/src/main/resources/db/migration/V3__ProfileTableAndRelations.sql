DROP TABLE if EXISTS cv_profile;

CREATE TABLE `cv_profile` (
  id bigint(20) AUTO_INCREMENT NOT NULL,
  phone_number CHAR (20) NOT NULL,
  birthday CHAR (20) NOT NULL,
  nationality CHAR (20) NOT NULL,
  address CHAR (50) NOT NULL,
  country CHAR (20) NOT NULL,
  city CHAR (20) NOT NULL,
  zip_code CHAR (20) NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE users ADD COLUMN profile_id bigint(20) DEFAULT NULL AFTER password;
ALTER TABLE users ADD CONSTRAINT FK_profile_id FOREIGN KEY (profile_id) REFERENCES cv_profile (id);