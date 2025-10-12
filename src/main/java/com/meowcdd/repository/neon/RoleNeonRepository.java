package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoleNeonRepository extends JpaRepository<Role, UUID> {
    
    @Query("SELECT r FROM Role r WHERE " +
           "(:keyword IS NULL OR LOWER(r.roleName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Role> search(String keyword, Pageable pageable);
    
    List<Role> findByRoleNameContainingIgnoreCase(String roleName);
}
