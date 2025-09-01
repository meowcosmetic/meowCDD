-- Update the check constraint for result_level to include NEEDS_ATTENTION
ALTER TABLE child_test_records DROP CONSTRAINT IF EXISTS child_test_records_result_level_check;

ALTER TABLE child_test_records ADD CONSTRAINT child_test_records_result_level_check 
CHECK (result_level IN ('EXCELLENT', 'GOOD', 'AVERAGE', 'BELOW_AVERAGE', 'POOR', 'NEEDS_INTERVENTION', 'NEEDS_ATTENTION'));

