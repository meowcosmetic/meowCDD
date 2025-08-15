# Supabase Configuration Fix

## 📋 Vấn đề đã được sửa

**Lỗi:** `org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'supabaseEntityManagerFactory' defined in class path resource [com/meowcdd/config/SupabaseDatabaseConfig.class]: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName.`

## 🔍 Nguyên nhân

`DataSourceBuilder` cần `jdbcUrl` nhưng cấu hình `@ConfigurationProperties` không cung cấp đúng format.

## 🛠️ Cách sửa

### Trước (Lỗi):
```java
@Primary
@Bean(name = "supabaseDataSource")
@ConfigurationProperties(prefix = "spring.datasource.supabase")
public DataSource supabaseDataSource() {
    return DataSourceBuilder.create().build();
}
```

### Sau (Đã sửa):
```java
@Primary
@Bean(name = "supabaseDataSource")
public DataSource supabaseDataSource(
        @Value("${spring.datasource.supabase.url}") String url,
        @Value("${spring.datasource.supabase.username}") String username,
        @Value("${spring.datasource.supabase.password}") String password,
        @Value("${spring.datasource.supabase.driver-class-name}") String driverClassName) {
    return DataSourceBuilder.create()
            .url(url)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build();
}
```

## 📋 Cấu hình application.properties

```properties
# Supabase PostgreSQL Configuration
spring.datasource.supabase.url=jdbc:postgresql://db.smgcifigqidmwjtfgvim.supabase.co:5432/postgres
spring.datasource.supabase.username=postgres
spring.datasource.supabase.password=your-db-password-here
spring.datasource.supabase.driver-class-name=org.postgresql.Driver

# JPA Configuration for Supabase
spring.jpa.supabase.hibernate.ddl-auto=update
spring.jpa.supabase.show-sql=true
spring.jpa.supabase.properties.hibernate.format_sql=true
spring.jpa.supabase.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 🔧 Cách lấy thông tin Supabase

### 1. Truy cập Supabase Dashboard
- Đăng nhập vào https://supabase.com
- Chọn project của bạn

### 2. Lấy Database URL
- Vào **Settings** → **Database**
- Copy **Connection string** (URI)

### 3. Lấy Database Password
- Vào **Settings** → **Database**
- Copy **Database password**

### 4. Cập nhật application.properties
```properties
spring.datasource.supabase.url=jdbc:postgresql://db.YOUR_PROJECT_REF.supabase.co:5432/postgres
spring.datasource.supabase.username=postgres
spring.datasource.supabase.password=YOUR_ACTUAL_PASSWORD
spring.datasource.supabase.driver-class-name=org.postgresql.Driver
```

## 🧪 Test

Chạy script test để kiểm tra:
```bash
chmod +x test_supabase_config_fix.sh
./test_supabase_config_fix.sh
```

## 📝 Lưu ý quan trọng

- **Bảo mật:** Không commit password thật vào git
- **Environment Variables:** Sử dụng environment variables cho production
- **Connection Pool:** Có thể cần cấu hình connection pool cho production
- **SSL:** Supabase yêu cầu SSL connection

## 🔒 Bảo mật cho Production

### Sử dụng Environment Variables:
```properties
spring.datasource.supabase.url=${SUPABASE_DB_URL}
spring.datasource.supabase.username=${SUPABASE_DB_USERNAME}
spring.datasource.supabase.password=${SUPABASE_DB_PASSWORD}
spring.datasource.supabase.driver-class-name=${SUPABASE_DB_DRIVER}
```

### Hoặc sử dụng Spring Cloud Config:
```yaml
spring:
  cloud:
    config:
      uri: ${CONFIG_SERVER_URL}
      label: ${CONFIG_LABEL}
```

## 🚀 Các bước tiếp theo

1. **Cập nhật credentials** trong `application.properties`
2. **Test connection** với Supabase
3. **Tạo tables** nếu cần thiết
4. **Enable data sync** nếu muốn đồng bộ dữ liệu
