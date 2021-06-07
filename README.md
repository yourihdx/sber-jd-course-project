# Курсовой проект

#### Перед началом работы создать БД:

docker run --rm -dit -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=creditsys postgres:13.2-alpine

#### Проверить что postgres доступна на локальной машине:

`pg_isready`
Ответ CLI:
`:5432 - принимает подключения`

### Для информации:
###### Коннект к БД из CLI:

`psql creditsys postgres`

###### Просмотр списка таблиц

`\dt` - список таблиц

#### Применение скрипта liquibase из CLI: 
###### (применять не нужно, работает при запуске DemoApplication)

liquibase --username=postgres --password=123 --changeLogFile create_tables.xml update
