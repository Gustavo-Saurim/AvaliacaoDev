CREATE TABLE funcionario (rowid bigint auto_increment, nm_funcionario VARCHAR(255));
INSERT INTO funcionario (nm_funcionario) VALUES ('João'), ('Maria'), ('José'), ('Joana');

CREATE TABLE agenda (rowid bigint auto_increment, nm_agenda VARCHAR(255), cd_periodo_disponivel INT);
INSERT INTO agenda (nm_agenda, cd_periodo_disponivel) VALUES ('Agenda Manhã', 1), ('Agenda Tarde', 2), ('Agenda Integral', 3);