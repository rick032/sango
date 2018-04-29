insert into users(username,password,enabled) values('admin','$2a$10$Wm1ZQOMu9/o8vE5D1od1zeTwGXugeFhAhs/bOqEMg26ovw1jk9TG.',true);
insert into authorities(username,authority)  values('admin','ROLE_ADMIN');

insert into DEVICE(MACADDR,IMEI,deviceId,gameName,userName,enabled) values('08:00:27:72:E8:A1','359090150142188','14dae9e9352e2959','若嚴','rick',true);
insert into DEVICE(MACADDR,IMEI,deviceId,gameName,userName,enabled) values('08:00:27:42:F8:2A','359090150142188','14dae9e9352e2959','wade','rick',true);	