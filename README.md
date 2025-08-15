# Child Development Service

Service quản lý thông tin trẻ em có rối loạn phát triển, sử dụng Spring Boot với MongoDB và MySQL.

## Công nghệ sử dụng

- **Spring Boot 3.2.0**
- **Java 17**
- **MongoDB** - Lưu trữ thông tin chi tiết về rối loạn phát triển
- **MySQL** - Lưu trữ thông tin cơ bản về phụ huynh và trẻ em
- **Spring Data JPA** - ORM cho MySQL
- **Spring Data MongoDB** - ODM cho MongoDB
- **Lombok** - Giảm boilerplate code
- **Validation** - Kiểm tra dữ liệu đầu vào

## Cấu trúc Database

### MySQL (JPA)
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
- MySQL 8.0+
- MongoDB 4.4+

### Cấu hình Database

1. **MySQL**
```sql
CREATE DATABASE child_development_sql;
```

2. **MongoDB**
```bash
# Khởi động MongoDB
mongod
```

### Cấu hình application.properties

Chỉnh sửa file `src/main/resources/application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/child_development_sql?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
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

Ứng dụng sẽ chạy tại: `http://localhost:8080/api/v1`

## API Endpoints

### Parent Management

#### Tạo phụ huynh mới
```http
POST /api/v1/parents
Content-Type: application/json

{
  "externalId": "PARENT_001",
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
GET /api/v1/parents/external/{externalId}
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
  "externalId": "CHILD_001",
  "fullName": "Nguyễn Thị B",
  "dateOfBirth": "2020-03-10T00:00:00",
  "gender": "FEMALE",
  "height": 95.5,
  "weight": 15.2,
  "bloodType": "A+",
  "allergies": "Không có",
  "medicalHistory": "Không có tiền sử bệnh",
  "parentExternalId": "PARENT_001"
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
  "childExternalId": "CHILD_001",
  "disorderType": "AUTISM",
  "severity": "MILD",
  "diagnosisDate": "2023-01-15T00:00:00",
  "diagnosedBy": "Dr. Trần Văn C",
  "diagnosisNotes": "Chẩn đoán ban đầu",
  "symptoms": [
    {
      "name": "Khó khăn giao tiếp",
      "description": "Trẻ gặp khó khăn trong việc giao tiếp bằng lời nói",
      "severity": "MILD",
      "firstObserved": "2022-12-01T00:00:00",
      "notes": "Cần theo dõi thêm"
    }
  ],
  "assessments": [
    {
      "type": "ADOS-2",
      "assessmentDate": "2023-01-15T00:00:00",
      "assessor": "Dr. Trần Văn C",
      "score": 7.5,
      "result": "MILD_AUTISM",
      "notes": "Đánh giá ban đầu",
      "recommendations": [
        "Can thiệp sớm",
        "Therapy ngôn ngữ",
        "Therapy hành vi"
      ]
    }
  ],
  "treatments": [
    {
      "type": "SPEECH_THERAPY",
      "description": "Trị liệu ngôn ngữ",
      "startDate": "2023-02-01T00:00:00",
      "provider": "Trung tâm ABC",
      "status": "ONGOING",
      "effectiveness": "PARTIALLY_EFFECTIVE",
      "notes": "Trẻ có tiến bộ chậm"
    }
  ],
  "currentStatus": "UNDER_TREATMENT"
}
```

#### Lấy thông tin rối loạn phát triển
```http
GET /api/v1/development-disorders/{id}
GET /api/v1/development-disorders/child/{childExternalId}
```

#### Lấy danh sách rối loạn phát triển
```http
GET /api/v1/development-disorders
GET /api/v1/development-disorders/type/AUTISM
GET /api/v1/development-disorders/severity/MILD
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

#### Xóa rối loạn phát triển
```http
DELETE /api/v1/development-disorders/{id}
```

### Progress Report Management

#### Tạo báo cáo tiến độ
```http
POST /api/v1/progress-reports
Content-Type: application/json

{
  "childExternalId": "CHILD_001",
  "reportDate": "2023-03-15T00:00:00",
  "reporter": "Dr. Nguyễn Thị D",
  "reportType": "MONTHLY",
  "physicalDevelopment": {
    "height": 120.5,
    "weight": 25.2,
    "motorSkills": "IMPROVING"
  },
  "cognitiveDevelopment": {
    "attention": "GOOD",
    "memory": "FAIR",
    "problemSolving": "IMPROVING"
  },
  "socialDevelopment": {
    "interaction": "IMPROVING",
    "communication": "NEEDS_SUPPORT"
  },
  "emotionalDevelopment": {
    "selfRegulation": "FAIR",
    "empathy": "GOOD"
  },
  "languageDevelopment": {
    "vocabulary": "EXPANDING",
    "grammar": "IMPROVING"
  },
  "milestones": [
    {
      "category": "LANGUAGE",
      "description": "Có thể nói câu đơn giản",
      "achievedDate": "2023-03-10T00:00:00",
      "notes": "Tiến bộ đáng kể"
    }
  ],
  "challenges": [
    {
      "category": "SOCIAL",
      "description": "Khó khăn trong tương tác nhóm",
      "severity": "MODERATE",
      "notes": "Cần hỗ trợ thêm"
    }
  ],
  "goals": [
    {
      "category": "COMMUNICATION",
      "description": "Tăng cường kỹ năng giao tiếp",
      "targetDate": "2023-06-15T00:00:00",
      "status": "IN_PROGRESS"
    }
  ],
  "overallProgress": "IMPROVING",
  "recommendations": "Tiếp tục therapy và tăng cường tương tác xã hội"
}
```

#### Lấy thông tin báo cáo tiến độ
```http
GET /api/v1/progress-reports/{id}
GET /api/v1/progress-reports/child/{childExternalId}
GET /api/v1/progress-reports/child/{childExternalId}/latest
```

#### Lấy danh sách báo cáo tiến độ
```http
GET /api/v1/progress-reports
GET /api/v1/progress-reports/type/MONTHLY
GET /api/v1/progress-reports/reporter/Dr. Nguyễn Thị D
GET /api/v1/progress-reports/date-range?startDate=2023-01-01T00:00:00&endDate=2023-12-31T23:59:59
```

#### Cập nhật báo cáo tiến độ
```http
PUT /api/v1/progress-reports/{id}
Content-Type: application/json

{
  "overallProgress": "SIGNIFICANT_IMPROVEMENT",
  "recommendations": "Có thể giảm tần suất therapy"
}
```

#### Xóa báo cáo tiến độ
```http
DELETE /api/v1/progress-reports/{id}
```

### Developmental Disorder Questions Management

#### Tạo câu hỏi sàng lọc mới
```http
POST /api/v1/developmental-disorder-questions
Content-Type: application/json

{
  "names": {
    "en": "Autism Spectrum Disorder (ASD)",
    "vi": "Tự kỷ (ASD)",
    "fr": "Trouble du spectre autistique (TSA)",
    "es": "Trastorno del espectro autista (TEA)"
  },
  "mainSymptoms": {
    "en": "Difficulty with communication; Repetitive behaviors; Poor social interaction",
    "vi": "Khó giao tiếp; Hành vi lặp lại; Kém tương tác xã hội",
    "fr": "Difficultés de communication; Comportements répétitifs; Faible interaction sociale",
    "es": "Dificultad en la comunicación; Comportamientos repetitivos; Pobre interacción social"
  },
  "detectionAgeMinMonths": 18,
  "detectionAgeMaxMonths": 36,
  "detectionAgeMinYears": null,
  "detectionAgeMaxYears": null,
  "screeningQuestions": {
    "en": "Does the child avoid eye contact when interacting with others?; Does the child show repetitive behaviors such as hand flapping or spinning?",
    "vi": "Trẻ có tránh nhìn vào mắt người khác khi tương tác không?; Trẻ có hành vi lặp lại như vẫy tay, xoay vòng không?",
    "fr": "L'enfant évite-t-il le contact visuel lors des interactions?; L'enfant montre-t-il des comportements répétitifs comme battre des mains ou tourner?",
    "es": "¿El niño evita el contacto visual al interactuar con otros?; ¿El niño muestra comportamientos repetitivos como aletear las manos o girar?"
  }
}
```

#### Lấy tất cả câu hỏi sàng lọc
```http
GET /api/v1/developmental-disorder-questions
```

#### Lấy câu hỏi theo ID
```http
GET /api/v1/developmental-disorder-questions/{id}
```

#### Lấy câu hỏi theo tên trong ngôn ngữ cụ thể
```http
GET /api/v1/developmental-disorder-questions/name/{languageCode}/{name}
```

**Ví dụ:**
```http
GET /api/v1/developmental-disorder-questions/name/en/Autism Spectrum Disorder (ASD)
GET /api/v1/developmental-disorder-questions/name/vi/Tự kỷ (ASD)
GET /api/v1/developmental-disorder-questions/name/fr/Trouble du spectre autistique (TSA)
```

#### Lấy câu hỏi theo độ tuổi (tháng)
```http
GET /api/v1/developmental-disorder-questions/age-range-months?minMonths=18&maxMonths=36
```

#### Lấy câu hỏi theo độ tuổi (năm)
```http
GET /api/v1/developmental-disorder-questions/age-range-years?minYears=2&maxYears=5
```

#### Tìm kiếm câu hỏi theo tên
```http
GET /api/v1/developmental-disorder-questions/search/name?name=autism
```

#### Tìm kiếm câu hỏi theo triệu chứng
```http
GET /api/v1/developmental-disorder-questions/search/symptoms?symptoms=communication
```

#### Tìm kiếm câu hỏi theo câu hỏi sàng lọc
```http
GET /api/v1/developmental-disorder-questions/search/screening-questions?questions=eye contact
```

#### Lấy câu hỏi theo ngôn ngữ có sẵn
```http
GET /api/v1/developmental-disorder-questions/language/{languageCode}
```

**Ví dụ:**
```http
GET /api/v1/developmental-disorder-questions/language/en
GET /api/v1/developmental-disorder-questions/language/vi
GET /api/v1/developmental-disorder-questions/language/fr
```

#### Lấy câu hỏi có tên trong ngôn ngữ cụ thể
```http
GET /api/v1/developmental-disorder-questions/language/{languageCode}/names
```

#### Cập nhật câu hỏi
```http
PUT /api/v1/developmental-disorder-questions/{id}
Content-Type: application/json

{
  "names": {
    "en": "Updated Autism Spectrum Disorder (ASD)",
    "vi": "Cập nhật Tự kỷ (ASD)",
    "fr": "Mise à jour Trouble du spectre autistique (TSA)"
  },
  "mainSymptoms": {
    "en": "Updated symptoms",
    "vi": "Triệu chứng đã cập nhật",
    "fr": "Symptômes mis à jour"
  },
  "detectionAgeMinMonths": 18,
  "detectionAgeMaxMonths": 36,
  "screeningQuestions": {
    "en": "Updated questions",
    "vi": "Câu hỏi đã cập nhật",
    "fr": "Questions mises à jour"
  }
}
```

#### Xóa câu hỏi
```http
DELETE /api/v1/developmental-disorder-questions/{id}
```

#### Kiểm tra tồn tại câu hỏi theo tên trong ngôn ngữ cụ thể
```http
GET /api/v1/developmental-disorder-questions/exists/name/{languageCode}/{name}
```

**Ví dụ:**
```http
GET /api/v1/developmental-disorder-questions/exists/name/en/Autism Spectrum Disorder (ASD)
GET /api/v1/developmental-disorder-questions/exists/name/vi/Tự kỷ (ASD)
```

#### Đếm số lượng câu hỏi
```http
GET /api/v1/developmental-disorder-questions/count
```

#### Đếm số lượng câu hỏi theo ngôn ngữ
```http
GET /api/v1/developmental-disorder-questions/count/language/{languageCode}
```

**Ví dụ:**
```http
GET /api/v1/developmental-disorder-questions/count/language/en
GET /api/v1/developmental-disorder-questions/count/language/vi
```

### Assessment Test Management

#### Tạo bài test mới
```http
POST /api/v1/assessment-tests
Content-Type: application/json

{
  "names": {
    "en": "Autism Diagnostic Observation Schedule, Second Edition (ADOS-2)",
    "vi": "Bảng quan sát chẩn đoán tự kỷ, phiên bản thứ hai (ADOS-2)",
    "fr": "Échelle d'observation pour le diagnostic de l'autisme, deuxième édition (ADOS-2)",
    "es": "Escala de observación para el diagnóstico del autismo, segunda edición (ADOS-2)"
  },
  "descriptions": {
    "en": "A standardized diagnostic assessment for autism spectrum disorders",
    "vi": "Đánh giá chẩn đoán chuẩn hóa cho rối loạn phổ tự kỷ",
    "fr": "Une évaluation diagnostique standardisée pour les troubles du spectre autistique",
    "es": "Una evaluación diagnóstica estandarizada para trastornos del espectro autista"
  },
  "testCode": "ADOS-2",
  "category": "AUTISM_DIAGNOSTIC",
  "targetAgeGroup": "12_MONTHS_PLUS",
  "estimatedDuration": 45,
  "difficultyLevel": "DIFFICULT",
  "administrationType": "INDIVIDUAL",
  "requiredQualifications": "ADOS-2 Certified Administrator",
  "status": "ACTIVE",
  "version": "2.0"
}
```

#### Lấy tất cả bài test
```http
GET /api/v1/assessment-tests
```

#### Lấy bài test theo ID
```http
GET /api/v1/assessment-tests/{id}
```

#### Lấy bài test theo mã test
```http
GET /api/v1/assessment-tests/code/{testCode}
```

#### Lấy bài test theo tên trong ngôn ngữ cụ thể
```http
GET /api/v1/assessment-tests/name/{languageCode}/{name}
```

#### Lấy bài test theo danh mục
```http
GET /api/v1/assessment-tests/category/{category}
```

#### Lấy bài test theo nhóm tuổi
```http
GET /api/v1/assessment-tests/age-group/{targetAgeGroup}
```

#### Lấy bài test theo mức độ khó
```http
GET /api/v1/assessment-tests/difficulty/{difficultyLevel}
```

#### Lấy bài test theo loại thực hiện
```http
GET /api/v1/assessment-tests/administration-type/{administrationType}
```

#### Lấy bài test theo trạng thái
```http
GET /api/v1/assessment-tests/status/{status}
```

#### Lấy bài test đang hoạt động
```http
GET /api/v1/assessment-tests/active
```

#### Tìm kiếm bài test theo tên
```http
GET /api/v1/assessment-tests/search/name?name=autism
```

#### Tìm kiếm bài test theo mô tả
```http
GET /api/v1/assessment-tests/search/description?description=diagnostic
```

#### Lấy bài test theo ngôn ngữ có sẵn
```http
GET /api/v1/assessment-tests/language/{languageCode}
```

#### Lấy bài test theo danh mục và nhóm tuổi
```http
GET /api/v1/assessment-tests/category/{category}/age-group/{targetAgeGroup}
```

#### Lấy bài test theo danh mục và mức độ khó
```http
GET /api/v1/assessment-tests/category/{category}/difficulty/{difficultyLevel}
```

#### Lấy bài test theo loại thực hiện và trạng thái
```http
GET /api/v1/assessment-tests/administration-type/{administrationType}/status/{status}
```

#### Lấy bài test theo khoảng thời gian
```http
GET /api/v1/assessment-tests/duration-range?minDuration=30&maxDuration=60
```

#### Lấy bài test theo thời gian tối đa
```http
GET /api/v1/assessment-tests/max-duration/{maxDuration}
```

#### Lấy bài test theo phiên bản
```http
GET /api/v1/assessment-tests/version/{version}
```

#### Lấy bài test theo người cập nhật cuối
```http
GET /api/v1/assessment-tests/updated-by/{lastUpdatedBy}
```

#### Lấy bài test theo nhiều danh mục
```http
GET /api/v1/assessment-tests/categories?categories=AUTISM_DIAGNOSTIC,LANGUAGE_ASSESSMENT
```

#### Lấy bài test theo nhiều nhóm tuổi
```http
GET /api/v1/assessment-tests/age-groups?ageGroups=12_MONTHS_PLUS,2_YEARS_PLUS
```

#### Tìm kiếm nâng cao
```http
GET /api/v1/assessment-tests/advanced-search?category=AUTISM_DIAGNOSTIC&targetAgeGroup=12_MONTHS_PLUS&difficultyLevel=DIFFICULT&status=ACTIVE
```

#### Cập nhật bài test
```http
PUT /api/v1/assessment-tests/{id}
Content-Type: application/json

{
  "names": {
    "en": "Updated ADOS-2",
    "vi": "Cập nhật ADOS-2",
    "fr": "ADOS-2 mis à jour"
  },
  "descriptions": {
    "en": "Updated description",
    "vi": "Mô tả đã cập nhật",
    "fr": "Description mise à jour"
  },
  "estimatedDuration": 50,
  "status": "ACTIVE",
  "version": "2.1"
}
```

#### Xóa bài test
```http
DELETE /api/v1/assessment-tests/{id}
```

#### Kiểm tra tồn tại bài test theo mã
```http
GET /api/v1/assessment-tests/exists/code/{testCode}
```

#### Kiểm tra tồn tại bài test theo tên trong ngôn ngữ
```http
GET /api/v1/assessment-tests/exists/name/{languageCode}/{name}
```

#### Đếm số lượng bài test
```http
GET /api/v1/assessment-tests/count
```

#### Đếm số lượng bài test theo danh mục
```http
GET /api/v1/assessment-tests/count/category/{category}
```

#### Đếm số lượng bài test theo trạng thái
```http
GET /api/v1/assessment-tests/count/status/{status}
```

#### Đếm số lượng bài test theo ngôn ngữ
```http
GET /api/v1/assessment-tests/count/language/{languageCode}
```

## Cấu trúc dữ liệu

### Parent Entity (MySQL)
- `id` - ID tự động tăng
- `externalId` - ID từ service khác (unique)
- `fullName` - Họ tên đầy đủ
- `phoneNumber` - Số điện thoại
- `email` - Email
- `address` - Địa chỉ
- `dateOfBirth` - Ngày sinh
- `gender` - Giới tính (MALE, FEMALE, OTHER)
- `createdAt` - Thời gian tạo
- `updatedAt` - Thời gian cập nhật

### Child Entity (MySQL)
- `id` - ID tự động tăng
- `externalId` - ID từ service khác (unique)
- `fullName` - Họ tên đầy đủ
- `dateOfBirth` - Ngày sinh
- `gender` - Giới tính
- `height` - Chiều cao (cm)
- `weight` - Cân nặng (kg)
- `bloodType` - Nhóm máu
- `allergies` - Dị ứng
- `medicalHistory` - Tiền sử bệnh
- `registrationDate` - Ngày đăng ký
- `status` - Trạng thái (ACTIVE, INACTIVE, SUSPENDED)
- `parent` - Liên kết với Parent

### DevelopmentDisorder Document (MongoDB)
- `id` - MongoDB ObjectId
- `childExternalId` - Liên kết với Child
- `disorderType` - Loại rối loạn
- `severity` - Mức độ (MILD, MODERATE, SEVERE)
- `diagnosisDate` - Ngày chẩn đoán
- `diagnosedBy` - Bác sĩ chẩn đoán
- `diagnosisNotes` - Ghi chú chẩn đoán
- `symptoms` - Danh sách triệu chứng
- `assessments` - Các đánh giá
- `treatments` - Các phương pháp điều trị
- `currentStatus` - Trạng thái hiện tại
- `createdAt` - Thời gian tạo
- `updatedAt` - Thời gian cập nhật

### ProgressReport Document (MongoDB)
- `id` - MongoDB ObjectId
- `childExternalId` - Liên kết với Child
- `reportDate` - Ngày báo cáo
- `reporter` - Người báo cáo
- `reportType` - Loại báo cáo (MONTHLY, QUARTERLY, ANNUAL, SPECIAL)
- `physicalDevelopment` - Phát triển thể chất (Map)
- `cognitiveDevelopment` - Phát triển nhận thức (Map)
- `socialDevelopment` - Phát triển xã hội (Map)
- `emotionalDevelopment` - Phát triển cảm xúc (Map)
- `languageDevelopment` - Phát triển ngôn ngữ (Map)
- `milestones` - Danh sách cột mốc đạt được
- `challenges` - Danh sách thách thức gặp phải
- `goals` - Danh sách mục tiêu tiếp theo
- `overallProgress` - Tiến độ tổng thể
- `recommendations` - Khuyến nghị
- `createdAt` - Thời gian tạo
- `updatedAt` - Thời gian cập nhật

### DevelopmentalDisorderQuestion Document (MongoDB)
- `id` - MongoDB ObjectId
- `names` - Map chứa tên rối loạn theo mã ngôn ngữ (Map<String, String>)
- `mainSymptoms` - Map chứa triệu chứng chính theo mã ngôn ngữ (Map<String, String>)
- `screeningQuestions` - Map chứa câu hỏi sàng lọc theo mã ngôn ngữ (Map<String, String>)
- `detectionAgeMinMonths` - Độ tuổi phát hiện tối thiểu (tháng)
- `detectionAgeMaxMonths` - Độ tuổi phát hiện tối đa (tháng)
- `detectionAgeMinYears` - Độ tuổi phát hiện tối thiểu (năm)
- `detectionAgeMaxYears` - Độ tuổi phát hiện tối đa (năm)
- `createdAt` - Thời gian tạo
- `updatedAt` - Thời gian cập nhật

**Ví dụ cấu trúc đa ngôn ngữ:**
```json
{
  "names": {
    "en": "Autism Spectrum Disorder (ASD)",
    "vi": "Tự kỷ (ASD)",
    "fr": "Trouble du spectre autistique (TSA)",
    "es": "Trastorno del espectro autista (TEA)"
  },
  "mainSymptoms": {
    "en": "Difficulty with communication; Repetitive behaviors",
    "vi": "Khó giao tiếp; Hành vi lặp lại",
    "fr": "Difficultés de communication; Comportements répétitifs",
    "es": "Dificultad en la comunicación; Comportamientos repetitivos"
  }
}
```

### AssessmentTest Document (MongoDB)
- `id` - MongoDB ObjectId
- `names` - Map chứa tên bài test theo mã ngôn ngữ (Map<String, String>)
- `descriptions` - Map chứa mô tả bài test theo mã ngôn ngữ (Map<String, String>)
- `instructions` - Map chứa hướng dẫn thực hiện theo mã ngôn ngữ (Map<String, String>)
- `testCode` - Mã bài test duy nhất (String)
- `category` - Danh mục bài test (String)
- `targetAgeGroup` - Nhóm tuổi mục tiêu (String)
- `estimatedDuration` - Thời gian ước tính (phút)
- `difficultyLevel` - Mức độ khó (EASY, MODERATE, DIFFICULT)
- `sections` - Danh sách các phần của bài test (List<TestSection>)
- `scoringCriteria` - Tiêu chí chấm điểm (Map<String, Object>)
- `cutoffScores` - Điểm cắt cho các mức độ nghiêm trọng (Map<String, Object>)
- `administrationType` - Loại thực hiện (INDIVIDUAL, GROUP, PARENT_REPORT, OBSERVATION)
- `requiredQualifications` - Yêu cầu trình độ người thực hiện (String)
- `requiredMaterials` - Danh sách vật liệu cần thiết (List<String>)
- `administrationNotes` - Ghi chú thực hiện theo mã ngôn ngữ (Map<String, String>)
- `validity` - Thông tin về tính hợp lệ (String)
- `reliability` - Thông tin về độ tin cậy (String)
- `normativeData` - Thông tin về dữ liệu chuẩn (String)
- `status` - Trạng thái (ACTIVE, INACTIVE, DRAFT, DEPRECATED)
- `version` - Phiên bản bài test (String)
- `lastUpdatedBy` - Người cập nhật cuối (String)
- `createdAt` - Thời gian tạo
- `updatedAt` - Thời gian cập nhật

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
4. **Quản lý báo cáo tiến độ**: Theo dõi và báo cáo tiến độ phát triển của trẻ
5. **Quản lý câu hỏi sàng lọc**: Lưu trữ và quản lý các câu hỏi sàng lọc rối loạn phát triển
6. **Quản lý bài test chuyên sâu**: Lưu trữ và quản lý các bài test đánh giá chuyên sâu
7. **Tìm kiếm và lọc**: Tìm kiếm theo tên, lọc theo trạng thái, loại rối loạn, độ tuổi, danh mục test
8. **Validation**: Kiểm tra dữ liệu đầu vào
9. **Exception Handling**: Xử lý lỗi toàn cục
10. **Logging**: Ghi log chi tiết
11. **Data Initialization**: Tự động khởi tạo dữ liệu mẫu khi khởi động ứng dụng

## Lưu ý

- Service này được thiết kế để tích hợp với các service khác thông qua `externalId`
- Dữ liệu được phân tách giữa MySQL (thông tin cơ bản) và MongoDB (thông tin chi tiết)
- Cần cấu hình đúng thông tin database trước khi chạy
- API sử dụng RESTful conventions
- Tất cả responses đều có format JSON
