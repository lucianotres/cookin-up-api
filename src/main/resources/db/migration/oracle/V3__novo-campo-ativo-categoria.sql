alter table categoria
   add ativo number(1) default 1 not null;

alter table categoria
  add constraint chk_categoria_ativo check (ativo IN (0,1));
