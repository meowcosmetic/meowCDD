package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.InterventionPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterventionPostNeonRepository extends JpaRepository<InterventionPost, Long> {
    
    // Tìm theo criteria
    List<InterventionPost> findByCriteria_Id(Long criteriaId);
    
    // Tìm theo program
    List<InterventionPost> findByProgram_Id(Long programId);
    
    // Tìm theo post type
    List<InterventionPost> findByPostType(InterventionPost.PostType postType);
    
    // Tìm theo published status
    List<InterventionPost> findByIsPublished(Boolean isPublished);
    
    // Tìm theo author
    List<InterventionPost> findByAuthor(String author);
    
    // Tìm theo độ tuổi
    @Query("SELECT p FROM InterventionPost p WHERE " +
           "(:minAge IS NULL OR p.targetAgeMinMonths <= :age) AND " +
           "(:maxAge IS NULL OR p.targetAgeMaxMonths >= :age)")
    List<InterventionPost> findByTargetAge(@Param("age") Integer age, 
                                          @Param("minAge") Integer minAge, 
                                          @Param("maxAge") Integer maxAge);
    
    // Tìm kiếm theo keyword trong title và content
    @Query("SELECT p FROM InterventionPost p WHERE " +
           "(:keyword IS NULL OR " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.tags) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<InterventionPost> search(@Param("keyword") String keyword, Pageable pageable);
    
    // Tìm theo criteria và post type
    @Query("SELECT p FROM InterventionPost p WHERE " +
           "(:criteriaId IS NULL OR p.criteria.id = :criteriaId) AND " +
           "(:postType IS NULL OR p.postType = :postType) AND " +
           "(:isPublished IS NULL OR p.isPublished = :isPublished)")
    Page<InterventionPost> findByFilters(@Param("criteriaId") Long criteriaId,
                                        @Param("postType") InterventionPost.PostType postType,
                                        @Param("isPublished") Boolean isPublished,
                                        Pageable pageable);
    
    // Tìm bài post theo criteria và sắp xếp theo difficulty level
    @Query("SELECT p FROM InterventionPost p WHERE p.criteria.id = :criteriaId " +
           "ORDER BY p.difficultyLevel ASC, p.createdAt DESC")
    List<InterventionPost> findByCriteriaOrderByDifficulty(@Param("criteriaId") Long criteriaId);
}
