-- Migration: Tạo bảng developmental_domains
-- Mục đích: Quản lý các lĩnh vực phát triển của trẻ em
-- Tác giả: System
-- Ngày tạo: 2024

-- Tạo extension uuid-ossp nếu chưa có (để generate UUID)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tạo bảng developmental_domains
CREATE TABLE IF NOT EXISTS developmental_domains (
    -- ID duy nhất của lĩnh vực phát triển (UUID)
    -- Sử dụng UUID để đảm bảo tính duy nhất toàn cục
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    
    -- Tên lĩnh vực phát triển
    -- VD: "Phát triển thể chất", "Phát triển nhận thức", "Phát triển ngôn ngữ"
    -- Không được null và phải unique
    name VARCHAR(255) NOT NULL UNIQUE,
    
    -- Mô tả chi tiết về lĩnh vực phát triển
    -- Giải thích về các khía cạnh, kỹ năng được đánh giá trong lĩnh vực này
    description TEXT,
    
    -- Phân loại lĩnh vực phát triển (dạng String để linh hoạt thêm mới)
    -- Dùng để nhóm các lĩnh vực có tính chất tương tự
    -- VD: "CORE", "SECONDARY", "SPECIALIZED", "INTEGRATED", v.v.
    -- Có thể thêm các category mới mà không cần thay đổi database schema
    category VARCHAR(100),
    
    -- Timestamps
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tạo index cho các trường thường được query
CREATE INDEX IF NOT EXISTS idx_developmental_domains_name ON developmental_domains(name);
CREATE INDEX IF NOT EXISTS idx_developmental_domains_category ON developmental_domains(category);
CREATE INDEX IF NOT EXISTS idx_developmental_domains_created_at ON developmental_domains(created_at);

-- Tạo trigger để tự động cập nhật updated_at
CREATE OR REPLACE FUNCTION update_developmental_domains_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_developmental_domains_updated_at 
    BEFORE UPDATE ON developmental_domains 
    FOR EACH ROW 
    EXECUTE FUNCTION update_developmental_domains_updated_at();

-- Thêm comment cho bảng và các cột
COMMENT ON TABLE developmental_domains IS 'Bảng quản lý các lĩnh vực phát triển của trẻ em';
COMMENT ON COLUMN developmental_domains.id IS 'ID duy nhất của lĩnh vực phát triển (UUID)';
COMMENT ON COLUMN developmental_domains.name IS 'Tên lĩnh vực phát triển (VD: "Phát triển thể chất", "Phát triển nhận thức")';
COMMENT ON COLUMN developmental_domains.description IS 'Mô tả chi tiết về lĩnh vực phát triển';
COMMENT ON COLUMN developmental_domains.category IS 'Phân loại lĩnh vực phát triển (String: CORE, SECONDARY, SPECIALIZED, INTEGRATED, v.v.)';
COMMENT ON COLUMN developmental_domains.created_at IS 'Thời gian tạo';
COMMENT ON COLUMN developmental_domains.updated_at IS 'Thời gian cập nhật cuối';

-- Insert dữ liệu mẫu cho các lĩnh vực phát triển chính
INSERT INTO developmental_domains (name, description, category) VALUES
-- Lĩnh vực phát triển cốt lõi
('Phát triển thể chất', 'Đánh giá sự phát triển về vận động thô, vận động tinh, sức khỏe và thể lực của trẻ. Bao gồm khả năng di chuyển, phối hợp động tác, và các kỹ năng vận động cơ bản.', 'CORE'),
('Phát triển nhận thức', 'Đánh giá khả năng tư duy, học tập, giải quyết vấn đề, trí nhớ và sự chú ý của trẻ. Bao gồm các kỹ năng toán học, logic và tư duy trừu tượng.', 'CORE'),
('Phát triển ngôn ngữ', 'Đánh giá khả năng giao tiếp, hiểu và sử dụng ngôn ngữ của trẻ. Bao gồm từ vựng, ngữ pháp, kỹ năng nghe-nói-đọc-viết.', 'CORE'),
('Phát triển xã hội-cảm xúc', 'Đánh giá khả năng tương tác xã hội, quản lý cảm xúc, empathy và các kỹ năng quan hệ của trẻ. Bao gồm tự tin, hợp tác và kiểm soát bản thân.', 'CORE'),

-- Lĩnh vực phát triển phụ
('Kỹ năng sống', 'Đánh giá khả năng tự chăm sóc bản thân, độc lập và thực hiện các hoạt động hàng ngày của trẻ. Bao gồm ăn uống, vệ sinh cá nhân, mặc quần áo.', 'SECONDARY'),
('Kỹ năng học tập', 'Đánh giá thói quen học tập, khả năng tập trung, tổ chức và quản lý thời gian của trẻ. Bao gồm động lực học tập và chiến lược học tập hiệu quả.', 'SECONDARY'),
('Phát triển đạo đức', 'Đánh giá sự hiểu biết về đúng-sai, giá trị đạo đức và khả năng đưa ra quyết định có đạo đức của trẻ. Bao gồm lòng trắc ẩn, trách nhiệm và công bằng.', 'SECONDARY'),

-- Lĩnh vực phát triển chuyên biệt
('Phát triển nghệ thuật', 'Đánh giá khả năng sáng tạo, thẩm mỹ và biểu đạt nghệ thuật của trẻ. Bao gồm hội họa, âm nhạc, múa và các hình thức nghệ thuật khác.', 'SPECIALIZED'),
('Phát triển thể thao', 'Đánh giá tài năng và kỹ năng thể thao chuyên môn của trẻ. Bao gồm các môn thể thao cụ thể và khả năng thi đấu.', 'SPECIALIZED'),
('Phát triển công nghệ', 'Đánh giá khả năng sử dụng và hiểu biết về công nghệ của trẻ. Bao gồm kỹ năng máy tính, lập trình và công nghệ số.', 'SPECIALIZED'),

-- Lĩnh vực phát triển tích hợp
('Phát triển toàn diện', 'Đánh giá tổng thể sự phát triển của trẻ qua tất cả các lĩnh vực. Tích hợp các khía cạnh thể chất, nhận thức, ngôn ngữ và xã hội-cảm xúc.', 'INTEGRATED'),
('Kỹ năng thế kỷ 21', 'Đánh giá các kỹ năng cần thiết cho thế kỷ 21 như tư duy phản biện, sáng tạo, hợp tác và giao tiếp. Tích hợp nhiều lĩnh vực phát triển.', 'INTEGRATED')

ON CONFLICT (name) DO NOTHING;

-- Tạo view để dễ dàng query các lĩnh vực theo category
CREATE OR REPLACE VIEW core_developmental_domains AS
SELECT * FROM developmental_domains WHERE category = 'CORE' ORDER BY name;

CREATE OR REPLACE VIEW secondary_developmental_domains AS
SELECT * FROM developmental_domains WHERE category = 'SECONDARY' ORDER BY name;

CREATE OR REPLACE VIEW specialized_developmental_domains AS
SELECT * FROM developmental_domains WHERE category = 'SPECIALIZED' ORDER BY name;

CREATE OR REPLACE VIEW integrated_developmental_domains AS
SELECT * FROM developmental_domains WHERE category = 'INTEGRATED' ORDER BY name;

-- Thêm comment cho các view
COMMENT ON VIEW core_developmental_domains IS 'View chứa các lĩnh vực phát triển cốt lõi';
COMMENT ON VIEW secondary_developmental_domains IS 'View chứa các lĩnh vực phát triển phụ';
COMMENT ON VIEW specialized_developmental_domains IS 'View chứa các lĩnh vực phát triển chuyên biệt';
COMMENT ON VIEW integrated_developmental_domains IS 'View chứa các lĩnh vực phát triển tích hợp';

-- Tạo function để lấy thống kê số lượng domain theo category
CREATE OR REPLACE FUNCTION get_domain_count_by_category()
RETURNS TABLE(category VARCHAR, domain_count BIGINT) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        dd.category::VARCHAR,
        COUNT(*)::BIGINT
    FROM developmental_domains dd
    WHERE dd.category IS NOT NULL
    GROUP BY dd.category
    ORDER BY dd.category;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_domain_count_by_category() IS 'Function trả về thống kê số lượng lĩnh vực phát triển theo category';

-- Tạo function để tìm kiếm domain theo từ khóa
CREATE OR REPLACE FUNCTION search_domains_by_keyword(search_keyword VARCHAR)
RETURNS TABLE(
    id UUID,
    name VARCHAR,
    description TEXT,
    category VARCHAR,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        dd.id,
        dd.name,
        dd.description,
        dd.category,
        dd.created_at,
        dd.updated_at
    FROM developmental_domains dd
    WHERE 
        LOWER(dd.name) LIKE LOWER('%' || search_keyword || '%') OR
        LOWER(dd.description) LIKE LOWER('%' || search_keyword || '%')
    ORDER BY dd.name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION search_domains_by_keyword(VARCHAR) IS 'Function tìm kiếm lĩnh vực phát triển theo từ khóa trong tên hoặc mô tả';
