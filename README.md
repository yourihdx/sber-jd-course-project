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

`\l` - список БД

#### Чтобы применить скрипт liquibase из CLI:
###### (применять не нужно, работает при сборке)
liquibase --username=postgres --password=123 --changeLogFile create_tables.xml update



#### Сборка для DOCKER
1) Во всех application поменять 
 - localhost на kafka для сервиса kafka (выполнено)
 - localhost на postgres БД (выполнено)
1.1) Прописать в hibernate.cfg.xml (выполнено)
<property name="hibernate.connection.url">
            jdbc:postgresql://postgres:5432/creditsys
</property>

!!! Внимание!
Если приложения работают локально, то необходимо или перенастроить все настройки обратно 
на localhost или в файле hosts прописать:
127.0.0.1	kafka
127.0.0.1       postgres
Если kafka запускается в docker, а приложение локально, то необходимо изменить сроку в 
docker-compose.yml :
KAFKA_ADVERTISED_HOST_NAME: kafka

2) Пересобрать проект

3) Если Kafka и postgres запущен локально - остановить postgres, kafka и zookeeper

4) Желательно перегрузить ПК. Проблема в том, порты могут быть заняты 
(или локальным приложением или, если kafka уже запущен в docker, отданы из docker наружу)

5) Проверить и удалить приложения в docker (вообще, docker обновляет образ, но лучше сделать):
bank_answer
credit_system
Для Docker Desktop  работы выполнятся в окне - сначала удалем контейнер, потом - образ
Порядок удаления в docker Toolkit(команды запускать в консоли (Windows или Docker).
Вместо имен контейнеров и образов можно использовать ID:

5.1) В консоли (Windows или Docker) выводим список контейнеров:
docker ps -a

5.2) Если контейнер есть и запущен - останавливаем
docker stop bank_answer

5.3) Удаляем контейнер:
docker rm bank_answer

5.4) Находим название образа:
docker images

5.5) Удаляем образ:
docker rmi sber-jd-course-project_bank_answer

6) Собираем новые образы и запускаем. В папке с docker-compose.yml выполняем:
docker-compose up -d

Проверить работу внутри контейнера:
- Docker Desktop: двойной щелчок по имени контейнера
- Docker Toolkit: Запустить Docker Quickstart Terminal и выполнить 
docker attach bank_answer