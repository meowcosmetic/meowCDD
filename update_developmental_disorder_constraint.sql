-- Migration: Update developmental_disorder_diagnosis check constraint
-- Date: 2024-01-15
-- Description: Add UNDER_INVESTIGATION value to the check constraint

-- Drop the existing check constraint
ALTER TABLE children DROP CONSTRAINT IF EXISTS children_developmental_disorder_diagnosis_check;

-- Add the new check constraint with all 4 values
ALTER TABLE children ADD CONSTRAINT children_developmental_disorder_diagnosis_check 
CHECK (developmental_disorder_diagnosis IN ('YES', 'NO', 'NOT_EVALUATED', 'UNDER_INVESTIGATION'));

-- Verify the constraint was added correctly
SELECT conname, pg_get_constraintdef(oid) 
FROM pg_constraint 
WHERE conname = 'children_developmental_disorder_diagnosis_check';
