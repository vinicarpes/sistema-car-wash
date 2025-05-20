select * from cor;
select * from marca;
select * from servico;

create table modelo(
	id int not null auto_increment,
    descricao varchar(50) not null,
    marca_id int not null,
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