CREATE DATABASE IF NOT EXISTS managebook;
USE managebook;

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS borrowing_record;

-- 使用者TABLE
CREATE TABLE `user` (
    user_id	      INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	phone_number  CHAR(10) NOT NULL UNIQUE,
	`password`	  VARCHAR(100) NOT NULL,
    username	  VARCHAR(30) NOT NULL,
    registration_time    DATETIME,
    last_login_time      DATETIME
);

-- 庫存TABLE
CREATE TABLE inventory (
    inventory_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    isbn VARCHAR(10) NOT NULL UNIQUE,
    store_time DATETIME,
    `status` INT NOT NULL
);

-- 書籍TABLE
CREATE TABLE book (
    isbn VARCHAR(10) NOT NULL PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    introduction VARCHAR(100) NOT NULL,
    inventory_id INT NOT NULL UNIQUE,
    CONSTRAINT inventory_book_FK FOREIGN KEY (isbn)
        REFERENCES inventory (isbn),
    CONSTRAINT inventory_id_book_FK FOREIGN KEY (inventory_id)
        REFERENCES inventory (inventory_id)
);

-- 借閱紀錄TABLE
CREATE TABLE borrowing_record (
    record_id           INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id	            INT NOT NULL,
    inventory_id        INT NOT NULL,
	borrowing_time      DATETIME,
    return_time         DATETIME,
    CONSTRAINT borrow_inventory_FK FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id),
    CONSTRAINT borrow_user_FK FOREIGN KEY (user_id) REFERENCES `user` (user_id),
    INDEX user_index (user_id),
    INDEX inventory_id (inventory_id)
) ;

