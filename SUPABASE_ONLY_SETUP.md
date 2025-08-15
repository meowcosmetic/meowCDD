# Supabase Only Configuration

## 📋 Cấu hình hiện tại

**Database duy nhất:** Supabase PostgreSQL
**Các database khác:** Đã tạm thời disable

## 🔧 Thay đổi đã thực hiện

### 1. Application Properties
- ✅ **Loại bỏ MongoDB configuration**
- ✅ **Loại bỏ MySQL configuration**
- ✅ **Loại bỏ H2 configuration**
- ✅ **Cấu hình Supabase làm primary database**

### 2. Controllers đã disable
- ❌ `CDDTestController` (MongoDB)
- ❌ `CDDTestResultController` (MongoDB)
- ❌ `AssessmentTestController` (MongoDB)
- ❌ `ProgressReportController` (MongoDB)
- ❌ `DevelopmentalDisorderQuestionController` (MongoDB)

### 3. Services đã disable
- ❌ `CDDTestService` (MongoDB)
- ❌ `CDDTestResultService` (MongoDB)
- ❌ `AssessmentTestService` (MongoDB)
- ❌ `DevelopmentalDisorderQuestionService` (MongoDB)

### 4. Supabase Configuration
- ✅ **Primary DataSource:** Supabase PostgreSQL
- ✅ **JPA Configuration:** PostgreSQL dialect
- ✅ **Entity scanning:** Cả `com.meowcdd.entity` và `com.meowcdd.entity.supabase`

## 🚀 Cách khởi động

### 1. Khởi động ứng dụng
```bash
mvn spring-boot:run
```

### 2. Test kết nối
```bash
chmod +x test_supabase_only.sh
./test_supabase_only.sh
```

## 📊 API Endpoints hoạt động

### Supabase Endpoints:
- `POST /api/v1/api/supabase/children` - Tạo child
- `GET /api/v1/api/supabase/children` - Lấy tất cả children
- `GET /api/v1/api/supabase/children/{id}` - Lấy child theo ID
- `PUT /api/v1/api/supabase/children/{id}` - Cập nhật child
- `DELETE /api/v1/api/supabase/children/{id}` - Xóa child

- `POST /api/v1/api/supabase/parents` - Tạo parent
- `GET /api/v1/api/supabase/parents` - Lấy tất cả parents
- `GET /api/v1/api/supabase/parents/{id}` - Lấy parent theo ID
- `PUT /api/v1/api/supabase/parents/{id}` - Cập nhật parent
- `DELETE /api/v1/api/supabase/parents/{id}` - Xóa parent

## 🔄 Cách enable lại các tính năng khác

### Enable MongoDB:
1. Uncomment MongoDB configuration trong `application.properties`
2. Uncomment `@Service` trong các MongoDB services
3. Uncomment `@RestController` trong các MongoDB controllers

### Enable MySQL:
1. Uncomment MySQL configuration trong `application.properties`
2. Cập nhật `SupabaseDatabaseConfig` để không conflict

## 🧪 Test Scripts

### Test Supabase Only:
```bash
./test_supabase_only.sh
```

### Test đầy đủ (khi enable lại):
```bash
./test_supabase_connection.sh
```

## 📝 Lưu ý quan trọng

1. **Chỉ Supabase hoạt động** - Các database khác đã disable
2. **Entities:** Chỉ `Child` và `Parent` entities hoạt động
3. **MongoDB Documents:** Đã disable tạm thời
4. **Data Sync:** Đã disable (không cần thiết khi chỉ dùng Supabase)

## 🚨 Troubleshooting

### Lỗi thường gặp:

1. **"No qualifying bean of type"**
   - Kiểm tra các services đã được comment out chưa

2. **"Table not found"**
   - Kiểm tra Supabase connection
   - Kiểm tra JPA auto-create tables

3. **"Connection refused"**
   - Kiểm tra Supabase credentials
   - Kiểm tra network connection

## 🔒 Bảo mật

- **Password:** Đã cấu hình trong `application.properties`
- **SSL:** Đã enable với `?sslmode=require`
- **Production:** Nên sử dụng environment variables
