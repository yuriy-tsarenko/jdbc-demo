CREATE TABLE IF NOT EXISTS user_account
(
    id       int auto_increment,
    username varchar(100),
    user_pass varchar(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contact
(
    id        int auto_increment,
    firstName varchar(100),
    lastName  varchar(100),
    phone     varchar(100),
    user_account_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (user_account_id) REFERENCES user_account(id)
);