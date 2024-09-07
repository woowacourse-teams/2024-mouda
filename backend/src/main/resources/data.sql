INSERT INTO darakbang (name, code)
VALUES ('테스트용 다락방', 'MOUDA');

INSERT INTO member (nickname, kakao_id)
VALUES ('안나', 1);

INSERT INTO darakbang_member (darakbang_id, member_id, nickname, role)
VALUES (1, 1, '안나', 'MANAGER');

INSERT INTO moim(title, date, time, place, max_people, description, moim_status, is_chat_opened, darakbang_id)
VALUES ('제목', '2024-10-01', '11:11', '장소', 2, 'description', 'MOIMING', true, 1);

INSERT INTO chamyo(moim_id, darakbang_member_id, moim_role, last_read_chat_id)
VALUES (1, 1, 'MOIMER', 1);