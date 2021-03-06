CREATE DATABASE IF NOT EXISTS demo;
USE demo;

CREATE TABLE IF NOT EXISTS patient_info (
    id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(255),
    dob DATE NOT NULL,
    family VARCHAR(64) NOT NULL,
    given VARCHAR(64) NOT NULL,
    phone VARCHAR(16),
    sex CHAR NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB;
