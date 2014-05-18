
CREATE SCHEMA IF NOT EXISTS `mydb` ;
SET SCHEMA `mydb` ;
-- -----------------------------------------------------
-- Table `mydb`.`accounts`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `mydb`.`accounts` ;

CREATE TABLE IF NOT EXISTS `mydb`.`accounts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
