create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table user_role
    (user_id bigint not null,
    roles varchar(255))
    engine=MyISAM;
create table usr
    (id bigint not null,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id))
    engine=MyISAM;
create table worker
    (id bigint not null,
    email varchar(255),
    filename varchar(255),
    name varchar(255),
    user_id bigint,
    primary key (id))
    engine=MyISAM;
alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);
alter table worker add constraint worker_user_fk foreign key (user_id) references usr (id);








create table applicant (id bigint not null, address varchar(255), name varchar(255), pan varchar(255), primary key (id)) engine=MyISAM
create table contacts (id bigint not null, email varchar(255), phone varchar(255), contact_id bigint, primary key (id)) engine=MyISAM
create table hibernate_sequence (next_val bigint) engine=MyISAM

create table message (id bigint not null, date varchar(255), number varchar(255), applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM
create table orders (id bigint not null, date varchar(255), number varchar(255), primary key (id)) engine=MyISAM
create table subject (id bigint not null, amount integer, code varchar(255), delivery varchar(255), name varchar(255), payment varchar(255), price double precision, units varchar(255), tender_id bigint, primary key (id)) engine=MyISAM
create table tender (id bigint not null, date varchar(255), namber varchar(255), name varchar(255), primary key (id)) engine=MyISAM
create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM
create table usr (id bigint not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=MyISAM
create table worker (id bigint not null, name varchar(255), patronymic varchar(255), position varchar(255), surname varchar(255), order_id bigint, primary key (id)) engine=MyISAM
alter table contacts add constraint FKaqjtw8y1rwj4arhg2nuaqoxel foreign key (contact_id) references worker (id)
alter table message add constraint FK2ai7p6477u3ljctoks132iaw5 foreign key (applicant_id) references applicant (id)
alter table message add constraint FKm5j8dtffxtv05qgyq47g4teuw foreign key (tender_id) references tender (id)
alter table subject add constraint FK9ywwf76ybpbboaovys9iefx2j foreign key (tender_id) references tender (id)
alter table user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id)
alter table worker add constraint FKk2omqq7r74b0rnopipjeu6jqr foreign key (order_id) references orders (id)

