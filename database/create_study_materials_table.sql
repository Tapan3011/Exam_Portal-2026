-- Create study_materials table for storing uploaded files
CREATE TABLE IF NOT EXISTS study_materials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL UNIQUE,
    file_size BIGINT NOT NULL,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- Create index for faster queries
CREATE INDEX idx_study_materials_upload_date ON study_materials(upload_date);
CREATE INDEX idx_study_materials_created_by ON study_materials(created_by);
