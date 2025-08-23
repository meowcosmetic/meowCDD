# Thêm Parent ID vào Child Entity

## Tổng quan
Tài liệu này ghi lại việc thêm trường `parentId` vào `ChildSupabase` entity và các thay đổi liên quan.

## Các thay đổi đã thực hiện

### 1. ChildSupabase Entity
- **File**: `src/main/java/com/meowcdd/entity/supabase/ChildSupabase.java`
- **Thay đổi**: Thêm trường `parentId` với annotation `@Column(name = "parent_id", nullable = false)`

### 2. ChildSupabaseService
- **File**: `src/main/java/com/meowcdd/service/ChildSupabaseService.java`
- **Thay đổi**:
  - Thêm method `getChildrenByParentId(Long parentId)`
  - Cập nhật method `updateChild` để include `parentId`

### 3. ChildSupabaseRepository
- **File**: `src/main/java/com/meowcdd/repository/supabase/ChildSupabaseRepository.java`
- **Thay đổi**: Thêm method `findByParentId(Long parentId)`

### 4. ChildSupabaseController
- **File**: `src/main/java/com/meowcdd/controller/ChildSupabaseController.java`
- **Thay đổi**: Thêm endpoint `GET /api/supabase/children/parent/{parentId}`

### 5. Database Migration
- **File**: `add_parent_id_migration.sql`
- **Thay đổi**: Script để thêm cột `parent_id` vào bảng `children`

### 6. Ví dụ dữ liệu
- **File**: `sample_child_data.json`, `sample_children_collection.json`
- **Thay đổi**: Thêm trường `parentId` vào tất cả ví dụ

## Lý do thay đổi
- Liên kết child với parent để quản lý quan hệ gia đình
- Cho phép tìm kiếm và lọc trẻ theo phụ huynh
- Hỗ trợ tính năng quản lý nhiều trẻ cho một phụ huynh

## Migration Steps

### Bước 1: Chạy Migration Script
```sql
-- Chạy file add_parent_id_migration.sql
```

### Bước 2: Kiểm tra kết quả
```sql
-- Kiểm tra cấu trúc bảng
\d children

-- Kiểm tra dữ liệu
SELECT id, parent_id, full_name, gender, date_of_birth 
FROM children 
LIMIT 5;
```

### Bước 3: Test API
```bash
# Test tạo child mới với parent_id
curl -X POST http://localhost:8080/api/supabase/children \
  -H "Content-Type: application/json" \
  -d '{
    "parentId": 1,
    "fullName": "Nguyễn Văn A",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01"
  }'

# Test lấy child theo parent ID
curl -X GET http://localhost:8080/api/supabase/children/parent/1
```

## API Endpoints cập nhật

### Child Management
- `POST /api/supabase/children` - Tạo child mới (cần parentId)
- `GET /api/supabase/children/{id}` - Lấy child theo ID
- `PUT /api/supabase/children/{id}` - Cập nhật child (có thể update parentId)
- `DELETE /api/supabase/children/{id}` - Xóa child
- `GET /api/supabase/children` - Lấy tất cả children

### Parent-related Endpoints
- `GET /api/supabase/children/parent/{parentId}` - Lấy tất cả trẻ của một phụ huynh

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

## Cấu trúc dữ liệu mới

### Child Entity với Parent ID
```java
@Entity
@Table(name = "children")
public class ChildSupabase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "parent_id", nullable = false)
    private Long parentId; // ID của phụ huynh
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    // ... các trường khác
}
```

### JSON Request/Response
```json
{
  "parentId": 1,
  "fullName": "Nguyễn Hoàng Minh",
  "gender": "MALE",
  "dateOfBirth": "2020-03-15",
  "isPremature": false,
  "birthWeightGrams": 3200,
  "specialMedicalConditions": "Không có tình trạng y tế đặc biệt",
  "developmentalDisorderDiagnosis": "NOT_EVALUATED",
  "hasEarlyIntervention": false,
  "primaryLanguage": "Tiếng Việt",
  "familyDevelopmentalIssues": "NO",
  "height": 95.5,
  "weight": 14.2,
  "bloodType": "A+",
  "allergies": "Không có dị ứng",
  "medicalHistory": "Tiêm chủng đầy đủ theo lịch, không có bệnh lý đặc biệt",
  "status": "ACTIVE"
}
```

## Lưu ý quan trọng

### Trước khi migration:
1. **Backup database** để đảm bảo an toàn
2. **Kiểm tra parent table** - đảm bảo có dữ liệu parent trước khi thêm parent_id
3. **Test trên môi trường dev** trước khi chạy trên production

### Sau khi migration:
1. **Kiểm tra ứng dụng** hoạt động bình thường
2. **Test tất cả API endpoints** liên quan đến child
3. **Cập nhật frontend** để include parentId trong requests
4. **Cập nhật documentation** nếu cần

### Validation mới:
- `parentId` phải tồn tại trong parent table (nếu có foreign key constraint)
- `parentId` không được null
- Mỗi child phải thuộc về một parent

## Rollback Plan
Nếu cần rollback, có thể:
1. Chạy script xóa cột `parent_id`
2. Restore code từ git history
3. Migrate lại dữ liệu nếu cần

```sql
-- Rollback script (nếu cần)
ALTER TABLE children DROP COLUMN IF EXISTS parent_id;
DROP INDEX IF EXISTS idx_children_parent_id;
```
