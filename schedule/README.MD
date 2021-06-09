Приложение предоставляет возможность рассчитать график платежей, исходя из заданных параметров.

Приложение имеет WEB интерфейс, который находится по url "schedule/show".

Также приложение имеет REST контроллер, который выдает: 

1. график платежей в формате JSON.
Для получения JSON нужно перейти по url "schedule/api". Также в url необходимо указать параметры:
"creditAmount" - Сумма кредита
"percentRate" - Процентная ставка
"creditTerm" - Срок
"annuityPayment" - true/false Аннуитетный платеж, либо нет.

Пример запроса при размещении приложения на localhost 
"http://localhost:8080/schedule/api/?creditAmount=100000&percentRate=9&creditTerm=10&annuityPayment=true"

Ответ на такой запрос будет выглядеть в формате 
{"percentRate":9,"creditTerm":10,"creditAmount":100000,"begin":[2021,6,9,8,36,10,363000000],
"payments":[
{"paymentDate":[2021,7,9,8,36,10,363000000],"principal":9677.39,"percent":739.73,"payment":10417.12,"balance":90322.61},
{"paymentDate":[2021,8,9,8,36,10,363000000],"principal":9726.71,"percent":690.41,"payment":10417.12,"balance":80595.90},
{"paymentDate":[2021,9,9,8,36,10,363000000],"principal":9801.06,"percent":616.06,"payment":10417.12,"balance":70794.84},
{"paymentDate":[2021,10,9,8,36,10,363000000],"principal":9893.43,"percent":523.69,"payment":10417.12,"balance":60901.41},
{"paymentDate":[2021,11,9,8,36,10,363000000],"principal":9951.60,"percent":465.52,"payment":10417.12,"balance":50949.81},
{"paymentDate":[2021,12,9,8,36,10,363000000],"principal":10040.23,"percent":376.89,"payment":10417.12,"balance":40909.58},
{"paymentDate":[2022,1,9,8,36,10,363000000],"principal":10104.41,"percent":312.71,"payment":10417.12,"balance":30805.17},
{"paymentDate":[2022,2,9,8,36,10,363000000],"principal":10181.65,"percent":235.47,"payment":10417.12,"balance":20623.52},
{"paymentDate":[2022,3,9,8,36,10,363000000],"principal":10274.73,"percent":142.39,"payment":10417.12,"balance":10348.79},
{"paymentDate":[2022,4,9,8,36,10,363000000],"principal":10348.79,"percent":79.10,"payment":10427.89,"balance":0.00}],
"annuityPayment":true}

2. Основные параметры кредита: общая переплата по процентам и размер ежемесячного платежа
   Для получения JSON нужно перейти по url "schedule/api/param". Также в url необходимо указать параметры:
   "creditAmount" - Сумма кредита
   "percentRate" - Процентная ставка
   "creditTerm" - Срок
   "annuityPayment" - true/false Аннуитетный платеж, либо нет.

Пример запроса при размещении приложения на localhost
"http://localhost:8080/schedule/api/param/?creditAmount=100000&percentRate=9&creditTerm=10&annuityPayment=true"

Ответ на такой запрос будет выглядеть в формате:
{"totalPercent":4181.97,"monthPayment":10417.12}
