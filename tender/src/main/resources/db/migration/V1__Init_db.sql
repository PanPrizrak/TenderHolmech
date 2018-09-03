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









CREATE TABLE `applicant` (
	`id` BIGINT(20) NOT NULL,
	`address` VARCHAR(255) NULL DEFAULT NULL,
	`name` VARCHAR(255) NULL DEFAULT NULL,
	`pan` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
;

CREATE TABLE `contacts` (
	`id` BIGINT(20) NOT NULL,
	`email` VARCHAR(255) NULL DEFAULT NULL,
	`phone` VARCHAR(255) NULL DEFAULT NULL,
	`contact_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FKaqjtw8y1rwj4arhg2nuaqoxel` (`contact_id`)
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
;

