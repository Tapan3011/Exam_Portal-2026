package com.examportal.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    
    public static void createStudyMaterialsTable() {
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            
            // Create study_materials table
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS study_materials (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    original_name VARCHAR(255) NOT NULL,
                    file_name VARCHAR(255) NOT NULL UNIQUE,
                    file_size BIGINT NOT NULL,
                    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    created_by INT,
                    FOREIGN KEY (created_by) REFERENCES users(id)
                )
                """;
            
            statement.execute(createTableSQL);
            
            // Create indexes
            statement.execute("CREATE INDEX IF NOT EXISTS idx_study_materials_upload_date ON study_materials(upload_date)");
            statement.execute("CREATE INDEX IF NOT EXISTS idx_study_materials_created_by ON study_materials(created_by)");
            
            System.out.println("Study materials table created successfully!");
            
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            System.err.println("Error creating study_materials table: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
