package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.RoleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoleDetailNeonRepository extends JpaRepository<RoleDetail, UUID> {
    
    List<RoleDetail> findByRole_RoleId(UUID roleId);
    
    @Query("SELECT rd FROM RoleDetail rd WHERE " +
           "(:keyword IS NULL OR LOWER(rd.role.roleName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<RoleDetail> search(String keyword, Pageable pageable);
}
