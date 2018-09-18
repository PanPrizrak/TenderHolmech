create table applicant (id bigint not null, address varchar(255), namea varchar(255), pan varchar(255), primary key (id)) engine=MyISAM;
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
create table message (id bigint not null, datem varchar(255), numberm varchar(255), applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM;
create table orders (id bigint not null, dateo date, numbero varchar(255), primary key (id)) engine=MyISAM;
create table subject (id bigint not null, amount integer, code varchar(255), delivery varchar(255), names varchar(255), payment varchar(255), price double precision, units varchar(255), tender_id bigint, primary key (id)) engine=MyISAM;
create table tender (id bigint not null, datet date, filename varchar(255), namet varchar(255), numbert varchar(255), payment_factor varchar(255), price_factor varchar(255), stage varchar(255), oreder_id bigint, primary key (id)) engine=MyISAM;
create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM;
create table usr (id bigint not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=MyISAM;
create table worker (id bigint not null, namew varchar(255), patronymic varchar(255), position varchar(255), surname varchar(255), worker_id bigint, primary key (id)) engine=MyISAM;
create table worker_role (id bigint not null, role varchar(255), order_id bigint, worker_id bigint, primary key (id)) engine=MyISAM;
alter table contacts add constraint contacts_worker_fk foreign key (contact_id) references worker (id);
alter table message add constraint message_applicant_fk foreign key (applicant_id) references applicant (id);
alter table message add constraint message_tender_fk foreign key (tender_id) references tender (id);
alter table subject add constraint subject_tender_fk foreign key (tender_id) references tender (id);
alter table tender add constraint FKh9wgotiu144ewj69wyjmhcpni foreign key (oreder_id) references orders (id);
alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);
alter table worker add constraint worker_applicant_fk foreign key (worker_id) references applicant (id);
alter table worker_role add constraint worker_role_orders_fk foreign key (order_id) references orders (id);
alter table worker_role add constraint worker_role_worker_fk foreign key (worker_id) references worker (id)

create table applicant (id bigint not null, address varchar(255), namea varchar(255), pan varchar(255), primary key (id)) engine=MyISAM
2018-09-18 19:09:53.225 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table contacts (id bigint not null, email varchar(255), phone varchar(255), contact_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:53.290 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table documents (id bigint not null, bankreference bit not null, charter bit not null, dealer bit not null, feedback bit not null, product bit not null, registration bit not null, applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:53.460 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table hibernate_sequence (next_val bigint) engine=MyISAM
2018-09-18 19:09:53.566 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.572 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.579 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.586 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.587 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.589 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.591 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.604 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.605 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.606 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : insert into hibernate_sequence values ( 1 )
2018-09-18 19:09:53.607 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table message (id bigint not null, datem varchar(255), numberm varchar(255), applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:53.701 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table orders (id bigint not null, dateo datetime, numbero varchar(255), primary key (id)) engine=MyISAM
2018-09-18 19:09:53.796 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table subject (id bigint not null, amount integer, code varchar(255), delivery varchar(255), names varchar(255), numbers integer, payment varchar(255), price double precision, units varchar(255), applicant_id bigint, tender_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:53.883 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table tender (id bigint not null, datet datetime, filename varchar(255), namet varchar(255), numbert varchar(255), payment_factor varchar(255), price_factor varchar(255), stage varchar(255), oreder_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:53.966 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM
2018-09-18 19:09:54.065 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table usr (id bigint not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=MyISAM
2018-09-18 19:09:54.136 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table worker (id bigint not null, namew varchar(255), patronymic varchar(255), position varchar(255), surname varchar(255), worker_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:54.228 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : create table worker_role (id bigint not null, role varchar(255), order_id bigint, worker_id bigint, primary key (id)) engine=MyISAM
2018-09-18 19:09:54.303 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table contacts add constraint FKaqjtw8y1rwj4arhg2nuaqoxel foreign key (contact_id) references worker (id)
2018-09-18 19:09:54.561 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table documents add constraint FK1s2tmswhokq58fp2jdubuth9w foreign key (applicant_id) references applicant (id)
2018-09-18 19:09:54.645 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table documents add constraint FK4pr7y1541ila2w6neg2rj0a0k foreign key (tender_id) references tender (id)
2018-09-18 19:09:54.716 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table message add constraint FK2ai7p6477u3ljctoks132iaw5 foreign key (applicant_id) references applicant (id)
2018-09-18 19:09:54.778 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table message add constraint FKm5j8dtffxtv05qgyq47g4teuw foreign key (tender_id) references tender (id)
2018-09-18 19:09:54.823 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table subject add constraint FKlhlb5fs5rgaraxmdkh3cdnyog foreign key (applicant_id) references applicant (id)
2018-09-18 19:09:54.852 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table subject add constraint FK9ywwf76ybpbboaovys9iefx2j foreign key (tender_id) references tender (id)
2018-09-18 19:09:54.882 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table tender add constraint FKh9wgotiu144ewj69wyjmhcpni foreign key (oreder_id) references orders (id)
2018-09-18 19:09:54.942 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id)
2018-09-18 19:09:54.992 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table worker add constraint FK21w7vc73o3a88pub5vldklcrq foreign key (worker_id) references applicant (id)
2018-09-18 19:09:55.050 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table worker_role add constraint FK222gu69tj80gg2vyte1ghx4wh foreign key (order_id) references orders (id)
2018-09-18 19:09:55.077 DEBUG 1488 --- [ost-startStop-1] org.hibernate.SQL                        : alter table worker_role add constraint FKcyvamiq864yg258swbjimhb8s foreign key (worker_id) references worker (id)




