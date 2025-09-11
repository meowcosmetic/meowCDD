package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.InterventionMethodGroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionMethodGroupMemberRepository extends JpaRepository<InterventionMethodGroupMember, Long> {
    List<InterventionMethodGroupMember> findByGroupIdOrderByOrderIndexAsc(Long groupId);
    Page<InterventionMethodGroupMember> findByGroupId(Long groupId, Pageable pageable);
    boolean existsByGroupIdAndMethodId(Long groupId, Long methodId);
}


