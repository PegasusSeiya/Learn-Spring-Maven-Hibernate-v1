USE tasks;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
  user_id int(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (user_id));
  

INSERT INTO users(username,password,enabled)
VALUES ('bach','123456', true);
INSERT INTO users(username,password,enabled)
VALUES ('alex','123456', true);