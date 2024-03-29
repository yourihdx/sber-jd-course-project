insert into bank (bank_name) values 
('Банк ВТБ'),
('ГАЗПРОМБАНК'),
('Райффайзенбанк'),
('Почта Банк'),
('ПСБ'),
('Открытие'),
('ПСБ'),
('Совкомбанк'),
('Хоум Кредит Банк'),
('УБРиР'),
('МТС Банк'),
('ПСБ'),
('Ренессанс Кредит'),
('Ситибанк'),
('РГСБАНК'),
('Металлинвестбанк'),
('ПСБ'),
('ЛокоБанк'),
('Банк Восточный'),
('СКБ-банк'),
('АТБ'),
('Экспобанк');
GO;
insert into client(full_name, birth_date, phone_number, email, passport_series_num, login, password) values
('Иванов Иван Иванович', '1978-03-15', '7-495-123-45-55', 'ivanov@example.com', '7707323232', '1', '1'),
('Петров Петр Петрович', '1986-12-08', '7-495-123-77-00', 'petrov@mail.example.com', '7707555001', '2', '2'),
('Сергеева Оксана Владимировна', '1992-06-20', '7-495-444-55-23', 'oxannas@mail.ru', '4310123050', '3', '3');
GO;
insert into dict_product_type(id, short_description) values
(1, 'Аннуитетные платежи'),
(2, 'Дифференцированные платежи');
GO;
insert into loan (bank_id, min_percent_rate, max_percent_rate, max_period, max_sum, product_type_id) values
(1, 0.064, 0.164, 84, 5000000, 2),
(2, 0.059, 0.159, 84, 5000000, 1),
(3, 0.0799, 0.1799, 60, 2000000, 1),
(4, 0.059, 0.159, 60, 4000000, 2),
(5, 0.068, 0.168, 84, 3000000, 2),
(6, 0.069, 0.169, 60, 5000000, 1),
(7, 0.068, 0.168, 84, 3000000, 1),
(8, 0, 0.1, 60, 1000000, 1),
(9, 0.079, 0.179, 84, 1000000, 1),
(10, 0.065, 0.165, 120, 5000000, 2),
(11, 0.069, 0.169, 60, 5000000, 2),
(12, 0.068, 0.168, 84, 3000000, 2),
(13, 0.085, 0.185, 60, 700000, 2),
(14, 0.065, 0.165, 60, 2500000, 1),
(15, 0.0799, 0.1799, 60, 3000000, 2),
(16, 0.068, 0.128, 84, 2000000, 2),
(17, 0.068, 0.128, 84, 3000000, 1),
(18, 0.07, 0.13, 84, 3000000, 1),
(19, 0.09, 0.18, 60, 1500000, 2),
(20, 0.07, 0.12, 60, 1500000, 1),
(21, 0.088, 0.158, 60, 1000000, 1),
(22, 0.089, 0.179, 60, 2000000, 2);
GO;
insert into dict_loan_offer_status(id, short_description) values
(0, 'Отказ'),
(1, 'Одобрение'),
(2, 'Заявка на рассмотрении в банке'),
(3, 'Заявка на рассмотрении у клиента');
GO;
