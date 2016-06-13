--------------------------------------------------------
--  DDL for Table QUOTES
--------------------------------------------------------
CREATE TABLE QUOTES (
  Q_ID NUMBER(3, 0) NOT NULL,
  QUOTE NVARCHAR2(300),
  AUTHOR NVARCHAR2(50),
  CONSTRAINT QUOTES_PK PRIMARY KEY (Q_ID)
)

--------------------------------------------------------
--  DDL for Index QUOTES_PK
--------------------------------------------------------
CREATE UNIQUE INDEX QUOTES_PK ON QUOTES (Q_ID ASC))

--------------------------------------------------------
--  Constraints for Table QUOTES
--------------------------------------------------------
ALTER TABLE "QUOTES" ADD CONSTRAINT "QUOTES_PK" PRIMARY KEY ("Q_ID")
