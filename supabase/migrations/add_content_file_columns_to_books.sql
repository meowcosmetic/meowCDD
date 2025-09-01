-- Migration: Add content file columns to books table
-- Description: Add columns for storing book content files directly in database as binary data

-- Add content file-related columns to books table
ALTER TABLE books 
ADD COLUMN content_file BYTEA,
ADD COLUMN content_file_name VARCHAR(255),
ADD COLUMN content_file_type VARCHAR(50),
ADD COLUMN content_file_size BIGINT,
ADD COLUMN content_mime_type VARCHAR(100),
ADD COLUMN content_uploaded_at TIMESTAMP,
ADD COLUMN content_uploaded_by VARCHAR(100),
ADD COLUMN content_is_verified BOOLEAN DEFAULT FALSE,
ADD COLUMN content_verification_date TIMESTAMP;

-- Add comments for documentation
COMMENT ON COLUMN books.content_file IS 'File nội dung sách (PDF, EPUB, etc.) lưu dưới dạng binary data';
COMMENT ON COLUMN books.content_file_name IS 'Tên file gốc';
COMMENT ON COLUMN books.content_file_type IS 'Loại file: PDF, EPUB, DOCX, TXT, etc.';
COMMENT ON COLUMN books.content_file_size IS 'Kích thước file (bytes)';
COMMENT ON COLUMN books.content_mime_type IS 'MIME type của file (application/pdf, application/epub+zip, etc.)';
COMMENT ON COLUMN books.content_uploaded_at IS 'Thời gian upload file';
COMMENT ON COLUMN books.content_uploaded_by IS 'Người upload file';
COMMENT ON COLUMN books.content_is_verified IS 'File đã được verify chưa';
COMMENT ON COLUMN books.content_verification_date IS 'Ngày verify file';

-- Create index for content file search
CREATE INDEX idx_books_content_file_type ON books(content_file_type);
CREATE INDEX idx_books_content_uploaded_at ON books(content_uploaded_at);
CREATE INDEX idx_books_content_is_verified ON books(content_is_verified);
CREATE INDEX idx_books_content_uploaded_by ON books(content_uploaded_by);
CREATE INDEX idx_books_content_file_size ON books(content_file_size);

-- Create function to update content_uploaded_at automatically
CREATE OR REPLACE FUNCTION update_book_content_upload_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    -- Update content_uploaded_at when content file is modified
    IF OLD.content_file IS DISTINCT FROM NEW.content_file THEN
        NEW.content_uploaded_at = CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger to automatically update content_uploaded_at
CREATE TRIGGER trigger_update_book_content_upload_timestamp
    BEFORE UPDATE ON books
    FOR EACH ROW
    EXECUTE FUNCTION update_book_content_upload_timestamp();

-- Create function to search books by content file name
CREATE OR REPLACE FUNCTION search_books_by_content_file_name(search_term TEXT)
RETURNS TABLE(
    id BIGINT,
    title VARCHAR,
    author VARCHAR,
    content_file_name VARCHAR,
    content_file_type VARCHAR,
    content_file_size BIGINT,
    content_uploaded_at TIMESTAMP,
    relevance_score REAL
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        b.id,
        b.title,
        b.author,
        b.content_file_name,
        b.content_file_type,
        b.content_file_size,
        b.content_uploaded_at,
        -- Simple relevance score based on text similarity
        GREATEST(
            SIMILARITY(b.title, search_term),
            SIMILARITY(b.author, search_term),
            SIMILARITY(b.content_file_name, search_term)
        ) as relevance_score
    FROM books b
    WHERE 
        b.is_active = true
        AND (
            b.title ILIKE '%' || search_term || '%'
            OR b.author ILIKE '%' || search_term || '%'
            OR b.content_file_name ILIKE '%' || search_term || '%'
        )
    ORDER BY relevance_score DESC;
END;
$$ LANGUAGE plpgsql;

-- Create function to get books by content file type
CREATE OR REPLACE FUNCTION get_books_by_content_file_type(file_type_filter VARCHAR)
RETURNS TABLE(
    id BIGINT,
    title VARCHAR,
    author VARCHAR,
    content_file_type VARCHAR,
    content_file_name VARCHAR,
    content_file_size BIGINT,
    content_uploaded_at TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        b.id,
        b.title,
        b.author,
        b.content_file_type,
        b.content_file_name,
        b.content_file_size,
        b.content_uploaded_at
    FROM books b
    WHERE 
        b.is_active = true
        AND b.content_file_type = file_type_filter
    ORDER BY b.content_uploaded_at DESC;
END;
$$ LANGUAGE plpgsql;

-- Create function to get books by uploader
CREATE OR REPLACE FUNCTION get_books_by_uploader(uploader_name VARCHAR)
RETURNS TABLE(
    id BIGINT,
    title VARCHAR,
    author VARCHAR,
    content_uploaded_by VARCHAR,
    content_file_type VARCHAR,
    content_uploaded_at TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        b.id,
        b.title,
        b.author,
        b.content_uploaded_by,
        b.content_file_type,
        b.content_uploaded_at
    FROM books b
    WHERE 
        b.is_active = true
        AND b.content_uploaded_by = uploader_name
    ORDER BY b.content_uploaded_at DESC;
END;
$$ LANGUAGE plpgsql;

-- Create function to get books with content files
CREATE OR REPLACE FUNCTION get_books_with_content_files()
RETURNS TABLE(
    id BIGINT,
    title VARCHAR,
    author VARCHAR,
    content_file_name VARCHAR,
    content_file_type VARCHAR,
    content_file_size BIGINT,
    content_uploaded_at TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        b.id,
        b.title,
        b.author,
        b.content_file_name,
        b.content_file_type,
        b.content_file_size,
        b.content_uploaded_at
    FROM books b
    WHERE 
        b.is_active = true
        AND b.content_file IS NOT NULL
        AND b.content_file_size > 0
    ORDER BY b.content_uploaded_at DESC;
END;
$$ LANGUAGE plpgsql;

-- Create function to get content file statistics
CREATE OR REPLACE FUNCTION get_content_file_statistics()
RETURNS TABLE(
    total_files BIGINT,
    total_size BIGINT,
    avg_file_size BIGINT,
    file_types JSON,
    uploaders JSON
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        COUNT(b.id) as total_files,
        COALESCE(SUM(b.content_file_size), 0) as total_size,
        COALESCE(AVG(b.content_file_size), 0) as avg_file_size,
        (SELECT json_object_agg(content_file_type, count) 
         FROM (SELECT content_file_type, COUNT(*) as count 
               FROM books 
               WHERE content_file IS NOT NULL 
               GROUP BY content_file_type) t) as file_types,
        (SELECT json_object_agg(content_uploaded_by, count) 
         FROM (SELECT content_uploaded_by, COUNT(*) as count 
               FROM books 
               WHERE content_file IS NOT NULL 
               GROUP BY content_uploaded_by) t) as uploaders
    FROM books b
    WHERE b.content_file IS NOT NULL;
END;
$$ LANGUAGE plpgsql;

-- Add constraints for content_file_type
ALTER TABLE books 
ADD CONSTRAINT chk_content_file_type 
CHECK (content_file_type IN ('PDF', 'EPUB', 'DOCX', 'TXT', 'MOBI', 'AZW3', 'RTF', 'ODT', 'HTML', 'XML'));

-- Add constraints for content_mime_type
ALTER TABLE books 
ADD CONSTRAINT chk_content_mime_type 
CHECK (content_mime_type IN (
    'application/pdf',
    'application/epub+zip',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    'text/plain',
    'application/x-mobipocket-ebook',
    'application/vnd.amazon.ebook',
    'application/rtf',
    'application/vnd.oasis.opendocument.text',
    'text/html',
    'application/xml'
));

-- Add constraint for file size (max 10MB)
ALTER TABLE books 
ADD CONSTRAINT chk_content_file_size 
CHECK (content_file_size IS NULL OR content_file_size <= 10485760);

-- Create view for books with content files
CREATE VIEW books_with_content_files AS
SELECT 
    id,
    title,
    author,
    content_file_name,
    content_file_type,
    content_file_size,
    content_mime_type,
    content_uploaded_at,
    content_uploaded_by,
    content_is_verified,
    content_verification_date
FROM books 
WHERE content_file IS NOT NULL 
AND content_file_size > 0;

-- Create view for content file statistics
CREATE VIEW content_file_statistics AS
SELECT 
    content_file_type,
    COUNT(*) as file_count,
    SUM(content_file_size) as total_size,
    AVG(content_file_size) as avg_size,
    MIN(content_file_size) as min_size,
    MAX(content_file_size) as max_size
FROM books 
WHERE content_file IS NOT NULL 
GROUP BY content_file_type;

-- Grant permissions
GRANT SELECT ON books_with_content_files TO PUBLIC;
GRANT SELECT ON content_file_statistics TO PUBLIC;
