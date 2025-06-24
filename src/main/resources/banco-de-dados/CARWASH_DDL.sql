select * from cor;
select * from marca;
select * from servico;

create table marca(
    id int not null auto_increment,
    nome varchar(255) not null,
    primary key (id)
)engine = innodb;

create table cor(
    id int not null auto_increment,
    nome varchar(200) not null,
    primary key(id)
)engine = InnoDB;

create table modelo(
	id int not null auto_increment,
    descricao varchar(50) not null,
    marca_id int not null,
    categoria ENUM('PEQUENO', 'GRANDE', 'PADRÃO', 'MÉDIO', 'MOTO') not null,
    constraint pk_modelo primary key(id),
    constraint fk_modelo_marca foreign key(marca_id) references marca(id)
)engine = InnoDB;

create table motor(
	id_modelo int not null,
    potencia int not null,
    tipo_combustivel ENUM('GASOLINA', 'ETANOL', 'FLEX', 'DIESEL', 'GNV', "OUTROS") not null,
    constraint pk_motor primary key(id_modelo),
    constraint fk_motor_modelo foreign key(id_modelo) references modelo(id) on delete cascade
)engine = InnoDB;

create table servico(
    id int not null auto_increment,
    descricao varchar(255) not null,
    valor decimal(7,2) not null,
    categoria ENUM('PEQUENO', 'MÉDIO', 'GRANDE', 'MOTO', 'PADRÃO') not null,
    primary key (id)
)engine = innodb;

create table cliente (
    id int not null auto_increment,
    nome varchar(100) not null,
    celular varchar(15) not null,
    email varchar(150) not null,
    data_cadastro date not null,
    primary key (id)
)engine = InnoDB;

create table pessoa_fisica(
    id_cliente int not null references cliente(id),
    cpf varchar(30) not null,
    data_nascimento date not null,
    constraint pk_pessoa_fisica primary key (id_cliente),
    constraint fk_cliente foreign key (id_cliente) references cliente(id)
                          on delete cascade
                          on update cascade
)engine = innodb;

create table pessoa_juridica(
   id_cliente int not null references cliente(id),
   cnpj varchar(30) not null,
    inscricao_estadual varchar(50)not null,
   constraint pk_pessoa_juridica primary key (id_cliente),
   constraint fk_pj_cliente foreign key (id_cliente) references cliente(id)
                                  on delete cascade
                                  on update cascade
)engine = innodb;

create table veiculo(
    id int not null auto_increment,
    placa varchar(20) not null unique,
    observacao varchar(255),
    modelo_id int not null references modelo(id),
    cliente_id int not null references cliente(id),
    cor_id int not null references cor(id),
    constraint pk_veiculo primary key (id),
    constraint fk_modelo foreign key (modelo_id) references modelo(id),
    constraint fk_cliente_veiculo foreign key (cliente_id) references cliente(id),
    constraint fk_cor foreign key(cor_id) references cor(id)
)engine=innodb;
