CREATE TABLE transacao (
  idTransaction SERIAL,
  indiceTransacao INTEGER,
  operacao CHAR,
  itemDado VARCHAR(10),
  CONSTRAINT pk_constraint PRIMARY KEY (idTransaction)
)
