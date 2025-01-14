INSERT INTO user_security (userEmail, userPassword, enabled)
VALUES ('admin@admin.ca', '$2a$12$G6YnERP06QFViUSrvVCDmekNbXezYJjP.J.EZcON8kz4SSAmz38IG', 1);

INSERT INTO user_security (userEmail, userPassword, enabled)
VALUES ('user@user.ca', '$2a$12$xvX3ZPVoYgtJGCqAZSRQYuW9//kHsFLLMT/Gt5mgnK8FBs2pZuKlm', 1);

INSERT INTO user_security (userEmail, userPassword, enabled)
VALUES ('guest@guest.ca', '$2a$12$3NK0Ul67fUU4gqDBf.Ecee66DVNa73LwxK66PjZ2XEpldWvi1E9nS', 1);

INSERT INTO role_security (roleName)
VALUES ('ROLE_ADMIN');

INSERT INTO role_security (roleName)
VALUES ('ROLE_USER');

INSERT INTO role_security (roleName)
VALUES ('ROLE_GUEST');

INSERT INTO user_role (userId, roleId)
VALUES (1, 1);
INSERT INTO user_role (userId, roleId)
VALUES (2, 2);
INSERT INTO user_role (userId, roleId)
VALUES (3, 3);