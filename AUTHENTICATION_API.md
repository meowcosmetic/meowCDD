# 🔐 MeowCDD Authentication API

## ⚠️ **LƯU Ý QUAN TRỌNG**
**Authentication hiện tại đã được DISABLE để dễ dàng test API. Tất cả endpoints đều có thể truy cập mà không cần login.**

## Tổng quan
Hệ thống authentication của MeowCDD sử dụng Spring Security với session-based authentication. Hệ thống hỗ trợ login, logout và kiểm tra trạng thái authentication.

**⚠️ Trạng thái hiện tại: DISABLED** - Tất cả API endpoints đều có thể truy cập tự do.

## 🔑 Test Accounts (Chỉ dùng khi enable authentication)

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `ADMIN`

### User Account
- **Username**: `user`
- **Password**: `user123`
- **Role**: `USER`

## 📋 API Endpoints

### 1. Login (Chỉ hoạt động khi enable authentication)
```http
POST /api/v1/auth/login
Content-Type: application/x-www-form-urlencoded

username=admin&password=admin123
```

**Response Success:**
```json
{
  "success": true,
  "message": "Login successful",
  "user": "admin"
}
```

**Response Error:**
```json
{
  "success": false,
  "message": "Login failed: Bad credentials"
}
```

### 2. Logout (Chỉ hoạt động khi enable authentication)
```http
POST /api/v1/auth/logout
```

**Response:**
```json
{
  "success": true,
  "message": "Logout successful",
  "user": "admin"
}
```

### 3. Check Authentication Status
```http
GET /api/v1/auth/status
```

**Response (Authenticated):**
```json
{
  "authenticated": true,
  "username": "admin",
  "authorities": [
    {
      "authority": "ROLE_ADMIN"
    }
  ]
}
```

**Response (Not Authenticated):**
```json
{
  "authenticated": false
}
```

### 4. Get Current User Info
```http
GET /api/v1/auth/user
```

**Response (Authenticated):**
```json
{
  "username": "admin",
  "authorities": [
    {
      "authority": "ROLE_ADMIN"
    }
  ],
  "authenticated": true
}
```

**Response (Not Authenticated):**
```json
{
  "authenticated": false,
  "message": "No authenticated user found"
}
```

### 5. Check Authentication (Alternative)
```http
POST /api/v1/auth/check
```

**Response:**
```json
{
  "authenticated": true,
  "username": "admin",
  "authorities": [
    {
      "authority": "ROLE_ADMIN"
    }
  ]
}
```

## 🧪 Testing với cURL

### Login (Chỉ khi enable authentication)
```bash
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt
```

### Check Status
```bash
curl -X GET http://localhost:8101/api/v1/auth/status \
  -b cookies.txt
```

### Logout (Chỉ khi enable authentication)
```bash
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt
```

## 🌐 Testing với Web Interface

Truy cập: `http://localhost:8101/login.html`

### Tính năng:
- ✅ Form login với validation
- ✅ Hiển thị trạng thái authentication
- ✅ Nút logout với confirmation
- ✅ Auto-check authentication status
- ✅ Responsive design

## 🔒 Security Features

### Session Management
- **Session Timeout**: 30 phút (có thể cấu hình)
- **Maximum Sessions**: 1 session per user
- **Session Invalidation**: Tự động khi logout

### Password Security
- **Encryption**: BCrypt password hashing
- **Salt**: Tự động generate salt
- **Strength**: 10 rounds (có thể tăng)

### CSRF Protection
- **Status**: Disabled (cho API endpoints)
- **Reason**: RESTful API không cần CSRF

## 🛡️ Authorization

### Role-based Access Control
- **ADMIN**: Full access to all endpoints
- **USER**: Limited access (có thể cấu hình)

### Protected Endpoints
**⚠️ HIỆN TẠI: Tất cả endpoints đều có thể truy cập tự do (authentication disabled)**

Khi enable authentication:
- Tất cả endpoints (trừ `/api/v1/auth/**` và `/api/v1/public/**`) yêu cầu authentication.

## 📝 Implementation Details

### Security Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Form login configuration
    // Logout configuration
    // Session management
    // Password encoder
    // User details service
}
```

### Authentication Flow
1. **Login Request** → Spring Security Form Login
2. **Authentication** → UserDetailsService validation
3. **Session Creation** → JSESSIONID cookie
4. **Request Authorization** → Session-based checks
5. **Logout** → Session invalidation + cookie deletion

### Session Storage
- **Type**: HttpSession
- **Storage**: In-memory (có thể migrate to Redis)
- **Cookie**: JSESSIONID

## 🚀 Deployment Notes

### Production Considerations
1. **HTTPS**: Bắt buộc cho production
2. **Session Storage**: Redis hoặc database
3. **Password Policy**: Implement strong password requirements
4. **Rate Limiting**: Implement login attempt limits
5. **Audit Logging**: Log authentication events

### Environment Variables
```properties
# Session timeout (seconds)
server.servlet.session.timeout=1800

# Maximum sessions per user
spring.security.session.maximum-sessions=1

# Session fixation protection
spring.security.session.fixation=migrateSession
```

## 🔧 Enable/Disable Authentication

### Để Enable Authentication:
```java
// Trong SecurityConfig.java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/v1/auth/**").permitAll()
    .requestMatchers("/api/v1/public/**").permitAll()
    .anyRequest().authenticated()
)
```

### Để Disable Authentication (hiện tại):
```java
// Trong SecurityConfig.java
.authorizeHttpRequests(auth -> auth
    .anyRequest().permitAll()
)
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Session Not Persisting
```bash
# Check if cookies are enabled
# Verify CORS configuration
# Check session timeout settings
```

#### 2. Logout Not Working
```bash
# Verify logout URL configuration
# Check session invalidation
# Clear browser cookies manually
```

#### 3. Authentication Bypass
```bash
# Verify security configuration
# Check endpoint protection
# Review authorization rules
```

### Debug Mode
Thêm vào `application.properties`:
```properties
logging.level.org.springframework.security=DEBUG
logging.level.com.meowcdd=DEBUG
```

## 📚 Additional Resources

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Session Management Best Practices](https://owasp.org/www-project-cheat-sheets/cheatsheets/Session_Management_Cheat_Sheet.html)
- [Authentication Patterns](https://auth0.com/blog/authentication-patterns/)
