# Техническое задание для компании Solva
_____________
Примечание:
Так как я использовал ключ демо версии для доступа к API, то там возможны токо 5 запросов в минуту. Следует перед запуском расскоменетриовать класс CheckCurrency.java и изменить Cron операцию с ежесекундной на ежедневную

Использованный стек - Spring Framework, PostgreSQL, FlyWay.
Курсы валют берутся с https://www.alphavantage.co/

```
http://localhost:8080/account/add - добавление нового пользователя
http://localhost:8080/account/transfer - перевод денег от 1 пользователя ко 2
http://localhost:8080/account/ - вывод всех пользователей
http://localhost:8080/account/limit - вывод всех транзакций пользователя с превышением лимита
http://localhost:8080/account/changeLimit/{id} - изменение лимита у пользователя
```


## Формат отправки JSON-ов
Отправка JSON-а для создания нового пользователя
```
{
  "accountNumber": "kzt2",
  "balanceUsd": 100,
  "limitUsd": 100
}
```

Пример перевода с использованием form-data в Postman-е
![image](https://user-images.githubusercontent.com/73342068/205733009-dd12d2a3-064a-456d-94cb-a288a3369097.png)

Пример вывода всех транзакций пользователя с превышением месячного лимита
![image](https://user-images.githubusercontent.com/73342068/205733148-7c971a85-60f0-4afb-babf-bee8ddf51f62.png)

Изменение лимита пользователя
![image](https://user-images.githubusercontent.com/73342068/205733174-8f9d9efb-b862-4c69-a54e-b63fb3574275.png)
