CREATE TABLE employee (
	id int IDENTITY(0,1) NOT NULL,
	first_name varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	last_name varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	email varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	password_hash varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT employee_PK PRIMARY KEY (id)
);


CREATE TABLE room_category (
	id int IDENTITY(1,1) NOT NULL,
	name text COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	description text COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	pricePerMonth decimal(18,0) NOT NULL,
	PricePerMonthForInternet decimal(18,0) NOT NULL,
	pricePerMonthForExtraTenant decimal(18,0) NOT NULL,
	maxTenants int NOT NULL,
	leaveNoticeDays int NOT NULL,
	CONSTRAINT PK__room_cat__3213E83F50D413E2 PRIMARY KEY (id)
);


CREATE TABLE study_proof (
	id int IDENTITY(0,1) NOT NULL,
	CONSTRAINT study_proof_PK PRIMARY KEY (id)
);


CREATE TABLE room (
	id int IDENTITY(1,1) NOT NULL,
	is_out_of_service bit NOT NULL,
	room_category_id int NOT NULL,
	CONSTRAINT PK__room__3213E83F7C875DF9 PRIMARY KEY (id),
	CONSTRAINT FK__room__room_categ__43D61337 FOREIGN KEY (room_category_id) REFERENCES room_category(id)
);


CREATE TABLE tenant (
	id int IDENTITY(1,1) NOT NULL,
	first_name text COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	last_name text COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	email text COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	phone text COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	study_proof_id int NULL,
	CONSTRAINT PK__tenant__3213E83F8D8BD701 PRIMARY KEY (id),
	CONSTRAINT FK__tenant__study_pr__4D5F7D71 FOREIGN KEY (study_proof_id) REFERENCES study_proof(id)
);


CREATE TABLE contract (
	id int IDENTITY(0,1) NOT NULL,
	include_internet bit NOT NULL,
	room_id int NOT NULL,
	start_date date NOT NULL,
	CONSTRAINT contract_PK2 PRIMARY KEY (id),
	CONSTRAINT contract_FK2 FOREIGN KEY (room_id) REFERENCES room(id)
);


CREATE TABLE tenant_contract (
	id int IDENTITY(0,1) NOT NULL,
	contract_id int NOT NULL,
	tenant_id int NOT NULL,
	CONSTRAINT contract_PK PRIMARY KEY (id),
	CONSTRAINT contract_FK_1 FOREIGN KEY (tenant_id) REFERENCES tenant(id),
	CONSTRAINT tenant_contract_FK FOREIGN KEY (contract_id) REFERENCES contract(id)
);
