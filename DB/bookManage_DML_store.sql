DELIMITER //

-- 1.電話號碼查詢用戶
-- 輸入: p_phone_number用戶電話(CHAR)
-- 輸出: 一筆用戶資料
CREATE PROCEDURE findByNumber (IN p_phone_number CHAR(10))
       BEGIN
		SELECT * FROM `user` WHERE phone_number = p_phone_number;
       END//


-- 2.註冊用戶
-- 輸入: p_phone_number用戶電話(CHAR)、p_password用戶密碼(VARCHAR)、p_username用戶姓名(VARCHAR)
-- 輸出: 無
CREATE PROCEDURE register (
    IN p_phone_number CHAR(10),
	IN p_password   VARCHAR(100),
    IN p_username	VARCHAR(30))
       BEGIN
		INSERT INTO `user`(phone_number, `password`, username, registration_time) VALUES (p_phone_number, p_password, p_username, now());
       END//


-- 3.用戶登入更新時間
-- 輸入: p_phone_number用戶電話(CHAR)
-- 輸出: 無
CREATE PROCEDURE login (
    IN p_phone_number CHAR(10))
       BEGIN
		   UPDATE `user` SET last_login_time = now() WHERE phone_number = p_phone_number;
       END//
 
 
-- 4.查詢所有可借書 
-- 輸入: 無
-- 輸出: 所有狀態為在庫的書籍資料
CREATE PROCEDURE findAllBook ()
       BEGIN
          SELECT a.isbn, a.name, a.author, a.introduction, a.inventory_id FROM book a JOIN inventory b ON a.isbn = b.isbn WHERE b.status = 0;
       END//
 
 
-- 5.查詢某用戶目前借出書籍的紀錄
-- 輸入: p_user_id用戶編號(INT)
-- 輸出: 某用戶所有在借書籍的借閱紀錄
CREATE PROCEDURE findBorrowedBook (IN p_user_id INT)
       BEGIN
          SELECT * FROM borrowing_record WHERE user_id = p_user_id AND return_time = date('2000-01-01'); 
       END//
       
       
-- 6.查詢某本書的庫存狀態
-- 輸入: p_inventory_id庫存編號(INT)
-- 輸出: 一筆庫存資料
CREATE PROCEDURE findBookStat (IN p_inventory_id INT)
       BEGIN
          SELECT * FROM inventory WHERE inventory_id = p_inventory_id; 
       END//      
 
 
-- 7.查詢某本書籍的細節資訊
-- 輸入: p_inventory_id庫存編號(INT)
-- 輸出: 一筆書籍資料
CREATE PROCEDURE findBookDetail (IN p_inventory_id INT)
       BEGIN
          SELECT * FROM book WHERE inventory_id = p_inventory_id; 
       END//    
       
-- 8.更新書籍狀態並新增紀錄
-- 輸入: p_status欲修改的狀態(INT)、p_user_id用戶編號(INT)、p_inventory_id庫存編號(INT)
-- 欲修改的狀態 : 歸還p_status = 0 / 借出p_status = 1 / 整理p_status = 2 / 遺失p_status = 3 / 損毀p_status = 4 / 廢棄p_status = 5
-- 輸出: 無
CREATE PROCEDURE updateBookStat (
     IN p_status INT,
	 IN p_user_id INT,
     IN p_inventory_id INT)
       BEGIN
		  START TRANSACTION;
			UPDATE inventory SET `status` = p_status WHERE inventory_id = p_inventory_id;
           -- 借出
           IF p_status = 1 THEN
             INSERT INTO borrowing_record (user_id, inventory_id, borrowing_time, return_time) VALUES (p_user_id, p_inventory_id, now(), date('2000-01-01'));
           END IF;
           -- 歸還
		   IF p_status = 0 THEN
             UPDATE borrowing_record SET return_time = now() WHERE user_id = p_user_id AND inventory_id = p_inventory_id AND return_time = date('2000-01-01');
           END IF;
          COMMIT;		
       END//
