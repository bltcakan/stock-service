CREATE TABLE customer (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(255) NOT NULL
);

CREATE TABLE asset (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       customer_id BIGINT NOT NULL,
                       asset_type VARCHAR(255) NOT NULL,
                       size DECIMAL(19, 2) NOT NULL,
                       usable_size DECIMAL(19, 2) NOT NULL,
                       version BIGINT,
                       created_date TIMESTAMP,
                       CONSTRAINT fk_asset_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE trade_order (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             customer_id BIGINT NOT NULL,
                             asset_id BIGINT NOT NULL,
                             side VARCHAR(10) NOT NULL,
                             size DECIMAL(19, 2) NOT NULL,
                             price DECIMAL(19, 2) NOT NULL,
                             status VARCHAR(255) NOT NULL,
                             version BIGINT,
                             created_date TIMESTAMP,
                             CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
                             CONSTRAINT fk_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
);