# Гарипов Даниил Максимович, РИМ-250950
# Поликлиника

Учебный проект по дисциплине «Базы данных». REST API для управления отделениями, врачами, пациентами, медицинскими картами и приёмами в поликлинике.

## Технологический стек

- Java 21
- Spring Boot 4.2.0
- Spring Data JPA / Hibernate
- PostgreSQL 17
- Maven
- Docker Compose

## Структура проекта

```text
src/main/java/garipov/database/clinic/
├── controller/       # REST-контроллеры
├── dto/
│   ├── request/      # DTO входящих запросов
│   └── response/     # DTO ответов
├── entity/
│   └── enums/        # Перечисления
├── exception/        # Обработка ошибок
├── repository/       # Репозитории Spring Data JPA
└── service/          # Бизнес-логика и транзакции
```

## Запуск проекта

Для запуска необходимы Docker и Docker Compose.

1. Склонировать репозиторий:

```bash
git clone <repository-url>
cd clinic
```

2. Собрать и запустить приложение вместе с PostgreSQL:

```bash
docker compose up --build
```

После запуска:
- Spring Boot приложение: `http://localhost:8080`
- PostgreSQL: `localhost:5432`
- База данных: `clinic`
- Пользователь: `clinic`
- Пароль: `clinic`

Для остановки:

```bash
docker compose down
```

Для остановки с удалением данных PostgreSQL:

```bash
docker compose down -v
```

## Модель данных

В проекте реализованы пять сущностей.

| Сущность | Назначение |
|---|---|
| Department | Отделение поликлиники |
| Doctor | Врач |
| Patient | Пациент |
| MedicalRecord | Медицинская карта пациента |
| Appointment | Приём пациента врачом |

### Связи между сущностями

- **Department — Doctor (1:N).** Одно отделение может содержать нескольких врачей. Владеющая сторона — `Doctor`, обратная — `Department`.
- **Doctor — Patient (N:M).** Связь реализована через промежуточную сущность `Appointment`, содержащую дату приёма, диагноз, примечания и статус.
- **Patient — MedicalRecord (1:1).** У пациента может быть одна медицинская карта.

## REST API

Все запросы выполняются относительно `http://localhost:8080`.

### Отделения

| Метод | Endpoint | Описание |
|---|---|---|
| GET | `/api/departments` | Получить все отделения |
| GET | `/api/departments/{id}` | Получить отделение |
| POST | `/api/departments` | Создать отделение |
| PUT | `/api/departments/{id}` | Обновить отделение |
| DELETE | `/api/departments/{id}` | Удалить отделение |

Пример создания:

```json
{
  "name": "Терапевтическое",
  "floor": 2,
  "description": "Терапевтическое отделение"
}
```

### Врачи

| Метод | Endpoint | Описание |
|---|---|---|
| GET | `/api/doctors` | Получить всех врачей |
| GET | `/api/doctors/{id}` | Получить врача |
| GET | `/api/doctors/department/{departmentId}` | Получить всех врачей подразделениея |
| POST | `/api/doctors` | Создать врача |
| PUT | `/api/doctors/{id}/department?departmentId=1` | Перевести врача в другое отделение |
| DELETE | `/api/doctors/{id}` | Удалить врача |

Пример создания:

```json
{
  "firstName": "Иван",
  "lastName": "Иванов",
  "specialization": "Терапевт",
  "experienceYears": 5,
  "departmentId": 1
}
```

### Пациенты

| Метод | Endpoint | Описание |
|---|---|---|
| GET | `/api/patients` | Получить всех пациентов |
| GET | `/api/patients/{id}` | Получить пациента |
| GET | `/api/patients/search?lastName=Иванов` | Поиск по фамилии |
| GET | `/api/patients/phone/{phone}` | Поиск по телефону |
| POST | `/api/patients` | Создать пациента |
| DELETE | `/api/patients/{id}` | Удалить пациента |

Пример создания:

```json
{
  "firstName": "Пётр",
  "lastName": "Петров",
  "birthDate": "2000-05-15",
  "phone": "+79991234567"
}
```

### Медицинские карты

| Метод | Endpoint | Описание |
|---|---|---|
| GET | `/api/medical-records/{id}` | Получить медицинскую карту |
| GET | `/api/medical-records/number/{number}` | Получить карту по номеру |
| GET | `/api/medical-records/patient/{patientId}` | Получить карту пациента |
| POST | `/api/medical-records` | Создать медицинскую карту |
| PUT | `/api/medical-records/{id}` | Обновить медицинскую карту |
| DELETE | `/api/medical-records/{id}` | Удалить медицинскую карту |

Пример создания:

```json
{
  "number": "MC-001",
  "medicalHistory": "Первичный осмотр",
  "patientId": 1
}
```

### Приёмы

| Метод | Endpoint | Описание |
|---|---|---|
| GET | `/api/appointments` | Получить все приёмы |
| GET | `/api/appointments/{id}` | Получить приём с данными врача и пациента |
| GET | `/api/appointments/search` | Поиск приёмов с фильтрацией |
| POST | `/api/appointments` | Создать приём |
| PATCH | `/api/appointments/{id}/status?status=COMPLETED` | Изменить статус приёма |
| DELETE | `/api/appointments/{id}` | Удалить приём |

Пример создания:

```json
{
  "appointmentDate": "2026-11-25T14:30:00",
  "diagnosis": "ОРВИ",
  "notes": "Повторный осмотр через неделю",
  "status": "SCHEDULED",
  "doctorId": 1,
  "patientId": 1
}
```

Для поиска приёмов поддерживаются необязательные параметры:

- `patientId` — идентификатор пациента;
- `doctorId` — идентификатор врача;
- `status` — статус приёма.

Например:

```http
GET /api/appointments/search?doctorId=1&status=SCHEDULED
```

Параметры можно комбинировать или передавать по отдельности.

## Проверка требований задания

| Пункт задания | Реализация |
|---|---|
| Сущность «Отделение» | `Department` |
| Сущность «Врач» | `Doctor` |
| Сущность «Пациент» | `Patient` |
| Сущность «Медицинская карта» | `MedicalRecord` |
| Сущность «Приём» | `Appointment` |
| Связь 1:N | `Department.doctors`, `Doctor.department` |
| Связь N:M через сущность-связку | `Appointment.doctor`, `Appointment.patient` |
| Связь 1:1 | `Patient.medicalRecord`, `MedicalRecord.patient` |
| JpaRepository для каждой сущности | `DepartmentRepository`, `DoctorRepository`, `PatientRepository`, `MedicalRecordRepository`, `AppointmentRepository` |
| Derived query | `PatientRepository.findByLastNameContainingIgnoreCase()` |
| Native SQL | `PatientRepository.findByPhoneNative()` |
| JPQL | `AppointmentRepository.findByIdWithDetails()` |
| Транзакции на уровне сервисов | `@Transactional` в классах `service` |
| Criteria API | `AppointmentCriteriaRepositoryImpl.findByFilter()` |
| REST API | Классы `controller` |
| DTO | Пакеты `dto/request` и `dto/response` |
| PostgreSQL в Docker | `docker-compose.yaml` |

## Дополнительные требования (*)

| Пункт | Реализация |
|---|---|
| Все связи LAZY | `fetch = FetchType.LAZY` в сущностях |
| JOIN FETCH | `AppointmentRepository.findByIdWithDetails()` |
| Точечные каскады PERSIST / MERGE | Связь `Patient.medicalRecord` |
| orphanRemoval | Связь `Patient.medicalRecord` |
| Синхронизация сторон связей | Вспомогательные методы `addDoctor()` и `addAppointment()` |
| SEQUENCE | Генерация ID в `BaseEntity` |
| Оптимистическая блокировка | `Appointment.version` с `@Version` |
| equals/hashCode без коллекций | Реализованы в `BaseEntity` по типу сущности и ID |
| DTO вместо Entity в REST API | Request- и Response DTO |
| open-in-view: false | `application.yml` |
| readOnly = true | На сервисах для методов чтения |
| Таблица самопроверки | Данный раздел README |

## Обработка ошибок

Глобальная обработка ошибок реализована в `GlobalExceptionHandler` с помощью `@RestControllerAdvice`.

| Ситуация | HTTP-статус |
|---|---|
| Сущность не найдена | 404 Not Found |
| Конфликт состояния | 409 Conflict |
| Ошибка валидации входных данных | 400 Bad Request |

