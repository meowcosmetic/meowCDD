-- Run this script in your PostgreSQL database to add content file columns

-- Add content file-related columns to books table
ALTER TABLE books 
ADD COLUMN IF NOT EXISTS content_file BYTEA,
ADD COLUMN IF NOT EXISTS content_file_name VARCHAR(255),
ADD COLUMN IF NOT EXISTS content_file_type VARCHAR(50),
ADD COLUMN IF NOT EXISTS content_file_size BIGINT,
ADD COLUMN IF NOT EXISTS content_mime_type VARCHAR(100),
ADD COLUMN IF NOT EXISTS content_uploaded_at TIMESTAMP,
ADD COLUMN IF NOT EXISTS content_uploaded_by VARCHAR(100),
ADD COLUMN IF NOT EXISTS content_is_verified BOOLEAN DEFAULT FALSE,
ADD COLUMN IF NOT EXISTS content_verification_date TIMESTAMP;

-- Create indexes if they don't exist
CREATE INDEX IF NOT EXISTS idx_books_content_file_type ON books(content_file_type);
CREATE INDEX IF NOT EXISTS idx_books_content_uploaded_at ON books(content_uploaded_at);
CREATE INDEX IF NOT EXISTS idx_books_content_is_verified ON books(content_is_verified);
CREATE INDEX IF NOT EXISTS idx_books_content_uploaded_by ON books(content_uploaded_by);
CREATE INDEX IF NOT EXISTS idx_books_content_file_size ON books(content_file_size);

-- Add constraints if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'chk_content_file_type') THEN
        ALTER TABLE books ADD CONSTRAINT chk_content_file_type 
        CHECK (content_file_type IN ('PDF', 'EPUB', 'DOCX', 'TXT', 'MOBI', 'AZW3', 'RTF', 'ODT', 'HTML', 'XML'));
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'chk_content_file_size') THEN
        ALTER TABLE books ADD CONSTRAINT chk_content_file_size 
        CHECK (content_file_size IS NULL OR content_file_size <= 10485760);
    END IF;
END $$;
