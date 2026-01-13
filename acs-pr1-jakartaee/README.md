
# Практическая работа №1 — Приложение с типовой архитектурой JakartaEE

**Дисциплина:** Архитектура корпоративных систем  

**Команда:** FSIS (Журавлев Н.С. и Асташин С.В.)

**Группа:** 6133-010402D 

## Описание

Веб-приложение на Jakarta EE с типовой архитектурой (3 слоя):

- **Data layer (слой данных):** JPA Entities (EclipseLink) + PostgreSQL  
- **Business layer (бизнес-логика):** EJB (Session Beans) для CRUD операций  
- **Presentation layer (представление):** JSF для взаимодействия с пользователем  

Функционал:

- Просмотр, добавление, редактирование и удаление записей (CRUD) для сущностей:
  - `categories` (Категория)
  - `jewelry` (Украшение), привязанные к автору (FK `category_id`)

---

## Как запустить приложение

### 1. Быстрый запуск с использованием Docker 

#### Требования

- Docker и Docker Compose (если не установлен, установите [Docker](https://www.docker.com/get-started)).

#### Шаги

1. В корне проекта, где находится файл `docker-compose.yml`, откройте терминал.
2. Запустите контейнеры с помощью команды:

   ```bash
   docker compose up --build
   ```

Эта команда:

- соберет все необходимые образы;
- запустит контейнеры для приложения и базы данных PostgreSQL.

Ожидайте несколько минут, пока контейнеры запустятся. После этого приложение будет доступно.

### Доступ к приложению

**JSF приложение:**

Откройте браузер и перейдите по адресу:  
`http://localhost:8080/lab/`

- Страница Categories: `http://localhost:8080/lab/categories.xhtml`
- Страница Jewelry: `http://localhost:8080/lab/jewelry.xhtml`

**Административная панель GlassFish:**

Если нужно зайти в административную консоль GlassFish для управления приложением, откройте:  
`https://localhost:8081/`

- Логин: `admin`
- Пароль: `admin123`

### Работа с базой данных

- PostgreSQL будет автоматически развернут в контейнере.
- Для инициализации базы данных используется скрипт: `docker/entrypoint.sh`.
- При первом запуске будет создана база данных и заполнены тестовыми данными.

---

### 2. Запуск без Docker (локально)

#### Требования

- JDK 17 или выше (скачать можно с официального сайта)
- Maven 3.9+ для сборки проекта (можно скачать с сайта Maven)
- PostgreSQL (если не установлен, скачайте с официального сайта PostgreSQL)
- GlassFish 7.x (скачать с сайта GlassFish)

#### Шаги для запуска

##### 1) Создание базы данных и пользователя в PostgreSQL

Запустите PostgreSQL и создайте базу данных с пользователем. Например:

```sql
CREATE DATABASE lab_db;
CREATE USER lab_user WITH PASSWORD 'lab_pass';
GRANT ALL PRIVILEGES ON DATABASE lab_db TO lab_user;
```

##### 2) Настройка приложения

В файле `src/main/resources/META-INF/persistence.xml` укажите настройки для подключения к базе данных:

```xml
<persistence-unit name="labPU">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <jta-data-source>jdbc/labDS</jta-data-source>
    ...
</persistence-unit>
```

##### 3) Сборка проекта

Перейдите в корень проекта и выполните команду:

```bash
mvn clean package
```

Это создаст WAR файл в директории `target/`.

##### 4) Развертывание на GlassFish

Запустите GlassFish сервер:

```bash
asadmin start-domain
```

Разверните WAR файл:

```bash
asadmin deploy target/acs-pr1-jakartaee.war
```

##### 5) Открытие приложения

После развертывания приложения GlassFish автоматически откроет его на порту 8080. Перейдите в браузер:

**JSF приложение:**  
`http://localhost:8080/lab/`

**Админ-консоль GlassFish:**  
`https://localhost:4848/`

- Логин: `admin`
- Пароль: `admin123`

---

### 3. Переменные окружения (Docker)

В файле `docker-compose.yml` можно настроить следующие переменные окружения:

- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` — настройки для подключения к базе данных PostgreSQL
- `APP_CONTEXT` — контекст приложения, по умолчанию `lab`
- `ADMIN_USER`, `ADMIN_PASS` — настройки для входа в административную консоль GlassFish (по умолчанию `admin/admin123`)

---

### 4. Структура проекта

- `src/main/java/...` — бизнес-логика, EJB-сервисы, управляемые бины
- `src/main/webapp/` — страницы JSF
- `src/main/resources/META-INF/persistence.xml` — настройки JPA
- `docker/entrypoint.sh` — скрипт инициализации GlassFish в Docker контейнере
- `Dockerfile`, `docker-compose.yml` — файлы для запуска приложения и базы данных в Docker
- `target/` — скомпилированный WAR файл приложения

---

### 5. Проблемы и решения

#### Если порт 8080 занят другим приложением

Поменяйте проброс портов в файле `docker-compose.yml`:

```yaml
ports:
  - "8081:8080"  # Пример, замените на свободный порт
```

#### Если не удается подключиться к админке GlassFish

Проверьте, что GlassFish действительно запущен и правильно настроен на `localhost:4848`.
