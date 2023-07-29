CREATE TABLE customer_role(
	customer_id int not null,
    role varchar(100) not null,
    CONSTRAINT fk_customer_role
            FOREIGN KEY (customer_id)
            REFERENCES Customer(id)
);