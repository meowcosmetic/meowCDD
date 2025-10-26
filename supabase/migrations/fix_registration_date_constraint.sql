-- Fix registration_date constraint for children table
-- This migration ensures registration_date column allows null values temporarily
-- and sets default value to current timestamp

-- Step 1: Drop the NOT NULL constraint temporarily
ALTER TABLE children ALTER COLUMN registration_date DROP NOT NULL;

-- Step 2: Update existing null values to current timestamp
UPDATE children 
SET registration_date = CURRENT_TIMESTAMP 
WHERE registration_date IS NULL;

-- Step 3: Add back the NOT NULL constraint
ALTER TABLE children ALTER COLUMN registration_date SET NOT NULL;

-- Step 4: Set default value for future inserts
ALTER TABLE children ALTER COLUMN registration_date SET DEFAULT CURRENT_TIMESTAMP;
