# Supabase Password Update Guide

## 📋 Cấu hình hiện tại

**Link PostgreSQL:** `postgresql://postgres:[YOUR-PASSWORD]@db.smgcifigqidmwjtfgvim.supabase.co:5432/postgres`

**File cấu hình:** `src/main/resources/application.properties`

## 🔧 Cách cập nhật password

### Bước 1: Thay thế [YOUR-PASSWORD]

Trong file `application.properties`, thay thế `[YOUR-PASSWORD]` bằng password thật của bạn:

```properties
# Supabase PostgreSQL Configuration
spring.datasource.supabase.url=jdbc:postgresql://db.smgcifigqidmwjtfgvim.supabase.co:5432/postgres
spring.datasource.supabase.username=postgres
spring.datasource.supabase.password=YOUR_ACTUAL_PASSWORD_HERE
spring.datasource.supabase.driver-class-name=org.postgresql.Driver
```

### Bước 2: Ví dụ cấu hình

```properties
# Ví dụ với password thật
spring.datasource.supabase.password=my-supabase-password-123
```

## 🔒 Bảo mật

### ⚠️ Lưu ý quan trọng:

1. **Không commit password vào git**
2. **Sử dụng environment variables cho production**
3. **Bảo vệ file application.properties**

### 🔐 Cách bảo mật cho Production:

#### Sử dụng Environment Variables:
```properties
spring.datasource.supabase.password=${SUPABASE_DB_PASSWORD}
```

#### Hoặc tạo file riêng (không commit):
```properties
# application-local.properties (không commit)
spring.datasource.supabase.password=your-actual-password
```

## 🧪 Test kết nối

### Chạy script test:
```bash
chmod +x test_supabase_connection.sh
./test_supabase_connection.sh
```

### Kiểm tra logs:
```bash
# Xem logs khi khởi động ứng dụng
tail -f logs/application.log
```

## 📋 Các bước kiểm tra

1. ✅ **Cập nhật password** trong `application.properties`
2. 🔄 **Khởi động ứng dụng** và kiểm tra logs
3. 🧪 **Chạy test script** để kiểm tra kết nối
4. 📊 **Kiểm tra Supabase Dashboard** để xem dữ liệu

## 🚨 Troubleshooting

### Lỗi thường gặp:

1. **"Authentication failed"**
   - Kiểm tra password có đúng không
   - Kiểm tra username có đúng không

2. **"Connection refused"**
   - Kiểm tra URL có đúng không
   - Kiểm tra firewall/network

3. **"SSL connection required"**
   - Thêm SSL parameters vào URL:
   ```properties
   spring.datasource.supabase.url=jdbc:postgresql://db.smgcifigqidmwjtfgvim.supabase.co:5432/postgres?sslmode=require
   ```

## 📞 Hỗ trợ

Nếu gặp vấn đề:
1. Kiểm tra Supabase Dashboard
2. Xem logs ứng dụng
3. Kiểm tra network connection
4. Liên hệ support nếu cần
