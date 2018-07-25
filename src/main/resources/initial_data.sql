insert into users(username,password,enabled) values('admin','$2a$10$Wm1ZQOMu9/o8vE5D1od1zeTwGXugeFhAhs/bOqEMg26ovw1jk9TG.',true);
insert into authorities(username,authority)  values('admin','ROLE_ADMIN');

insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:6F:02:B5','359090150142188','214dae9e9352e872','若嚴','rick',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:D9:D4:C1','359090150142188','14dae9e9352e2959','孤苟寒','孤苟寒',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:BE:0B:EA','359090150142188','14dae9e9352e2959','孤玖寒','孤苟寒',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:A7:42:9C','359090150142188','14dae9e9352e2959','孤玖寒2','孤苟寒',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:1E:CC:EB','359090150142188','14dae9e9352e2959','帆苟禮','猴子爸爸',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:97:2C:77','359090150142188','14dae9e9352e2959','帆苟禮','猴子爸爸Mac',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:B5:33:53','359090150142188','14dae9e9352e2959','咒怨馬','AndyKao',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
insert into DEVICE(oid,MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(UUID(),'08:00:27:BE:ED:C6','359090150142188','14dae9e9352e2959','富士山','張哲滈',true,CURRENT_TIMESTAMP,TIMESTAMPADD(YEAR,1,CURRENT_DATE));
;