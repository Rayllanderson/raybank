insert into users(id, username, password, authorities, name) values ( 50, 'kaguya', '$2a$10$uReUukGDU8G5c1./Ax7/V.gGswlkgYIbYXRgOxWP0.ZPhse3A6A62', 'ROLE_USER', 'Kaguya Shinomiya' );
insert into bank_account(id, account_number, balance, user_id) values ( 20, 222222222, 5000, 50 );
insert into credit_card(id, card_number, balance, invoice, bank_account_id) values ( 30, 3205222159999431, 5000, 0, 20 );
insert into external_token(token, creation_time, client_name) values ( '1aCgA8kQGwok01p9WsVLoJZwfxVC33yd', '2021-11-14', 'whatever' );
update users set bank_account_id = 20 where id = 50;
update bank_account set credit_card_id = 30 where id = 20;