-- MySQL Script generated by MySQL Workbench
-- 06/01/16 20:25:54
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema recruitment
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `recruitment` ;

-- -----------------------------------------------------
-- Schema recruitment
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `recruitment` DEFAULT CHARACTER SET utf8 ;
USE `recruitment` ;

-- -----------------------------------------------------
-- Table `recruitment`.`person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`person` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`manager`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`manager` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`manager` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `person_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_manager_person_idx` (`person_id` ASC),
  UNIQUE INDEX `person_id_UNIQUE` (`person_id` ASC),
  CONSTRAINT `fk_manager_person`
    FOREIGN KEY (`person_id`)
    REFERENCES `recruitment`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`employer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`employer` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`employer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  `site` VARCHAR(45) NULL,
  `person_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Employer_person1_idx` (`person_id` ASC),
  UNIQUE INDEX `person_id_UNIQUE` (`person_id` ASC),
  CONSTRAINT `fk_Employer_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `recruitment`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`applicant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`applicant` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`applicant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `person_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Applicant_person1_idx` (`person_id` ASC),
  UNIQUE INDEX `person_id_UNIQUE` (`person_id` ASC),
  CONSTRAINT `fk_Applicant_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `recruitment`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`mark`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`mark` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`mark` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `manager_id` INT NOT NULL,
  `evaluated_person_id` INT NOT NULL,
  `mark` INT NOT NULL,
  `comment` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_mark_person1_idx` (`evaluated_person_id` ASC),
  CONSTRAINT `fk_mark_person1`
    FOREIGN KEY (`evaluated_person_id`)
    REFERENCES `recruitment`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`vacancy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`vacancy` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`vacancy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `employer_id` INT NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `requirments` VARCHAR(500) NOT NULL,
  `salary` DECIMAL(15,2) NULL,
  `status` INT NOT NULL DEFAULT -1,
  `applicant_id` INT NULL,
  `closeDate` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_vacancy_employer1_idx` (`employer_id` ASC),
  INDEX `fk_vacancy_applicant1_idx` (`applicant_id` ASC),
  CONSTRAINT `fk_vacancy_employer1`
    FOREIGN KEY (`employer_id`)
    REFERENCES `recruitment`.`employer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vacancy_applicant1`
    FOREIGN KEY (`applicant_id`)
    REFERENCES `recruitment`.`applicant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`interview`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`interview` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`interview` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `applicant_id` INT NULL,
  `vacancy_id` INT NULL,
  `date` VARCHAR(100) NULL,
  `employer_result` INT NULL DEFAULT 0,
  `applicant_result` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_interview_applicant1_idx` (`applicant_id` ASC),
  INDEX `fk_interview_vacancy1_idx` (`vacancy_id` ASC),
  CONSTRAINT `fk_interview_applicant1`
    FOREIGN KEY (`applicant_id`)
    REFERENCES `recruitment`.`applicant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interview_vacancy1`
    FOREIGN KEY (`vacancy_id`)
    REFERENCES `recruitment`.`vacancy` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recruitment`.`resume`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `recruitment`.`resume` ;

CREATE TABLE IF NOT EXISTS `recruitment`.`resume` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `applicant_id` INT NOT NULL,
  `experience` VARCHAR(500) NOT NULL,
  `skills` VARCHAR(500) NOT NULL,
  `education` VARCHAR(500) NOT NULL,
  `description` VARCHAR(500) NULL,
  `in_search` TINYINT(1) NULL DEFAULT 1,
  `vacancy_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_resume_vacancy1_idx` (`vacancy_id` ASC),
  INDEX `fk_resume_applicant1_idx` (`applicant_id` ASC),
  CONSTRAINT `fk_resume_vacancy1`
    FOREIGN KEY (`vacancy_id`)
    REFERENCES `recruitment`.`vacancy` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_applicant1`
    FOREIGN KEY (`applicant_id`)
    REFERENCES `recruitment`.`applicant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
