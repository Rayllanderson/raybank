insert into users(id, username, password, authorities, name) values ( 50, 'kaguya', '$2a$10$uReUukGDU8G5c1./Ax7/V.gGswlkgYIbYXRgOxWP0.ZPhse3A6A62', 'ROLE_USER', 'Kaguya Shinomiya' );
insert into bank_account(id, account_number, balance, user_id) values ( 20, 222222222, 5000, 50 );
insert into credit_card(id, card_number, expiry_date, security_code, balance, invoice, bank_account_id) values ( 30, 3205222159999431, 'aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c0000787077060c000007ef0878', 333, 5000, 0, 20 );
update users set bank_account_id = 20 where id = 50;
update bank_account set credit_card_id = 30 where id = 20;