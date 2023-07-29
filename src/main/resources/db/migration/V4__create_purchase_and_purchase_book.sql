CREATE TABLE purchase(
	id int auto_increment primary key,
    customer_id Int not null,
    nfe varchar(100) null,
    price double(8,2) null,
    create_at datetime not null,
    CONSTRAINT fk_customer_purchase
        FOREIGN KEY (customer_id)
        REFERENCES Customer(id)
);

CREATE TABLE purchase_book(
	purchase_id Int not null ,
    book_id Int not null,
    CONSTRAINT fk_customer_purchase_book
        FOREIGN KEY (purchase_id)
        REFERENCES Purchase(id),
    CONSTRAINT fk_book_purchase_book
        FOREIGN KEY (book_id)
        REFERENCES Book(id),
    PRIMARY key (purchase_id,book_id)
);