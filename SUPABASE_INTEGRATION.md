# Supabase Integration Guide

## Tổng quan

Dự án đã được tích hợp với Supabase để cung cấp khả năng lưu trữ và đồng bộ dữ liệu đám mây. Supabase cung cấp PostgreSQL database, authentication, real-time subscriptions, và storage.

**Có 2 cách sử dụng Supabase:**
1. **REST API** - Gọi API để tương tác với dữ liệu
2. **Direct SQL** - Kết nối trực tiếp với PostgreSQL database như database thông thường

## Cấu hình

### 1. Cập nhật application.properties

Thêm thông tin Supabase vào file `src/main/resources/application.properties`:

```properties
# Supabase Configuration
supabase.url=https://smgcifigqidmwjtfgvim.supabase.co
supabase.anon-key=your-anon-key-here
supabase.service-role-key=your-service-role-key-here
```

### 2. Lấy API Keys và Database Info từ Supabase

1. Đăng nhập vào [Supabase Dashboard](https://app.supabase.com)
2. Chọn project của bạn
3. Vào Settings > API
4. Copy các keys:
   - **URL**: Project URL
   - **anon public**: Anon public key
   - **service_role secret**: Service role key (có quyền cao hơn)

5. Vào Settings > Database để lấy thông tin database:
   - **Host**: Database host
   - **Database name**: postgres
   - **Port**: 5432
   - **User**: postgres
   - **Password**: Database password

## Cấu trúc Database

### Bảng cần tạo trong Supabase

#### 1. cdd_tests
```sql
CREATE TABLE cdd_tests (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    assessment_code VARCHAR(50) UNIQUE NOT NULL,
    names JSONB,
    descriptions JSONB,
    instructions JSONB,
    category VARCHAR(100),
    target_age_group VARCHAR(50),
    min_age_months INTEGER,
    max_age_months INTEGER,
    status VARCHAR(20),
    version VARCHAR(20),
    estimated_duration INTEGER,
    questions JSONB,
    scoring_criteria JSONB,
    administration_type VARCHAR(50),
    required_qualifications TEXT,
    required_materials JSONB,
    notes JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
```

#### 2. cdd_test_results
```sql
CREATE TABLE cdd_test_results (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    child_id VARCHAR(100),
    assessment_id VARCHAR(100),
    assessment_code VARCHAR(50),
    administrator_id VARCHAR(100),
    start_time TIMESTAMP WITH TIME ZONE,
    end_time TIMESTAMP WITH TIME ZONE,
    duration_minutes INTEGER,
    answers JSONB,
    total_score INTEGER,
    risk_level VARCHAR(20),
    interpretation TEXT,
    notes TEXT,
    parent_comments TEXT,
    status VARCHAR(20),
    additional_data JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
```

## API Endpoints

### 1. Supabase REST API Access
- `GET /api/supabase/test` - Test kết nối
- `GET /api/supabase/{table}` - Lấy dữ liệu từ bảng
- `POST /api/supabase/{table}` - Thêm dữ liệu vào bảng
- `PUT /api/supabase/{table}` - Cập nhật dữ liệu
- `DELETE /api/supabase/{table}` - Xóa dữ liệu
- `POST /api/supabase/rpc/{functionName}` - Gọi RPC function

### 2. Supabase Direct SQL Access (JPA)
- `GET /api/supabase/children` - Lấy tất cả trẻ
- `POST /api/supabase/children` - Tạo trẻ mới
- `GET /api/supabase/children/{id}` - Lấy trẻ theo ID
- `PUT /api/supabase/children/{id}` - Cập nhật trẻ
- `DELETE /api/supabase/children/{id}` - Xóa trẻ
- `GET /api/supabase/children/search?name={name}` - Tìm trẻ theo tên
- `GET /api/supabase/children/gender/{gender}` - Tìm trẻ theo giới tính
- `GET /api/supabase/children/age?minAge={min}&maxAge={max}` - Tìm trẻ theo độ tuổi
- `GET /api/supabase/children/premature` - Tìm trẻ sinh non
- `GET /api/supabase/children/developmental-disorder/{status}` - Tìm trẻ có rối loạn phát triển
- `GET /api/supabase/children/early-intervention` - Tìm trẻ có can thiệp sớm
- `GET /api/supabase/children/stats/gender/{gender}` - Thống kê theo giới tính
- `GET /api/supabase/children/stats/status/{status}` - Thống kê theo trạng thái

### 3. Data Sync
- `POST /api/sync/all` - Đồng bộ tất cả dữ liệu
- `POST /api/sync/cdd-tests` - Đồng bộ CDDTests
- `POST /api/sync/cdd-test-results` - Đồng bộ CDDTestResults
- `GET /api/sync/compare` - So sánh dữ liệu
- `GET /api/sync/status` - Trạng thái đồng bộ

## Sử dụng

### 1. Test kết nối
```bash
curl http://localhost:8080/api/v1/api/supabase/test
```

### 2. Đồng bộ dữ liệu
```bash
# Đồng bộ tất cả
curl -X POST http://localhost:8080/api/v1/api/sync/all

# Đồng bộ CDDTests
curl -X POST http://localhost:8080/api/v1/api/sync/cdd-tests

# So sánh dữ liệu
curl http://localhost:8080/api/v1/api/sync/compare
```

### 3. Truy cập trực tiếp Supabase (REST API)
```bash
# Lấy tất cả CDDTests
curl http://localhost:8080/api/v1/api/supabase/cdd_tests

# Thêm CDDTest mới
curl -X POST http://localhost:8080/api/v1/api/supabase/cdd_tests \
  -H "Content-Type: application/json" \
  -d '{"assessment_code":"TEST-001","names":{"vi":"Bài kiểm tra 1","en":"Test 1"}}'
```

### 4. Sử dụng Supabase như SQL Database
```bash
# Tạo trẻ mới
curl -X POST http://localhost:8080/api/v1/api/supabase/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD-001",
    "fullName": "Nguyễn Văn A",
    "gender": "MALE",
    "dateOfBirth": "2020-01-15",
    "isPremature": false,
    "birthWeightGrams": 3200,
    "primaryLanguage": "Tiếng Việt",
    "familyDevelopmentalIssues": "NO"
  }'

# Lấy tất cả trẻ
curl http://localhost:8080/api/v1/api/supabase/children

# Tìm trẻ theo tên
curl "http://localhost:8080/api/v1/api/supabase/children/search?name=Nguyễn"

# Tìm trẻ theo giới tính
curl http://localhost:8080/api/v1/api/supabase/children/gender/MALE

# Tìm trẻ theo độ tuổi (12-36 tháng)
curl "http://localhost:8080/api/v1/api/supabase/children/age?minAge=12&maxAge=36"

# Thống kê theo giới tính
curl http://localhost:8080/api/v1/api/supabase/children/stats/gender/MALE
```

## Row Level Security (RLS)

Để bảo mật dữ liệu, bạn có thể bật RLS trong Supabase:

```sql
-- Bật RLS cho bảng
ALTER TABLE cdd_tests ENABLE ROW LEVEL SECURITY;
ALTER TABLE cdd_test_results ENABLE ROW LEVEL SECURITY;

-- Tạo policies
CREATE POLICY "Allow read access" ON cdd_tests FOR SELECT USING (true);
CREATE POLICY "Allow insert access" ON cdd_tests FOR INSERT WITH CHECK (true);
CREATE POLICY "Allow update access" ON cdd_tests FOR UPDATE USING (true);
```

## Monitoring

### Logs
Tất cả các hoạt động với Supabase được log với level DEBUG:
```properties
logging.level.com.meowcdd.service.SupabaseService=DEBUG
logging.level.com.meowcdd.service.DataSyncService=DEBUG
```

### Error Handling
Service tự động xử lý lỗi và log chi tiết:
- Connection errors
- Authentication errors
- Data validation errors
- Sync conflicts

## Best Practices

1. **API Keys**: Không commit API keys vào git, sử dụng environment variables
2. **Rate Limiting**: Supabase có rate limits, implement retry logic nếu cần
3. **Data Validation**: Validate dữ liệu trước khi gửi đến Supabase
4. **Backup**: Regular backup của dữ liệu MongoDB
5. **Monitoring**: Monitor sync status và error rates

## Troubleshooting

### Common Issues

1. **Connection refused**: Kiểm tra URL và network connectivity
2. **Authentication failed**: Kiểm tra API keys
3. **Data sync failed**: Kiểm tra data format và RLS policies
4. **Rate limit exceeded**: Implement exponential backoff

### Debug Commands
```bash
# Kiểm tra cấu hình
curl http://localhost:8080/api/v1/api/supabase/test

# Kiểm tra trạng thái sync
curl http://localhost:8080/api/v1/api/sync/status

# So sánh dữ liệu
curl http://localhost:8080/api/v1/api/sync/compare
```
