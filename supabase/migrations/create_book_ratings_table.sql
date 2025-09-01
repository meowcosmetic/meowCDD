-- Migration: Create book_ratings table
-- Description: Tạo bảng book_ratings để lưu trữ đánh giá sách

-- Tạo bảng book_ratings
CREATE TABLE book_ratings (
    id BIGSERIAL PRIMARY KEY,
    
    -- Quan hệ với các bảng khác
    book_id BIGINT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    parent_id VARCHAR(255) NOT NULL, -- ID phụ huynh đánh giá
    
    -- Thông tin đánh giá
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5), -- Điểm đánh giá (1-5)
    review TEXT, -- Nội dung đánh giá
    review_title VARCHAR(255), -- Tiêu đề đánh giá
    
    -- Thông tin bổ sung
    helpful_count INTEGER DEFAULT 0, -- Số người thấy hữu ích
    not_helpful_count INTEGER DEFAULT 0, -- Số người thấy không hữu ích
    rating_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Ngày đánh giá
    is_edited BOOLEAN DEFAULT false, -- Có chỉnh sửa không
    edit_date TIMESTAMP, -- Ngày chỉnh sửa
    
    -- Loại đánh giá
    rating_type VARCHAR(50) DEFAULT 'PARENT_RATING' CHECK (rating_type IN ('PARENT_RATING', 'EDUCATOR_RATING', 'EXPERT_RATING', 'CHILD_FEEDBACK')),
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo indexes
CREATE INDEX idx_book_ratings_book_id ON book_ratings(book_id);
CREATE INDEX idx_book_ratings_parent_id ON book_ratings(parent_id);
CREATE INDEX idx_book_ratings_rating ON book_ratings(rating);
CREATE INDEX idx_book_ratings_rating_type ON book_ratings(rating_type);
CREATE INDEX idx_book_ratings_rating_date ON book_ratings(rating_date);
CREATE INDEX idx_book_ratings_is_edited ON book_ratings(is_edited);
CREATE INDEX idx_book_ratings_helpful_count ON book_ratings(helpful_count);
CREATE INDEX idx_book_ratings_created_at ON book_ratings(created_at);

-- Tạo unique constraint để đảm bảo mỗi phụ huynh chỉ đánh giá một lần cho mỗi sách
CREATE UNIQUE INDEX idx_book_ratings_book_parent_unique ON book_ratings(book_id, parent_id);

-- Tạo trigger để tự động cập nhật updated_at
CREATE OR REPLACE FUNCTION update_book_ratings_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_book_ratings_updated_at
    BEFORE UPDATE ON book_ratings
    FOR EACH ROW
    EXECUTE FUNCTION update_book_ratings_updated_at();

-- Tạo trigger để tự động cập nhật edit_date khi is_edited = true
CREATE OR REPLACE FUNCTION update_book_ratings_edit_date()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_edited = true AND OLD.is_edited = false THEN
        NEW.edit_date = CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_book_ratings_edit_date
    BEFORE UPDATE ON book_ratings
    FOR EACH ROW
    EXECUTE FUNCTION update_book_ratings_edit_date();

-- Tạo function để cập nhật thống kê đánh giá cho sách
CREATE OR REPLACE FUNCTION update_book_rating_statistics()
RETURNS TRIGGER AS $$
BEGIN
    -- Cập nhật average_rating và total_ratings cho sách
    UPDATE books 
    SET 
        average_rating = (
            SELECT AVG(rating)::DECIMAL(3,2)
            FROM book_ratings 
            WHERE book_id = COALESCE(NEW.book_id, OLD.book_id)
        ),
        total_ratings = (
            SELECT COUNT(*)
            FROM book_ratings 
            WHERE book_id = COALESCE(NEW.book_id, OLD.book_id)
        )
    WHERE id = COALESCE(NEW.book_id, OLD.book_id);
    
    RETURN COALESCE(NEW, OLD);
END;
$$ LANGUAGE plpgsql;

-- Tạo trigger để tự động cập nhật thống kê khi có thay đổi đánh giá
CREATE TRIGGER trigger_update_book_rating_statistics
    AFTER INSERT OR UPDATE OR DELETE ON book_ratings
    FOR EACH ROW
    EXECUTE FUNCTION update_book_rating_statistics();

-- Thêm comments
COMMENT ON TABLE book_ratings IS 'Bảng lưu trữ đánh giá sách của người dùng';
COMMENT ON COLUMN book_ratings.id IS 'ID chính của đánh giá';
COMMENT ON COLUMN book_ratings.book_id IS 'ID sách được đánh giá';
COMMENT ON COLUMN book_ratings.parent_id IS 'ID phụ huynh đánh giá';
COMMENT ON COLUMN book_ratings.rating IS 'Điểm đánh giá (1-5)';
COMMENT ON COLUMN book_ratings.review IS 'Nội dung đánh giá';
COMMENT ON COLUMN book_ratings.review_title IS 'Tiêu đề đánh giá';
COMMENT ON COLUMN book_ratings.helpful_count IS 'Số người thấy hữu ích';
COMMENT ON COLUMN book_ratings.not_helpful_count IS 'Số người thấy không hữu ích';
COMMENT ON COLUMN book_ratings.rating_date IS 'Ngày đánh giá';
COMMENT ON COLUMN book_ratings.is_edited IS 'Có chỉnh sửa không';
COMMENT ON COLUMN book_ratings.edit_date IS 'Ngày chỉnh sửa';
COMMENT ON COLUMN book_ratings.rating_type IS 'Loại đánh giá: PARENT_RATING, EDUCATOR_RATING, EXPERT_RATING, CHILD_FEEDBACK';

-- Thêm dữ liệu mẫu
INSERT INTO book_ratings (
    book_id, parent_id, rating, review, review_title, 
    helpful_count, not_helpful_count, rating_type
) VALUES 
(
    1, 'parent-001', 5, 
    'Sách rất hay và bổ ích cho trẻ. Các câu chuyện cổ tích được kể một cách sinh động và dễ hiểu. Con tôi rất thích nghe kể chuyện từ sách này.',
    'Sách cổ tích tuyệt vời cho trẻ',
    3, 0, 'PARENT_RATING'
),
(
    1, 'parent-002', 4, 
    'Nội dung tốt, hình ảnh đẹp. Tuy nhiên có thể cải thiện thêm về phần âm thanh.',
    'Sách hay, cần cải thiện âm thanh',
    1, 1, 'PARENT_RATING'
),
(
    2, 'parent-003', 5, 
    'Perfect book for teaching colors to my toddler. Bright and engaging illustrations.',
    'Excellent color learning book',
    5, 0, 'PARENT_RATING'
),
(
    2, 'parent-004', 4, 
    'Good book, but could use more interactive elements.',
    'Good but needs more interaction',
    2, 0, 'PARENT_RATING'
),
(
    3, 'parent-005', 5, 
    'Sách toán học rất vui nhộn và dễ hiểu. Con tôi học được nhiều điều bổ ích.',
    'Sách toán học tuyệt vời',
    4, 0, 'PARENT_RATING'
),
(
    3, 'parent-006', 4, 
    'Nội dung tốt, nhưng có thể thêm nhiều bài tập hơn.',
    'Nội dung tốt, cần thêm bài tập',
    1, 0, 'PARENT_RATING'
),
(
    1, 'educator-001', 5, 
    'Sách rất phù hợp để sử dụng trong lớp học mầm non. Trẻ em rất thích thú.',
    'Phù hợp cho giáo dục mầm non',
    8, 0, 'EDUCATOR_RATING'
),
(
    2, 'expert-001', 4, 
    'Well-designed educational content with appropriate difficulty level for target age group.',
    'Well-designed educational content',
    6, 0, 'EXPERT_RATING'
);
