/*角色表初始化数据*/
INSERT INTO fx_role VALUES((SELECT REPLACE(UUID(), '-', '')),'ROLE_ADMIN','核心管理员');
INSERT INTO fx_role VALUES((SELECT REPLACE(UUID(), '-', '')),'ROLE_DBA','数据库管理员');
INSERT INTO fx_role VALUES((SELECT REPLACE(UUID(), '-', '')),'ROLE_USER','普通用户：');
INSERT INTO fx_role VALUES((SELECT REPLACE(UUID(), '-', '')),'ROLE_VISITOR','游客：只注册了账户，未绑定银行卡');