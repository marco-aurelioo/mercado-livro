CREATE TABLE book(
	id int auto_increment primary key,
    name varchar(255) not null,
    STATUS varchar(30) null,
    price double(8,2) null,
    customer_id int not null,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
        REFERENCES Customer(id)
);