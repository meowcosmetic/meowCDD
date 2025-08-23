# Ví dụ dữ liệu Child hoàn chỉnh

## Tổng quan
Tài liệu này cung cấp các ví dụ hoàn chỉnh về dữ liệu child trong hệ thống, bao gồm các trường hợp khác nhau để test và tham khảo.

## 1. Child cơ bản (Phát triển bình thường)

### Nguyễn Hoàng Minh
```json
{
  "parentId": "68a85732aef15b0165a40f75",
  "fullName": "Nguyễn Hoàng Minh",
  "gender": "MALE",
  "dateOfBirth": "2020-03-15",
  "isPremature": false,
  "gestationalWeek": null,
  "birthWeightGrams": 3200,
  "specialMedicalConditions": "Không có tình trạng y tế đặc biệt",
  "developmentalDisorderDiagnosis": "NOT_EVALUATED",
  "hasEarlyIntervention": false,
  "earlyInterventionDetails": null,
  "primaryLanguage": "Tiếng Việt",
  "familyDevelopmentalIssues": "Không có ai trong gia đình có vấn đề phát triển",
  "height": 95.5,
  "weight": 14.2,
  "bloodType": "A+",
  "allergies": "Không có dị ứng",
  "medicalHistory": "Tiêm chủng đầy đủ theo lịch, không có bệnh lý đặc biệt",
  "status": "ACTIVE"
}
```

**Đặc điểm:**
- Trẻ phát triển bình thường
- Sinh đủ tháng
- Không có vấn đề phát triển
- Chưa được đánh giá rối loạn phát triển

## 2. Child sinh non (Cần theo dõi)

### Trần Thị Anh Thư
```json
{
  "parentId": "68a85732aef15b0165a40f76",
  "fullName": "Trần Thị Anh Thư",
  "gender": "FEMALE",
  "dateOfBirth": "2019-08-22",
  "isPremature": true,
  "gestationalWeek": 34,
  "birthWeightGrams": 2100,
  "specialMedicalConditions": "Sinh non, cần theo dõi phát triển",
  "developmentalDisorderDiagnosis": "NOT_EVALUATED",
  "hasEarlyIntervention": true,
  "earlyInterventionDetails": "Vật lý trị liệu từ 6 tháng tuổi, theo dõi phát triển vận động",
  "primaryLanguage": "Tiếng Việt",
  "familyDevelopmentalIssues": "Không có ai trong gia đình có vấn đề phát triển",
  "height": 88.0,
  "weight": 12.5,
  "bloodType": "O+",
  "allergies": "Dị ứng sữa bò",
  "medicalHistory": "Sinh non 34 tuần, nằm lồng ấp 2 tuần, tiêm chủng đầy đủ",
  "status": "ACTIVE"
}
```

**Đặc điểm:**
- Sinh non 34 tuần
- Cân nặng khi sinh thấp (2100g)
- Có can thiệp sớm (vật lý trị liệu)
- Có dị ứng sữa bò

## 3. Child có rối loạn phát triển

### Lê Văn Đức
```json
{
  "parentId": "68a85732aef15b0165a40f77",
  "fullName": "Lê Văn Đức",
  "gender": "MALE",
  "dateOfBirth": "2021-01-10",
  "isPremature": false,
  "gestationalWeek": null,
  "birthWeightGrams": 3500,
  "specialMedicalConditions": "Không có",
  "developmentalDisorderDiagnosis": "YES",
  "hasEarlyIntervention": true,
  "earlyInterventionDetails": "Can thiệp sớm cho rối loạn phổ tự kỷ từ 18 tháng tuổi, bao gồm: trị liệu ngôn ngữ, trị liệu hành vi, trị liệu vận động",
  "primaryLanguage": "Tiếng Việt",
  "familyDevelopmentalIssues": "Có anh trai từng có vấn đề về ngôn ngữ, đã được can thiệp sớm",
  "height": 82.0,
  "weight": 11.8,
  "bloodType": "B+",
  "allergies": "Không có",
  "medicalHistory": "Chẩn đoán rối loạn phổ tự kỷ ở 18 tháng tuổi, đang điều trị tại trung tâm can thiệp sớm",
  "status": "ACTIVE"
}
```

**Đặc điểm:**
- Được chẩn đoán rối loạn phổ tự kỷ
- Có can thiệp sớm toàn diện
- Có tiền sử gia đình về vấn đề phát triển
- Đang điều trị tại trung tâm chuyên môn

## 4. Child có dị ứng

### Phạm Thị Mai Anh
```json
{
  "parentId": "68a85732aef15b0165a40f78",
  "fullName": "Phạm Thị Mai Anh",
  "gender": "FEMALE",
  "dateOfBirth": "2020-11-05",
  "isPremature": false,
  "gestationalWeek": null,
  "birthWeightGrams": 3100,
  "specialMedicalConditions": "Không có",
  "developmentalDisorderDiagnosis": "NO",
  "hasEarlyIntervention": false,
  "earlyInterventionDetails": null,
  "primaryLanguage": "Tiếng Việt",
  "familyDevelopmentalIssues": "Không rõ tiền sử gia đình",
  "height": 92.0,
  "weight": 13.5,
  "bloodType": "AB+",
  "allergies": "Dị ứng đậu phộng",
  "medicalHistory": "Phát triển bình thường, tiêm chủng đầy đủ, có tiền sử dị ứng đậu phộng",
  "status": "ACTIVE"
}
```

**Đặc điểm:**
- Phát triển bình thường
- Có dị ứng đậu phộng
- Không có rối loạn phát triển
- Tiền sử gia đình không rõ

## 5. Child phát triển bình thường

### Hoàng Văn Khang
```json
{
  "parentId": "68a85732aef15b0165a40f79",
  "fullName": "Hoàng Văn Khang",
  "gender": "MALE",
  "dateOfBirth": "2019-12-20",
  "isPremature": false,
  "gestationalWeek": null,
  "birthWeightGrams": 3300,
  "specialMedicalConditions": "Không có",
  "developmentalDisorderDiagnosis": "NOT_EVALUATED",
  "hasEarlyIntervention": false,
  "earlyInterventionDetails": null,
  "primaryLanguage": "Tiếng Việt",
  "familyDevelopmentalIssues": "Không có ai trong gia đình có vấn đề phát triển",
  "height": 96.0,
  "weight": 15.0,
  "bloodType": "A-",
  "allergies": "Không có",
  "medicalHistory": "Phát triển bình thường, tiêm chủng đầy đủ, không có bệnh lý đặc biệt",
  "status": "ACTIVE"
}
```

**Đặc điểm:**
- Phát triển bình thường
- Không có vấn đề sức khỏe
- Chưa được đánh giá rối loạn phát triển
- Không có tiền sử gia đình

## Cách sử dụng với API

### 1. Tạo child mới
```bash
curl -X POST http://localhost:8080/api/supabase/children \
  -H "Content-Type: application/json" \
  -d @sample_child_data.json
```

### 2. Tạo nhiều child
```bash
# Sử dụng từng child trong collection
curl -X POST http://localhost:8080/api/supabase/children \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

### 3. Test các API endpoints

#### Tìm trẻ theo giới tính
```bash
curl -X GET "http://localhost:8080/api/supabase/children/gender/MALE"
```

#### Tìm trẻ sinh non
```bash
curl -X GET "http://localhost:8080/api/supabase/children/premature"
```

#### Tìm trẻ có can thiệp sớm
```bash
curl -X GET "http://localhost:8080/api/supabase/children/early-intervention"
```

#### Tìm trẻ theo độ tuổi
```bash
curl -X GET "http://localhost:8080/api/supabase/children/age?minAge=12&maxAge=36"
```

#### Lấy trẻ theo parent ID
```bash
curl -X GET "http://localhost:8080/api/supabase/children/parent/1"
```

#### Thống kê theo giới tính
```bash
curl -X GET "http://localhost:8080/api/supabase/children/stats/gender/MALE"
```

## Lưu ý quan trọng

### 1. Các trường bắt buộc
- `parentId`: ID của phụ huynh (String - MongoDB ObjectId format)
- `fullName`: Tên đầy đủ của trẻ
- `gender`: Giới tính (MALE, FEMALE, OTHER)
- `dateOfBirth`: Ngày sinh (format: YYYY-MM-DD)
- `status`: Trạng thái (ACTIVE, INACTIVE, SUSPENDED)

### 2. Các trường tự động tính
- `currentAgeMonths`: Được tính tự động từ `dateOfBirth`
- `registrationDate`: Được set tự động khi tạo
- `createdAt`, `updatedAt`: Được quản lý bởi BaseEntity

### 3. Các enum values
- **Gender**: MALE, FEMALE, OTHER
- **Status**: ACTIVE, INACTIVE, SUSPENDED
- **DevelopmentalDisorderStatus**: YES, NO, NOT_EVALUATED, UNDER_INVESTIGATION
- **familyDevelopmentalIssues**: String (mô tả chi tiết về vấn đề phát triển trong gia đình)

### 4. Validation
- `dateOfBirth` phải là ngày trong quá khứ
- `birthWeightGrams` phải > 0 nếu có
- `height` và `weight` phải > 0 nếu có
- `gestationalWeek` phải từ 22-42 nếu `isPremature = true`
