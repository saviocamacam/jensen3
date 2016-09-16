CREATE TABLE transacao (
  idTransaction SERIAL,
  indiceTransacao VARCHAR(10),
  operacao CHAR,
  itemDado VARCHAR(10),
  CONSTRAINT pk_constraint PRIMARY KEY (idTransaction)
)
