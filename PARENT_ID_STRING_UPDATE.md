# 🔄 Parent ID String Update

## Tổng quan
Tài liệu này ghi lại việc thay đổi kiểu dữ liệu của trường `parentId` từ `Long` sang `String` trong entity `ChildSupabase` để hỗ trợ format MongoDB ObjectId.

## 🎯 Lý do thay đổi
- **Tương thích MongoDB**: Để có thể sử dụng MongoDB ObjectId format như `68a85732aef15b0165a40f75`
- **Flexibility**: String format cho phép linh hoạt hơn trong việc lưu trữ ID
- **Consistency**: Đồng nhất với các hệ thống sử dụng MongoDB

## 📝 Thay đổi chi tiết

### 1. Entity Changes

#### `ChildSupabase.java`
- **Thay đổi**: `private Long parentId` → `private String parentId`
- **Comment**: Cập nhật comment để reflect String format

### 2. Repository Changes

#### `ChildSupabaseRepository.java`
- **Thay đổi**: `findByParentId(Long parentId)` → `findByParentId(String parentId)`

### 3. Service Changes

#### `ChildSupabaseService.java`
- **Thay đổi**: `getChildrenByParentId(Long parentId)` → `getChildrenByParentId(String parentId)`

### 4. Controller Changes

#### `ChildSupabaseController.java`
- **Thay đổi**: `@PathVariable Long parentId` → `@PathVariable String parentId`

### 5. Database Migration

#### `update_parent_id_to_string.sql`
- **Thêm**: Migration script để thay đổi kiểu dữ liệu trong database
- **Bước 1**: Tạo temporary column với kiểu VARCHAR
- **Bước 2**: Copy và convert data từ BIGINT sang VARCHAR
- **Bước 3**: Drop old column và rename new column
- **Bước 4**: Thêm index cho performance

## 🔧 Migration Steps

### 1. Backup Database
```sql
-- Backup current data
CREATE TABLE children_backup AS SELECT * FROM children;
```

### 2. Run Migration
```sql
-- Execute the migration script
\i supabase/migrations/update_parent_id_to_string.sql
```

### 3. Verify Changes
```sql
-- Check column type
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'children' AND column_name = 'parent_id';

-- Check sample data
SELECT id, parent_id, full_name FROM children LIMIT 5;
```

## 📊 Sample Data Format

### Before (Long format)
```json
{
  "parentId": 1,
  "fullName": "Nguyễn Hoàng Minh",
  "gender": "MALE"
}
```

### After (String format)
```json
{
  "parentId": "68a85732aef15b0165a40f75",
  "fullName": "Nguyễn Hoàng Minh",
  "gender": "MALE"
}
```

## 🚀 API Endpoints

### Get Children by Parent ID
```http
GET /api/v1/supabase/children/parent/{parentId}
```

**Example:**
```bash
# Before
curl -X GET "http://localhost:8101/api/v1/supabase/children/parent/1"

# After
curl -X GET "http://localhost:8101/api/v1/supabase/children/parent/68a85732aef15b0165a40f75"
```

## 📋 Updated Files

### Core Files
- ✅ `src/main/java/com/meowcdd/entity/supabase/ChildSupabase.java`
- ✅ `src/main/java/com/meowcdd/repository/supabase/ChildSupabaseRepository.java`
- ✅ `src/main/java/com/meowcdd/service/ChildSupabaseService.java`
- ✅ `src/main/java/com/meowcdd/controller/ChildSupabaseController.java`

### Database
- ✅ `supabase/migrations/update_parent_id_to_string.sql`

### Sample Data
- ✅ `sample_child_data.json`
- ✅ `sample_children_collection.json`

### Documentation
- ✅ `CHILD_DATA_EXAMPLES.md`
- ✅ `PARENT_ID_STRING_UPDATE.md` (this file)

## ⚠️ Breaking Changes

### 1. API Compatibility
- **Breaking**: API endpoints now expect String instead of Long for parentId
- **Impact**: Frontend applications need to be updated
- **Migration**: Update all API calls to use String format

### 2. Database Schema
- **Breaking**: Column type change from BIGINT to VARCHAR
- **Impact**: Existing data will be converted
- **Migration**: Run the provided migration script

### 3. Validation
- **Breaking**: Validation rules may need updates
- **Impact**: String format validation instead of numeric
- **Migration**: Update validation logic if needed

## 🔍 Testing

### 1. Unit Tests
```java
// Test with String parentId
@Test
public void testGetChildrenByParentId() {
    String parentId = "68a85732aef15b0165a40f75";
    List<ChildSupabase> children = childService.getChildrenByParentId(parentId);
    assertNotNull(children);
}
```

### 2. Integration Tests
```bash
# Test API endpoint
curl -X GET "http://localhost:8101/api/v1/supabase/children/parent/68a85732aef15b0165a40f75"
```

### 3. Database Tests
```sql
-- Test data insertion
INSERT INTO children (parent_id, full_name, gender, date_of_birth) 
VALUES ('68a85732aef15b0165a40f75', 'Test Child', 'MALE', '2020-01-01');

-- Test data retrieval
SELECT * FROM children WHERE parent_id = '68a85732aef15b0165a40f75';
```

## 🚀 Deployment Checklist

- [ ] **Backup database** before migration
- [ ] **Run migration script** in test environment first
- [ ] **Update application code** (deploy new version)
- [ ] **Run migration script** in production
- [ ] **Verify data integrity** after migration
- [ ] **Update frontend applications** to use String format
- [ ] **Test all API endpoints** with new format
- [ ] **Monitor application logs** for any issues

## 📚 Related Documentation

- [CHILD_DATA_EXAMPLES.md](./CHILD_DATA_EXAMPLES.md) - Updated with String format examples
- [CHILD_PARENT_ID_UPDATE.md](./CHILD_PARENT_ID_UPDATE.md) - Previous parentId implementation
- [Database Migration Guide](./supabase/migrations/) - Migration scripts

## 🔄 Rollback Plan

If issues occur, rollback can be performed:

### 1. Code Rollback
- Revert all code changes to use Long format
- Deploy previous version

### 2. Database Rollback
```sql
-- Convert back to BIGINT
ALTER TABLE children ADD COLUMN parent_id_old BIGINT;
UPDATE children SET parent_id_old = parent_id::BIGINT WHERE parent_id ~ '^[0-9]+$';
ALTER TABLE children DROP COLUMN parent_id;
ALTER TABLE children RENAME COLUMN parent_id_old TO parent_id;
ALTER TABLE children ALTER COLUMN parent_id SET NOT NULL;
```

## 📞 Support

For any issues related to this migration:
1. Check application logs for errors
2. Verify database migration completed successfully
3. Test API endpoints with new String format
4. Contact development team if issues persist
