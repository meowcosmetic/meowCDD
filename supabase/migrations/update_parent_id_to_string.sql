-- Migration: Update parent_id column type from BIGINT to VARCHAR
-- Date: 2024-01-15
-- Description: Change parent_id column type to support string format like MongoDB ObjectId

-- Step 1: Add a temporary column with the new type
ALTER TABLE children ADD COLUMN parent_id_new VARCHAR(255);

-- Step 2: Copy data from old column to new column (convert to string)
UPDATE children SET parent_id_new = parent_id::VARCHAR WHERE parent_id IS NOT NULL;

-- Step 3: Drop the old column
ALTER TABLE children DROP COLUMN parent_id;

-- Step 4: Rename the new column to the original name
ALTER TABLE children RENAME COLUMN parent_id_new TO parent_id;

-- Step 5: Add NOT NULL constraint back
ALTER TABLE children ALTER COLUMN parent_id SET NOT NULL;

-- Step 6: Add index for better performance
CREATE INDEX idx_children_parent_id ON children(parent_id);

-- Step 7: Verify the change
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'children' AND column_name = 'parent_id';

-- Step 8: Show sample data to verify
SELECT id, parent_id, full_name FROM children LIMIT 5;
