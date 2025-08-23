# Cập nhật FamilyDevelopmentalIssues từ Enum thành String

## Tổng quan
Tài liệu này ghi lại việc cập nhật thuộc tính `familyDevelopmentalIssues` từ enum `FamilyDevelopmentalIssues` thành kiểu dữ liệu `String` trong `ChildSupabase` entity.

## Lý do thay đổi
- Cho phép lưu trữ thông tin chi tiết hơn về vấn đề phát triển trong gia đình
- Linh hoạt hơn trong việc mô tả các trường hợp cụ thể
- Không bị giới hạn bởi các giá trị enum cố định

## Các thay đổi đã thực hiện

### 1. ChildSupabase Entity
- **File**: `src/main/java/com/meowcdd/entity/supabase/ChildSupabase.java`
- **Thay đổi**:
  - Thay đổi kiểu dữ liệu từ `FamilyDevelopmentalIssues` thành `String`
  - Thay đổi annotation từ `@Enumerated(EnumType.STRING)` thành `@Column(columnDefinition = "TEXT")`
  - Xóa enum `FamilyDevelopmentalIssues`

### 2. Ví dụ dữ liệu
- **File**: `sample_child_data.json`, `sample_children_collection.json`
- **Thay đổi**: Cập nhật giá trị từ enum thành mô tả chi tiết

### 3. Database Migration
- **File**: `update_family_developmental_issues_migration.sql`
- **Thay đổi**: Script để chuyển đổi cột từ enum sang text

## Migration Steps

### Bước 1: Chạy Migration Script
```sql
-- Chạy file update_family_developmental_issues_migration.sql
```

### Bước 2: Kiểm tra kết quả
```sql
-- Kiểm tra cấu trúc cột
SELECT 
    column_name, 
    data_type, 
    is_nullable
FROM information_schema.columns 
WHERE table_name = 'children' 
    AND column_name = 'family_developmental_issues';

-- Kiểm tra dữ liệu
SELECT 
    id, 
    full_name, 
    family_developmental_issues 
FROM children 
LIMIT 5;
```

### Bước 3: Test API
```bash
# Test tạo child mới với familyDevelopmentalIssues dạng string
curl -X POST http://localhost:8080/api/supabase/children \
  -H "Content-Type: application/json" \
  -d '{
    "parentId": 1,
    "fullName": "Nguyễn Văn A",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01",
    "familyDevelopmentalIssues": "Có chị gái từng có vấn đề về phát triển ngôn ngữ, đã được can thiệp sớm và hiện tại đã cải thiện tốt"
  }'
```

## Cấu trúc dữ liệu mới

### Trước khi thay đổi (Enum)
```java
public enum FamilyDevelopmentalIssues {
    YES("Có"),
    NO("Không"),
    UNKNOWN("Không rõ");
}
```

### Sau khi thay đổi (String)
```java
@Column(name = "family_developmental_issues", columnDefinition = "TEXT")
private String familyDevelopmentalIssues; // Mô tả chi tiết về vấn đề phát triển trong gia đình
```

## Ví dụ giá trị mới

### Trước khi thay đổi
```json
{
  "familyDevelopmentalIssues": "YES"
}
```

### Sau khi thay đổi
```json
{
  "familyDevelopmentalIssues": "Có anh trai từng có vấn đề về ngôn ngữ, đã được can thiệp sớm"
}
```

### Các ví dụ khác
- `"Không có ai trong gia đình có vấn đề phát triển"`
- `"Có chị gái từng có vấn đề về phát triển vận động, đã được vật lý trị liệu"`
- `"Có tiền sử gia đình về rối loạn phổ tự kỷ, đã được chẩn đoán và điều trị"`
- `"Không rõ tiền sử gia đình"`
- `"Có em trai đang được theo dõi về chậm phát triển ngôn ngữ"`

## Lợi ích của thay đổi

### 1. Tính linh hoạt
- Có thể mô tả chi tiết các trường hợp cụ thể
- Không bị giới hạn bởi các giá trị enum cố định
- Dễ dàng mở rộng và tùy chỉnh

### 2. Thông tin chi tiết hơn
- Có thể ghi chú về loại vấn đề cụ thể
- Có thể mô tả quá trình can thiệp
- Có thể ghi chú về tình trạng hiện tại

### 3. Dễ dàng tìm kiếm và phân tích
- Có thể tìm kiếm theo từ khóa
- Có thể phân tích nội dung chi tiết
- Hỗ trợ báo cáo và thống kê linh hoạt

## Lưu ý quan trọng

### Trước khi migration:
1. **Backup database** để đảm bảo an toàn
2. **Kiểm tra dữ liệu hiện tại** để đảm bảo migration chính xác
3. **Test trên môi trường dev** trước khi chạy trên production

### Sau khi migration:
1. **Kiểm tra ứng dụng** hoạt động bình thường
2. **Test tất cả API endpoints** liên quan đến child
3. **Cập nhật frontend** để xử lý string thay vì enum
4. **Cập nhật validation** nếu cần
### Validation mới:
- `familyDevelopmentalIssues` có thể là null hoặc empty string
- Không cần validation enum values
- Có thể thêm validation về độ dài nếu cần
## Rollback Plan
Nếu cần rollback, có thể:
1. Restore enum từ git history
2. Chạy script migration ngược
3. Cập nhật lại code
```sql
CREATE TYPE family_developmental_issues_enum AS ENUM ('YES', 'NO', 'UNKNOWN');
ALTER TABLE children 
ADD COLUMN family_developmental_issues_enum family_developmental_issues_enum;
```
