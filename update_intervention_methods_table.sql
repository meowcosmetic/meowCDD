-- Update intervention_methods table to add group_id column
DO $$
BEGIN
    -- Add group_id column if it doesn't exist
    IF NOT EXISTS (SELECT FROM information_schema.columns WHERE table_name = 'intervention_methods' AND column_name = 'group_id') THEN
        ALTER TABLE intervention_methods ADD COLUMN group_id BIGINT;
        
        -- Add foreign key constraint
        ALTER TABLE intervention_methods ADD CONSTRAINT fk_intervention_methods_group_id 
            FOREIGN KEY (group_id) REFERENCES intervention_method_groups(id);
            
        -- Create index
        CREATE INDEX IF NOT EXISTS idx_intervention_methods_group_id ON intervention_methods(group_id);
        
        RAISE NOTICE 'Added group_id column to intervention_methods table';
    ELSE
        RAISE NOTICE 'group_id column already exists in intervention_methods table';
    END IF;
END $$;
