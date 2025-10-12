# Migration Guide - Fix Developmental Item Criteria

## Vấn đề
Lỗi: `column "item_id" is of type bigint but expression is of type uuid`

## Nguyên nhân
- Entity `DevelopmentalItemCriteria` sử dụng `DevelopmentalDomainItem` với UUID
- Nhưng database column `item_id` vẫn là `bigint` thay vì `uuid`

## Giải pháp

### 1. Chạy Migration Script
```sql
-- File: supabase/migrations/fix_developmental_item_criteria_item_id_type.sql

-- Drop foreign key constraint
ALTER TABLE developmental_item_criteria DROP CONSTRAINT IF EXISTS fk_developmental_item_criteria_item;

-- Change column type from bigint to uuid
ALTER TABLE developmental_item_criteria 
ALTER COLUMN item_id TYPE uuid USING item_id::text::uuid;

-- Recreate foreign key constraint
ALTER TABLE developmental_item_criteria 
ADD CONSTRAINT fk_developmental_item_criteria_item 
FOREIGN KEY (item_id) REFERENCES developmental_domain_items(id) ON DELETE CASCADE;
```

### 2. Cách chạy migration

#### Option A: Chạy trực tiếp trên database
```bash
# Kết nối vào PostgreSQL database
psql -h your-host -U your-username -d your-database

# Chạy script
\i supabase/migrations/fix_developmental_item_criteria_item_id_type.sql
```

#### Option B: Sử dụng Supabase CLI
```bash
# Nếu sử dụng Supabase
supabase db push
```

#### Option C: Chạy qua Supabase Dashboard
1. Mở Supabase Dashboard
2. Vào SQL Editor
3. Copy và paste nội dung file migration
4. Click "Run"

### 3. Test API sau migration

#### Test tạo criteria:
```bash
POST http://192.168.1.184/api/cdd/api/v1/neon/developmental-item-criteria
Content-Type: application/json

{
  "itemId": "27213c8b-43e2-4083-bd84-a9d32d6592b9",
  "description": {
    "vi": "Trẻ có thể quay đầu về phía âm thanh vui nhộn",
    "en": "Child can turn head toward cheerful sounds"
  },
  "minAgeMonths": 3,
  "maxAgeMonths": 6,
  "level": 1
}
```

## Lưu ý
- Migration này sẽ thay đổi kiểu dữ liệu của cột `item_id`
- Nếu có dữ liệu cũ, cần đảm bảo chúng có thể convert sang UUID
- Backup database trước khi chạy migration (khuyến nghị)

## Entity Changes
- `DevelopmentalItemCriteria.item_id`: bigint → uuid
- `DevelopmentalItemCriteria.description`: TEXT → JSONB
- Sử dụng `@JdbcTypeCode(SqlTypes.JSON)` cho JSONB fields
