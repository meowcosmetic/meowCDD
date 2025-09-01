package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.DevelopmentalDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository cho entity DevelopmentalDomain
 * Cung cấp các method để truy vấn và thao tác với bảng developmental_domains
 */
@Repository
public interface DevelopmentalDomainNeonRepository extends JpaRepository<DevelopmentalDomain, UUID> {
    
    /**
     * Tìm lĩnh vực phát triển theo tên (exact match)
     * @param name tên lĩnh vực cần tìm
     * @return Optional chứa DevelopmentalDomain nếu tìm thấy
     */
    Optional<DevelopmentalDomain> findByName(String name);
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo tên (contains, không phân biệt hoa thường)
     * @param name tên lĩnh vực cần tìm (có thể là một phần)
     * @return List các lĩnh vực có tên chứa từ khóa
     */
    @Query("SELECT dd FROM DevelopmentalDomain dd WHERE LOWER(dd.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<DevelopmentalDomain> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category
     * @param category loại lĩnh vực cần lọc (String)
     * @return List các lĩnh vực thuộc category đó
     */
    List<DevelopmentalDomain> findByCategory(String category);
    
    /**
     * Kiểm tra xem có tồn tại lĩnh vực với tên này không
     * @param name tên lĩnh vực cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByName(String name);
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo từ khóa trong tên hoặc mô tả
     * @param keyword từ khóa cần tìm
     * @return List các lĩnh vực có tên hoặc mô tả chứa từ khóa
     */
    @Query("SELECT dd FROM DevelopmentalDomain dd WHERE " +
           "LOWER(dd.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(dd.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DevelopmentalDomain> searchByKeyword(@Param("keyword") String keyword);
    
    /**
     * Lấy danh sách lĩnh vực phát triển được sắp xếp theo tên (A-Z)
     * @return List các lĩnh vực sắp xếp theo tên
     */
    List<DevelopmentalDomain> findAllByOrderByNameAsc();
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category và sắp xếp theo tên
     * @param category loại lĩnh vực (String)
     * @return List các lĩnh vực thuộc category, sắp xếp theo tên
     */
    List<DevelopmentalDomain> findByCategoryOrderByNameAsc(String category);
    
    /**
     * Đếm số lượng lĩnh vực phát triển theo category
     * @param category loại lĩnh vực (String)
     * @return số lượng lĩnh vực thuộc category đó
     */
    long countByCategory(String category);
    
    /**
     * Lấy danh sách tất cả categories đang được sử dụng
     * @return List các category đang có dữ liệu (String)
     */
    @Query("SELECT DISTINCT dd.category FROM DevelopmentalDomain dd WHERE dd.category IS NOT NULL ORDER BY dd.category")
    List<String> findDistinctCategories();
    
    /**
     * Tìm lĩnh vực phát triển theo displayed name (exact match)
     * @param displayedName tên hiển thị cần tìm
     * @return Optional chứa DevelopmentalDomain nếu tìm thấy
     */
    Optional<DevelopmentalDomain> findByDisplayedName(String displayedName);
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo displayed name (contains, không phân biệt hoa thường)
     * Hỗ trợ cả JSON và text thường
     * @param displayedName tên hiển thị cần tìm (có thể là một phần)
     * @return List các lĩnh vực có tên hiển thị chứa từ khóa
     */
    @Query("SELECT dd FROM DevelopmentalDomain dd WHERE " +
           "LOWER(dd.displayedName) LIKE LOWER(CONCAT('%', :displayedName, '%')) OR " +
           "LOWER(dd.displayedName) LIKE LOWER(CONCAT('%\"vi\":\"%', :displayedName, '%\"%')) OR " +
           "LOWER(dd.displayedName) LIKE LOWER(CONCAT('%\"en\":\"%', :displayedName, '%\"%'))")
    List<DevelopmentalDomain> findByDisplayedNameContainingIgnoreCase(@Param("displayedName") String displayedName);
    
    /**
     * Kiểm tra xem có tồn tại lĩnh vực với displayed name này không
     * @param displayedName tên hiển thị cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByDisplayedName(String displayedName);
    
    /**
     * Lấy danh sách lĩnh vực phát triển được sắp xếp theo displayed name (A-Z)
     * @return List các lĩnh vực sắp xếp theo tên hiển thị
     */
    List<DevelopmentalDomain> findAllByOrderByDisplayedNameAsc();
    
    /**
     * Lấy danh sách lĩnh vực phát triển theo category và sắp xếp theo displayed name
     * @param category loại lĩnh vực (String)
     * @return List các lĩnh vực thuộc category, sắp xếp theo tên hiển thị
     */
    List<DevelopmentalDomain> findByCategoryOrderByDisplayedNameAsc(String category);
    
    /**
     * Tìm kiếm lĩnh vực phát triển theo từ khóa trong tên, tên hiển thị hoặc mô tả
     * Hỗ trợ cả JSON và text thường cho displayedName và description
     * @param keyword từ khóa cần tìm
     * @return List các lĩnh vực có tên, tên hiển thị hoặc mô tả chứa từ khóa
     */
    @Query("SELECT dd FROM DevelopmentalDomain dd WHERE " +
           "LOWER(dd.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(dd.displayedName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(dd.displayedName) LIKE LOWER(CONCAT('%\"vi\":\"%', :keyword, '%\"%')) OR " +
           "LOWER(dd.displayedName) LIKE LOWER(CONCAT('%\"en\":\"%', :keyword, '%\"%')) OR " +
           "LOWER(dd.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(dd.description) LIKE LOWER(CONCAT('%\"vi\":\"%', :keyword, '%\"%')) OR " +
           "LOWER(dd.description) LIKE LOWER(CONCAT('%\"en\":\"%', :keyword, '%\"%'))")
    List<DevelopmentalDomain> searchByKeywordExtended(@Param("keyword") String keyword);
}
