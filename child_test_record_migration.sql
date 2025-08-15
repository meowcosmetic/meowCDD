-- Migration script for ChildTestRecord table
-- This script creates the child_test_records table with all necessary columns, indexes, and constraints

-- Create the child_test_records table
CREATE TABLE child_test_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    external_id VARCHAR(255) NOT NULL UNIQUE,
    child_id BIGINT NOT NULL,
    test_id BIGINT NOT NULL,
    test_type VARCHAR(50) NOT NULL,
    test_date DATETIME NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    status VARCHAR(50) NOT NULL DEFAULT 'IN_PROGRESS',
    total_score DOUBLE,
    max_score DOUBLE,
    percentage_score DOUBLE,
    result_level VARCHAR(50),
    interpretation TEXT,
    question_answers TEXT,
    correct_answers INT,
    total_questions INT,
    skipped_questions INT,
    notes TEXT,
    environment VARCHAR(100),
    assessor VARCHAR(255),
    parent_present BOOLEAN,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

-- Create indexes for better query performance
CREATE INDEX idx_child_test_record_external_id ON child_test_records(external_id);
CREATE INDEX idx_child_test_record_child_id ON child_test_records(child_id);
CREATE INDEX idx_child_test_record_test_id ON child_test_records(test_id);
CREATE INDEX idx_child_test_record_test_type ON child_test_records(test_type);
CREATE INDEX idx_child_test_record_test_date ON child_test_records(test_date);
CREATE INDEX idx_child_test_record_status ON child_test_records(status);
CREATE INDEX idx_child_test_record_result_level ON child_test_records(result_level);
CREATE INDEX idx_child_test_record_assessor ON child_test_records(assessor);
CREATE INDEX idx_child_test_record_environment ON child_test_records(environment);
CREATE INDEX idx_child_test_record_parent_present ON child_test_records(parent_present);

-- Create composite indexes for common query patterns
CREATE INDEX idx_child_test_record_child_test_type ON child_test_records(child_id, test_type);
CREATE INDEX idx_child_test_record_child_status ON child_test_records(child_id, status);
CREATE INDEX idx_child_test_record_child_date ON child_test_records(child_id, test_date);
CREATE INDEX idx_child_test_record_test_date_range ON child_test_records(test_date, status);

-- Add foreign key constraints (optional - uncomment if you want to enforce referential integrity)
-- ALTER TABLE child_test_records ADD CONSTRAINT fk_child_test_record_child_id 
--     FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE;

-- Add check constraints for data validation
ALTER TABLE child_test_records ADD CONSTRAINT chk_test_type 
    CHECK (test_type IN ('CDD_TEST', 'ASSESSMENT_TEST'));

ALTER TABLE child_test_records ADD CONSTRAINT chk_status 
    CHECK (status IN ('IN_PROGRESS', 'COMPLETED', 'ABANDONED', 'INVALID', 'REVIEWED'));

ALTER TABLE child_test_records ADD CONSTRAINT chk_result_level 
    CHECK (result_level IN ('EXCELLENT', 'GOOD', 'AVERAGE', 'BELOW_AVERAGE', 'POOR') OR result_level IS NULL);

ALTER TABLE child_test_records ADD CONSTRAINT chk_percentage_score 
    CHECK (percentage_score >= 0 AND percentage_score <= 100 OR percentage_score IS NULL);

ALTER TABLE child_test_records ADD CONSTRAINT chk_total_score 
    CHECK (total_score >= 0 OR total_score IS NULL);

ALTER TABLE child_test_records ADD CONSTRAINT chk_max_score 
    CHECK (max_score > 0 OR max_score IS NULL);

-- Insert sample data for testing
INSERT INTO child_test_records (
    external_id, child_id, test_id, test_type, test_date, start_time, end_time, 
    status, total_score, max_score, percentage_score, result_level, interpretation,
    correct_answers, total_questions, skipped_questions, environment, assessor, parent_present
) VALUES 
(
    'TEST_RECORD_001', 1, 5, 'CDD_TEST', '2024-01-15 10:30:00', '2024-01-15 10:30:00', '2024-01-15 11:15:00',
    'COMPLETED', 85.5, 100.0, 85.5, 'GOOD', 'Trẻ có khả năng phát triển tốt, cần tiếp tục theo dõi',
    17, 20, 0, 'CLINIC', 'Dr. Nguyen Van A', true
),
(
    'TEST_RECORD_002', 1, 3, 'ASSESSMENT_TEST', '2024-01-20 14:00:00', '2024-01-20 14:00:00', '2024-01-20 15:30:00',
    'COMPLETED', 92.0, 100.0, 92.0, 'EXCELLENT', 'Trẻ có khả năng phát triển xuất sắc',
    23, 25, 0, 'HOME', 'Dr. Tran Thi B', true
),
(
    'TEST_RECORD_003', 2, 5, 'CDD_TEST', '2024-01-18 09:00:00', '2024-01-18 09:00:00', '2024-01-18 09:45:00',
    'COMPLETED', 75.0, 100.0, 75.0, 'AVERAGE', 'Trẻ có khả năng phát triển trung bình, cần hỗ trợ thêm',
    15, 20, 0, 'SCHOOL', 'Dr. Le Van C', false
);

-- Verification queries
SELECT 'Table created successfully' as status;
SELECT COUNT(*) as total_records FROM child_test_records;
SELECT test_type, COUNT(*) as count FROM child_test_records GROUP BY test_type;
SELECT status, COUNT(*) as count FROM child_test_records GROUP BY status;
SELECT result_level, COUNT(*) as count FROM child_test_records GROUP BY result_level;
