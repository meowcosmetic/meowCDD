# Test Guide for Updated Child API (No Parent Relationship)

## Prerequisites
- Application running on port 8101
- Database migration completed

## Test Commands

### 1. Create a Child
```bash
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_001",
    "fullName": "Nguyễn Văn A",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01",
    "height": 100.5,
    "weight": 15.2,
    "isPremature": false,
    "primaryLanguage": "Vietnamese",
    "developmentalDisorderDiagnosis": "NOT_EVALUATED",
    "familyDevelopmentalIssues": "NO"
  }'
```

### 2. Get Child by ID
```bash
curl -X GET http://localhost:8101/api/v1/children/1
```

### 3. Get Child by External ID
```bash
curl -X GET http://localhost:8101/api/v1/children/external/CHILD_001
```

### 4. Get All Children
```bash
curl -X GET http://localhost:8101/api/v1/children
```

### 5. Search Children by Name
```bash
curl -X GET "http://localhost:8101/api/v1/children/search?name=Nguyễn"
```

### 6. Update Child
```bash
curl -X PUT http://localhost:8101/api/v1/children/1 \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Nguyễn Văn A Updated",
    "height": 105.0,
    "weight": 16.0
  }'
```

### 7. Get Children by Status
```bash
curl -X GET http://localhost:8101/api/v1/children/status/ACTIVE
```

### 8. Check if Child Exists
```bash
curl -X GET http://localhost:8101/api/v1/children/exists/CHILD_001
```

### 9. Delete Child
```bash
curl -X DELETE http://localhost:8101/api/v1/children/1
```

## Supabase API Tests

### 1. Create Child in Supabase
```bash
curl -X POST http://localhost:8101/api/v1/api/supabase/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_SUPA_001",
    "fullName": "Trần Thị B",
    "gender": "FEMALE",
    "dateOfBirth": "2019-06-15",
    "height": 95.0,
    "weight": 14.0,
    "isPremature": true,
    "gestationalWeek": 35,
    "birthWeightGrams": 2500,
    "primaryLanguage": "Vietnamese",
    "developmentalDisorderDiagnosis": "NO",
    "familyDevelopmentalIssues": "NO"
  }'
```

### 2. Get All Children from Supabase
```bash
curl -X GET http://localhost:8101/api/v1/api/supabase/children
```

### 3. Search Children by Name in Supabase
```bash
curl -X GET "http://localhost:8101/api/v1/api/supabase/children/search?name=Trần"
```

### 4. Get Children by Gender in Supabase
```bash
curl -X GET http://localhost:8101/api/v1/api/supabase/children/gender/FEMALE
```

### 5. Get Children by Age Range in Supabase
```bash
curl -X GET "http://localhost:8101/api/v1/api/supabase/children/age?minAge=12&maxAge=60"
```

### 6. Get Premature Children in Supabase
```bash
curl -X GET http://localhost:8101/api/v1/api/supabase/children/premature
```

### 7. Get Children with Developmental Disorder in Supabase
```bash
curl -X GET http://localhost:8101/api/v1/api/supabase/children/developmental-disorder/YES
```

### 8. Get Children with Early Intervention in Supabase
```bash
curl -X GET http://localhost:8101/api/v1/api/supabase/children/early-intervention
```

## Expected Responses

### Successful Child Creation
```json
{
  "id": 1,
  "externalId": "CHILD_001",
  "fullName": "Nguyễn Văn A",
  "gender": "MALE",
  "dateOfBirth": "2020-01-01",
  "currentAgeMonths": 48,
  "isPremature": false,
  "height": 100.5,
  "weight": 15.2,
  "status": "ACTIVE",
  "registrationDate": "2024-01-15T10:30:00",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Error Response (Missing Required Fields)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.meowcdd.dto.ChildDto> com.meowcdd.controller.ChildController.createChild(com.meowcdd.dto.ChildDto): [Field error in object 'childDto' on field 'fullName': rejected value [null]; codes [NotBlank.childDto.fullName,NotBlank.fullName,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [childDto.fullName,fullName]; arguments []; default message [fullName]]; default message [Full name is required]]"
}
```

## Validation Tests

### 1. Test Missing Required Fields
```bash
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_002",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01"
  }'
```

### 2. Test Invalid Gender
```bash
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_003",
    "fullName": "Test Child",
    "gender": "INVALID_GENDER",
    "dateOfBirth": "2020-01-01"
  }'
```

### 3. Test Duplicate External ID
```bash
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_001",
    "fullName": "Another Child",
    "gender": "FEMALE",
    "dateOfBirth": "2020-01-01"
  }'
```

### 4. Test Invalid Date Format
```bash
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_004",
    "fullName": "Test Child",
    "gender": "MALE",
    "dateOfBirth": "invalid-date"
  }'
```

## Performance Tests

### 1. Bulk Create Children
```bash
for i in {1..10}; do
  curl -X POST http://localhost:8101/api/v1/children \
    -H "Content-Type: application/json" \
    -d "{
      \"externalId\": \"CHILD_BULK_$i\",
      \"fullName\": \"Child $i\",
      \"gender\": \"MALE\",
      \"dateOfBirth\": \"2020-01-01\"
    }" &
done
wait
```

### 2. Test Search Performance
```bash
time curl -X GET "http://localhost:8101/api/v1/children/search?name=Child"
```

### 3. Test Get All Children Performance
```bash
time curl -X GET "http://localhost:8101/api/v1/children"
```

## Advanced Tests

### 1. Test Age Calculation
```bash
# Create child with specific birth date
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_AGE_TEST",
    "fullName": "Age Test Child",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01"
  }'

# Check if currentAgeMonths is calculated correctly
curl -X GET http://localhost:8101/api/v1/children/external/CHILD_AGE_TEST
```

### 2. Test Enum Values
```bash
# Test all gender values
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_GENDER_TEST",
    "fullName": "Gender Test Child",
    "gender": "OTHER",
    "dateOfBirth": "2020-01-01"
  }'

# Test all status values
curl -X PUT http://localhost:8101/api/v1/children/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "SUSPENDED"
  }'
```

### 3. Test Large Text Fields
```bash
curl -X POST http://localhost:8101/api/v1/children \
  -H "Content-Type: application/json" \
  -d '{
    "externalId": "CHILD_TEXT_TEST",
    "fullName": "Text Test Child",
    "gender": "MALE",
    "dateOfBirth": "2020-01-01",
    "specialMedicalConditions": "This is a very long text field with detailed medical information that should be stored properly in the database without any issues.",
    "earlyInterventionDetails": "Another long text field with detailed information about early intervention programs and treatments received by the child.",
    "medicalHistory": "Comprehensive medical history including all previous illnesses, treatments, medications, and hospital visits."
  }'
```

## Notes
- Replace `localhost:8101` with your actual server URL if different
- All dates should be in ISO format (YYYY-MM-DD)
- Gender values: MALE, FEMALE, OTHER
- Status values: ACTIVE, INACTIVE, SUSPENDED
- DevelopmentalDisorderStatus values: YES, NO, NOT_EVALUATED
- FamilyDevelopmentalIssues values: YES, NO, UNKNOWN
- The application no longer requires parent information
- All parent-related endpoints have been removed
