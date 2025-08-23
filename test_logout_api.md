# 🧪 Test Logout API

## Test Cases cho Logout Functionality

### 1. Test Login Flow

#### Test Case 1.1: Successful Login
```bash
# Test admin login
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt \
  -v

# Expected Response:
{
  "success": true,
  "message": "Login successful",
  "user": "admin"
}
```

#### Test Case 1.2: Failed Login
```bash
# Test wrong password
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=wrongpassword" \
  -v

# Expected Response:
{
  "success": false,
  "message": "Login failed: Bad credentials"
}
```

### 2. Test Logout Flow

#### Test Case 2.1: Successful Logout
```bash
# First login
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# Then logout
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt \
  -v

# Expected Response:
{
  "success": true,
  "message": "Logout successful",
  "user": "admin"
}
```

#### Test Case 2.2: Logout without Session
```bash
# Try logout without being logged in
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -v

# Expected Response:
{
  "success": false,
  "message": "No active session found"
}
```

### 3. Test Authentication Status

#### Test Case 3.1: Check Status After Login
```bash
# Login first
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# Check status
curl -X GET http://localhost:8101/api/v1/auth/status \
  -b cookies.txt \
  -v

# Expected Response:
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

#### Test Case 3.2: Check Status After Logout
```bash
# Login
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# Logout
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt

# Check status again
curl -X GET http://localhost:8101/api/v1/auth/status \
  -b cookies.txt \
  -v

# Expected Response:
{
  "authenticated": false
}
```

### 4. Test Protected Endpoints

#### Test Case 4.1: Access Protected Endpoint with Authentication
```bash
# Login first
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# Access protected endpoint
curl -X GET http://localhost:8101/api/v1/supabase/children \
  -b cookies.txt \
  -v

# Should return data (not redirect to login)
```

#### Test Case 4.2: Access Protected Endpoint without Authentication
```bash
# Try to access protected endpoint without login
curl -X GET http://localhost:8101/api/v1/supabase/children \
  -v

# Should redirect to login page or return 401/403
```

### 5. Test Session Management

#### Test Case 5.1: Multiple Logout Attempts
```bash
# Login
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# First logout
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt

# Second logout (should fail)
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt

# Expected: Second logout should return "No active session found"
```

#### Test Case 5.2: Session Invalidation
```bash
# Login
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# Check status
curl -X GET http://localhost:8101/api/v1/auth/status \
  -b cookies.txt

# Logout
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt

# Try to use the same session
curl -X GET http://localhost:8101/api/v1/auth/status \
  -b cookies.txt

# Expected: Should return "authenticated": false
```

### 6. Test User Accounts

#### Test Case 6.1: Admin User
```bash
# Login as admin
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin123" \
  -c cookies.txt

# Check user info
curl -X GET http://localhost:8101/api/v1/auth/user \
  -b cookies.txt

# Logout
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt
```

#### Test Case 6.2: Regular User
```bash
# Login as user
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=user&password=user123" \
  -c cookies.txt

# Check user info
curl -X GET http://localhost:8101/api/v1/auth/user \
  -b cookies.txt

# Logout
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -b cookies.txt
```

### 7. Test Web Interface

#### Test Case 7.1: Browser Testing
1. Open browser and go to `http://localhost:8101/login.html`
2. Try to login with:
   - Username: `admin`, Password: `admin123`
   - Username: `user`, Password: `user123`
3. Verify logout button appears after login
4. Click logout button
5. Verify user is logged out and login form appears

#### Test Case 7.2: Session Persistence
1. Login in browser
2. Close browser tab
3. Open new tab and go to `http://localhost:8101/login.html`
4. Check if still logged in (should be)
5. Click logout
6. Verify logout successful

### 8. Test Error Scenarios

#### Test Case 8.1: Invalid Credentials
```bash
# Test non-existent user
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=nonexistent&password=password" \
  -v

# Expected: 401 Unauthorized
```

#### Test Case 8.2: Missing Parameters
```bash
# Test missing username
curl -X POST http://localhost:8101/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "password=admin123" \
  -v

# Expected: 400 Bad Request or 401 Unauthorized
```

### 9. Performance Tests

#### Test Case 9.1: Concurrent Logout
```bash
# Create multiple sessions and logout simultaneously
# (Use tools like Apache Bench or similar)

# Example with multiple curl processes
for i in {1..5}; do
  curl -X POST http://localhost:8101/api/v1/auth/login \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=admin&password=admin123" \
    -c cookies_$i.txt &
done

# Then logout all
for i in {1..5}; do
  curl -X POST http://localhost:8101/api/v1/auth/logout \
    -b cookies_$i.txt &
done
```

### 10. Security Tests

#### Test Case 10.1: CSRF Protection
```bash
# Test if logout works without proper session
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -H "X-Requested-With: XMLHttpRequest" \
  -v

# Expected: Should not work without proper session
```

#### Test Case 10.2: Session Hijacking Prevention
```bash
# Test with invalid session ID
curl -X POST http://localhost:8101/api/v1/auth/logout \
  -H "Cookie: JSESSIONID=invalid_session_id" \
  -v

# Expected: Should return "No active session found"
```

## 🎯 Test Results Checklist

- [ ] Login works with valid credentials
- [ ] Login fails with invalid credentials
- [ ] Logout works when authenticated
- [ ] Logout fails when not authenticated
- [ ] Session is properly invalidated after logout
- [ ] Protected endpoints require authentication
- [ ] Authentication status is correctly reported
- [ ] Multiple user accounts work correctly
- [ ] Web interface functions properly
- [ ] Error handling works as expected
- [ ] Performance is acceptable under load
- [ ] Security measures are effective

## 📝 Notes

- All tests should be run against a running application
- Cookies are used to maintain session state
- The `-v` flag provides verbose output for debugging
- Test both success and failure scenarios
- Verify that sessions are properly cleaned up
