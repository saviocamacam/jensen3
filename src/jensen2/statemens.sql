DROP TABLE IF EXISTS scheduleIn;
DROP TABLE IF EXISTS scheduleOut;

CREATE TABLE scheduleIn (
  idOperacao SERIAL,
  indiceTransacao INTEGER,
  operacao VARCHAR(10),
  itemDado VARCHAR(10),
  timestampj VARCHAR(15),
  estado int,
  CONSTRAINT pk_constraint_in PRIMARY KEY (idOperacao)
);

CREATE TABLE scheduleOut (
  idOperacao SERIAL,
  indiceTransacao INTEGER,
  operacao VARCHAR(10),
  itemDado VARCHAR(10),
  timestampj VARCHAR(15),
  estado int,
  CONSTRAINT pk_constraint_out PRIMARY KEY (idOperacao)
);