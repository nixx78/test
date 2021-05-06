 CREATE TABLE IF NOT EXISTS PERSON_TBL (
   id INT NOT NULL,
   name    VARCHAR(20) NOT NULL,
   surname VARCHAR(20) NOT NULL,
   submission_date DATE,
   PRIMARY KEY (id)
);