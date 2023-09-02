

Drop table Notificacao
Drop table Pessoa
drop table Endereco

CREATE table Endereco
(
cep varchar(8) ,
logradouro varchar(255) not null,
bairro varchar(255),
localidade varchar(255),
uf varchar(2)
)

CREATE table Pessoa
(
nome varchar(255) not null,
cep varchar(8) null,
numero varchar(255),
complemento varchar(255),
email varchar(255) null,
FOREIGN KEY (cep) REFERENCES Endereco(cep)
)

CREATE table Notificacao
(
id integer PRIMARY KEY IDENTITY,
pessoa varchar(16) not null,
tipoDocumento varchar(255) not null,
descricao varchar(max),
situacao integer,
dataSubmissao datetime,
dataEnvio datetime,
tipoEnvio integer,
FOREIGN KEY (pessoa) REFERENCES Pessoa(documento)
)