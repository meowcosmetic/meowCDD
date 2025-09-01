-- Migration: Tạo bảng supported_formats
-- Mục đích: Quản lý các định dạng file được hỗ trợ trong hệ thống
-- Tác giả: System
-- Ngày tạo: 2024

-- Tạo bảng supported_formats
CREATE TABLE IF NOT EXISTS supported_formats (
    id BIGSERIAL PRIMARY KEY,
    
    -- Tên định dạng file (VD: "PDF Document", "JPEG Image", "MP4 Video")
    -- Dùng để hiển thị cho người dùng, phải unique
    format_name VARCHAR(255) NOT NULL UNIQUE,
    
    -- Phần mở rộng file (VD: .pdf, .jpg, .mp4)
    -- Dùng để validate file upload và mapping
    file_extension VARCHAR(50) NOT NULL,
    
    -- MIME type chuẩn (VD: application/pdf, image/jpeg, video/mp4)
    -- Dùng để xác định loại file khi upload
    mime_type VARCHAR(100),
    
    -- Phân loại định dạng theo nhóm chức năng
    -- Giúp tổ chức và filter các định dạng theo category
    category VARCHAR(50) NOT NULL CHECK (category IN ('DOCUMENT', 'IMAGE', 'AUDIO', 'VIDEO', 'ARCHIVE', 'DATA', 'CODE', 'OTHER')),
    
    -- Trạng thái hoạt động của định dạng
    -- false = không còn hỗ trợ, true = đang hỗ trợ
    is_active BOOLEAN NOT NULL DEFAULT true,
    
    -- Kích thước file tối đa được phép upload (tính bằng bytes)
    -- null = không giới hạn kích thước
    max_file_size BIGINT,
    
    -- Mô tả chi tiết về định dạng file
    -- Thông tin về cách sử dụng, ưu nhược điểm
    description TEXT,
    
    -- Thông tin về phiên bản được hỗ trợ
    -- VD: "PDF 1.4+", "MP4 H.264", "JPEG Baseline"
    version_info VARCHAR(255),
    
    -- Độ ưu tiên khi xử lý file (1-10)
    -- Số càng cao càng ưu tiên xử lý trước
    processing_priority INTEGER CHECK (processing_priority >= 1 AND processing_priority <= 10),
    
    -- URL icon đại diện cho định dạng
    -- Dùng để hiển thị trong UI
    icon_url VARCHAR(500),
    
    -- Timestamps từ BaseEntity
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tạo index cho các trường thường được query
CREATE INDEX IF NOT EXISTS idx_supported_formats_format_name ON supported_formats(format_name);
CREATE INDEX IF NOT EXISTS idx_supported_formats_file_extension ON supported_formats(file_extension);
CREATE INDEX IF NOT EXISTS idx_supported_formats_mime_type ON supported_formats(mime_type);
CREATE INDEX IF NOT EXISTS idx_supported_formats_category ON supported_formats(category);
CREATE INDEX IF NOT EXISTS idx_supported_formats_is_active ON supported_formats(is_active);
CREATE INDEX IF NOT EXISTS idx_supported_formats_processing_priority ON supported_formats(processing_priority);

-- Tạo unique constraint cho file_extension để tránh trùng lặp
CREATE UNIQUE INDEX IF NOT EXISTS idx_supported_formats_file_extension_unique ON supported_formats(file_extension);

-- Tạo trigger để tự động cập nhật updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_supported_formats_updated_at 
    BEFORE UPDATE ON supported_formats 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();

-- Thêm comment cho bảng và các cột
COMMENT ON TABLE supported_formats IS 'Bảng quản lý các định dạng file được hỗ trợ trong hệ thống';
COMMENT ON COLUMN supported_formats.id IS 'ID duy nhất của định dạng';
COMMENT ON COLUMN supported_formats.format_name IS 'Tên định dạng file (VD: "PDF Document", "JPEG Image")';
COMMENT ON COLUMN supported_formats.file_extension IS 'Phần mở rộng file (VD: .pdf, .jpg, .mp4)';
COMMENT ON COLUMN supported_formats.mime_type IS 'MIME type chuẩn (VD: application/pdf, image/jpeg)';
COMMENT ON COLUMN supported_formats.category IS 'Phân loại định dạng (DOCUMENT, IMAGE, AUDIO, VIDEO, ARCHIVE, DATA, CODE, OTHER)';
COMMENT ON COLUMN supported_formats.is_active IS 'Trạng thái hoạt động (true = đang hỗ trợ, false = không hỗ trợ)';
COMMENT ON COLUMN supported_formats.max_file_size IS 'Kích thước file tối đa được phép upload (bytes)';
COMMENT ON COLUMN supported_formats.description IS 'Mô tả chi tiết về định dạng';
COMMENT ON COLUMN supported_formats.version_info IS 'Thông tin phiên bản được hỗ trợ';
COMMENT ON COLUMN supported_formats.processing_priority IS 'Độ ưu tiên xử lý (1-10)';
COMMENT ON COLUMN supported_formats.icon_url IS 'URL icon đại diện cho định dạng';
COMMENT ON COLUMN supported_formats.created_at IS 'Thời gian tạo';
COMMENT ON COLUMN supported_formats.updated_at IS 'Thời gian cập nhật cuối';

-- Insert dữ liệu mẫu cho các định dạng phổ biến
INSERT INTO supported_formats (format_name, file_extension, mime_type, category, is_active, max_file_size, description, version_info, processing_priority, icon_url) VALUES
-- Document formats
('PDF Document', '.pdf', 'application/pdf', 'DOCUMENT', true, 10485760, 'Portable Document Format - định dạng tài liệu phổ biến', 'PDF 1.4+', 10, '/icons/pdf.png'),
('Microsoft Word', '.docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'DOCUMENT', true, 10485760, 'Microsoft Word Document - tài liệu văn bản', 'Word 2007+', 9, '/icons/docx.png'),
('Plain Text', '.txt', 'text/plain', 'DOCUMENT', true, 5242880, 'Plain text file - file văn bản thuần', 'UTF-8', 8, '/icons/txt.png'),
('Rich Text Format', '.rtf', 'application/rtf', 'DOCUMENT', true, 5242880, 'Rich Text Format - định dạng văn bản có định dạng', 'RTF 1.0+', 7, '/icons/rtf.png'),

-- Image formats
('JPEG Image', '.jpg', 'image/jpeg', 'IMAGE', true, 10485760, 'JPEG image - định dạng ảnh nén phổ biến', 'JPEG Baseline', 10, '/icons/jpg.png'),
('PNG Image', '.png', 'image/png', 'IMAGE', true, 10485760, 'PNG image - định dạng ảnh không mất dữ liệu', 'PNG 1.0+', 9, '/icons/png.png'),
('GIF Image', '.gif', 'image/gif', 'IMAGE', true, 5242880, 'GIF image - định dạng ảnh động', 'GIF 89a', 8, '/icons/gif.png'),
('Bitmap Image', '.bmp', 'image/bmp', 'IMAGE', true, 10485760, 'Bitmap image - định dạng ảnh không nén', 'BMP', 6, '/icons/bmp.png'),
('SVG Image', '.svg', 'image/svg+xml', 'IMAGE', true, 5242880, 'Scalable Vector Graphics - ảnh vector', 'SVG 1.1+', 7, '/icons/svg.png'),

-- Audio formats
('MP3 Audio', '.mp3', 'audio/mpeg', 'AUDIO', true, 52428800, 'MP3 audio - định dạng âm thanh nén phổ biến', 'MPEG-1 Layer 3', 10, '/icons/mp3.png'),
('WAV Audio', '.wav', 'audio/wav', 'AUDIO', true, 104857600, 'WAV audio - định dạng âm thanh không nén', 'PCM', 9, '/icons/wav.png'),
('AAC Audio', '.aac', 'audio/aac', 'AUDIO', true, 52428800, 'AAC audio - định dạng âm thanh nén tiên tiến', 'AAC-LC', 8, '/icons/aac.png'),
('OGG Audio', '.ogg', 'audio/ogg', 'AUDIO', true, 52428800, 'OGG audio - định dạng âm thanh mã nguồn mở', 'OGG Vorbis', 7, '/icons/ogg.png'),

-- Video formats
('MP4 Video', '.mp4', 'video/mp4', 'VIDEO', true, 104857600, 'MP4 video - định dạng video phổ biến', 'H.264/AVC', 10, '/icons/mp4.png'),
('AVI Video', '.avi', 'video/x-msvideo', 'VIDEO', true, 104857600, 'AVI video - định dạng video cũ', 'AVI', 6, '/icons/avi.png'),
('MOV Video', '.mov', 'video/quicktime', 'VIDEO', true, 104857600, 'MOV video - định dạng video của Apple', 'QuickTime', 8, '/icons/mov.png'),
('WMV Video', '.wmv', 'video/x-ms-wmv', 'VIDEO', true, 104857600, 'WMV video - định dạng video của Microsoft', 'Windows Media', 7, '/icons/wmv.png'),

-- Archive formats
('ZIP Archive', '.zip', 'application/zip', 'ARCHIVE', true, 104857600, 'ZIP archive - định dạng nén phổ biến', 'ZIP', 10, '/icons/zip.png'),
('RAR Archive', '.rar', 'application/vnd.rar', 'ARCHIVE', true, 104857600, 'RAR archive - định dạng nén hiệu quả', 'RAR 5.0+', 9, '/icons/rar.png'),
('7-Zip Archive', '.7z', 'application/x-7z-compressed', 'ARCHIVE', true, 104857600, '7-Zip archive - định dạng nén mã nguồn mở', '7z', 8, '/icons/7z.png'),

-- Data formats
('JSON Data', '.json', 'application/json', 'DATA', true, 5242880, 'JSON data - định dạng dữ liệu cấu trúc', 'JSON', 10, '/icons/json.png'),
('XML Data', '.xml', 'application/xml', 'DATA', true, 5242880, 'XML data - định dạng dữ liệu có cấu trúc', 'XML 1.0+', 9, '/icons/xml.png'),
('CSV Data', '.csv', 'text/csv', 'DATA', true, 5242880, 'CSV data - định dạng dữ liệu bảng', 'UTF-8', 8, '/icons/csv.png'),
('Excel Spreadsheet', '.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'DATA', true, 10485760, 'Excel spreadsheet - bảng tính', 'Excel 2007+', 9, '/icons/xlsx.png'),

-- Code formats
('Java Source', '.java', 'text/x-java-source', 'CODE', true, 1048576, 'Java source code file', 'Java 8+', 8, '/icons/java.png'),
('JavaScript', '.js', 'application/javascript', 'CODE', true, 1048576, 'JavaScript source code file', 'ES6+', 9, '/icons/js.png'),
('HTML Document', '.html', 'text/html', 'CODE', true, 1048576, 'HTML document file', 'HTML5', 9, '/icons/html.png'),
('CSS Stylesheet', '.css', 'text/css', 'CODE', true, 1048576, 'CSS stylesheet file', 'CSS3', 8, '/icons/css.png'),
('Python Script', '.py', 'text/x-python', 'CODE', true, 1048576, 'Python source code file', 'Python 3.6+', 8, '/icons/py.png')

ON CONFLICT (format_name) DO NOTHING;

-- Tạo view để dễ dàng query các định dạng đang hoạt động
CREATE OR REPLACE VIEW active_supported_formats AS
SELECT * FROM supported_formats WHERE is_active = true;

COMMENT ON VIEW active_supported_formats IS 'View chứa tất cả định dạng đang hoạt động';

-- Tạo function để kiểm tra định dạng có được hỗ trợ không
CREATE OR REPLACE FUNCTION is_format_supported(file_ext VARCHAR)
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS (
        SELECT 1 FROM supported_formats 
        WHERE file_extension = file_ext AND is_active = true
    );
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION is_format_supported(VARCHAR) IS 'Function kiểm tra định dạng có được hỗ trợ không';
