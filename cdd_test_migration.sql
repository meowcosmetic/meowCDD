-- Migration script for CDD Tests table structure update
-- This script updates the cdd_tests table to support the new structure

-- Step 1: Add new columns for multilingual support and new fields
ALTER TABLE cdd_tests 
ADD COLUMN IF NOT EXISTS assessment_code VARCHAR(255) UNIQUE,
ADD COLUMN IF NOT EXISTS names_json TEXT,
ADD COLUMN IF NOT EXISTS descriptions_json TEXT,
ADD COLUMN IF NOT EXISTS instructions_json TEXT,
ADD COLUMN IF NOT EXISTS version VARCHAR(50),
ADD COLUMN IF NOT EXISTS estimated_duration INTEGER,
ADD COLUMN IF NOT EXISTS administration_type VARCHAR(50),
ADD COLUMN IF NOT EXISTS required_qualifications VARCHAR(50),
ADD COLUMN IF NOT EXISTS required_materials_json TEXT,
ADD COLUMN IF NOT EXISTS notes_json TEXT;

-- Step 2: Update existing data to migrate from old structure to new structure
-- Migrate code to assessment_code
UPDATE cdd_tests 
SET assessment_code = code 
WHERE assessment_code IS NULL AND code IS NOT NULL;

-- Migrate name to names_json
UPDATE cdd_tests 
SET names_json = json_build_object('vi', name, 'en', name)
WHERE names_json IS NULL AND name IS NOT NULL;

-- Migrate description to descriptions_json
UPDATE cdd_tests 
SET descriptions_json = json_build_object('vi', description, 'en', description)
WHERE descriptions_json IS NULL AND description IS NOT NULL;

-- Migrate instructions to instructions_json
UPDATE cdd_tests 
SET instructions_json = json_build_object('vi', instructions, 'en', instructions)
WHERE instructions_json IS NULL AND instructions IS NOT NULL;

-- Set default values for new required fields
UPDATE cdd_tests 
SET version = '1.0',
    estimated_duration = 15,
    administration_type = 'PARENT_REPORT',
    required_qualifications = 'NO_QUALIFICATION_REQUIRED',
    required_materials_json = '["Bảng câu hỏi", "Bút"]',
    notes_json = json_build_object('vi', notes, 'en', notes)
WHERE version IS NULL;

-- Step 3: Make assessment_code NOT NULL after migration
ALTER TABLE cdd_tests 
ALTER COLUMN assessment_code SET NOT NULL;

-- Step 4: Drop old columns that are no longer needed
ALTER TABLE cdd_tests 
DROP COLUMN IF EXISTS code,
DROP COLUMN IF EXISTS name,
DROP COLUMN IF EXISTS description,
DROP COLUMN IF EXISTS instructions,
DROP COLUMN IF EXISTS total_questions,
DROP COLUMN IF EXISTS passing_score,
DROP COLUMN IF EXISTS max_score;

-- Step 5: Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_cdd_tests_assessment_code ON cdd_tests(assessment_code);
CREATE INDEX IF NOT EXISTS idx_cdd_tests_status ON cdd_tests(status);
CREATE INDEX IF NOT EXISTS idx_cdd_tests_category ON cdd_tests(category);
CREATE INDEX IF NOT EXISTS idx_cdd_tests_age_range ON cdd_tests(min_age_months, max_age_months);
CREATE INDEX IF NOT EXISTS idx_cdd_tests_administration_type ON cdd_tests(administration_type);
CREATE INDEX IF NOT EXISTS idx_cdd_tests_required_qualifications ON cdd_tests(required_qualifications);

-- Step 6: Add constraints
ALTER TABLE cdd_tests 
ADD CONSTRAINT IF NOT EXISTS chk_cdd_tests_age_range 
CHECK (min_age_months <= max_age_months),
ADD CONSTRAINT IF NOT EXISTS chk_cdd_tests_estimated_duration 
CHECK (estimated_duration > 0);

-- Step 7: Insert sample data for DEVELOPMENTAL_SCREENING_V1
INSERT INTO cdd_tests (
    assessment_code,
    names_json,
    descriptions_json,
    instructions_json,
    category,
    min_age_months,
    max_age_months,
    status,
    version,
    estimated_duration,
    administration_type,
    required_qualifications,
    required_materials_json,
    notes_json,
    questions_json,
    scoring_criteria_json,
    created_at,
    updated_at
) VALUES (
    'DEVELOPMENTAL_SCREENING_V1',
    '{"vi": "Bảng câu hỏi sàng lọc phát triển trẻ em", "en": "Developmental Screening Questionnaire"}',
    '{"vi": "Bảng câu hỏi đánh giá các kỹ năng phát triển của trẻ em bao gồm giao tiếp-ngôn ngữ, vận động thô, vận động tinh, bắt chước và học, cá nhân-xã hội", "en": "Questionnaire to assess children''s developmental skills including communication-language, gross motor, fine motor, imitation and learning, personal-social"}',
    '{"vi": "Hãy trả lời các câu hỏi dưới đây dựa trên quan sát và hiểu biết về trẻ. Chọn ''Có'' nếu trẻ có dấu hiệu được mô tả, ''Không'' nếu trẻ không có dấu hiệu đó.", "en": "Please answer the following questions based on your observation and knowledge of the child. Select ''Yes'' if the child shows the described sign, ''No'' if the child does not show that sign."}',
    'DEVELOPMENTAL_SCREENING',
    0,
    6,
    'ACTIVE',
    '1.0',
    15,
    'PARENT_REPORT',
    'NO_QUALIFICATION_REQUIRED',
    '["Bảng câu hỏi", "Bút"]',
    '{"vi": "Bảng câu hỏi này được thiết kế để sàng lọc sớm các dấu hiệu bất thường trong phát triển của trẻ sơ sinh và trẻ nhỏ", "en": "This questionnaire is designed for early screening of abnormal developmental signs in infants and young children"}',
    '[
        {
            "questionId": "COMM_001",
            "questionNumber": 1,
            "questionTexts": {
                "vi": "Trẻ có dấu hiệu \"Không bao giờ phát ra những âm thanh\"?",
                "en": "Does the child show signs of \"Never making any sounds\"?"
            },
            "category": "COMMUNICATION_LANGUAGE",
            "weight": 1,
            "required": true,
            "hints": {
                "vi": "Quan sát xem trẻ có phát ra âm thanh gì không (khóc, cười, bập bẹ...)",
                "en": "Observe if the child makes any sounds (crying, laughing, babbling...)"
            },
            "explanations": {
                "vi": "Trẻ sơ sinh thường phát ra âm thanh khi khóc, cười hoặc bập bẹ. Nếu trẻ không bao giờ phát ra âm thanh nào có thể là dấu hiệu bất thường.",
                "en": "Infants typically make sounds when crying, laughing, or babbling. If the child never makes any sounds, it could be an abnormal sign."
            }
        },
        {
            "questionId": "COMM_002",
            "questionNumber": 2,
            "questionTexts": {
                "vi": "Trẻ có dấu hiệu \"Không biết thể hiện gì khi đói, ướt do đái, ỉa\"?",
                "en": "Does the child show signs of \"Not knowing how to express when hungry, wet from urination, defecation\"?"
            },
            "category": "COMMUNICATION_LANGUAGE",
            "weight": 1,
            "required": true,
            "hints": {
                "vi": "Quan sát xem trẻ có phản ứng khi khó chịu (khóc, cựa quậy...) không",
                "en": "Observe if the child reacts when uncomfortable (crying, fidgeting...)"
            },
            "explanations": {
                "vi": "Trẻ thường có phản ứng khi khó chịu như khóc, cựa quậy. Nếu trẻ không có phản ứng gì có thể là dấu hiệu bất thường.",
                "en": "Children usually react when uncomfortable such as crying, fidgeting. If the child shows no reaction, it could be an abnormal sign."
            }
        }
    ]',
    '{
        "totalQuestions": 20,
        "yesScore": 1,
        "noScore": 0,
        "scoreRanges": {
            "LOW_RISK": {
                "minScore": 0,
                "maxScore": 2,
                "level": "LOW_RISK",
                "descriptions": {
                    "vi": "Nguy cơ thấp - Trẻ có ít dấu hiệu bất thường",
                    "en": "Low risk - Child has few abnormal signs"
                },
                "recommendation": "Tiếp tục theo dõi phát triển bình thường"
            },
            "MEDIUM_RISK": {
                "minScore": 3,
                "maxScore": 5,
                "level": "MEDIUM_RISK",
                "descriptions": {
                    "vi": "Nguy cơ trung bình - Trẻ có một số dấu hiệu bất thường",
                    "en": "Medium risk - Child has some abnormal signs"
                },
                "recommendation": "Cần theo dõi chặt chẽ và đánh giá lại sau 1-2 tháng"
            },
            "HIGH_RISK": {
                "minScore": 6,
                "maxScore": 20,
                "level": "HIGH_RISK",
                "descriptions": {
                    "vi": "Nguy cơ cao - Trẻ có nhiều dấu hiệu bất thường",
                    "en": "High risk - Child has many abnormal signs"
                },
                "recommendation": "Cần đánh giá chuyên môn ngay lập tức"
            }
        },
        "interpretation": "Điểm càng cao, nguy cơ bất thường phát triển càng lớn. Cần đánh giá chuyên môn nếu có nhiều dấu hiệu bất thường."
    }',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON CONFLICT (assessment_code) DO NOTHING;

-- Step 8: Verify the migration
SELECT 
    assessment_code,
    names_json,
    category,
    status,
    version,
    estimated_duration,
    administration_type,
    required_qualifications
FROM cdd_tests 
WHERE assessment_code = 'DEVELOPMENTAL_SCREENING_V1';


