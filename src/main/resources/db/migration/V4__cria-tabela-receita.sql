create table receita (
    id serial primary key,
    nome varchar(100) not null,
    imagem varchar(255),
    ativo boolean default true not null
);

create table receita_ingrediente (
    receita_id integer not null references receita(id),
    ingrediente_id integer not null references ingrediente(id),
    primary key (receita_id, ingrediente_id)
);
