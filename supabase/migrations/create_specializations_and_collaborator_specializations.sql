-- Migration script to create specializations and collaborator_specializations tables
-- This creates a proper many-to-many relationship between collaborators and specializations

-- Drop existing table if exists
DROP TABLE IF EXISTS collaborator_specializations CASCADE;

-- Create specializations table (master data)
CREATE TABLE IF NOT EXISTS specializations (
    specialization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    specialization_name VARCHAR(255) NOT NULL UNIQUE,
    specialization_type VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    required_certifications JSONB,
    typical_skills JSONB,
    min_experience_years INTEGER,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- Check constraints
    CONSTRAINT chk_specialization_status 
        CHECK (status IN ('ACTIVE', 'INACTIVE', 'DEPRECATED')),
    
    CONSTRAINT chk_min_experience_years 
        CHECK (min_experience_years IS NULL OR min_experience_years >= 0)
);

-- Create collaborator_specializations table (junction table)
CREATE TABLE IF NOT EXISTS collaborator_specializations (
    collaborator_specialization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    collaborator_id UUID NOT NULL,
    specialization_id UUID NOT NULL,
    years_of_experience INTEGER,
    certifications JSONB,
    skills JSONB,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    proficiency_level VARCHAR(20) NOT NULL DEFAULT 'INTERMEDIATE',
    notes TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraints
    CONSTRAINT fk_collaborator_specializations_collaborator_id 
        FOREIGN KEY (collaborator_id) 
        REFERENCES collaborators(collaborator_id) 
        ON DELETE CASCADE,
    
    CONSTRAINT fk_collaborator_specializations_specialization_id 
        FOREIGN KEY (specialization_id) 
        REFERENCES specializations(specialization_id) 
        ON DELETE CASCADE,
    
    -- Check constraints
    CONSTRAINT chk_proficiency_level 
        CHECK (proficiency_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')),
    
    CONSTRAINT chk_collaborator_specialization_status 
        CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    
    CONSTRAINT chk_years_of_experience 
        CHECK (years_of_experience IS NULL OR years_of_experience >= 0),
    
    -- Unique constraint to prevent duplicate assignments
    CONSTRAINT uk_collaborator_specialization 
        UNIQUE (collaborator_id, specialization_id)
);

-- Create indexes for better performance
-- Specializations table indexes
CREATE INDEX IF NOT EXISTS idx_specializations_specialization_name 
    ON specializations(specialization_name);

CREATE INDEX IF NOT EXISTS idx_specializations_specialization_type 
    ON specializations(specialization_type);

CREATE INDEX IF NOT EXISTS idx_specializations_category 
    ON specializations(category);

CREATE INDEX IF NOT EXISTS idx_specializations_is_active 
    ON specializations(is_active);

CREATE INDEX IF NOT EXISTS idx_specializations_status 
    ON specializations(status);

-- Collaborator specializations table indexes
CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_collaborator_id 
    ON collaborator_specializations(collaborator_id);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_specialization_id 
    ON collaborator_specializations(specialization_id);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_proficiency_level 
    ON collaborator_specializations(proficiency_level);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_status 
    ON collaborator_specializations(status);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_is_primary 
    ON collaborator_specializations(is_primary);

-- Composite indexes for common queries
CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_collaborator_status 
    ON collaborator_specializations(collaborator_id, status);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_specialization_status 
    ON collaborator_specializations(specialization_id, status);

-- Add triggers to automatically update updated_at timestamp
CREATE OR REPLACE FUNCTION update_specializations_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_specializations_updated_at
    BEFORE UPDATE ON specializations
    FOR EACH ROW
    EXECUTE FUNCTION update_specializations_updated_at();

CREATE OR REPLACE FUNCTION update_collaborator_specializations_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_collaborator_specializations_updated_at
    BEFORE UPDATE ON collaborator_specializations
    FOR EACH ROW
    EXECUTE FUNCTION update_collaborator_specializations_updated_at();

-- Insert sample specializations data
INSERT INTO specializations (
    specialization_name,
    specialization_type,
    description,
    category,
    required_certifications,
    typical_skills,
    min_experience_years,
    is_active,
    status
) VALUES 
(
    'Speech Therapy',
    'THERAPY',
    'Specialized in speech and language development for children with communication disorders',
    'DEVELOPMENTAL',
    '{"required": ["ASHA CCC-SLP", "State License"], "optional": ["FEES", "VitalStim"]}'::jsonb,
    '{"core_skills": ["Articulation Therapy", "Language Development", "Fluency Therapy"], "age_groups": ["0-3", "3-6", "6-12"], "conditions": ["Apraxia", "Stuttering", "Language Delay"]}'::jsonb,
    2,
    true,
    'ACTIVE'
),
(
    'Occupational Therapy',
    'THERAPY',
    'Focus on fine motor skills, sensory integration, and daily living activities',
    'DEVELOPMENTAL',
    '{"required": ["AOTA NBCOT", "State License"], "optional": ["SIPT", "Sensory Integration"]}'::jsonb,
    '{"core_skills": ["Fine Motor Skills", "Sensory Integration", "Handwriting", "ADL Training"], "age_groups": ["0-3", "3-6", "6-12"], "conditions": ["Sensory Processing", "Motor Delay", "Handwriting Issues"]}'::jsonb,
    2,
    true,
    'ACTIVE'
),
(
    'Physical Therapy',
    'THERAPY',
    'Focus on gross motor skills and physical development',
    'DEVELOPMENTAL',
    '{"required": ["APTA License", "State License"], "optional": ["NDT", "PNF"]}'::jsonb,
    '{"core_skills": ["Gross Motor Skills", "Balance Training", "Gait Training", "Strength Training"], "age_groups": ["0-3", "3-6", "6-12"], "conditions": ["Cerebral Palsy", "Motor Delay", "Balance Issues"]}'::jsonb,
    2,
    true,
    'ACTIVE'
),
(
    'Developmental Assessment',
    'ASSESSMENT',
    'Comprehensive evaluation of child development across multiple domains',
    'ASSESSMENT',
    '{"required": ["Licensed Psychologist", "Assessment Training"], "optional": ["Bayley", "Mullen", "ADOS"]}'::jsonb,
    '{"core_skills": ["Cognitive Assessment", "Language Assessment", "Motor Assessment", "Social Assessment"], "age_groups": ["0-3", "3-6", "6-12"], "tools": ["Bayley-III", "Mullen", "ADOS-2"]}'::jsonb,
    3,
    true,
    'ACTIVE'
),
(
    'Behavioral Intervention',
    'INTERVENTION',
    'Applied Behavior Analysis and behavioral intervention strategies',
    'BEHAVIORAL',
    '{"required": ["BCBA", "State License"], "optional": ["RBT", "BCaBA"]}'::jsonb,
    '{"core_skills": ["ABA Therapy", "Behavior Management", "Social Skills Training", "Parent Training"], "age_groups": ["0-3", "3-6", "6-12"], "conditions": ["Autism", "ADHD", "Behavioral Issues"]}'::jsonb,
    2,
    true,
    'ACTIVE'
),
(
    'Early Intervention',
    'INTERVENTION',
    'Comprehensive early intervention services for infants and toddlers',
    'DEVELOPMENTAL',
    '{"required": ["Early Intervention Certification", "State License"], "optional": ["ITDS", "Service Coordinator"]}'::jsonb,
    '{"core_skills": ["Family-Centered Services", "Developmental Monitoring", "Service Coordination", "Transition Planning"], "age_groups": ["0-3"], "conditions": ["Developmental Delay", "Risk Factors", "Prematurity"]}'::jsonb,
    2,
    true,
    'ACTIVE'
);

-- Add comments to tables
COMMENT ON TABLE specializations IS 'Master data table containing all available specializations in the system';
COMMENT ON TABLE collaborator_specializations IS 'Junction table linking collaborators to their specializations with additional details';

COMMENT ON COLUMN specializations.specialization_id IS 'Unique identifier for the specialization';
COMMENT ON COLUMN specializations.specialization_name IS 'Name of the specialization (e.g., Speech Therapy)';
COMMENT ON COLUMN specializations.specialization_type IS 'Type of specialization (THERAPY, ASSESSMENT, INTERVENTION)';
COMMENT ON COLUMN specializations.description IS 'Detailed description of the specialization';
COMMENT ON COLUMN specializations.category IS 'Category of specialization (DEVELOPMENTAL, BEHAVIORAL, ASSESSMENT)';
COMMENT ON COLUMN specializations.required_certifications IS 'JSON object containing required certifications';
COMMENT ON COLUMN specializations.typical_skills IS 'JSON object containing typical skills and competencies';
COMMENT ON COLUMN specializations.min_experience_years IS 'Minimum years of experience required';

COMMENT ON COLUMN collaborator_specializations.collaborator_specialization_id IS 'Unique identifier for the collaborator-specialization relationship';
COMMENT ON COLUMN collaborator_specializations.collaborator_id IS 'Reference to the collaborator';
COMMENT ON COLUMN collaborator_specializations.specialization_id IS 'Reference to the specialization';
COMMENT ON COLUMN collaborator_specializations.years_of_experience IS 'Years of experience in this specialization';
COMMENT ON COLUMN collaborator_specializations.certifications IS 'JSON object containing specific certifications for this specialization';
COMMENT ON COLUMN collaborator_specializations.skills IS 'JSON object containing specific skills for this specialization';
COMMENT ON COLUMN collaborator_specializations.is_primary IS 'Whether this is the primary specialization';
COMMENT ON COLUMN collaborator_specializations.proficiency_level IS 'Level of proficiency (BEGINNER, INTERMEDIATE, ADVANCED, EXPERT)';
COMMENT ON COLUMN collaborator_specializations.notes IS 'Additional notes about this specialization for the collaborator';
