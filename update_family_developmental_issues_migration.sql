-- Migration script to update family_developmental_issues column from enum to text
-- This script changes the column type and migrates existing data

-- Step 1: Create a temporary column
ALTER TABLE children 
ADD COLUMN family_developmental_issues_new TEXT;

-- Step 2: Migrate existing data from enum to text
UPDATE children 
SET family_developmental_issues_new = 
    CASE 
        WHEN family_developmental_issues = 'YES' THEN 'Có vấn đề phát triển trong gia đình'
        WHEN family_developmental_issues = 'NO' THEN 'Không có ai trong gia đình có vấn đề phát triển'
        WHEN family_developmental_issues = 'UNKNOWN' THEN 'Không rõ tiền sử gia đình'
        ELSE 'Không có thông tin'
    END;

-- Step 3: Drop the old column
ALTER TABLE children 
DROP COLUMN family_developmental_issues;

-- Step 4: Rename the new column to the original name
ALTER TABLE children 
RENAME COLUMN family_developmental_issues_new TO family_developmental_issues;

-- Step 5: Add column definition to ensure it's TEXT
ALTER TABLE children 
ALTER COLUMN family_developmental_issues TYPE TEXT;

-- Step 6: Verify the changes
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'children' 
    AND column_name = 'family_developmental_issues';

-- Step 7: Check sample data
SELECT 
    id, 
    full_name, 
    family_developmental_issues 
FROM children 
LIMIT 5;
