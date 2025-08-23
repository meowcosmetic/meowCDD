# CDD Test API - Filter theo độ tuổi cụ thể và phân trang

## Tổng quan
API đã được cập nhật để hỗ trợ filter theo độ tuổi cụ thể (tháng) và phân trang. Logic: tìm những bài test có khoảng độ tuổi bao gồm số tháng được truyền vào (minAgeMonths <= ageMonths <= maxAgeMonths).

## Các API mới

### 1. Filter theo độ tuổi cụ thể (không phân trang)

#### 1.1. Filter theo độ tuổi cụ thể
```http
GET /supabase/cdd-tests/age/{ageMonths}
```
**Tham số:**
- `ageMonths` (Integer): Độ tuổi cụ thể (tháng)

**Response:** Danh sách tất cả bài test phù hợp với độ tuổi

### 2. Filter theo độ tuổi cụ thể với phân trang

#### 2.1. Filter theo độ tuổi cụ thể với phân trang
```http
GET /supabase/cdd-tests/age/{ageMonths}/paginated?page=0&size=10
```
**Tham số:**
- `ageMonths` (Integer): Độ tuổi cụ thể (tháng)
- `page` (Integer, optional): Số trang (mặc định: 0)
- `size` (Integer, optional): Số item mỗi trang (mặc định: 10)

#### 2.2. Filter theo độ tuổi cụ thể và trạng thái với phân trang
```http
GET /supabase/cdd-tests/age/{ageMonths}/status/{status}/paginated?page=0&size=10
```
**Tham số:**
- `ageMonths` (Integer): Độ tuổi cụ thể (tháng)
- `status` (String): Trạng thái (ACTIVE, INACTIVE)
- `page` (Integer, optional): Số trang (mặc định: 0)
- `size` (Integer, optional): Số item mỗi trang (mặc định: 10)

#### 2.3. Filter theo độ tuổi cụ thể và danh mục với phân trang
```http
GET /supabase/cdd-tests/age/{ageMonths}/category/{category}/paginated?page=0&size=10
```
**Tham số:**
- `ageMonths` (Integer): Độ tuổi cụ thể (tháng)
- `category` (String): Danh mục bài test
- `page` (Integer, optional): Số trang (mặc định: 0)
- `size` (Integer, optional): Số item mỗi trang (mặc định: 10)

#### 2.3.1. Filter theo độ tuổi cụ thể, danh mục và trạng thái với phân trang
```http
GET /supabase/cdd-tests/age/{ageMonths}/category/{category}/status/{status}/paginated?page=0&size=10
```
**Tham số:**
- `ageMonths` (Integer): Độ tuổi cụ thể (tháng)
- `category` (String): Danh mục bài test
- `status` (String): Trạng thái (ACTIVE, INACTIVE)
- `page` (Integer, optional): Số trang (mặc định: 0)
- `size` (Integer, optional): Số item mỗi trang (mặc định: 10)

#### 2.4. Filter theo độ tuổi cụ thể, trạng thái và danh mục với phân trang
```http
GET /supabase/cdd-tests/age/{ageMonths}/filter/paginated?status=ACTIVE&category=communication&page=0&size=10
```
**Tham số:**
- `ageMonths` (Integer): Độ tuổi cụ thể (tháng)
- `status` (String): Trạng thái (ACTIVE, INACTIVE)
- `category` (String): Danh mục bài test
- `page` (Integer, optional): Số trang (mặc định: 0)
- `size` (Integer, optional): Số item mỗi trang (mặc định: 10)

#### 2.5. Filter theo danh mục và trạng thái với phân trang
```http
GET /supabase/cdd-tests/category/{category}/status/{status}/paginated?page=0&size=10
```
**Tham số:**
- `category` (String): Danh mục bài test
- `status` (String): Trạng thái (ACTIVE, INACTIVE)
- `page` (Integer, optional): Số trang (mặc định: 0)
- `size` (Integer, optional): Số item mỗi trang (mặc định: 10)

**Response:**
```json
{
  "content": [
    {
      "id": "1",
      "assessmentCode": "TEST001",
      "category": "communication",
      "minAgeMonths": 24,
      "maxAgeMonths": 60,
      "status": "ACTIVE",
      // ... other fields
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 25,
  "totalPages": 3,
  "hasNext": true,
  "hasPrevious": false,
  "isFirst": true,
  "isLast": false
}
```

## Ví dụ sử dụng

### Ví dụ 1: Tìm bài test cho trẻ 36 tháng tuổi
```bash
curl -X GET "http://localhost:8080/supabase/cdd-tests/age/36"
```

### Ví dụ 2: Tìm bài test active cho trẻ 48 tháng tuổi với phân trang
```bash
curl -X GET "http://localhost:8080/supabase/cdd-tests/age/48/status/ACTIVE/paginated?page=0&size=5"
```

### Ví dụ 3: Tìm bài test communication cho trẻ 24 tháng tuổi
```bash
curl -X GET "http://localhost:8080/supabase/cdd-tests/age/24/category/communication/paginated?page=0&size=10"
```

### Ví dụ 3.1: Tìm bài test theo độ tuổi, danh mục và trạng thái
```bash
curl -X GET "http://localhost:8080/supabase/cdd-tests/age/33/category/DEVELOPMENTAL_SCREENING/status/ACTIVE/paginated?page=0&size=10"
```

### Ví dụ 4: Tìm bài test với filter đầy đủ cho trẻ 60 tháng tuổi
```bash
curl -X GET "http://localhost:8080/supabase/cdd-tests/age/60/filter/paginated?status=ACTIVE&category=communication&page=0&size=10"
```

### Ví dụ 5: Tìm bài test theo danh mục và trạng thái
```bash
curl -X GET "http://localhost:8080/supabase/cdd-tests/category/DEVELOPMENTAL_SCREENING/status/ACTIVE/paginated?page=0&size=10"
```

## Lưu ý

1. **Độ tuổi tính theo tháng**: Tất cả tham số độ tuổi đều tính theo tháng
2. **Phân trang**: 
   - `page` bắt đầu từ 0
   - `size` mặc định là 10, tối đa có thể là 100
3. **Trạng thái**: Chỉ chấp nhận `ACTIVE` hoặc `INACTIVE`
4. **Logic filter**: Tìm các bài test có `minAgeMonths <= ageMonths <= maxAgeMonths`

## API cũ vẫn hoạt động

API cũ filter theo độ tuổi cụ thể vẫn hoạt động:
```http
GET /supabase/cdd-tests/age/{ageMonths}
```

## Error Handling

- **400 Bad Request**: Khi tham số `status` không hợp lệ
- **200 OK**: Khi tìm thấy kết quả
- **200 OK với content rỗng**: Khi không tìm thấy kết quả nào
