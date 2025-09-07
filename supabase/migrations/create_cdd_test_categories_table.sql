-- Create cdd_test_categories table
CREATE TABLE IF NOT EXISTS cdd_test_categories (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    displayed_name TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create index on code for faster lookups
CREATE INDEX IF NOT EXISTS idx_cdd_test_categories_code ON cdd_test_categories(code);

-- Insert some sample data
INSERT INTO cdd_test_categories (code, displayed_name, description) VALUES
('DEVELOPMENTAL_SCREENING', '{"vi": "Sàng lọc phát triển", "en": "Developmental Screening"}', '{"vi": "Các bài kiểm tra sàng lọc phát triển cho trẻ em", "en": "Developmental screening tests for children"}'),
('COGNITIVE_ASSESSMENT', '{"vi": "Đánh giá nhận thức", "en": "Cognitive Assessment"}', '{"vi": "Các bài kiểm tra đánh giá khả năng nhận thức", "en": "Tests to assess cognitive abilities"}'),
('MOTOR_SKILLS', '{"vi": "Kỹ năng vận động", "en": "Motor Skills"}', '{"vi": "Đánh giá kỹ năng vận động thô và tinh", "en": "Assessment of gross and fine motor skills"}'),
('LANGUAGE_DEVELOPMENT', '{"vi": "Phát triển ngôn ngữ", "en": "Language Development"}', '{"vi": "Đánh giá khả năng ngôn ngữ và giao tiếp", "en": "Assessment of language and communication skills"}'),
('SOCIAL_EMOTIONAL', '{"vi": "Phát triển xã hội-cảm xúc", "en": "Social-Emotional Development"}', '{"vi": "Đánh giá phát triển xã hội và cảm xúc", "en": "Assessment of social and emotional development"}')
ON CONFLICT (code) DO NOTHING;

