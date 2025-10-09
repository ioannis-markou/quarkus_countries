CREATE TABLE countries (
                        id SERIAL PRIMARY KEY,
                        country_code TEXT NOT NULL,
                        common_name TEXT NOT NULL,
                        official_name TEXT NOT NULL
);

CREATE TABLE currencies (
                        id SERIAL PRIMARY KEY ,
                        name TEXT NOT NULL,
                        symbol TEXT NOT NULL,
                        currency_code TEXT
);

CREATE TABLE country_currency(
                        id SERIAL PRIMARY KEY ,
                        country_id INT NOT NULL,
                        currency_id INT NOT NULL,
                        FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE,
                        FOREIGN KEY (currency_id) REFERENCES currencies(id) ON DELETE  CASCADE
);
