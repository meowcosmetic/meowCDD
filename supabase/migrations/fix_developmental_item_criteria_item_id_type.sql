-- Fix item_id column type in developmental_item_criteria table
-- Change from bigint to uuid to match DevelopmentalDomainItem.id

-- First, drop the foreign key constraint
ALTER TABLE developmental_item_criteria DROP CONSTRAINT IF EXISTS fk_developmental_item_criteria_item;

-- Change the column type from bigint to uuid
ALTER TABLE developmental_item_criteria 
ALTER COLUMN item_id TYPE uuid USING item_id::text::uuid;

-- Recreate the foreign key constraint
ALTER TABLE developmental_item_criteria 
ADD CONSTRAINT fk_developmental_item_criteria_item 
FOREIGN KEY (item_id) REFERENCES developmental_domain_items(id) ON DELETE CASCADE;
