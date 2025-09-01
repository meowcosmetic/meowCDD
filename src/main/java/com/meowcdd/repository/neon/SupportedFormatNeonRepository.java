package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.SupportedFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho entity SupportedFormat
 * Cung cấp các method để truy vấn và thao tác với bảng supported_formats
 */
@Repository
public interface SupportedFormatNeonRepository extends JpaRepository<SupportedFormat, Long> {
    
    /**
     * Tìm định dạng theo tên (exact match)
     * @param formatName tên định dạng cần tìm
     * @return Optional chứa SupportedFormat nếu tìm thấy
     */
    Optional<SupportedFormat> findByFormatName(String formatName);
    
    /**
     * Tìm định dạng theo phần mở rộng file
     * @param fileExtension phần mở rộng file (VD: .pdf, .jpg)
     * @return Optional chứa SupportedFormat nếu tìm thấy
     */
    Optional<SupportedFormat> findByFileExtension(String fileExtension);
    
    /**
     * Tìm định dạng theo MIME type
     * @param mimeType MIME type cần tìm
     * @return Optional chứa SupportedFormat nếu tìm thấy
     */
    Optional<SupportedFormat> findByMimeType(String mimeType);
    
    /**
     * Lấy danh sách tất cả định dạng đang hoạt động
     * @return List các định dạng có isActive = true
     */
    List<SupportedFormat> findByIsActiveTrue();
    
    /**
     * Lấy danh sách định dạng theo category
     * @param category loại định dạng cần lọc
     * @return List các định dạng thuộc category đó
     */
    List<SupportedFormat> findByCategory(SupportedFormat.FormatCategory category);
    
    /**
     * Lấy danh sách định dạng đang hoạt động theo category
     * @param category loại định dạng cần lọc
     * @return List các định dạng đang hoạt động thuộc category đó
     */
    List<SupportedFormat> findByCategoryAndIsActiveTrue(SupportedFormat.FormatCategory category);
    
    /**
     * Kiểm tra xem có tồn tại định dạng với tên này không
     * @param formatName tên định dạng cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByFormatName(String formatName);
    
    /**
     * Kiểm tra xem có tồn tại định dạng với phần mở rộng này không
     * @param fileExtension phần mở rộng cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByFileExtension(String fileExtension);
    
    /**
     * Tìm kiếm định dạng theo tên (contains, không phân biệt hoa thường)
     * @param formatName tên định dạng cần tìm (có thể là một phần)
     * @return List các định dạng có tên chứa từ khóa
     */
    @Query("SELECT sf FROM SupportedFormat sf WHERE LOWER(sf.formatName) LIKE LOWER(CONCAT('%', :formatName, '%'))")
    List<SupportedFormat> findByFormatNameContainingIgnoreCase(@Param("formatName") String formatName);
    
    /**
     * Lấy danh sách định dạng được sắp xếp theo độ ưu tiên xử lý (giảm dần)
     * @return List các định dạng đang hoạt động, sắp xếp theo processingPriority
     */
    @Query("SELECT sf FROM SupportedFormat sf WHERE sf.isActive = true ORDER BY sf.processingPriority DESC")
    List<SupportedFormat> findActiveFormatsOrderByPriority();
    
    /**
     * Lấy danh sách định dạng theo category và sắp xếp theo độ ưu tiên
     * @param category loại định dạng
     * @return List các định dạng đang hoạt động thuộc category, sắp xếp theo priority
     */
    @Query("SELECT sf FROM SupportedFormat sf WHERE sf.category = :category AND sf.isActive = true ORDER BY sf.processingPriority DESC")
    List<SupportedFormat> findByCategoryOrderByPriority(@Param("category") SupportedFormat.FormatCategory category);
}
