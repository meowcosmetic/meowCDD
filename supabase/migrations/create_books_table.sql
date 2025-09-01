-- Migration: Create books table
-- Description: Tạo bảng books để lưu trữ thông tin sách

-- Tạo bảng books
CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publisher VARCHAR(255),
    isbn VARCHAR(50),
    publication_year INTEGER,
    
    -- Quan hệ với các bảng khác
    supported_format_id BIGINT NOT NULL REFERENCES supported_formats(id),
    
    -- Thông tin độ tuổi
    min_age INTEGER, -- Độ tuổi tối thiểu (tháng)
    max_age INTEGER, -- Độ tuổi tối đa (tháng)
    age_group VARCHAR(50), -- Nhóm tuổi: INFANT, TODDLER, PRESCHOOL, SCHOOL_AGE
    
    -- Thông tin nội dung
    description TEXT,
    summary TEXT,
    language VARCHAR(10), -- Ngôn ngữ: VI, EN, v.v.
    
    -- Thông tin file
    file_size BIGINT, -- Kích thước file (bytes)
    page_count INTEGER, -- Số trang (nếu là sách)
    
    -- Thông tin đánh giá (tính toán từ book_ratings)
    average_rating DECIMAL(3,2), -- Điểm đánh giá trung bình (1-5)
    total_ratings INTEGER DEFAULT 0, -- Tổng số lượt đánh giá
    total_views BIGINT DEFAULT 0, -- Tổng lượt xem
    
    -- Thông tin trạng thái
    is_active BOOLEAN DEFAULT true,
    is_featured BOOLEAN DEFAULT false,
    
    -- Thông tin bổ sung
    cover_image_url TEXT,
    preview_url TEXT,
    keywords TEXT, -- Từ khóa tìm kiếm (phân cách bằng dấu phẩy)
    tags TEXT, -- Tags (JSON array)
    metadata TEXT, -- Metadata bổ sung (JSON)
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng trung gian cho quan hệ many-to-many với developmental_domains
CREATE TABLE book_developmental_domains (
    book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
    developmental_domain_id UUID REFERENCES developmental_domains(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, developmental_domain_id)
);

-- Tạo indexes
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_books_publisher ON books(publisher);
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_supported_format_id ON books(supported_format_id);
CREATE INDEX idx_books_age_group ON books(age_group);
CREATE INDEX idx_books_language ON books(language);
CREATE INDEX idx_books_is_active ON books(is_active);
CREATE INDEX idx_books_is_featured ON books(is_featured);
CREATE INDEX idx_books_average_rating ON books(average_rating);
CREATE INDEX idx_books_publication_year ON books(publication_year);
CREATE INDEX idx_books_created_at ON books(created_at);

-- Index cho bảng trung gian
CREATE INDEX idx_book_developmental_domains_book_id ON book_developmental_domains(book_id);
CREATE INDEX idx_book_developmental_domains_domain_id ON book_developmental_domains(developmental_domain_id);

-- Tạo unique constraint cho ISBN
CREATE UNIQUE INDEX idx_books_isbn_unique ON books(isbn) WHERE isbn IS NOT NULL;

-- Tạo trigger để tự động cập nhật updated_at
CREATE OR REPLACE FUNCTION update_books_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_books_updated_at
    BEFORE UPDATE ON books
    FOR EACH ROW
    EXECUTE FUNCTION update_books_updated_at();

-- Thêm comments
COMMENT ON TABLE books IS 'Bảng lưu trữ thông tin sách trong hệ thống';
COMMENT ON COLUMN books.id IS 'ID chính của sách';
COMMENT ON COLUMN books.title IS 'Tiêu đề sách';
COMMENT ON COLUMN books.author IS 'Tác giả';
COMMENT ON COLUMN books.publisher IS 'Nhà xuất bản';
COMMENT ON COLUMN books.isbn IS 'Mã ISBN (nếu có)';
COMMENT ON COLUMN books.publication_year IS 'Năm xuất bản';
COMMENT ON COLUMN books.supported_format_id IS 'ID định dạng file được hỗ trợ';
COMMENT ON COLUMN books.min_age IS 'Độ tuổi tối thiểu (tháng)';
COMMENT ON COLUMN books.max_age IS 'Độ tuổi tối đa (tháng)';
COMMENT ON COLUMN books.age_group IS 'Nhóm tuổi: INFANT, TODDLER, PRESCHOOL, SCHOOL_AGE';
COMMENT ON COLUMN books.description IS 'Mô tả nội dung';
COMMENT ON COLUMN books.summary IS 'Tóm tắt';
COMMENT ON COLUMN books.language IS 'Ngôn ngữ (VI, EN, v.v.)';
COMMENT ON COLUMN books.file_size IS 'Kích thước file (bytes)';
COMMENT ON COLUMN books.page_count IS 'Số trang (nếu là sách)';
COMMENT ON COLUMN books.average_rating IS 'Điểm đánh giá trung bình (1-5)';
COMMENT ON COLUMN books.total_ratings IS 'Tổng số lượt đánh giá';
COMMENT ON COLUMN books.total_views IS 'Tổng lượt xem';
COMMENT ON COLUMN books.is_active IS 'Có đang hoạt động không';
COMMENT ON COLUMN books.is_featured IS 'Có phải sách nổi bật không';
COMMENT ON COLUMN books.cover_image_url IS 'Ảnh bìa';
COMMENT ON COLUMN books.preview_url IS 'Link xem trước';
COMMENT ON COLUMN books.keywords IS 'Từ khóa tìm kiếm (phân cách bằng dấu phẩy)';
COMMENT ON COLUMN books.tags IS 'Tags (JSON array)';
COMMENT ON COLUMN books.metadata IS 'Metadata bổ sung (JSON)';

COMMENT ON TABLE book_developmental_domains IS 'Bảng trung gian cho quan hệ many-to-many giữa books và developmental_domains';

-- Thêm dữ liệu mẫu
INSERT INTO books (
    title, author, publisher, isbn, publication_year, supported_format_id,
    min_age, max_age, age_group, description, summary, language,
    file_size, page_count, average_rating, total_ratings, total_views,
    is_active, is_featured, cover_image_url, preview_url, keywords, tags
) VALUES 
(
    'Truyện Cổ Tích Việt Nam', 'Nhiều tác giả', 'NXB Văn Học', '978-604-321-123-4', 2020,
    1, -- PDF format
    24, 72, 'TODDLER', 
    'Tuyển tập những câu chuyện cổ tích dân gian Việt Nam dành cho trẻ em',
    'Sách gồm 20 câu chuyện cổ tích quen thuộc như Tấm Cám, Thạch Sanh, Cây Khế...',
    'VI',
    2048576, 120, 4.5, 25, 150,
    true, true,
    'https://example.com/covers/truyen-co-tich.jpg',
    'https://example.com/preview/truyen-co-tich.pdf',
    'truyện cổ tích, dân gian, Việt Nam, trẻ em',
    '["truyện cổ tích", "dân gian", "văn hóa Việt"]'
),
(
    'Learning Colors', 'Sarah Johnson', 'Kids Publishing', '978-123-456-789-0', 2021,
    1, -- PDF format
    12, 36, 'INFANT',
    'A colorful book to help toddlers learn basic colors',
    'Simple and engaging book with bright illustrations to teach colors',
    'EN',
    1536000, 24, 4.2, 18, 89,
    true, false,
    'https://example.com/covers/learning-colors.jpg',
    'https://example.com/preview/learning-colors.pdf',
    'colors, learning, toddler, education',
    '["colors", "learning", "toddler"]'
),
(
    'Toán Học Vui Nhộn', 'Nguyễn Văn Toán', 'NXB Giáo Dục', '978-604-321-456-7', 2022,
    1, -- PDF format
    48, 84, 'PRESCHOOL',
    'Sách toán học dành cho trẻ mầm non với các bài tập vui nhộn',
    'Giúp trẻ làm quen với các khái niệm toán học cơ bản thông qua trò chơi',
    'VI',
    3072000, 64, 4.8, 32, 210,
    true, true,
    'https://example.com/covers/toan-hoc-vui-nhon.jpg',
    'https://example.com/preview/toan-hoc-vui-nhon.pdf',
    'toán học, mầm non, giáo dục, vui nhộn',
    '["toán học", "mầm non", "giáo dục"]'
);

-- Thêm dữ liệu cho bảng trung gian (giả sử đã có developmental_domains)
-- INSERT INTO book_developmental_domains (book_id, developmental_domain_id) VALUES 
-- (1, 'uuid-1'), -- Truyện cổ tích -> Ngôn ngữ
-- (1, 'uuid-2'), -- Truyện cổ tích -> Văn hóa
-- (2, 'uuid-3'), -- Learning Colors -> Nhận thức
-- (3, 'uuid-4'); -- Toán học -> Toán học
