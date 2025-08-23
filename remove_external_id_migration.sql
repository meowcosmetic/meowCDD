-- Migration script to remove external_id column from children table
-- This script removes the external_id column and related functionality

-- Step 1: Drop the external_id column
ALTER TABLE children 
DROP COLUMN IF EXISTS external_id;

-- Step 2: Drop any indexes related to external_id
DROP INDEX IF EXISTS idx_children_external_id;

-- Step 3: Verify the changes
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'children' 
AND table_schema = 'public'
ORDER BY ordinal_position;

-- Step 4: Check if there are any remaining references to external_id
-- This will show any remaining constraints or indexes
SELECT 
    tc.constraint_name, 
    tc.table_name, 
    kcu.column_name
FROM information_schema.table_constraints tc
JOIN information_schema.key_column_usage kcu 
    ON tc.constraint_name = kcu.constraint_name
WHERE tc.table_name = 'children' 
    AND kcu.column_name = 'external_id';
