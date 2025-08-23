# Xóa External ID khỏi Child Entity

## Tổng quan
Tài liệu này ghi lại việc xóa trường `externalId` khỏi `ChildSupabase` entity và các thay đổi liên quan.

## Các thay đổi đã thực hiện

### 1. ChildSupabase Entity
- **File**: `src/main/java/com/meowcdd/entity/supabase/ChildSupabase.java`
- **Thay đổi**: Xóa trường `externalId` và annotation `@Column(name = "external_id", unique = true, nullable = false)`

### 2. ChildSupabaseService
- **File**: `src/main/java/com/meowcdd/service/ChildSupabaseService.java`
- **Thay đổi**:
  - Xóa method `getChildByExternalId(String externalId)`
  - Xóa dòng `existingChild.setExternalId(childDetails.getExternalId());` trong method `updateChild`

### 3. ChildSupabaseRepository
- **File**: `src/main/java/com/meowcdd/repository/supabase/ChildSupabaseRepository.java`
- **Thay đổi**: Xóa method `findByExternalId(String externalId)`

### 4. ChildSupabaseController
- **File**: `src/main/java/com/meowcdd/controller/ChildSupabaseController.java`
- **Thay đổi**: Xóa endpoint `GET /api/supabase/children/external/{externalId}`

### 5. Database Migration
- **File**: `remove_external_id_migration.sql`
- **Thay đổi**: Script để xóa cột `external_id` khỏi bảng `children`

## Lý do thay đổi
- Đơn giản hóa cấu trúc dữ liệu
- Loại bỏ sự phụ thuộc vào external system
- Sử dụng ID tự động của database làm primary key duy nhất

## Migration Steps

### Bước 1: Chạy Migration Script
```sql
-- Chạy file remove_external_id_migration.sql
```

### Bước 2: Kiểm tra kết quả
```sql
-- Kiểm tra cấu trúc bảng
\d children

-- Kiểm tra dữ liệu
SELECT id, full_name, gender, date_of_birth 
FROM children 
LIMIT 5;
```

### Bước 3: Test API
```bash
# Test tạo child mới (không cần external_id)
curl -X POST http://localhost:8080/api/supabase/children \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Nguyễn Văn A",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01"
  }'

# Test lấy child theo ID
curl -X GET http://localhost:8080/api/supabase/children/1
```

## API Endpoints còn lại

### Child Management
- `POST /api/supabase/children` - Tạo child mới
- `GET /api/supabase/children/{id}` - Lấy child theo ID
- `PUT /api/supabase/children/{id}` - Cập nhật child
- `DELETE /api/supabase/children/{id}` - Xóa child
- `GET /api/supabase/children` - Lấy tất cả children

### Search & Filter
- `GET /api/supabase/children/search?name={name}` - Tìm theo tên
- `GET /api/supabase/children/gender/{gender}` - Tìm theo giới tính
- `GET /api/supabase/children/age?minAge={min}&maxAge={max}` - Tìm theo độ tuổi
- `GET /api/supabase/children/premature` - Tìm trẻ sinh non
- `GET /api/supabase/children/developmental-disorder/{status}` - Tìm theo rối loạn phát triển
- `GET /api/supabase/children/early-intervention` - Tìm trẻ có can thiệp sớm

### Statistics
- `GET /api/supabase/children/stats/gender/{gender}` - Thống kê theo giới tính
- `GET /api/supabase/children/stats/status/{status}` - Thống kê theo trạng thái

### Advanced Search
- `GET /api/supabase/children/birth-date?startDate={start}&endDate={end}` - Tìm theo khoảng ngày sinh
- `GET /api/supabase/children/birth-weight?minWeight={min}&maxWeight={max}` - Tìm theo cân nặng khi sinh

## Lưu ý quan trọng

### Trước khi migration:
1. **Backup database** để đảm bảo an toàn
2. **Kiểm tra dependencies** - đảm bảo không có code nào khác đang sử dụng `externalId`
3. **Test trên môi trường dev** trước khi chạy trên production

### Sau khi migration:
1. **Kiểm tra ứng dụng** hoạt động bình thường
2. **Test tất cả API endpoints** liên quan đến child
3. **Cập nhật documentation** nếu cần

## Rollback Plan
Nếu cần rollback, có thể:
1. Chạy script tạo lại cột `external_id`
2. Restore code từ git history
3. Migrate lại dữ liệu nếu cần

```sql
-- Rollback script (nếu cần)
ALTER TABLE children 
ADD COLUMN external_id VARCHAR(255) UNIQUE;

-- Tạo index
CREATE INDEX idx_children_external_id ON children(external_id);
```
