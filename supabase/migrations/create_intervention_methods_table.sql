-- Create intervention_methods table
CREATE TABLE IF NOT EXISTS intervention_methods (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    displayed_name JSONB NOT NULL,
    description JSONB,
    min_age_months INT,
    max_age_months INT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_intervention_methods_code ON intervention_methods(code);
CREATE INDEX IF NOT EXISTS idx_intervention_methods_name_gin ON intervention_methods USING GIN (displayed_name jsonb_path_ops);
CREATE INDEX IF NOT EXISTS idx_intervention_methods_desc_gin ON intervention_methods USING GIN (description jsonb_path_ops);
CREATE INDEX IF NOT EXISTS idx_intervention_methods_created_at ON intervention_methods(created_at);
CREATE INDEX IF NOT EXISTS idx_intervention_methods_updated_at ON intervention_methods(updated_at);

-- Insert some sample data
INSERT INTO intervention_methods (code, displayed_name, description, min_age_months, max_age_months) VALUES
('PLAY_THERAPY', '{"vi": "Trị liệu bằng trò chơi", "en": "Play Therapy"}', '{"vi": "Sử dụng trò chơi để hỗ trợ phát triển tâm lý và xã hội của trẻ", "en": "Using play to support psychological and social development of children"}', 12, 72),
('SPEECH_THERAPY', '{"vi": "Trị liệu ngôn ngữ", "en": "Speech Therapy"}', '{"vi": "Hỗ trợ phát triển kỹ năng giao tiếp và ngôn ngữ", "en": "Supporting communication and language skills development"}', 6, 120),
('OCCUPATIONAL_THERAPY', '{"vi": "Trị liệu nghề nghiệp", "en": "Occupational Therapy"}', '{"vi": "Hỗ trợ phát triển kỹ năng vận động và sinh hoạt hàng ngày", "en": "Supporting motor skills and daily living activities development"}', 12, 144),
('BEHAVIORAL_INTERVENTION', '{"vi": "Can thiệp hành vi", "en": "Behavioral Intervention"}', '{"vi": "Các phương pháp điều chỉnh và cải thiện hành vi", "en": "Methods to modify and improve behavior"}', 18, 180),
('SENSORY_INTEGRATION', '{"vi": "Tích hợp cảm giác", "en": "Sensory Integration"}', '{"vi": "Hỗ trợ xử lý và tích hợp thông tin cảm giác", "en": "Supporting sensory information processing and integration"}', 6, 96);

