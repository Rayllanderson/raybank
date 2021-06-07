create sequence hibernate_sequence start 1 increment 1;
create table bank_account (id int8 not null, account_number int4, balance numeric(19, 2), credit_card_id int8, user_id int8, primary key (id));
create table bank_account_contacts (bank_account_id int8 not null, contacts_id int8 not null, primary key (bank_account_id, contacts_id));
create table bank_account_statements (bank_account_id int8 not null, statements_id int8 not null);
create table bank_statement (id int8 not null, amount numeric(19, 2), message varchar(255), moment TIMESTAMP, statement_type varchar(255), account_owner_id int8, account_sender_id int8, primary key (id));
create table credit_card (id int8 not null, balance numeric(19, 2), card_number int8, invoice numeric(19, 2), bank_account_id int8, primary key (id));
create table credit_card_statements (credit_card_id int8 not null, statements_id int8 not null);
create table pix (id int8 not null, key varchar(99), owner_id int8, primary key (id));
create table users (id int8 not null, authorities varchar(255), name varchar(100), password varchar(100), username varchar(100), bank_account_id int8, primary key (id));
create table users_pix_keys (users_id int8 not null, pix_keys_id int8 not null, primary key (users_id, pix_keys_id));
alter table if exists bank_account_statements add constraint UK_73s2irvsdqq3uwwtyvb4wn5e unique (statements_id);
alter table if exists credit_card_statements add constraint UK_sh9voac4duit2wte86sljwph4 unique (statements_id);
alter table if exists users_pix_keys add constraint UK_fvxvn9y1d3v5d9p2rm8l1hrl3 unique (pix_keys_id);
alter table if exists bank_account add constraint FKbc8o2jsw6157rx5phptjgf4x foreign key (credit_card_id) references credit_card;
alter table if exists bank_account add constraint FKftsfxon3d4ectm5bv7glrhlko foreign key (user_id) references users;
alter table if exists bank_account_contacts add constraint FKqdaftrejdax2l4n9opo5imkul foreign key (contacts_id) references bank_account;
alter table if exists bank_account_contacts add constraint FKolv1ty9rr52gbbjc8g7dvrsto foreign key (bank_account_id) references bank_account;
alter table if exists bank_account_statements add constraint FKa18ujkhqinp3xxjmw0w8plkta foreign key (statements_id) references bank_statement;
alter table if exists bank_account_statements add constraint FK6r4segfwrgupuxtew7xkcmp79 foreign key (bank_account_id) references bank_account;
alter table if exists bank_statement add constraint FK30uswcv9bwdcy2i5os3yrs3ut foreign key (account_owner_id) references bank_account;
alter table if exists bank_statement add constraint FKbsj835lxu88b2x9iakt5ne1x9 foreign key (account_sender_id) references bank_account;
alter table if exists credit_card add constraint FK6hn5ehrab2px64klybnvqdx0p foreign key (bank_account_id) references bank_account;
alter table if exists credit_card_statements add constraint FK2w85wo2hcec5kave5vcg1vew foreign key (statements_id) references bank_statement;
alter table if exists credit_card_statements add constraint FK9bk2psyk5ps8ufvhwr2yiw9w6 foreign key (credit_card_id) references credit_card;
alter table if exists pix add constraint FKgee7umdr53te0fc220wqyanyk foreign key (owner_id) references users;
alter table if exists users add constraint FKsmda401hah6pnja2hymvij1bk foreign key (bank_account_id) references bank_account;
alter table if exists users_pix_keys add constraint FKgxjjge8rth9c08enkk3k3r958 foreign key (pix_keys_id) references pix;
alter table if exists users_pix_keys add constraint FKaebgpwxhn6prq48f055n1ar63 foreign key (users_id) references users;