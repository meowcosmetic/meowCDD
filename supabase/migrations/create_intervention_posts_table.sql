-- Tạo bảng intervention_posts để lưu trữ các bài post can thiệp
CREATE TABLE IF NOT EXISTS intervention_posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    content JSONB NOT NULL, -- JSON object chứa nội dung bài post
    post_type VARCHAR(50) NOT NULL CHECK (post_type IN (
        'INTERVENTION_METHOD', 'CHECKLIST', 'GUIDELINE', 
        'EXAMPLE', 'TIP', 'TROUBLESHOOTING', 'CONCLUSION'
    )),
    difficulty_level INTEGER CHECK (difficulty_level >= 1 AND difficulty_level <= 5),
    target_age_min_months INTEGER,
    target_age_max_months INTEGER,
    estimated_duration_minutes INTEGER,
    tags VARCHAR(1000), -- Các tag phân cách bằng dấu phẩy
    is_published BOOLEAN NOT NULL DEFAULT FALSE,
    author VARCHAR(100),
    version VARCHAR(20),
    
    -- Liên kết với mục tiêu can thiệp
    criteria_id BIGINT REFERENCES developmental_item_criteria(id) ON DELETE SET NULL,
    
    -- Liên kết với chương trình can thiệp
    program_id BIGINT REFERENCES developmental_programs(id) ON DELETE SET NULL,
    
    -- Timestamps
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tạo indexes để tối ưu hiệu suất
CREATE INDEX IF NOT EXISTS idx_intervention_posts_criteria_id ON intervention_posts(criteria_id);
CREATE INDEX IF NOT EXISTS idx_intervention_posts_program_id ON intervention_posts(program_id);
CREATE INDEX IF NOT EXISTS idx_intervention_posts_post_type ON intervention_posts(post_type);
CREATE INDEX IF NOT EXISTS idx_intervention_posts_is_published ON intervention_posts(is_published);
CREATE INDEX IF NOT EXISTS idx_intervention_posts_author ON intervention_posts(author);
CREATE INDEX IF NOT EXISTS idx_intervention_posts_difficulty_level ON intervention_posts(difficulty_level);
CREATE INDEX IF NOT EXISTS idx_intervention_posts_target_age ON intervention_posts(target_age_min_months, target_age_max_months);

-- Tạo index cho full-text search trên title và tags
CREATE INDEX IF NOT EXISTS idx_intervention_posts_title_search ON intervention_posts USING gin(to_tsvector('english', title));
CREATE INDEX IF NOT EXISTS idx_intervention_posts_tags_search ON intervention_posts USING gin(to_tsvector('english', tags));

-- Tạo trigger để tự động cập nhật updated_at
CREATE OR REPLACE FUNCTION update_intervention_posts_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_intervention_posts_updated_at
    BEFORE UPDATE ON intervention_posts
    FOR EACH ROW
    EXECUTE FUNCTION update_intervention_posts_updated_at();

-- Thêm comment cho bảng
COMMENT ON TABLE intervention_posts IS 'Bảng lưu trữ các bài post can thiệp phát triển trẻ em';
COMMENT ON COLUMN intervention_posts.title IS 'Tiêu đề bài post';
COMMENT ON COLUMN intervention_posts.content IS 'Nội dung bài post dạng JSON';
COMMENT ON COLUMN intervention_posts.post_type IS 'Loại bài post (INTERVENTION_METHOD, CHECKLIST, GUIDELINE, EXAMPLE, TIP, TROUBLESHOOTING, CONCLUSION)';
COMMENT ON COLUMN intervention_posts.difficulty_level IS 'Mức độ khó từ 1-5';
COMMENT ON COLUMN intervention_posts.target_age_min_months IS 'Độ tuổi tối thiểu (tháng)';
COMMENT ON COLUMN intervention_posts.target_age_max_months IS 'Độ tuổi tối đa (tháng)';
COMMENT ON COLUMN intervention_posts.estimated_duration_minutes IS 'Thời gian ước tính thực hiện (phút)';
COMMENT ON COLUMN intervention_posts.tags IS 'Các tag phân cách bằng dấu phẩy';
COMMENT ON COLUMN intervention_posts.is_published IS 'Trạng thái xuất bản';
COMMENT ON COLUMN intervention_posts.author IS 'Tác giả bài post';
COMMENT ON COLUMN intervention_posts.version IS 'Phiên bản bài post';
COMMENT ON COLUMN intervention_posts.criteria_id IS 'ID mục tiêu can thiệp liên kết';
COMMENT ON COLUMN intervention_posts.program_id IS 'ID chương trình can thiệp liên kết';
