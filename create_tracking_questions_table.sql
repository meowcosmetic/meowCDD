-- Tạo bảng tracking_questions
CREATE TABLE IF NOT EXISTS tracking_questions (
    id BIGSERIAL PRIMARY KEY,
    category JSONB NOT NULL, -- { "vi": "Giao tiếp", "en": "Communication" }
    domain VARCHAR(50) NOT NULL, -- Ví dụ: "communication", "social_interaction", "behavior_emotion", "cognition", "independence"
    age_range VARCHAR(20) NOT NULL, -- Độ tuổi áp dụng, ví dụ "2-6"
    frequency VARCHAR(20) NOT NULL, -- Tần suất, ví dụ "daily"
    context JSONB NOT NULL, -- JSON array: ["home", "school"]
    question JSONB NOT NULL, -- { "vi": "...", "en": "..." }
    options JSONB NOT NULL, -- JSON array: [{ "text": { "vi": "...", "en": "..." }, "score": 0 }]
    note TEXT, -- Ghi chú phụ huynh/giáo viên
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo index cho các trường thường được query
CREATE INDEX IF NOT EXISTS idx_tracking_questions_domain ON tracking_questions(domain);
CREATE INDEX IF NOT EXISTS idx_tracking_questions_age_range ON tracking_questions(age_range);
CREATE INDEX IF NOT EXISTS idx_tracking_questions_frequency ON tracking_questions(frequency);
CREATE INDEX IF NOT EXISTS idx_tracking_questions_domain_age_range ON tracking_questions(domain, age_range);

-- Tạo trigger để tự động cập nhật updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_tracking_questions_updated_at 
    BEFORE UPDATE ON tracking_questions 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();

-- Thêm comment cho bảng
COMMENT ON TABLE tracking_questions IS 'Bảng lưu trữ các câu hỏi theo dõi phát triển của trẻ';
COMMENT ON COLUMN tracking_questions.category IS 'Danh mục câu hỏi (song ngữ)';
COMMENT ON COLUMN tracking_questions.domain IS 'Lĩnh vực phát triển';
COMMENT ON COLUMN tracking_questions.age_range IS 'Độ tuổi áp dụng';
COMMENT ON COLUMN tracking_questions.frequency IS 'Tần suất theo dõi';
COMMENT ON COLUMN tracking_questions.context IS 'Bối cảnh áp dụng';
COMMENT ON COLUMN tracking_questions.question IS 'Nội dung câu hỏi (song ngữ)';
COMMENT ON COLUMN tracking_questions.options IS 'Các lựa chọn trả lời với điểm số';
COMMENT ON COLUMN tracking_questions.note IS 'Ghi chú bổ sung';
