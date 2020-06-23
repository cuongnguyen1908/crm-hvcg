insert into account_type(name) value("Giám đốc chi nhánh");
insert into account_type(name) value("Nhân viên bán hàng");
insert into account_type(name) value("Nhân viên giao hàng");

insert into region(name, address) values("YeahStar", "Hồ Chí Minh");
insert into region(name, address) values("Apple", "New York");

insert into role(name) values("ROLE_USER");
insert into role(name) values("ROLE_ADMIN");
insert into role(name) values("ROLE_MODERATOR");

insert into task_priority(name) values("Hight");
insert into task_priority(name) values("Medium");
insert into task_priority(name) values("Low");

insert into task_status(name) values("Open");
insert into task_status(name) values("Close");
insert into task_status(name) values("In progess");

insert into customer(email, first_name, full_name, gender, last_name, phone) values("lamthanh@gmail.com", "Lam", "Nguyễn Lam Thanh", true, "Nguyễn", "0546746546");

insert into customer(email, first_name, full_name, gender, last_name, phone) values("namanh@gmail.com", "lam", "Nguyễn Nam Anh", true, "nguyen", "0879684798");

insert into employee_account(username,password,account_type_id) values("01564658", "123456", "1");

insert into employee_account(username,password,account_type_id) values("055464552", "123456", "2");

insert into employee(address,bank_account,bank_name,email,first_name,full_name,identity_number,last_name,phone,position,employee_account_id,region_id)
values("Hồ Chí Minh, Q1", "12345679","Vietcombank","duongnguyen@gmail.com", "Dương", "Nguyễn Lam Dương", "31235435135", "Nguyễn", "094654564", "Giám đốc", 1, 1);

insert into employee(address,bank_account,bank_name,email,first_name,full_name,identity_number,last_name,phone,position,employee_account_id,region_id)
values("Hà Nội, Hai Bà Trưng", "4864646484","Techcombank","minhanh@gmail.com", "Minh Anh", "Nguyễn Minh Anh", "31235435135", "Nguyễn", "096468416", "Giao Hàng", 2, 2);

