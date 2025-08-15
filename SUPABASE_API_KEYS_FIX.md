# Fix Supabase API Keys - 401 Unauthorized Error

## 🚨 Vấn đề
Lỗi `401 Unauthorized from POST https://smgcifigqidmwjtfgvim.supabase.co/rest/v1/cdd_tests` xảy ra vì API keys trong `application.properties` đang là placeholder.

## 🔧 Cách fix

### Bước 1: Lấy API Keys từ Supabase Dashboard

1. **Truy cập Supabase Dashboard:**
   - Vào https://supabase.com/dashboard
   - Chọn project của bạn: `smgcifigqidmwjtfgvim`

2. **Lấy API Keys:**
   - Vào **Settings** → **API**
   - Copy **anon public** key
   - Copy **service_role** key (cẩn thận, đây là key có quyền cao!)

### Bước 2: Cập nhật application.properties

```properties
# Supabase Configuration (Primary Database)
supabase.url=https://smgcifigqidmwjtfgvim.supabase.co
supabase.anon-key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...  # Thay bằng anon key thật
supabase.service-role-key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...  # Thay bằng service role key thật
```

### Bước 3: Test lại

```bash
# Test với curl
curl -X POST "https://smgcifigqidmwjtfgvim.supabase.co/rest/v1/cdd_tests" \
  -H "apikey: YOUR_ANON_KEY" \
  -H "Authorization: Bearer YOUR_ANON_KEY" \
  -H "Content-Type: application/json" \
  -d '{"test": "data"}'
```

## 🔒 Security Note

**⚠️ QUAN TRỌNG:** 
- **Service Role Key** có quyền cao nhất, chỉ dùng cho server-side
- **Anon Key** có quyền hạn chế, an toàn cho client-side
- Không commit API keys vào git!

## 📋 Checklist

- [ ] Lấy anon key từ Supabase Dashboard
- [ ] Lấy service role key từ Supabase Dashboard  
- [ ] Cập nhật `application.properties`
- [ ] Test API call
- [ ] Restart application

## 🎯 Kết quả mong đợi

Sau khi fix, bạn sẽ thấy:
- ✅ HTTP 200 thay vì 401
- ✅ API calls thành công
- ✅ Data sync hoạt động bình thường
