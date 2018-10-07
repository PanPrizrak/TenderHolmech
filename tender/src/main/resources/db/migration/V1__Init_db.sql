create table applicant (ida bigint not null, address varchar(255), namea varchar(255), pan varchar(255), primary key (ida)) engine=MyISAM;
create table contacts (idc bigint not null, email varchar(255), phone varchar(255), contact_id bigint, primary key (idc)) engine=MyISAM;
create table documents (idd bigint not null, bankreference bit not null, charter bit not null, dealer bit not null, feedback bit not null, product bit not null, registration bit not null, applicant_id bigint, tender_id bigint, primary key (idd)) engine=MyISAM;
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
insert into hibernate_sequence values ( 1 );
create table message (id bigint not null, datem varchar(255), numberm varchar(255), applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM;
create table orders (ido bigint not null, dateo datetime, numbero varchar(255), primary key (ido)) engine=MyISAM;
create table subject (ids bigint not null, amount integer, code varchar(255), delivery varchar(255), names varchar(255), numbers integer, payment varchar(255), price double precision, units varchar(255), applicant_id bigint, tender_id bigint, primary key (ids)) engine=MyISAM;
create table tender (idt bigint not null, datet datetime, filename varchar(255), namet varchar(255), numbert varchar(255), payment_factor varchar(255), price_factor varchar(255), stage varchar(255), oreder_id bigint, primary key (idt)) engine=MyISAM;
create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM;
create table usr (id bigint not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=MyISAM;
create table worker (idw bigint not null, namew varchar(255), patronymic varchar(255), position varchar(255), surname varchar(255), worker_id bigint, primary key (idw)) engine=MyISAM;
create table worker_role (idwr bigint not null, role varchar(255), order_id bigint, worker_id bigint, primary key (idwr)) engine=MyISAM;
alter table contacts add constraint contacts_worker_fk foreign key (contact_id) references worker (idw);
alter table message add constraint message_applicant_fk foreign key (applicant_id) references applicant (ida);
alter table message add constraint message_tender_fk foreign key (tender_id) references tender (idt);
alter table subject add constraint subject_applicant_fk foreign key (applicant_id) references applicant (ida);
alter table subject add constraint subject_tender_fk foreign key (tender_id) references tender (idt);
alter table tender add constraint tender_orders_fk foreign key (oreder_id) references orders (ido);
alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);
alter table worker add constraint worker_applicant_fk foreign key (worker_id) references applicant (ida);
alter table worker_role add constraint worker_role_orders_fk foreign key (order_id) references orders (ido);
alter table worker_role add constraint worker_role_worker_fk foreign key (worker_id) references worker (ide);
alter table documents add constraint documents_applicant_fk foreign key (applicant_id) references applicant (ids);
alter table documents add constraint documents_tender_fk foreign key (tender_id) references tender (idt)







