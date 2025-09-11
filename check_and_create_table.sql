-- Check if books table exists and create it if needed
DO $$
BEGIN
    -- Check if books table exists
    IF NOT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'books') THEN
        -- Create books table
        CREATE TABLE books (
            id BIGSERIAL PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            author VARCHAR(255),
            publisher VARCHAR(255),
            isbn VARCHAR(50),
            publication_year INTEGER,
            min_age INTEGER,
            max_age INTEGER,
            age_group VARCHAR(50),
            description TEXT,
            summary TEXT,
            language VARCHAR(10),
            file_size BIGINT,
            page_count INTEGER,
            average_rating DOUBLE PRECISION,
            total_ratings INTEGER,
            total_views BIGINT,
            is_active BOOLEAN DEFAULT TRUE,
            is_featured BOOLEAN DEFAULT FALSE,
            keywords TEXT,
            tags TEXT,
            metadata TEXT,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            supported_format_id BIGINT,
            content_file BYTEA,
            content_file_name VARCHAR(255),
            content_file_type VARCHAR(50),
            content_file_size BIGINT,
            content_mime_type VARCHAR(100),
            content_uploaded_at TIMESTAMP,
            content_uploaded_by VARCHAR(100),
            content_is_verified BOOLEAN DEFAULT FALSE,
            content_verification_date TIMESTAMP
        );
        
        -- Create indexes
        CREATE INDEX idx_books_title ON books(title);
        CREATE INDEX idx_books_author ON books(author);
        CREATE INDEX idx_books_isbn ON books(isbn);
        CREATE INDEX idx_books_language ON books(language);
        CREATE INDEX idx_books_age_group ON books(age_group);
        CREATE INDEX idx_books_is_active ON books(is_active);
        CREATE INDEX idx_books_is_featured ON books(is_featured);
        CREATE INDEX idx_books_content_file_type ON books(content_file_type);
        CREATE INDEX idx_books_content_uploaded_at ON books(content_uploaded_at);
        CREATE INDEX idx_books_content_is_verified ON books(content_is_verified);
        CREATE INDEX idx_books_content_uploaded_by ON books(content_uploaded_by);
        CREATE INDEX idx_books_content_file_size ON books(content_file_size);
        
        RAISE NOTICE 'Books table created successfully';
    ELSE
        -- Check if content_file column exists
        IF NOT EXISTS (SELECT FROM information_schema.columns WHERE table_name = 'books' AND column_name = 'content_file') THEN
            -- Add content file columns
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
            
            -- Create indexes for new columns
            CREATE INDEX idx_books_content_file_type ON books(content_file_type);
            CREATE INDEX idx_books_content_uploaded_at ON books(content_uploaded_at);
            CREATE INDEX idx_books_content_is_verified ON books(content_is_verified);
            CREATE INDEX idx_books_content_uploaded_by ON books(content_uploaded_by);
            CREATE INDEX idx_books_content_file_size ON books(content_file_size);
            
            RAISE NOTICE 'Content file columns added to books table';
        ELSE
            RAISE NOTICE 'Books table and content file columns already exist';
        END IF;
    END IF;
END $$;

-- Create intervention method grouping tables if needed
DO $$
BEGIN
    -- Create intervention_method_groups table if not exists
    IF NOT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'intervention_method_groups') THEN
        CREATE TABLE intervention_method_groups (
            id BIGSERIAL PRIMARY KEY,
            code VARCHAR(100) NOT NULL UNIQUE,
            displayed_name JSONB NOT NULL,
            description JSONB,
            min_age_months INT,
            max_age_months INT,
            created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
            updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
            deleted_at TIMESTAMPTZ
        );

        CREATE INDEX idx_img_code ON intervention_method_groups(code);
        CREATE INDEX idx_img_displayed_name_gin ON intervention_method_groups USING GIN (displayed_name jsonb_path_ops);
        CREATE INDEX idx_img_created_at ON intervention_method_groups(created_at);
    END IF;

    -- Create intervention_method_group_members table if not exists
    IF NOT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'intervention_method_group_members') THEN
        CREATE TABLE intervention_method_group_members (
            id BIGSERIAL PRIMARY KEY,
            group_id BIGINT NOT NULL REFERENCES intervention_method_groups(id) ON DELETE CASCADE,
            method_id BIGINT NOT NULL REFERENCES intervention_methods(id) ON DELETE CASCADE,
            order_index INT,
            notes JSONB,
            created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
            updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
            CONSTRAINT uq_imgm_group_method UNIQUE (group_id, method_id)
        );

        CREATE INDEX idx_imgm_group_id ON intervention_method_group_members(group_id);
        CREATE INDEX idx_imgm_method_id ON intervention_method_group_members(method_id);
        CREATE INDEX idx_imgm_order_index ON intervention_method_group_members(order_index);
    END IF;
END $$;