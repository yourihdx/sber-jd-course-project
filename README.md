# Курсовой проект

#### Перед началом работы создать БД:

docker run --rm -dit -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=creditsys postgres:13.2-alpine

#### Проверить что postgres доступна на локальной машине:

`pg_isready`
Ответ CLI:
`:5432 - принимает подключения`

#### Коннект к БД из CLI:

`psql creditsys postgres`
`\dt` - список таблиц

#### Применить скрипт liquibase:

liquibase --username=postgres --password=123 --changeLogFile create_tables.xml update