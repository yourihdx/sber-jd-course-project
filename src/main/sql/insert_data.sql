insert into bank (bank_name) values 
('Банк ВТБ'),
('ГАЗПРОМБАНК'),
('Райффайзенбанка'),
('Почта Банка'),
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
insert into loan (bank_id, percent_rate, max_period, max_sum) values
(1, 0.064, 84, 5000000),
(2, 0.059, 84, 5000000),
(3, 0.0799, 60, 2000000),
(4, 0.059, 60, 4000000),
(5, 0.068, 84, 3000000),
(6, 0.069, 60, 5000000),
(7, 0.068, 84, 3000000),
(8, 0, 60, 1000000),
(9, 0.079, 84, 1000000),
(10, 0.065, 120, 5000000),
(11, 0.069, 60, 5000000),
(12, 0.068, 84, 3000000),
(13, 0.085, 60, 700000),
(14, 0.065, 60, 2500000),
(15, 0.0799, 60, 3000000),
(16, 0.068, 84, 2000000),
(17, 0.068, 84, 3000000),
(18, 0.07, 84, 3000000),
(19, 0.09, 60, 1500000),
(20, 0.07, 60, 1500000),
(21, 0.088, 60, 1000000),
(22, 0.089, 60, 2000000);
GO;
insert into client(full_name, birth_date, phone_number, email, passport_series_num) values
('Банк Восточный', '1978-03-15', '7-495-123-45-55', 'ivanov@example.com', '7707323232');
GO;
