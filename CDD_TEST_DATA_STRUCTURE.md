# Cấu trúc dữ liệu bài test (CDD Test)

## Tổng quan
Tài liệu này mô tả cấu trúc dữ liệu hoàn chỉnh của bài test phát triển trẻ em (CDD Test) trong hệ thống.

## 1. Cấu trúc JSON chính

### 1.1 Thông tin cơ bản
```json
{
  "assessmentCode": "DEVELOPMENTAL_SCREENING_V1",
  "names": {
    "vi": "Bảng câu hỏi sàng lọc phát triển trẻ em",
    "en": "Developmental Screening Questionnaire"
  },
  "descriptions": {
    "vi": "Bảng câu hỏi đánh giá các kỹ năng phát triển...",
    "en": "Questionnaire to assess children's developmental skills..."
  },
  "instructions": {
    "vi": "Hãy trả lời các câu hỏi dưới đây...",
    "en": "Please answer the following questions..."
  },
  "category": "DEVELOPMENTAL_SCREENING",
  "minAgeMonths": 0,
  "maxAgeMonths": 6,
  "status": "ACTIVE",
  "version": "1.0",
  "estimatedDuration": 15,
  "administrationType": "PARENT_REPORT",
  "requiredQualifications": "NO_QUALIFICATION_REQUIRED",
  "requiredMaterials": ["Bảng câu hỏi", "Bút"],
  "notes": {
    "vi": "Bảng câu hỏi này được thiết kế để sàng lọc sớm...",
    "en": "This questionnaire is designed for early screening..."
  }
}
```

### 1.2 Danh sách câu hỏi
```json
{
  "questions": [
    {
      "questionId": "COMM_001",
      "questionNumber": 1,
      "questionTexts": {
        "vi": "Trẻ có dấu hiệu \"Không bao giờ phát ra những âm thanh\"?",
        "en": "Does the child show signs of \"Never making any sounds\"?"
      },
      "category": "COMMUNICATION_LANGUAGE",
      "weight": 1,
      "required": true,
      "hints": {
        "vi": "Quan sát xem trẻ có phát ra âm thanh gì không...",
        "en": "Observe if the child makes any sounds..."
      },
      "explanations": {
        "vi": "Trẻ sơ sinh thường phát ra âm thanh khi khóc...",
        "en": "Infants typically make sounds when crying..."
      }
    }
  ]
}
```

### 1.3 Tiêu chí chấm điểm
```json
{
  "scoringCriteria": {
    "totalQuestions": 20,
    "yesScore": 1,
    "noScore": 0,
    "scoreRanges": {
      "LOW_RISK": {
        "minScore": 0,
        "maxScore": 2,
        "level": "LOW_RISK",
        "descriptions": {
          "vi": "Nguy cơ thấp - Trẻ có ít dấu hiệu bất thường",
          "en": "Low risk - Child has few abnormal signs"
        },
        "recommendation": "Tiếp tục theo dõi phát triển bình thường"
      },
      "MEDIUM_RISK": {
        "minScore": 3,
        "maxScore": 5,
        "level": "MEDIUM_RISK",
        "descriptions": {
          "vi": "Nguy cơ trung bình - Trẻ có một số dấu hiệu bất thường",
          "en": "Medium risk - Child has some abnormal signs"
        },
        "recommendation": "Cần theo dõi chặt chẽ và đánh giá lại sau 1-2 tháng"
      },
      "HIGH_RISK": {
        "minScore": 6,
        "maxScore": 20,
        "level": "HIGH_RISK",
        "descriptions": {
          "vi": "Nguy cơ cao - Trẻ có nhiều dấu hiệu bất thường",
          "en": "High risk - Child has many abnormal signs"
        },
        "recommendation": "Cần đánh giá chuyên môn ngay lập tức"
      }
    },
    "interpretation": "Điểm càng cao, nguy cơ bất thường phát triển càng lớn..."
  }
}
```

## 2. Cấu trúc Database (Entity)

### 2.1 CDDTestSupabase Entity
```java
@Entity
@Table(name = "cdd_tests")
public class CDDTestSupabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_code", unique = true, nullable = false)
    private String assessmentCode;                    // Mã bài test

    @Column(columnDefinition = "TEXT")
    private String namesJson;                         // Tên bài test (đa ngôn ngữ)

    @Column(columnDefinition = "TEXT")
    private String descriptionsJson;                  // Mô tả bài test (đa ngôn ngữ)

    @Column(columnDefinition = "TEXT")
    private String instructionsJson;                  // Hướng dẫn (đa ngôn ngữ)

    @Column(nullable = false)
    private String category;                          // Loại bài test

    @Column(name = "min_age_months")
    private Integer minAgeMonths;                     // Độ tuổi tối thiểu (tháng)

    @Column(name = "max_age_months")
    private Integer maxAgeMonths;                     // Độ tuổi tối đa (tháng)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;                            // Trạng thái bài test

    @Column(nullable = false)
    private String version;                           // Phiên bản

    @Column(name = "estimated_duration")
    private Integer estimatedDuration;                // Thời gian ước tính (phút)

    @Enumerated(EnumType.STRING)
    @Column(name = "administration_type")
    private AdministrationType administrationType;     // Loại thực hiện

    @Enumerated(EnumType.STRING)
    @Column(name = "required_qualifications")
    private RequiredQualifications requiredQualifications; // Yêu cầu trình độ

    @Column(columnDefinition = "TEXT")
    private String requiredMaterialsJson;             // Vật liệu cần thiết

    @Column(columnDefinition = "TEXT")
    private String notesJson;                         // Ghi chú (đa ngôn ngữ)

    @Column(columnDefinition = "TEXT")
    private String questionsJson;                     // Danh sách câu hỏi

    @Column(columnDefinition = "TEXT")
    private String scoringCriteriaJson;               // Tiêu chí chấm điểm

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;                  // Thời gian tạo

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;                  // Thời gian cập nhật
}
```

## 3. Enums

### 3.1 Status (Trạng thái bài test)
```java
public enum Status {
    DRAFT("Bản nháp"),
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động"),
    ARCHIVED("Đã lưu trữ");
}
```

### 3.2 AdministrationType (Loại thực hiện)
```java
public enum AdministrationType {
    PARENT_REPORT("Báo cáo phụ huynh"),
    PROFESSIONAL_OBSERVATION("Quan sát chuyên môn"),
    DIRECT_ASSESSMENT("Đánh giá trực tiếp"),
    SELF_REPORT("Tự báo cáo");
}
```

### 3.3 RequiredQualifications (Yêu cầu trình độ)
```java
public enum RequiredQualifications {
    NO_QUALIFICATION_REQUIRED("Không yêu cầu trình độ chuyên môn"),
    PSYCHOLOGIST_REQUIRED("Yêu cầu chuyên gia tâm lý"),
    PEDIATRICIAN_REQUIRED("Yêu cầu bác sĩ nhi khoa"),
    DEVELOPMENTAL_SPECIALIST_REQUIRED("Yêu cầu chuyên gia phát triển trẻ em"),
    THERAPIST_REQUIRED("Yêu cầu nhà trị liệu"),
    NURSE_REQUIRED("Yêu cầu y tá"),
    TEACHER_REQUIRED("Yêu cầu giáo viên");
}
```

## 4. Cấu trúc câu hỏi

### 4.1 YesNoQuestionDto
```java
public static class YesNoQuestionDto {
    private String questionId;                        // ID câu hỏi
    private Integer questionNumber;                   // Số thứ tự câu hỏi
    private Map<String, String> questionTexts;        // Nội dung câu hỏi (đa ngôn ngữ)
    private String category;                          // Danh mục câu hỏi
    private Integer weight;                           // Trọng số câu hỏi
    private Boolean required;                         // Bắt buộc trả lời
    private Map<String, String> hints;                // Gợi ý (đa ngôn ngữ)
    private Map<String, String> explanations;         // Giải thích (đa ngôn ngữ)
}
```

### 4.2 Danh mục câu hỏi
- `COMMUNICATION_LANGUAGE`: Giao tiếp - Ngôn ngữ
- `GROSS_MOTOR`: Vận động thô
- `FINE_MOTOR`: Vận động tinh
- `IMITATION_LEARNING`: Bắt chước và học
- `PERSONAL_SOCIAL`: Cá nhân - Xã hội
- `OTHER`: Khác

## 5. Cấu trúc chấm điểm

### 5.1 ScoringCriteriaDto
```java
public static class ScoringCriteriaDto {
    private Integer totalQuestions;                   // Tổng số câu hỏi
    private Integer yesScore;                         // Điểm cho câu trả lời "Có"
    private Integer noScore;                          // Điểm cho câu trả lời "Không"
    private Map<String, ScoreRangeDto> scoreRanges;   // Các khoảng điểm
    private String interpretation;                    // Giải thích cách chấm điểm
}
```

### 5.2 ScoreRangeDto
```java
public static class ScoreRangeDto {
    private Integer minScore;                         // Điểm tối thiểu
    private Integer maxScore;                         // Điểm tối đa
    private String level;                             // Mức độ rủi ro
    private Map<String, String> descriptions;         // Mô tả (đa ngôn ngữ)
    private String recommendation;                    // Khuyến nghị
}
```

### 5.3 Mức độ rủi ro
- `LOW_RISK`: Nguy cơ thấp (0-2 điểm)
- `MEDIUM_RISK`: Nguy cơ trung bình (3-5 điểm)
- `HIGH_RISK`: Nguy cơ cao (6-20 điểm)

## 6. Hỗ trợ đa ngôn ngữ

### 6.1 Cấu trúc JSON đa ngôn ngữ
Tất cả các trường text đều được lưu dưới dạng JSON với key ngôn ngữ:
```json
{
  "vi": "Nội dung tiếng Việt",
  "en": "Content in English"
}
```

### 6.2 Các trường hỗ trợ đa ngôn ngữ
- `names`: Tên bài test
- `descriptions`: Mô tả bài test
- `instructions`: Hướng dẫn
- `notes`: Ghi chú
- `questionTexts`: Nội dung câu hỏi
- `hints`: Gợi ý câu hỏi
- `explanations`: Giải thích câu hỏi
- `descriptions` (trong ScoreRangeDto): Mô tả mức độ rủi ro

## 7. API Endpoints

### 7.1 CDDTestSupabaseController
```
POST   /supabase/cdd-tests                    # Tạo bài test mới
GET    /supabase/cdd-tests/{id}              # Lấy bài test theo ID
GET    /supabase/cdd-tests/code/{code}       # Lấy bài test theo mã
GET    /supabase/cdd-tests                   # Lấy tất cả bài test
GET    /supabase/cdd-tests/status/{status}   # Lấy bài test theo trạng thái
GET    /supabase/cdd-tests/category/{category} # Lấy bài test theo danh mục
GET    /supabase/cdd-tests/age/{ageMonths}   # Lấy bài test theo độ tuổi
PUT    /supabase/cdd-tests/{id}              # Cập nhật bài test
DELETE /supabase/cdd-tests/{id}              # Xóa bài test
GET    /supabase/cdd-tests/exists/{code}     # Kiểm tra bài test tồn tại
GET    /supabase/cdd-tests/count             # Đếm số lượng bài test
GET    /supabase/cdd-tests/active            # Lấy bài test đang hoạt động
GET    /supabase/cdd-tests/inactive          # Lấy bài test không hoạt động
```

## 8. Lưu ý quan trọng

### 8.1 Validation
- `assessmentCode`: Phải unique, không được null
- `minAgeMonths` và `maxAgeMonths`: Phải hợp lệ (min <= max)
- `estimatedDuration`: Phải > 0
- `version`: Không được null

### 8.2 Performance
- Indexes được tạo cho các field thường query
- JSON fields cho complex data
- Constraints để đảm bảo data integrity

### 8.3 Extensibility
- Dễ dàng thêm ngôn ngữ mới
- Có thể mở rộng thêm loại câu hỏi
- Linh hoạt trong việc thêm tiêu chí chấm điểm
