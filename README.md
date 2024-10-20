CRM-система SHIFTLab
Описание проекта

Проект представляет собой упрощенную CRM-систему для управления информацией о продавцах и их транзакциях. 
Реализован набор RESTful API для выполнения операций с продавцами и транзакциями, включая создание, чтение, обновление и удаление данных.
Также присутствуют аналитические функции для вывода самого продуктивного продавца за определенный период и продавцов с транзакциями ниже заданной суммы.
Функциональность
Продавцы

    GET /api/sellers — Получить список всех продавцов.
    GET /api/sellers/{sellerId} — Получить информацию о конкретном продавце по его ID.
    POST /api/sellers/create — Создать нового продавца.
    PUT /api/sellers/{id} — Обновить данные продавца.
    DELETE /api/sellers/{id} — Удалить продавца.
    GET /api/sellers/best/day — Получить самого продуктивного продавца за указанный день.
    GET /api/sellers/best/month — Получить самого продуктивного продавца за указанный месяц.
    GET /api/sellers/best/year — Получить самого продуктивного продавца за указанный год.
    GET /api/sellers/best/quarter — Получить самого продуктивного продавца за указанный квартал.
    GET /api/sellers/losers — Получить список продавцов, у которых сумма всех транзакций за указанный период меньше указанной суммы.

Транзакции

    GET /api/transactions — Получить список всех транзакций.
    GET /api/transactions/{id} — Получить информацию о конкретной транзакции по ее ID.
    POST /api/transactions/create — Создать новую транзакцию.
    GET /api/sellers/transactions/{id} — Получить все транзакции конкретного продавца по его ID.

Структура данных

    Продавец (Seller)
        id: Уникальный идентификатор (Long)
        name: Имя продавца (String)
        contactInfo: Контактная информация (String)
        registrationDate: Дата регистрации (LocalDateTime)

    Транзакция (Transaction)
        id: Уникальный идентификатор (Long)
        seller: Ссылка на продавца (Seller)
        amount: Сумма транзакции (BigDecimal)
        paymentType: Тип оплаты (CASH, CARD, TRANSFER)
        transactionDate: Дата транзакции (LocalDateTime)

Примеры использования API
Получение всех продавцов

curl -X GET http://localhost:8080/api/sellers

Создание нового продавца

curl -X POST http://localhost:8080/api/sellers/create \
    -H "Content-Type: application/json" \
    -d '{"name": "John Doe", "contactInfo": "john@example.com"}'

Получение транзакций продавца

curl -X GET http://localhost:8080/api/sellers/transactions/1

База данных

Для работы проекта используется PostgreSQL. Для тестирования можно использовать базу данных H2 в режиме in-memory.
Настройки для PostgreSQL:

properties

spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Тестирование

Для выполнения тестов используйте команду:

./gradlew test

Зависимости

    Spring Boot: для создания REST API
    PostgreSQL: основная база данных
    H2: база данных для тестов
    Lombok: для сокращения шаблонного кода
    MapStruct: для маппинга сущностей
