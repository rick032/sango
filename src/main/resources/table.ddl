create table users(
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not null
);
create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

create table DEVICE(
	oid varchar(32) not null primary key,
	MACADDR varchar(17) not null,
	IMEI varchar(32) not null,
	deviceId varchar(32) not null,
	gameName varchar(120) not null,
	userName varchar(120) not null,
	enabled boolean not null,
	startTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
	endTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
	lastCheckTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
);


create table DEVICELOG(	
	id char(32) not null primary key,	
	CHECKTIME varchar(120) not null,
	CHECKRESULT char(1) ,
	DEVICE_MAC varchar(17) not null FOREIGN KEY (MACADDR) REFERENCES DEVICE(MACADDR)	
);