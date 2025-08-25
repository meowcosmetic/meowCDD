# Child Development Service

Service quản lý thông tin trẻ em có rối loạn phát triển, sử dụng Spring Boot với MongoDB và Neon PostgreSQL.

## Công nghệ sử dụng

- **Spring Boot 3.2.0**
- **Java 17**
- **MongoDB** - Lưu trữ thông tin chi tiết về rối loạn phát triển
- **Neon PostgreSQL** - Lưu trữ thông tin cơ bản về phụ huynh và trẻ em
- **Spring Data JPA** - ORM cho Neon PostgreSQL
- **Spring Data MongoDB** - ODM cho MongoDB
- **Lombok** - Giảm boilerplate code
- **Validation** - Kiểm tra dữ liệu đầu vào

## Cấu trúc Database

### Neon PostgreSQL (JPA)
- **Parent** - Thông tin phụ huynh
- **Child** - Thông tin cơ bản của trẻ em

### MongoDB
- **DevelopmentDisorder** - Thông tin chi tiết về rối loạn phát triển
- **ProgressReport** - Báo cáo tiến độ phát triển
- **DevelopmentalDisorderQuestion** - Câu hỏi sàng lọc rối loạn phát triển
- **AssessmentTest** - Bài test chuyên sâu để đánh giá rối loạn phát triển

## Cài đặt và chạy

### Yêu cầu hệ thống
- Java 17+
- Maven 3.6+
- Neon PostgreSQL (cloud database)
- MongoDB 4.4+

### Cấu hình Database

1. **Neon PostgreSQL**
   - Tạo database trên Neon.tech
   - Cập nhật thông tin kết nối trong `application.properties`

2. **MongoDB**
```bash
# Khởi động MongoDB
mongod
```

### Cấu hình application.properties

Chỉnh sửa file `src/main/resources/application.properties`:

```properties
# Neon PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://your-neon-host/your-database?sslmode=require
spring.datasource.username=your_username
spring.datasource.password=your_password

# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=child_development_db
```

### Chạy ứng dụng

```bash
# Build project
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8101/api/v1`

## Docker Deployment

### Build Docker Image
```bash
docker build -t child-development-service:latest .
```

### Run Docker Container
```bash
docker run -p 8101:8101 --name child-dev-service child-development-service:latest
```

### Test API
```bash
curl http://localhost:8101/api/v1/health
```

## API Endpoints

### Parent Management

#### Tạo phụ huynh mới
```http
POST /api/v1/parents
Content-Type: application/json

{
  "fullName": "Nguyễn Văn A",
  "phoneNumber": "0123456789",
  "email": "nguyenvana@email.com",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "dateOfBirth": "1985-05-15T00:00:00",
  "gender": "MALE"
}
```

#### Lấy thông tin phụ huynh
```http
GET /api/v1/parents/{id}
```

#### Lấy danh sách phụ huynh
```http
GET /api/v1/parents
GET /api/v1/parents/search?name=Nguyễn
GET /api/v1/parents/with-children
```

#### Cập nhật phụ huynh
```http
PUT /api/v1/parents/{id}
Content-Type: application/json

{
  "fullName": "Nguyễn Văn A (Updated)",
  "phoneNumber": "0987654321"
}
```

#### Xóa phụ huynh
```http
DELETE /api/v1/parents/{id}
```

### Child Management

#### Tạo trẻ em mới
```http
POST /api/v1/children
Content-Type: application/json

{
  "fullName": "Nguyễn Thị B",
  "dateOfBirth": "2020-03-10T00:00:00",
  "gender": "FEMALE",
  "height": 95.5,
  "weight": 15.2,
  "bloodType": "A+",
  "allergies": "Không có",
  "medicalHistory": "Không có tiền sử bệnh",
}
```

#### Lấy thông tin trẻ em
```http
GET /api/v1/children/{id}
GET /api/v1/children/external/{externalId}
```

#### Lấy danh sách trẻ em
```http
GET /api/v1/children
GET /api/v1/children/parent/{parentId}
GET /api/v1/children/parent/external/{parentExternalId}
GET /api/v1/children/status/ACTIVE
GET /api/v1/children/search?name=Nguyễn
```

#### Cập nhật trẻ em
```http
PUT /api/v1/children/{id}
Content-Type: application/json

{
  "fullName": "Nguyễn Thị B (Updated)",
  "height": 100.0,
  "weight": 16.5
}
```

#### Xóa trẻ em
```http
DELETE /api/v1/children/{id}
```

### Development Disorder Management

#### Tạo rối loạn phát triển
```http
POST /api/v1/development-disorders
Content-Type: application/json

{
  "disorderType": "AUTISM",
  "severity": "MILD",
  "diagnosisDate": "2023-01-15T00:00:00",
  "diagnosedBy": "Dr. Nguyễn Văn B",
  "diagnosisNotes": "Chẩn đoán sơ bộ",
  "currentStatus": "UNDER_TREATMENT"
}
```

#### Lấy thông tin rối loạn phát triển
```http
GET /api/v1/development-disorders/{id}
GET /api/v1/development-disorders/child/{childExternalId}
```

#### Cập nhật rối loạn phát triển
```http
PUT /api/v1/development-disorders/{id}
Content-Type: application/json

{
  "severity": "MODERATE",
  "currentStatus": "IMPROVING"
}
```

### Progress Report Management

#### Tạo báo cáo tiến độ
```http
POST /api/v1/progress-reports
Content-Type: application/json

{
  "reportDate": "2023-02-01T00:00:00",
  "reporter": "Dr. Nguyễn Văn C",
  "reportType": "MONTHLY",
  "overallProgress": "IMPROVING",
  "recommendations": "Tiếp tục can thiệp sớm"
}
```

#### Lấy báo cáo tiến độ
```http
GET /api/v1/progress-reports/{id}
GET /api/v1/progress-reports/child/{childExternalId}
```

### Assessment Test Management

#### Tạo bài test đánh giá
```http
POST /api/v1/assessment-tests
Content-Type: application/json

{
  "testCode": "AT_001",
  "testName": "Bài test đánh giá phát triển toàn diện",
  "description": "Bài test đánh giá các lĩnh vực phát triển của trẻ",
  "ageRange": "3-6",
  "duration": 30,
  "status": "ACTIVE"
}
```

#### Lấy thông tin bài test
```http
GET /api/v1/assessment-tests/{id}
GET /api/v1/assessment-tests/code/{testCode}
```

## Quản lý Metadata

Dự án sử dụng các base class để quản lý metadata chung:

### BaseEntity (JPA)
- `BaseEntity` - Base class cho tất cả JPA entities
- Tự động quản lý `createdAt` và `updatedAt`
- Sử dụng `@PrePersist` và `@PreUpdate` để tự động cập nhật timestamp

### BaseDocument (MongoDB)
- `BaseDocument` - Base class cho tất cả MongoDB documents
- Tự động quản lý `createdAt` và `updatedAt`
- Cung cấp lifecycle methods `onCreate()` và `onUpdate()`

### Cấu trúc Base Classes
```
src/main/java/com/meowcdd/
├── entity/base/
│   └── BaseEntity.java          # Base class cho JPA entities
└── document/base/
    └── BaseDocument.java        # Base class cho MongoDB documents
```

## Tính năng chính

1. **Quản lý phụ huynh**: CRUD operations cho thông tin phụ huynh
2. **Quản lý trẻ em**: CRUD operations cho thông tin trẻ em
3. **Quản lý rối loạn phát triển**: Lưu trữ chi tiết về các rối loạn phát triển
4. **Báo cáo tiến độ**: Theo dõi và báo cáo tiến độ phát triển của trẻ
5. **Bài test đánh giá**: Quản lý các bài test đánh giá phát triển
6. **Tìm kiếm và lọc**: Hỗ trợ tìm kiếm và lọc dữ liệu theo nhiều tiêu chí
7. **Validation**: Kiểm tra và validate dữ liệu đầu vào
8. **Error Handling**: Xử lý lỗi toàn cục với thông báo chi tiết

## Cấu trúc Project

```
src/main/java/com/meowcdd/
├── ChildDevelopmentServiceApplication.java
├── config/
│   ├── NeonDatabaseConfig.java
│   ├── MongoConfig.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ChildSupabaseController.java
│   ├── CDDTestSupabaseController.java
│   ├── ChildTestRecordSupabaseController.java
│   └── TrackingQuestionSupabaseController.java
├── entity/
│   ├── base/
│   │   └── BaseEntity.java
│   ├── Child.java
│   └── supabase/
│       ├── CDDTestSupabase.java
│       ├── ChildSupabase.java
│       ├── ChildTestRecordSupabase.java
│       └── TrackingQuestion.java
├── document/
│   ├── base/
│   │   └── BaseDocument.java
│   ├── AssessmentTest.java
│   ├── CDDTest.java
│   ├── CDDTestResult.java
│   ├── DevelopmentalDisorderQuestion.java
│   ├── DevelopmentDisorder.java
│   └── ProgressReport.java
├── dto/
│   ├── CDDTestDto.java
│   ├── CDDTestResultDto.java
│   ├── ChildDto.java
│   ├── ChildTestRecordSupabaseDto.java
│   ├── DevelopmentalDisorderQuestionDto.java
│   ├── DevelopmentDisorderDto.java
│   ├── PageResponseDto.java
│   ├── ParentDto.java
│   └── TrackingQuestionDto.java
├── repository/
│   ├── mongo/
│   └── supabase/
│       ├── CDDTestSupabaseRepository.java
│       ├── ChildSupabaseRepository.java
│       ├── ChildTestRecordSupabaseRepository.java
│       └── TrackingQuestionSupabaseRepository.java
├── service/
│   ├── CDDTestSupabaseService.java
│   ├── ChildSupabaseService.java
│   ├── ChildTestRecordSupabaseService.java
│   └── TrackingQuestionSupabaseService.java
└── exception/
    ├── ErrorResponse.java
    └── GlobalExceptionHandler.java
```

## Cấu hình Database

### Neon PostgreSQL Entities

**Child Entity:**
- `id` - Primary key (Long)
- `fullName` - Họ và tên trẻ (String)
- `gender` - Giới tính (MALE, FEMALE, OTHER)
- `dateOfBirth` - Ngày tháng năm sinh (LocalDate)
- `currentAgeMonths` - Tuổi hiện tại theo tháng (Integer)
- `isPremature` - Trẻ có phải sinh non không (Boolean)
- `gestationalWeek` - Tuần thai thứ (Integer)
- `birthWeightGrams` - Cân nặng khi sinh (Integer)
- `specialMedicalConditions` - Tình trạng y tế đặc biệt (String)
- `developmentalDisorderDiagnosis` - Đã từng được chẩn đoán rối loạn phát triển (YES, NO, NOT_EVALUATED, UNDER_INVESTIGATION)
- `hasEarlyIntervention` - Có từng được can thiệp sớm (Boolean)
- `earlyInterventionDetails` - Chi tiết can thiệp sớm (String)
- `primaryLanguage` - Ngôn ngữ chủ yếu (String)
- `familyDevelopmentalIssues` - Vấn đề phát triển trong gia đình (String)
- `height` - Chiều cao (Double, cm)
- `weight` - Cân nặng (Double, kg)
- `bloodType` - Nhóm máu (String)
- `allergies` - Dị ứng (String)
- `medicalHistory` - Tiền sử bệnh (String)
- `parentId` - ID phụ huynh (String)
- `registrationDate` - Ngày đăng ký (LocalDateTime)
- `status` - Trạng thái (ACTIVE, INACTIVE, SUSPENDED)
- `createdAt` - Thời gian tạo (LocalDateTime)
- `updatedAt` - Thời gian cập nhật (LocalDateTime)

**CDDTestSupabase Entity:**
- `id` - Primary key (Long)
- `assessmentCode` - Mã bài test (String, unique)
- `category` - Danh mục (String)
- `version` - Phiên bản (String)
- `status` - Trạng thái (DRAFT, ACTIVE, INACTIVE, ARCHIVED)
- `administrationType` - Loại thực hiện (PARENT_REPORT, PROFESSIONAL_OBSERVATION, DIRECT_ASSESSMENT, SELF_REPORT)
- `requiredQualifications` - Yêu cầu chuyên môn (NO_QUALIFICATION_REQUIRED, PSYCHOLOGIST_REQUIRED, PEDIATRICIAN_REQUIRED, DEVELOPMENTAL_SPECIALIST_REQUIRED, THERAPIST_REQUIRED, NURSE_REQUIRED, TEACHER_REQUIRED)
- `minAgeMonths` - Tuổi tối thiểu (Integer)
- `maxAgeMonths` - Tuổi tối đa (Integer)
- `estimatedDuration` - Thời gian ước tính (Integer)
- `namesJson` - Tên bài test theo ngôn ngữ (JSON)
- `descriptionsJson` - Mô tả theo ngôn ngữ (JSON)
- `instructionsJson` - Hướng dẫn theo ngôn ngữ (JSON)
- `questionsJson` - Câu hỏi (JSON)
- `scoringCriteriaJson` - Tiêu chí chấm điểm (JSON)
- `requiredMaterialsJson` - Vật liệu cần thiết (JSON)
- `notesJson` - Ghi chú theo ngôn ngữ (JSON)
- `createdAt` - Thời gian tạo (LocalDateTime)
- `updatedAt` - Thời gian cập nhật (LocalDateTime)

**ChildTestRecordSupabase Entity:**
- `id` - Primary key (Long)
- `childId` - ID trẻ em (String)
- `testId` - ID bài test (String)
- `testType` - Loại test (CDD_TEST, ASSESSMENT_TEST)
- `testDate` - Ngày test (LocalDateTime)
- `startTime` - Thời gian bắt đầu (LocalDateTime)
- `endTime` - Thời gian kết thúc (LocalDateTime)
- `assessor` - Người đánh giá (String)
- `environment` - Môi trường thực hiện (String)
- `parentPresent` - Phụ huynh có mặt (Boolean)
- `totalQuestions` - Tổng số câu hỏi (Integer)
- `correctAnswers` - Số câu trả lời đúng (Integer)
- `skippedQuestions` - Số câu bỏ qua (Integer)
- `totalScore` - Tổng điểm (Float)
- `maxScore` - Điểm tối đa (Float)
- `percentageScore` - Phần trăm điểm (Float)
- `resultLevel` - Mức độ kết quả (EXCELLENT, GOOD, AVERAGE, BELOW_AVERAGE, POOR)
- `interpretation` - Diễn giải kết quả (String)
- `notes` - Ghi chú (String)
- `questionAnswers` - Câu trả lời (JSON)
- `status` - Trạng thái (IN_PROGRESS, COMPLETED, ABANDONED, INVALID, REVIEWED)
- `createdAt` - Thời gian tạo (LocalDateTime)
- `updatedAt` - Thời gian cập nhật (LocalDateTime)

**TrackingQuestion Entity:**
- `id` - Primary key (Long)
- `domain` - Lĩnh vực (PHYSICAL, COGNITIVE, SOCIAL, EMOTIONAL, LANGUAGE, ADAPTIVE)
- `ageRange` - Độ tuổi (String)
- `frequency` - Tần suất (DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY)
- `question` - Câu hỏi (JSON)
- `options` - Tùy chọn trả lời (JSON)
- `category` - Danh mục (JSON)
- `context` - Ngữ cảnh (JSON)
- `note` - Ghi chú (String)
- `createdAt` - Thời gian tạo (LocalDateTime)
- `updatedAt` - Thời gian cập nhật (LocalDateTime)

### MongoDB Documents

**AssessmentTest Document:**
- `id` - Primary key (String)
- `testCode` - Mã bài test (String, unique)
- `testName` - Tên bài test (String)
- `description` - Mô tả (String)
- `ageRange` - Độ tuổi (String)
- `duration` - Thời gian (Integer, phút)
- `sections` - Các phần của bài test (List<TestSection>)
- `scoringMethod` - Phương pháp chấm điểm (String)
- `validity` - Tính hợp lệ (String)
- `reliability` - Độ tin cậy (String)
- `normativeData` - Dữ liệu chuẩn (String)
- `status` - Trạng thái (ACTIVE, INACTIVE, DRAFT, DEPRECATED)
- `version` - Phiên bản (String)
- `lastUpdatedBy` - Người cập nhật cuối (String)
- `createdAt` - Thời gian tạo (LocalDateTime)
- `updatedAt` - Thời gian cập nhật (LocalDateTime)

**Cấu trúc TestSection:**
```json
{
  "sectionId": "COMMUNICATION",
  "sectionNames": {
    "en": "Communication",
    "vi": "Giao tiếp"
  },
  "questions": [...],
  "timeLimit": 15,
  "scoringMethod": "LIKERT_SCALE"
}
```

**Cấu trúc TestQuestion:**
```json
{
  "questionId": "COMM_1",
  "questionTexts": {
    "en": "Eye contact",
    "vi": "Giao tiếp bằng mắt"
  },
  "questionType": "OBSERVATION",
  "category": "COMMUNICATION",
  "weight": 3,
  "required": true,
  "scoringRules": {
    "scale": "0-3",
    "description": "0=Normal, 1=Mild, 2=Moderate, 3=Severe"
  }
}
```

## Quản lý Metadata

Dự án sử dụng các base class để quản lý metadata chung:

### BaseEntity (JPA)
- `BaseEntity` - Base class cho tất cả JPA entities
- Tự động quản lý `createdAt` và `updatedAt`
- Sử dụng `@PrePersist` và `@PreUpdate` để tự động cập nhật timestamp

### BaseDocument (MongoDB)
- `BaseDocument` - Base class cho tất cả MongoDB documents
- Tự động quản lý `createdAt` và `updatedAt`
- Cung cấp lifecycle methods `onCreate()` và `onUpdate()`

### Cấu trúc Base Classes
```
src/main/java/com/meowcdd/
├── entity/base/
│   └── BaseEntity.java          # Base class cho JPA entities
└── document/base/
    └── BaseDocument.java        # Base class cho MongoDB documents
```

## Tính năng chính

1. **Quản lý phụ huynh**: CRUD operations cho thông tin phụ huynh
2. **Quản lý trẻ em**: CRUD operations cho thông tin trẻ em
3. **Quản lý rối loạn phát triển**: Lưu trữ chi tiết về các rối loạn phát triển
4. **Báo cáo tiến độ**: Theo dõi và báo cáo tiến độ phát triển của trẻ
5. **Bài test đánh giá**: Quản lý các bài test đánh giá phát triển
6. **Tìm kiếm và lọc**: Hỗ trợ tìm kiếm và lọc dữ liệu theo nhiều tiêu chí
7. **Validation**: Kiểm tra và validate dữ liệu đầu vào
8. **Error Handling**: Xử lý lỗi toàn cục với thông báo chi tiết

## Troubleshooting

### Lỗi thường gặp

1. **NullPointerException với Hibernate**: Đã được sửa bằng cách cập nhật cấu hình Hibernate
2. **Database connection issues**: Kiểm tra thông tin kết nối Neon PostgreSQL
3. **MongoDB conflicts**: Đã được xử lý bằng profile configuration

### Logs và Debugging

```bash
# Xem logs ứng dụng
docker logs child-dev-service

# Test database connection
curl http://localhost:8101/api/v1/health

# Check container status
docker ps
```
