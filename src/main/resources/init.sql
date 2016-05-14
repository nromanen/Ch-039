INSERT INTO role (id, type) VALUES (1, 'ADMIN');
INSERT INTO role (id, type) VALUES (2, 'PATIENT');
INSERT INTO role (id, type) VALUES (3, 'MANAGER');
INSERT INTO role (id, type) VALUES (4, 'DOCTOR');

INSERT INTO USERS (id, email, enabled, password) VALUES (1, 'andrew@gmail.com',
true, 'andrew');
INSERT INTO USERS (id, email, enabled, password) VALUES (2, 'andrew2@gmail.com',
true, 'andrew');

INSERT INTO USERS (id, email, enabled, password) VALUES (3, 'andrew3@gmail.com',
true, 'andrew');

INSERT INTO role_users (users_id, role_id) VALUES (1, 1);
INSERT INTO role_users (users_id, role_id) VALUES (1, 3);
INSERT INTO role_users (users_id, role_id) VALUES (2, 2);
INSERT INTO role_users (users_id, role_id) VALUES (3, 4);

