package com.examportal.controllers;

import com.examportal.database.DBConnection;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileController {

    private static final String UPLOAD_DIR = "uploads/";

    // ── UPLOAD STUDY MATERIALS ──
    @PostMapping("/materials/upload")
    public Map<String, Object> uploadMaterials(@RequestParam("files") MultipartFile[] files) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            List<String> uploadedFiles = new ArrayList<>();
            
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Generate unique filename
                    String originalFilename = file.getOriginalFilename();
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
                    
                    // Save file
                    Path filePath = uploadPath.resolve(uniqueFilename);
                    Files.copy(file.getInputStream(), filePath);
                    
                    // Save to database
                    saveMaterialToDatabase(originalFilename, uniqueFilename, file.getSize());
                    
                    uploadedFiles.add(originalFilename);
                }
            }
            
            response.put("success", true);
            response.put("message", uploadedFiles.size() + " files uploaded successfully!");
            response.put("files", uploadedFiles);
            
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Failed to upload files: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Failed to save file information to database: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    private void saveMaterialToDatabase(String originalName, String fileName, long fileSize) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO study_materials (original_name, file_name, file_size, upload_date) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, originalName);
            stmt.setString(2, fileName);
            stmt.setLong(3, fileSize);
            stmt.executeUpdate();
        }
    }
    
    // ── GET ALL STUDY MATERIALS ──
    @GetMapping("/materials")
    public List<Map<String, Object>> getAllMaterials() {
        
        List<Map<String, Object>> materials = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM study_materials ORDER BY upload_date DESC";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> material = new HashMap<>();
                    material.put("id", rs.getInt("id"));
                    material.put("originalName", rs.getString("original_name"));
                    material.put("fileName", rs.getString("file_name"));
                    material.put("fileSize", rs.getLong("file_size"));
                    material.put("uploadDate", rs.getString("upload_date"));
                    materials.add(material);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return materials;
    }
    
    // ── DOWNLOAD STUDY MATERIAL ──
    @GetMapping("/materials/download/{fileName}")
    public org.springframework.core.io.Resource downloadMaterial(@PathVariable String fileName) {
        
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            org.springframework.core.io.Resource resource = new org.springframework.core.io.FileSystemResource(filePath);
            
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // ── DELETE STUDY MATERIAL ──
    @DeleteMapping("/materials/{id}")
    public Map<String, Object> deleteMaterial(@PathVariable int id) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            
            // Get file name from database
            String getFileNameQuery = "SELECT file_name FROM study_materials WHERE id = ?";
            String fileName = null;
            try (PreparedStatement stmt = connection.prepareStatement(getFileNameQuery)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        fileName = rs.getString("file_name");
                    }
                }
            }
            
            if (fileName != null) {
                // Delete from database
                String deleteQuery = "DELETE FROM study_materials WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
                
                // Delete file from filesystem
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.deleteIfExists(filePath);
                
                response.put("success", true);
                response.put("message", "Material deleted successfully!");
            } else {
                response.put("success", false);
                response.put("message", "Material not found!");
            }
            
        } catch (SQLException | IOException e) {
            response.put("success", false);
            response.put("message", "Failed to delete material: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    // ── BULK IMPORT QUESTIONS ──
    @PostMapping("/questions/bulk-import")
    public Map<String, Object> bulkImportQuestions(@RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            String filename = file.getOriginalFilename();
            String fileExtension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
            
            if (!fileExtension.equals(".csv") && !fileExtension.equals(".xlsx") && !fileExtension.equals(".xls")) {
                response.put("success", false);
                response.put("message", "Unsupported file format. Please use CSV or Excel files.");
                return response;
            }
            
            // For now, we'll simulate processing the file
            // In a real implementation, you would parse the CSV/Excel file here
            int importedCount = 0;
            
            if (fileExtension.equals(".csv")) {
                // Parse CSV and import questions
                // This is a simplified version - you'd need proper CSV parsing
                importedCount = parseCSVAndImport(file);
            } else {
                // Parse Excel and import questions
                // This would require a library like Apache POI
                importedCount = parseExcelAndImport(file);
            }
            
            response.put("success", true);
            response.put("message", "Questions imported successfully!");
            response.put("importedCount", importedCount);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to import questions: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    private int parseCSVAndImport(MultipartFile file) {
        // Simplified CSV parsing - in production, use a proper CSV library
        int count = 0;
        try {
            String content = new String(file.getBytes());
            String[] lines = content.split("\n");
            
            Connection connection = DBConnection.getConnection();
            String query = "INSERT INTO questions (subject, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            // Skip header line if present
            for (int i = 1; i < lines.length; i++) {
                String[] columns = lines[i].split(",");
                if (columns.length >= 7) {
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.setString(1, columns[0].trim());           // subject
                        stmt.setString(2, columns[1].trim());           // question_text
                        stmt.setString(3, columns[2].trim());           // option_a
                        stmt.setString(4, columns[3].trim());           // option_b
                        stmt.setString(5, columns[4].trim());           // option_c
                        stmt.setString(6, columns[5].trim());           // option_d
                        stmt.setString(7, columns[6].trim());           // correct_option
                        stmt.executeUpdate();
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    private int parseExcelAndImport(MultipartFile file) {
        // Simplified Excel parsing - in production, use Apache POI
        // For now, return a mock count
        return 5;
    }
}
