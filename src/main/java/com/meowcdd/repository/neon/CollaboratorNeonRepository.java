package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CollaboratorNeonRepository extends JpaRepository<Collaborator, UUID> {
    
    List<Collaborator> findByUserId(UUID userId);
    
    List<Collaborator> findByRole_RoleId(UUID roleId);
    
    List<Collaborator> findByStatus(Collaborator.Status status);
    
    @Query("SELECT c FROM Collaborator c WHERE " +
           "(:keyword IS NULL OR " +
           "LOWER(c.specialization) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.organization) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.role.roleName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Collaborator> search(String keyword, Pageable pageable);
}
