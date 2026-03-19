EXEC sys.xp_instance_regwrite N'HKEY_LOCAL_MACHINE', N'Software\Microsoft\MSSQLServer\MSSQLServer', N'LoginMode', REG_DWORD, 2;
GO

CREATE DATABASE BookManager;
GO

USE BookManager;
GO

CREATE LOGIN BookManager WITH PASSWORD = 'BookStoremgmt-Secure_@_032026';
CREATE USER BookManager FOR LOGIN BookManager;
GO

ALTER ROLE db_owner ADD MEMBER BookManager;
GO