drop table if exists money_transfer_history;
CREATE TABLE money_transfer_history
( id INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  creditor_name VARCHAR(50),
  creditor_iban VARCHAR(50) NOT NULL ,
  amount DOUBLE,
  currency VARCHAR(10),
  debtor_name VARCHAR(50),
  debtor_iban VARCHAR(50),
  status VARCHAR(50),
  date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  bonifico VARCHAR(2000) ,
  PRIMARY KEY (`id`));
  
 DROP TABLE IF EXISTS USER_TRANSACTION;

CREATE TABLE USER_TRANSACTION (
   id INT AUTO_INCREMENT  PRIMARY KEY,
   accountId INT NOT NULL,
   transactionId VARCHAR(100) NOT NULL,
   valueDate DATE NOT NULL,
   amount NUMBER NOT NULL,
   currency VARCHAR(3) NOT NULL,
   description VARCHAR(1000)
);