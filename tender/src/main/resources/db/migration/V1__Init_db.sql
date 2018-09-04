/*create table worker
(id bigint not null,
name varchar(255),
patronymic varchar(255),
position varchar(255),
surname varchar(255),
worker_id bigint,
primary key (id)) engine=MyISAM;

create table tender
(id bigint not null,
date varchar(255),
namber varchar(255),
name varchar(255),
primary key (id)) engine=MyISAM;



create table applicant
(id bigint not null,
address varchar(255), name varchar(255),
pan varchar(255), primary key (id)) engine=MyISAM;

create table contacts
(id bigint not null,
email varchar(255),
phone varchar(255),
contact_id bigint,
primary key (id)) engine=MyISAM;

create table message
(id bigint not null,
date varchar(255),
number varchar(255),
applicant_id bigint,
tender_id bigint,
primary key (id)) engine=MyISAM;

create table orders
(id bigint not null,
date varchar(255),
number varchar(255),
primary key (id)) engine=MyISAM;

create table subject
(id bigint not null,
amount integer,
code varchar(255),
delivery varchar(255),
name varchar(255),
payment varchar(255),
price double precision,
units varchar(255),
tender_id bigint,
primary key (id)) engine=MyISAM;



create table user_role
(user_id bigint not null,
roles varchar(255)) engine=MyISAM;

create table usr
(id bigint not null,
activation_code varchar(255),
active bit not null,
email varchar(255),
password varchar(255),
username varchar(255),
primary key (id)) engine=MyISAM;



create table worker_role (id bigint not null,
role varchar(255),
order_id bigint,
worker_role_id bigint,
 primary key (id)) engine=MyISAM;

alter table contacts add constraint contacts_worker_fk foreign key (contact_id) references worker (id);
alter table message add constraint message_applicant_fk foreign key (applicant_id) references applicant (id);
alter table message add constraint message_tender_fk foreign key (tender_id) references tender (id);
alter table subject add constraint subject_tender_fk foreign key (tender_id) references tender (id);
alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);
alter table worker add constraint worker_applicant_fk foreign key (worker_id) references applicant (id);
alter table worker_role add constraint worker_role_order_fk foreign key (order_id) references orders (id);
alter table worker_role add constraint worker_role_worker_fk foreign key (worker_id) references worker (id);*/


create table applicant (id bigint not null, address varchar(255), name varchar(255), pan varchar(255), primary key (id)) engine=MyISAM;
create table contacts (id bigint not null, email varchar(255), phone varchar(255), contact_id bigint, primary key (id)) engine=MyISAM;
create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table message (id bigint not null, date varchar(255), number varchar(255), applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM;
create table orders (id bigint not null, date varchar(255), number varchar(255), primary key (id)) engine=MyISAM;
create table subject (id bigint not null, amount integer, code varchar(255), delivery varchar(255), name varchar(255), payment varchar(255), price double precision, units varchar(255), tender_id bigint, primary key (id)) engine=MyISAM;
create table tender (id bigint not null, date varchar(255), namber varchar(255), name varchar(255), primary key (id)) engine=MyISAM;
create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM;
create table usr (id bigint not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=MyISAM;
create table worker (id bigint not null, name varchar(255), patronymic varchar(255), position varchar(255), surname varchar(255), worker_id bigint, primary key (id)) engine=MyISAM;
create table worker_role (id bigint not null, role varchar(255), order_id bigint, worker_id bigint, primary key (id)) engine=MyISAM;
alter table contacts add constraint contacts_worker_fk foreign key (contact_id) references worker (id);
alter table message add constraint message_applicant_fk foreign key (applicant_id) references applicant (id);
alter table message add constraint message_tender_fk foreign key (tender_id) references tender (id);
alter table subject add constraint subject_tender_fk foreign key (tender_id) references tender (id);
alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);
alter table worker add constraint worker_applicant_fk foreign key (worker_id) references applicant (id);
alter table worker_role add constraint worker_role_orders_fk foreign key (order_id) references orders (id);
alter table worker_role add constraint worker_role_worker_fk foreign key (worker_id) references worker (id)

