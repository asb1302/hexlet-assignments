-- apply changes
create table article (
  id                            bigint auto_increment not null,
  title                         varchar(255),
  body                          longtext,
  category_id                   bigint not null,
  constraint pk_article primary key (id)
);

create table category (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_category primary key (id)
);

create index ix_article_category_id on article (category_id);
alter table article add constraint fk_article_category_id foreign key (category_id) references category (id) on delete restrict on update restrict;

