USE tasks;

DROP TABLE IF EXISTS user_roles;

CREATE TABLE IF NOT EXISTS user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username));
  

INSERT INTO user_roles (username, role)
VALUES ('bach', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('bach', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('alex', 'ROLE_USER');

Alter Table tasks.user_roles  
Add constraint fk_username 
	Foreign Key (username)
    References tasks.users(username)
On Update Cascade;