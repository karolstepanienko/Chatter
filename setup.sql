-- Setup file
-- Creates database and necessary user used by backend app
CREATE USER 'springuser'@'%' IDENTIFIED BY 'ThePassword';
CREATE DATABASE chatter;
GRANT DELETE, INSERT, SELECT, UPDATE ON chatter.* TO 'springuser'@'%';
-- Bug fix for 8.0 version
SET GLOBAL time_zone = '+02:00';
FLUSH PRIVILEGES;