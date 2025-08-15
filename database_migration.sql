-- Database Migration Script for Child Entity Update - Remove Parent Relationship
-- This script removes the parent_id column and all parent-related constraints from the children table

-- Step 1: Drop foreign key constraint if it exists
-- You may need to find the actual constraint name from your database
-- ALTER TABLE children DROP CONSTRAINT IF EXISTS fk_children_parent_id;

-- Step 2: Drop the parent_id column
ALTER TABLE children DROP COLUMN IF EXISTS parent_id;

-- Step 3: Drop index for parent_id if it exists
DROP INDEX IF EXISTS idx_children_parent_id;

-- Step 4: Add index for external_id if not exists
CREATE INDEX IF NOT EXISTS idx_children_external_id ON children(external_id);

-- Step 5: Add index for full_name for search functionality
CREATE INDEX IF NOT EXISTS idx_children_full_name ON children(full_name);

-- Step 6: Add index for status for filtering
CREATE INDEX IF NOT EXISTS idx_children_status ON children(status);

-- Step 7: Add index for gender for filtering
CREATE INDEX IF NOT EXISTS idx_children_gender ON children(gender);

-- Step 8: Add index for date_of_birth for age calculations
CREATE INDEX IF NOT EXISTS idx_children_date_of_birth ON children(date_of_birth);

-- Verification queries
-- Check if the migration was successful
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'children' 
    AND column_name = 'parent_id';

-- Check that parent_id column no longer exists
SELECT COUNT(*) as parent_id_column_exists
FROM information_schema.columns 
WHERE table_name = 'children' 
    AND column_name = 'parent_id';

-- List all remaining columns in children table
SELECT 
    column_name, 
    data_type, 
    is_nullable
FROM information_schema.columns 
WHERE table_name = 'children'
ORDER BY ordinal_position;

-- List all indexes on children table
SELECT 
    indexname,
    indexdef
FROM pg_indexes 
WHERE tablename = 'children';
