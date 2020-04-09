create table artists(
    id integer not null auto_increment,
    name varchar(100) not null,
    country varchar(100),
    primary key (id)
);

create table albums(
    id integer not null auto_increment,
    name varchar(100) not null,
    artist_id integer not null,
    release_year integer,
    primary key (id),
    foreign key (artist_id) references artists (id) on delete restrict
);

create table charts(
    id integer not null auto_increment,
    name varchar(100) not null,
    places integer not null,
    active integer default 1,
    primary key (id)
);

create table clasaments(
    chart_id integer not null,
    album_id integer not null,
    artist_id integer not null,
    place integer not null,
    primary key (chart_id, album_id),
    unique (chart_id, place),
    foreign key (chart_id) references charts(id) on delete cascade,
    foreign key (album_id) references albums(id) on delete restrict,
    foreign key (artist_id) references artists(id) on delete restrict
);

