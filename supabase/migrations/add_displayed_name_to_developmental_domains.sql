-- Migration: Thêm cột displayed_name vào bảng developmental_domains
-- Mục đích: Thêm tên hiển thị cho các lĩnh vực phát triển và hỗ trợ song ngữ cho description
-- Tác giả: System
-- Ngày tạo: 2024

-- Thêm cột displayed_name vào bảng developmental_domains
ALTER TABLE developmental_domains 
ADD COLUMN displayed_name TEXT NOT NULL DEFAULT '';

-- Cập nhật dữ liệu hiện có cho cột displayed_name
-- Sử dụng name hiện tại làm displayed_name cho các bản ghi cũ (dạng JSON)
UPDATE developmental_domains 
SET displayed_name = CASE 
    WHEN name = 'physical_development' THEN '{"vi": "Phát triển thể chất", "en": "Physical Development"}'
    WHEN name = 'cognitive_development' THEN '{"vi": "Phát triển nhận thức", "en": "Cognitive Development"}'
    WHEN name = 'language_development' THEN '{"vi": "Phát triển ngôn ngữ", "en": "Language Development"}'
    WHEN name = 'social_emotional_development' THEN '{"vi": "Phát triển xã hội-cảm xúc", "en": "Social-Emotional Development"}'
    WHEN name = 'life_skills' THEN '{"vi": "Kỹ năng sống", "en": "Life Skills"}'
    WHEN name = 'learning_skills' THEN '{"vi": "Kỹ năng học tập", "en": "Learning Skills"}'
    WHEN name = 'moral_development' THEN '{"vi": "Phát triển đạo đức", "en": "Moral Development"}'
    WHEN name = 'artistic_development' THEN '{"vi": "Phát triển nghệ thuật", "en": "Artistic Development"}'
    WHEN name = 'sports_development' THEN '{"vi": "Phát triển thể thao", "en": "Sports Development"}'
    WHEN name = 'technology_development' THEN '{"vi": "Phát triển công nghệ", "en": "Technology Development"}'
    WHEN name = 'comprehensive_development' THEN '{"vi": "Phát triển toàn diện", "en": "Comprehensive Development"}'
    WHEN name = '21st_century_skills' THEN '{"vi": "Kỹ năng thế kỷ 21", "en": "21st Century Skills"}'
    ELSE '{"vi": "' || name || '", "en": "' || name || '"}'
END
WHERE displayed_name = '';

-- Cập nhật lại name thành dạng kỹ thuật (snake_case) cho các bản ghi cũ
UPDATE developmental_domains 
SET name = CASE 
    WHEN name = 'Phát triển thể chất' THEN 'physical_development'
    WHEN name = 'Phát triển nhận thức' THEN 'cognitive_development'
    WHEN name = 'Phát triển ngôn ngữ' THEN 'language_development'
    WHEN name = 'Phát triển xã hội-cảm xúc' THEN 'social_emotional_development'
    WHEN name = 'Kỹ năng sống' THEN 'life_skills'
    WHEN name = 'Kỹ năng học tập' THEN 'learning_skills'
    WHEN name = 'Phát triển đạo đức' THEN 'moral_development'
    WHEN name = 'Phát triển nghệ thuật' THEN 'artistic_development'
    WHEN name = 'Phát triển thể thao' THEN 'sports_development'
    WHEN name = 'Phát triển công nghệ' THEN 'technology_development'
    WHEN name = 'Phát triển toàn diện' THEN 'comprehensive_development'
    WHEN name = 'Kỹ năng thế kỷ 21' THEN '21st_century_skills'
    ELSE name
END;

-- Cập nhật description thành dạng song ngữ JSON cho các bản ghi cũ
UPDATE developmental_domains 
SET description = CASE 
    WHEN name = 'physical_development' THEN '{"vi": "Đánh giá sự phát triển về vận động thô, vận động tinh, sức khỏe và thể lực của trẻ. Bao gồm khả năng di chuyển, phối hợp động tác, và các kỹ năng vận động cơ bản.", "en": "Evaluates the development of gross motor, fine motor, health and physical fitness of children. Includes movement ability, coordination, and basic motor skills."}'
    WHEN name = 'cognitive_development' THEN '{"vi": "Đánh giá khả năng tư duy, học tập, giải quyết vấn đề, trí nhớ và sự chú ý của trẻ. Bao gồm các kỹ năng toán học, logic và tư duy trừu tượng.", "en": "Evaluates thinking ability, learning, problem solving, memory and attention of children. Includes mathematical skills, logic and abstract thinking."}'
    WHEN name = 'language_development' THEN '{"vi": "Đánh giá khả năng giao tiếp, hiểu và sử dụng ngôn ngữ của trẻ. Bao gồm từ vựng, ngữ pháp, kỹ năng nghe-nói-đọc-viết.", "en": "Evaluates children''s communication ability, understanding and use of language. Includes vocabulary, grammar, listening-speaking-reading-writing skills."}'
    WHEN name = 'social_emotional_development' THEN '{"vi": "Đánh giá khả năng tương tác xã hội, quản lý cảm xúc, empathy và các kỹ năng quan hệ của trẻ. Bao gồm tự tin, hợp tác và kiểm soát bản thân.", "en": "Evaluates social interaction ability, emotion management, empathy and relationship skills of children. Includes confidence, cooperation and self-control."}'
    WHEN name = 'life_skills' THEN '{"vi": "Đánh giá khả năng tự chăm sóc bản thân, độc lập và thực hiện các hoạt động hàng ngày của trẻ. Bao gồm ăn uống, vệ sinh cá nhân, mặc quần áo.", "en": "Evaluates children''s self-care ability, independence and daily activities. Includes eating, personal hygiene, dressing."}'
    WHEN name = 'learning_skills' THEN '{"vi": "Đánh giá thói quen học tập, khả năng tập trung, tổ chức và quản lý thời gian của trẻ. Bao gồm động lực học tập và chiến lược học tập hiệu quả.", "en": "Evaluates study habits, concentration ability, organization and time management of children. Includes learning motivation and effective learning strategies."}'
    WHEN name = 'moral_development' THEN '{"vi": "Đánh giá sự hiểu biết về đúng-sai, giá trị đạo đức và khả năng đưa ra quyết định có đạo đức của trẻ. Bao gồm lòng trắc ẩn, trách nhiệm và công bằng.", "en": "Evaluates understanding of right-wrong, moral values and ability to make ethical decisions of children. Includes compassion, responsibility and fairness."}'
    WHEN name = 'artistic_development' THEN '{"vi": "Đánh giá khả năng sáng tạo, thẩm mỹ và biểu đạt nghệ thuật của trẻ. Bao gồm hội họa, âm nhạc, múa và các hình thức nghệ thuật khác.", "en": "Evaluates creativity, aesthetics and artistic expression of children. Includes painting, music, dance and other art forms."}'
    WHEN name = 'sports_development' THEN '{"vi": "Đánh giá tài năng và kỹ năng thể thao chuyên môn của trẻ. Bao gồm các môn thể thao cụ thể và khả năng thi đấu.", "en": "Evaluates children''s sports talent and professional skills. Includes specific sports and competition ability."}'
    WHEN name = 'technology_development' THEN '{"vi": "Đánh giá khả năng sử dụng và hiểu biết về công nghệ của trẻ. Bao gồm kỹ năng máy tính, lập trình và công nghệ số.", "en": "Evaluates children''s ability to use and understand technology. Includes computer skills, programming and digital technology."}'
    WHEN name = 'comprehensive_development' THEN '{"vi": "Đánh giá tổng thể sự phát triển của trẻ qua tất cả các lĩnh vực. Tích hợp các khía cạnh thể chất, nhận thức, ngôn ngữ và xã hội-cảm xúc.", "en": "Evaluates overall development of children across all areas. Integrates physical, cognitive, language and social-emotional aspects."}'
    WHEN name = '21st_century_skills' THEN '{"vi": "Đánh giá các kỹ năng cần thiết cho thế kỷ 21 như tư duy phản biện, sáng tạo, hợp tác và giao tiếp. Tích hợp nhiều lĩnh vực phát triển.", "en": "Evaluates essential skills for the 21st century such as critical thinking, creativity, collaboration and communication. Integrates multiple developmental areas."}'
    ELSE description
END;

-- Tạo index cho cột displayed_name (sử dụng GIN index cho JSON)
CREATE INDEX IF NOT EXISTS idx_developmental_domains_displayed_name ON developmental_domains USING GIN ((displayed_name::jsonb));

-- Thêm comment cho cột mới
COMMENT ON COLUMN developmental_domains.displayed_name IS 'Tên hiển thị của lĩnh vực phát triển (đa ngôn ngữ JSON hoặc text thường)';

-- Cập nhật comment cho cột description
COMMENT ON COLUMN developmental_domains.description IS 'Mô tả chi tiết về lĩnh vực phát triển (song ngữ JSON hoặc text thường)';

-- Cập nhật comment cho cột name
COMMENT ON COLUMN developmental_domains.name IS 'Tên kỹ thuật của lĩnh vực phát triển (VD: "physical_development", "cognitive_development")';

-- Tạo function để lấy description theo ngôn ngữ
CREATE OR REPLACE FUNCTION get_domain_description_in_language(domain_id UUID, language_code VARCHAR(2))
RETURNS TEXT AS $$
DECLARE
    domain_description TEXT;
    description_json JSONB;
BEGIN
    -- Lấy description từ bảng
    SELECT description INTO domain_description
    FROM developmental_domains
    WHERE id = domain_id;
    
    -- Nếu description là JSON, parse và trả về ngôn ngữ cụ thể
    IF domain_description IS NOT NULL AND domain_description::TEXT LIKE '{%' THEN
        BEGIN
            description_json := domain_description::JSONB;
            RETURN description_json->>language_code;
        EXCEPTION
            WHEN OTHERS THEN
                -- Nếu không parse được JSON, trả về description gốc
                RETURN domain_description;
        END;
    ELSE
        -- Nếu không phải JSON, trả về description gốc
        RETURN domain_description;
    END IF;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_domain_description_in_language(UUID, VARCHAR) IS 'Function lấy description của domain theo ngôn ngữ cụ thể (vi/en)';

-- Tạo function để lấy displayed_name theo ngôn ngữ
CREATE OR REPLACE FUNCTION get_domain_displayed_name_in_language(domain_id UUID, language_code VARCHAR(2))
RETURNS TEXT AS $$
DECLARE
    domain_displayed_name TEXT;
    displayed_name_json JSONB;
BEGIN
    -- Lấy displayed_name từ bảng
    SELECT displayed_name INTO domain_displayed_name
    FROM developmental_domains
    WHERE id = domain_id;
    
    -- Nếu displayed_name là JSON, parse và trả về ngôn ngữ cụ thể
    IF domain_displayed_name IS NOT NULL AND domain_displayed_name::TEXT LIKE '{%' THEN
        BEGIN
            displayed_name_json := domain_displayed_name::JSONB;
            RETURN displayed_name_json->>language_code;
        EXCEPTION
            WHEN OTHERS THEN
                -- Nếu không parse được JSON, trả về displayed_name gốc
                RETURN domain_displayed_name;
        END;
    ELSE
        -- Nếu không phải JSON, trả về displayed_name gốc
        RETURN domain_displayed_name;
    END IF;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_domain_displayed_name_in_language(UUID, VARCHAR) IS 'Function lấy displayed_name của domain theo ngôn ngữ cụ thể (vi/en)';

-- Tạo function để tìm kiếm domain theo displayed_name (hỗ trợ JSON)
CREATE OR REPLACE FUNCTION search_domains_by_displayed_name(search_keyword VARCHAR)
RETURNS TABLE(
    id UUID,
    name VARCHAR,
    displayed_name TEXT,
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
        dd.displayed_name,
        dd.description,
        dd.category,
        dd.created_at,
        dd.updated_at
    FROM developmental_domains dd
    WHERE 
        -- Tìm kiếm trong JSON displayed_name
        (dd.displayed_name::TEXT LIKE '{%' AND 
         (dd.displayed_name::jsonb->>'vi' LIKE '%' || search_keyword || '%' OR
          dd.displayed_name::jsonb->>'en' LIKE '%' || search_keyword || '%'))
        OR
        -- Tìm kiếm trong text thường
        (dd.displayed_name::TEXT NOT LIKE '{%' AND 
         LOWER(dd.displayed_name) LIKE LOWER('%' || search_keyword || '%'))
    ORDER BY 
        CASE 
            WHEN dd.displayed_name::TEXT LIKE '{%' THEN dd.displayed_name::jsonb->>'vi'
            ELSE dd.displayed_name
        END;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION search_domains_by_displayed_name(VARCHAR) IS 'Function tìm kiếm lĩnh vực phát triển theo displayed_name (hỗ trợ JSON)';

-- Cập nhật các view hiện có để bao gồm displayed_name (sắp xếp theo tiếng Việt)
DROP VIEW IF EXISTS core_developmental_domains;
CREATE OR REPLACE VIEW core_developmental_domains AS
SELECT * FROM developmental_domains 
WHERE category = 'CORE' 
ORDER BY 
    CASE 
        WHEN displayed_name::TEXT LIKE '{%' THEN displayed_name::jsonb->>'vi'
        ELSE displayed_name
    END;

DROP VIEW IF EXISTS secondary_developmental_domains;
CREATE OR REPLACE VIEW secondary_developmental_domains AS
SELECT * FROM developmental_domains 
WHERE category = 'SECONDARY' 
ORDER BY 
    CASE 
        WHEN displayed_name::TEXT LIKE '{%' THEN displayed_name::jsonb->>'vi'
        ELSE displayed_name
    END;

DROP VIEW IF EXISTS specialized_developmental_domains;
CREATE OR REPLACE VIEW specialized_developmental_domains AS
SELECT * FROM developmental_domains 
WHERE category = 'SPECIALIZED' 
ORDER BY 
    CASE 
        WHEN displayed_name::TEXT LIKE '{%' THEN displayed_name::jsonb->>'vi'
        ELSE displayed_name
    END;

DROP VIEW IF EXISTS integrated_developmental_domains;
CREATE OR REPLACE VIEW integrated_developmental_domains AS
SELECT * FROM developmental_domains 
WHERE category = 'INTEGRATED' 
ORDER BY 
    CASE 
        WHEN displayed_name::TEXT LIKE '{%' THEN displayed_name::jsonb->>'vi'
        ELSE displayed_name
    END;
