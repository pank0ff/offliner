create sequence hibernate_sequence start 1 increment 1;
create table comment
(
    id         int8 not null,
    text       varchar(255),
    user_id    int8 not null,
    message_id int4,
    primary key (id)
);
create table message
(
    id           int4   not null,
    average_rate float8 not null,
    filename     varchar(255),
    hashtag      varchar(255),
    likes_count  int4   not null,
    me_liked     int4   not null,
    name         varchar(255),
    tag          varchar(255),
    text         text,
    user_id      int8,
    primary key (id)
);
create table message_likes
(
    message_id int4 not null,
    user_id    int8 not null,
    primary key (message_id, user_id)
);
create table rate
(
    id         int8 not null,
    rate       int4 not null,
    message_id int4,
    user_id    int8,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table user_subscriptions
(
    subscriber_id int8 not null,
    channel_id    int8 not null,
    primary key (channel_id, subscriber_id)
);
create table usr
(
    id                   int8    not null,
    about_myself         varchar(255),
    active               boolean not null,
    avatar_filename      varchar(255),
    choice               varchar(255),
    count_of_likes       int4    not null,
    count_of_posts       int4    not null,
    date_of_registration varchar(255),
    email                varchar(255),
    link_dribble         varchar(255),
    link_facebook        varchar(255),
    link_google          varchar(255),
    link_linked_in       varchar(255),
    link_youtube         varchar(255),
    password             varchar(255),
    theme                varchar(255),
    username             varchar(255),
    primary key (id)
);
alter table if exists comment
    add constraint FKgcgdcgly6u49hf4g8y2di3g4p
        foreign key (user_id) references usr;
alter table if exists comment
    add constraint FKatlrxw2dnvma9h401t2ql2ri8
        foreign key (message_id) references message;
alter table if exists message
    add constraint FK70bv6o4exfe3fbrho7nuotopf
        foreign key (user_id) references usr;
alter table if exists message_likes
    add constraint FKsitk1a427r8b4733gym5f173i
        foreign key (user_id) references usr;
alter table if exists message_likes
    add constraint FKbldk7l0d886p3mfscd4ti3ppn
        foreign key (message_id) references message;
alter table if exists rate
    add constraint FK96d9qh6s5yx6n2qdioe8qpdbc
        foreign key (message_id) references message;
alter table if exists rate
    add constraint FKau5mrxt39r0s3etvqe4462gym
        foreign key (user_id) references usr;
alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5
        foreign key (user_id) references usr;
alter table if exists user_subscriptions
    add constraint FK74b7d4i0rhhj8jljvidimewie
        foreign key (channel_id) references usr;
alter table if exists user_subscriptions
    add constraint FKm69uaasbua17sgdnhsq10yxd5
        foreign key (subscriber_id) references usr;