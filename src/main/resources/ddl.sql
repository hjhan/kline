
CREATE TABLE PUBLIC.TRADE (
	ID VARCHAR(30) NOT NULL,
	INSTRUMENT VARCHAR(10) NOT NULL,
	PRICE DECIMAL(10,2),
	QUANTITY DECIMAL(10,6) NOT NULL,
	SIDE VARCHAR(5) NOT NULL,
	TS BIGINT NOT NULL,
	CONSTRAINT TRADE_PK PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX PRIMARY_KEY_4 ON PUBLIC.TRADE (ID);