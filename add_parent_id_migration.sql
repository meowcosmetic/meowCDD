-- Migration script to add parent_id column to children table
-- This script adds the parent_id column and related functionality

-- Step 1: Add parent_id column
ALTER TABLE children 
ADD COLUMN IF NOT EXISTS parent_id BIGINT NOT NULL DEFAULT 1;

-- Step 2: Create index for better performance
CREATE INDEX IF NOT EXISTS idx_children_parent_id ON children(parent_id);

-- Step 3: Add foreign key constraint (if parent table exists)
-- ALTER TABLE children 
-- ADD CONSTRAINT fk_children_parent_id 
-- FOREIGN KEY (parent_id) REFERENCES parents(id);

-- Step 4: Update existing records with a default parent_id
-- This assumes you have at least one parent record with id = 1
-- UPDATE children SET parent_id = 1 WHERE parent_id IS NULL;

-- Step 5: Verify the changes
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'children' 
    AND column_name = 'parent_id';

-- Step 6: Check indexes
SELECT 
    indexname, 
    indexdef 
FROM pg_indexes 
WHERE tablename = 'children' 
    AND indexname LIKE '%parent_id%';
