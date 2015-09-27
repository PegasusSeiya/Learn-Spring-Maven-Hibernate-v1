SELECT * FROM tasks.user_roles;

Alter Table tasks.user_roles Drop Foreign Key fk_username;
Alter Table tasks.user_roles  
Add constraint fk_username 
	Foreign Key (username)
    References tasks.users(username)
On Update Cascade;


