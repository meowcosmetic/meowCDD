# ChildTestRecord Documentation

## Tổng quan

Entity `ChildTestRecord` được thiết kế để lưu trữ thông tin về các bài kiểm tra mà một trẻ đã thực hiện. Entity này cung cấp khả năng theo dõi lịch sử kiểm tra, kết quả, và tiến độ phát triển của trẻ.

## Cấu trúc Entity

### Các trường chính

#### Thông tin cơ bản
- `id` (Long): Primary key
- `externalId` (String): ID từ service khác (unique)
- `childId` (Long): ID của trẻ thực hiện bài test
- `testId` (Long): ID của bài test (CDDTest hoặc AssessmentTest)
- `testType` (TestType): Loại bài test (CDD_TEST, ASSESSMENT_TEST)

#### Thông tin thực hiện
- `testDate` (LocalDateTime): Ngày thực hiện bài test
- `startTime` (LocalDateTime): Thời gian bắt đầu
- `endTime` (LocalDateTime): Thời gian kết thúc
- `status` (Status): Trạng thái (IN_PROGRESS, COMPLETED, ABANDONED, INVALID, REVIEWED)

#### Kết quả
- `totalScore` (Double): Tổng điểm
- `maxScore` (Double): Điểm tối đa có thể đạt
- `percentageScore` (Double): Phần trăm điểm đạt được (tự động tính)
- `resultLevel` (ResultLevel): Mức độ kết quả (EXCELLENT, GOOD, AVERAGE, BELOW_AVERAGE, POOR)
- `interpretation` (String): Diễn giải kết quả

#### Chi tiết câu trả lời
- `questionAnswers` (String): Chi tiết câu trả lời (JSON format)
- `correctAnswers` (Integer): Số câu trả lời đúng
- `totalQuestions` (Integer): Tổng số câu hỏi
- `skippedQuestions` (Integer): Số câu bỏ qua

#### Thông tin bổ sung
- `notes` (String): Ghi chú của người thực hiện
- `environment` (String): Môi trường thực hiện (HOME, CLINIC, SCHOOL)
- `assessor` (String): Người đánh giá
- `parentPresent` (Boolean): Có phụ huynh tham gia không

### Enums

#### TestType
- `CDD_TEST`: Bài test CDD
- `ASSESSMENT_TEST`: Bài đánh giá tổng quát

#### Status
- `IN_PROGRESS`: Đang thực hiện
- `COMPLETED`: Hoàn thành
- `ABANDONED`: Bỏ dở
- `INVALID`: Không hợp lệ
- `REVIEWED`: Đã xem xét

#### ResultLevel
- `EXCELLENT`: Xuất sắc (≥90%)
- `GOOD`: Tốt (80-89%)
- `AVERAGE`: Trung bình (70-79%)
- `BELOW_AVERAGE`: Dưới trung bình (60-69%)
- `POOR`: Kém (<60%)

## API Endpoints

### 1. Tạo Child Test Record
```http
POST /child-test-records
Content-Type: application/json

{
  "externalId": "TEST_RECORD_001",
  "childId": 1,
  "testId": 5,
  "testType": "CDD_TEST",
  "testDate": "2024-01-15T10:30:00",
  "startTime": "2024-01-15T10:30:00",
  "endTime": "2024-01-15T11:15:00",
  "status": "COMPLETED",
  "totalScore": 85.5,
  "maxScore": 100.0,
  "interpretation": "Trẻ có khả năng phát triển tốt",
  "correctAnswers": 17,
  "totalQuestions": 20,
  "skippedQuestions": 0,
  "environment": "CLINIC",
  "assessor": "Dr. Nguyen Van A",
  "parentPresent": true
}
```

### 2. Lấy Child Test Record theo ID
```http
GET /child-test-records/{id}
```

### 3. Lấy Child Test Record theo External ID
```http
GET /child-test-records/external/{externalId}
```

### 4. Lấy tất cả Child Test Records
```http
GET /child-test-records
```

### 5. Lấy Child Test Records theo Child ID
```http
GET /child-test-records/child/{childId}
```

### 6. Lấy Child Test Records theo Test ID
```http
GET /child-test-records/test/{testId}
```

### 7. Lấy Child Test Records theo Test Type
```http
GET /child-test-records/test-type/{testType}
```

### 8. Lấy Child Test Records theo Status
```http
GET /child-test-records/status/{status}
```

### 9. Lấy Child Test Records theo Child ID và Test Type
```http
GET /child-test-records/child/{childId}/test-type/{testType}
```

### 10. Lấy Child Test Records theo Child ID và Status
```http
GET /child-test-records/child/{childId}/status/{status}
```

### 11. Lấy Child Test Records theo khoảng thời gian
```http
GET /child-test-records/date-range?startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59
```

### 12. Lấy Child Test Records theo Result Level
```http
GET /child-test-records/result-level/{resultLevel}
```

### 13. Lấy Child Test Records theo khoảng điểm
```http
GET /child-test-records/score-range?minScore=80&maxScore=100
```

### 14. Cập nhật Child Test Record
```http
PUT /child-test-records/{id}
Content-Type: application/json

{
  "externalId": "TEST_RECORD_001_UPDATED",
  "status": "REVIEWED",
  "interpretation": "Kết quả đã được xem xét và cập nhật"
}
```

### 15. Xóa Child Test Record
```http
DELETE /child-test-records/{id}
```

### 16. Kiểm tra Child Test Record tồn tại
```http
GET /child-test-records/exists/{externalId}
```

### 17. Kiểm tra Child Test Record theo Child ID và Test ID
```http
GET /child-test-records/child/{childId}/test/{testId}/exists
```

### 18. Thống kê
```http
# Số bài test đã hoàn thành của một trẻ
GET /child-test-records/child/{childId}/completed-count

# Điểm trung bình của một trẻ
GET /child-test-records/child/{childId}/average-score

# Ngày test cuối cùng của một trẻ
GET /child-test-records/child/{childId}/last-test-date
```

## Ví dụ sử dụng

### Tạo một Child Test Record mới
```bash
curl -X POST http://localhost:8080/child-test-records \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "TEST_RECORD_001",
    "childId": 1,
    "testId": 5,
    "testType": "CDD_TEST",
    "testDate": "2024-01-15T10:30:00",
    "startTime": "2024-01-15T10:30:00",
    "endTime": "2024-01-15T11:15:00",
    "status": "COMPLETED",
    "totalScore": 85.5,
    "maxScore": 100.0,
    "interpretation": "Trẻ có khả năng phát triển tốt",
    "correctAnswers": 17,
    "totalQuestions": 20,
    "skippedQuestions": 0,
    "environment": "CLINIC",
    "assessor": "Dr. Nguyen Van A",
    "parentPresent": true
  }'
```

### Lấy tất cả test records của một trẻ
```bash
curl -X GET http://localhost:8080/child-test-records/child/1
```

### Lấy test records theo loại test
```bash
curl -X GET http://localhost:8080/child-test-records/test-type/CDD_TEST
```

### Cập nhật trạng thái test record
```bash
curl -X PUT http://localhost:8080/child-test-records/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "REVIEWED",
    "interpretation": "Kết quả đã được xem xét"
  }'
```

## Database Migration

Chạy script migration để tạo bảng và dữ liệu mẫu:

```sql
-- Chạy file child_test_record_migration.sql
```

Script này sẽ:
1. Tạo bảng `child_test_records` với tất cả các cột cần thiết
2. Tạo các indexes cho hiệu suất truy vấn
3. Thêm các constraints để đảm bảo tính toàn vẹn dữ liệu
4. Chèn dữ liệu mẫu để test

## Tính năng đặc biệt

### 1. Tự động tính toán
- `percentageScore`: Tự động tính dựa trên `totalScore` và `maxScore`
- `resultLevel`: Tự động xác định dựa trên `percentageScore`

### 2. Lifecycle hooks
- `@PrePersist`: Tự động set `testDate` và `status` mặc định
- `@PreUpdate`: Tự động tính lại `percentageScore` và `resultLevel`

### 3. Utility methods
- `isCompleted()`: Kiểm tra đã hoàn thành chưa
- `getDuration()`: Tính thời gian làm bài (phút)

## Lưu ý quan trọng

1. **External ID**: Phải unique, dùng để đồng bộ với hệ thống khác
2. **Test ID**: Liên kết với CDDTest hoặc AssessmentTest
3. **Validation**: Các trường bắt buộc được validate ở DTO level
4. **Performance**: Có nhiều indexes để tối ưu truy vấn
5. **Data Integrity**: Có check constraints để đảm bảo dữ liệu hợp lệ

## Lợi ích

1. **Theo dõi tiến độ**: Có thể theo dõi sự tiến bộ của trẻ qua thời gian
2. **Phân tích**: Có thể phân tích kết quả theo nhiều tiêu chí khác nhau
3. **Báo cáo**: Dễ dàng tạo báo cáo thống kê
4. **Linh hoạt**: Hỗ trợ nhiều loại test khác nhau
5. **Hiệu suất**: Được tối ưu cho truy vấn nhanh
