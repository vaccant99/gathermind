INSERT INTO member (member_id, nickname, password, created_at, email) VALUES
('member1', 'nickname1', 'password1', CURRENT_TIMESTAMP, 'email1@example.com'),
('member2', 'nickname2', 'password2', CURRENT_TIMESTAMP, 'email2@example.com'),
('member3', 'nickname3', 'password3', CURRENT_TIMESTAMP, 'email3@example.com'),
('member4', 'nickname4', 'password4', CURRENT_TIMESTAMP, 'email4@example.com'),
('member5', 'nickname5', 'password5', CURRENT_TIMESTAMP, 'email5@example.com'),
('member6', 'nickname6', 'password6', CURRENT_TIMESTAMP, 'email6@example.com'),
('member7', 'nickname7', 'password7', CURRENT_TIMESTAMP, 'email7@example.com'),
('member8', 'nickname8', 'password8', CURRENT_TIMESTAMP, 'email8@example.com'),
('member9', 'nickname9', 'password9', CURRENT_TIMESTAMP, 'email9@example.com'),
('member10', 'nickname10', 'password10', CURRENT_TIMESTAMP, 'email10@example.com'),
('member11', 'nickname11', 'password11', CURRENT_TIMESTAMP, 'email11@example.com'),
('member12', 'nickname12', 'password12', CURRENT_TIMESTAMP, 'email12@example.com'),
('member13', 'nickname13', 'password13', CURRENT_TIMESTAMP, 'email13@example.com'),
('member14', 'nickname14', 'password14', CURRENT_TIMESTAMP, 'email14@example.com'),
('member15', 'nickname15', 'password15', CURRENT_TIMESTAMP, 'email15@example.com');


INSERT INTO study (study_id, title, description, status, created_at) VALUES
(1, 'Study Title 1', 'Description for Study 1', 'Active', CURRENT_TIMESTAMP),
(2, 'Study Title 2', 'Description for Study 2', 'Active', CURRENT_TIMESTAMP),
(3, 'Study Title 3', 'Description for Study 3', 'Completed', CURRENT_TIMESTAMP),
(4, 'Study Title 4', 'Description for Study 4', 'Active', CURRENT_TIMESTAMP),
(5, 'Study Title 5', 'Description for Study 5', 'Active', CURRENT_TIMESTAMP),
(6, 'Study Title 6', 'Description for Study 6', 'Inactive', CURRENT_TIMESTAMP),
(7, 'Study Title 7', 'Description for Study 7', 'Active', CURRENT_TIMESTAMP),
(8, 'Study Title 8', 'Description for Study 8', 'Completed', CURRENT_TIMESTAMP),
(9, 'Study Title 9', 'Description for Study 9', 'Inactive', CURRENT_TIMESTAMP),
(10, 'Study Title 10', 'Description for Study 10', 'Active', CURRENT_TIMESTAMP),
(11, 'Study Title 11', 'Description for Study 11', 'Completed', CURRENT_TIMESTAMP),
(12, 'Study Title 12', 'Description for Study 12', 'Inactive', CURRENT_TIMESTAMP),
(13, 'Study Title 13', 'Description for Study 13', 'Active', CURRENT_TIMESTAMP),
(14, 'Study Title 14', 'Description for Study 14', 'Completed', CURRENT_TIMESTAMP),
(15, 'Study Title 15', 'Description for Study 15', 'Inactive', CURRENT_TIMESTAMP);


INSERT INTO schedule (schedule_id, title, description, date_time, location, created_at, study_id, member_id) VALUES
(1, 'Schedule 1', 'Description 1', CURRENT_TIMESTAMP + INTERVAL '1' DAY, 'Location 1', CURRENT_TIMESTAMP, 1, 'member1'),
(2, 'Schedule 2', 'Description 2', CURRENT_TIMESTAMP + INTERVAL '2' DAY, 'Location 2', CURRENT_TIMESTAMP, 1, 'member1'),
(3, 'Schedule 3', 'Description 3', CURRENT_TIMESTAMP + INTERVAL '3' DAY, 'Location 3', CURRENT_TIMESTAMP, 1, 'member1'),
(4, 'Schedule 4', 'Description 4', CURRENT_TIMESTAMP + INTERVAL '4' DAY, 'Location 4', CURRENT_TIMESTAMP, 1, 'member1'),
(5, 'Schedule 5', 'Description 5', CURRENT_TIMESTAMP + INTERVAL '5' DAY, 'Location 5', CURRENT_TIMESTAMP, 1, 'member1'),
(6, 'Schedule 6', 'Description 6', CURRENT_TIMESTAMP + INTERVAL '6' DAY, 'Location 6', CURRENT_TIMESTAMP, 1, 'member1'),
(7, 'Schedule 7', 'Description 7', CURRENT_TIMESTAMP + INTERVAL '7' DAY, 'Location 7', CURRENT_TIMESTAMP, 2, 'member1'),
(8, 'Schedule 8', 'Description 8', CURRENT_TIMESTAMP + INTERVAL '8' DAY, 'Location 8', CURRENT_TIMESTAMP, 2, 'member1'),
(9, 'Schedule 9', 'Description 9', CURRENT_TIMESTAMP + INTERVAL '9' DAY, 'Location 9', CURRENT_TIMESTAMP, 2, 'member1'),
(10, 'Schedule 10', 'Description 10', CURRENT_TIMESTAMP + INTERVAL '10' DAY, 'Location 10', CURRENT_TIMESTAMP, 3, 'member1'),
(11, 'Schedule 11', 'Description 11', CURRENT_TIMESTAMP + INTERVAL '11' DAY, 'Location 11', CURRENT_TIMESTAMP, 3, 'member1'),
(12, 'Schedule 12', 'Description 12', CURRENT_TIMESTAMP + INTERVAL '12' DAY, 'Location 12', CURRENT_TIMESTAMP, 3, 'member1'),
(13, 'Schedule 13', 'Description 13', CURRENT_TIMESTAMP + INTERVAL '13' DAY, 'Location 13', CURRENT_TIMESTAMP, 3, 'member1'),
(14, 'Schedule 14', 'Description 14', CURRENT_TIMESTAMP + INTERVAL '14' DAY, 'Location 14', CURRENT_TIMESTAMP, 1, 'member1'),
(15, 'Schedule 15', 'Description 15', CURRENT_TIMESTAMP + INTERVAL '15' DAY, 'Location 15', CURRENT_TIMESTAMP, 1, 'member1');


INSERT INTO study_member (study_member_id, role, status, progress, joined_date, member_id, study_id) VALUES
(1, 'admin', 'Active', 'Progress 1', CURRENT_TIMESTAMP, 'member1', 1),
(2, 'Member', 'Inactive', 'Progress 2', CURRENT_TIMESTAMP, 'member2', 1),
(3, 'Member', 'Active', 'Progress 3', CURRENT_TIMESTAMP, 'member3', 1),
(4, 'admin', 'Active', 'Progress 4', CURRENT_TIMESTAMP, 'member4', 1),
(5, 'Member', 'Inactive', 'Progress 5', CURRENT_TIMESTAMP, 'member5', 1),
(6, 'admin', 'Active', 'Progress 6', CURRENT_TIMESTAMP, 'member6', 1),
(7, 'Member', 'Inactive', 'Progress 7', CURRENT_TIMESTAMP, 'member7', 1),
(8, 'Member', 'Active', 'Progress 8', CURRENT_TIMESTAMP, 'member8', 1),
(9, 'admin', 'Active', 'Progress 9', CURRENT_TIMESTAMP, 'member9', 1),
(10, 'Member', 'Inactive', 'Progress 10', CURRENT_TIMESTAMP, 'member10', 1),
(11, 'admin', 'Active', 'Progress 11', CURRENT_TIMESTAMP, 'member11', 1),
(12, 'Member', 'Inactive', 'Progress 12', CURRENT_TIMESTAMP, 'member12', 2),
(13, 'admin', 'Active', 'Progress 13', CURRENT_TIMESTAMP, 'member13', 3),
(14, 'Member', 'Inactive', 'Progress 14', CURRENT_TIMESTAMP, 'member14', 4),
(15, 'admin', 'Active', 'Progress 15', CURRENT_TIMESTAMP, 'member15', 5);

INSERT INTO question (question_id, content, title, option, created_at, study_member_id) VALUES
(1, 'content1', 'title1', '질문', CURRENT_TIMESTAMP + INTERVAL '1' DAY,1),
(2, 'content2', 'title2', '질문', CURRENT_TIMESTAMP + INTERVAL '2' DAY,2),
(3, 'content3', 'title3', '질문', CURRENT_TIMESTAMP+ INTERVAL '3' DAY,3),
(4, 'content4', 'title4', '질문', CURRENT_TIMESTAMP+ INTERVAL '4' DAY,4),
(5, 'content5', 'title5', '질문', CURRENT_TIMESTAMP+ INTERVAL '5' DAY,5),
(6, 'content6', 'title6', '질문', CURRENT_TIMESTAMP+ INTERVAL '6' DAY,6),
(7, 'content7', 'title7', '질문', CURRENT_TIMESTAMP+ INTERVAL '7' DAY,7),
(8, 'content8', 'title8', '질문', CURRENT_TIMESTAMP+ INTERVAL '8' DAY,8),
(9, 'content9', 'title9', '질문', CURRENT_TIMESTAMP+ INTERVAL '9' DAY,9),
(10, 'content10', 'title10', '질문', CURRENT_TIMESTAMP+ INTERVAL '10' DAY,10),
(11, 'content11', 'title11', '질문', CURRENT_TIMESTAMP+ INTERVAL '11' DAY,11),
(12, 'content12', 'title12', '질문', CURRENT_TIMESTAMP+ INTERVAL '12' DAY,12),
(13, 'content13', 'title13', '질문', CURRENT_TIMESTAMP+ INTERVAL '13' DAY,13),
(14, 'content14', 'title14', '질문', CURRENT_TIMESTAMP+ INTERVAL '14' DAY,14),
(15, 'content15', 'title15', '질문', CURRENT_TIMESTAMP+ INTERVAL '15' DAY,15);
