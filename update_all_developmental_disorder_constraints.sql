-- Migration: Update all developmental_disorder_diagnosis check constraints
-- Date: 2024-01-15
-- Description: Add UNDER_INVESTIGATION value to all check constraints

-- Function to update check constraints for a given table
CREATE OR REPLACE FUNCTION update_developmental_disorder_constraint(table_name text)
RETURNS void AS $$
BEGIN
    -- Drop existing constraint if it exists
    EXECUTE format('ALTER TABLE %I DROP CONSTRAINT IF EXISTS %I_developmental_disorder_diagnosis_check', 
                   table_name, table_name);
    
    -- Add new constraint with all 4 values
    EXECUTE format('ALTER TABLE %I ADD CONSTRAINT %I_developmental_disorder_diagnosis_check 
                   CHECK (developmental_disorder_diagnosis IN (''YES'', ''NO'', ''NOT_EVALUATED'', ''UNDER_INVESTIGATION''))', 
                   table_name, table_name);
    
    RAISE NOTICE 'Updated constraint for table: %', table_name;
END;
$$ LANGUAGE plpgsql;

-- Update constraints for all tables that might have this column
SELECT update_developmental_disorder_constraint('children');

-- If you have other tables with similar constraints, add them here
-- SELECT update_developmental_disorder_constraint('other_table_name');

-- Clean up the function
DROP FUNCTION update_developmental_disorder_constraint(text);

-- Verify all constraints were updated correctly
SELECT 
    t.table_name,
    c.constraint_name,
    c.check_clause
FROM information_schema.table_constraints t
JOIN information_schema.check_constraints c ON t.constraint_name = c.constraint_name
WHERE t.constraint_type = 'CHECK' 
  AND c.constraint_name LIKE '%developmental_disorder_diagnosis%'
ORDER BY t.table_name;
