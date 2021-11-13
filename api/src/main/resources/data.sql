insert into users(id, username, password, authorities) values ( 1, 'kaguya', '$2a$10$uReUukGDU8G5c1./Ax7/V.gGswlkgYIbYXRgOxWP0.ZPhse3A6A62', 'ROLE_USER' );
insert into bank_account(id, account_number, balance, user_id) values ( 2, 222222222, 5000, 1 );
insert into credit_card(id, card_number, balance, invoice, bank_account_id) values ( 3, 3205222159999431, 5000, 0, 2 );
update users set bank_account_id = 2 where id = 1;
update bank_account set credit_card_id = 3 where id = 2;