DROP TABLE if EXISTS products CASCADE;
DROP sequence if EXISTS products_seq;
CREATE sequence products_seq start WITH 1 increment by 50;
CREATE TABLE products (price float(53), tax_rate float(53), id bigint NOT NULL, description varchar(255), name varchar(255), sku varchar(255) UNIQUE, PRIMARY KEY (id));

INSERT INTO products (id,sku,name,description,price,tax_rate)
VALUES
  (1,5223,'#ea9c9a','purus mauris a nunc. In at pede. Cras vulputate velit',34,3),
  (2,8192,'#70eace','NULLam feugiat placerat velit. Quisque varius. Nam porttitor',39,2),
  (3,5050,'#f7f25b','Fusce aliquet magna a neque. NULLam ut',103,6);