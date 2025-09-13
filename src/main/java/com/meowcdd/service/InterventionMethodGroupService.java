package com.meowcdd.service;

import com.meowcdd.entity.neon.InterventionMethod;
import com.meowcdd.entity.neon.InterventionMethodGroup;
// import com.meowcdd.entity.neon.InterventionMethodGroupMember;
// import com.meowcdd.repository.neon.InterventionMethodGroupMemberRepository;
import com.meowcdd.repository.neon.InterventionMethodGroupRepository;
import com.meowcdd.repository.neon.InterventionMethodRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterventionMethodGroupService {

    private final InterventionMethodGroupRepository groupRepository;
    private final InterventionMethodRepository methodRepository;

    public InterventionMethodGroup createGroup(InterventionMethodGroup group) {
        if (groupRepository.existsByCode(group.getCode())) {
            throw new IllegalArgumentException("Group code already exists: " + group.getCode());
        }
        return groupRepository.save(group);
    }

    public InterventionMethodGroup updateGroup(Long id, InterventionMethodGroup update) {
        InterventionMethodGroup existing = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + id));
        if (update.getCode() != null) existing.setCode(update.getCode());
        if (update.getDisplayedName() != null) existing.setDisplayedName(update.getDisplayedName());
        if (update.getDescription() != null) existing.setDescription(update.getDescription());
        if (update.getMinAgeMonths() != null) existing.setMinAgeMonths(update.getMinAgeMonths());
        if (update.getMaxAgeMonths() != null) existing.setMaxAgeMonths(update.getMaxAgeMonths());
        return groupRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public InterventionMethodGroup getGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + id));
    }

    @Transactional(readOnly = true)
    public Page<InterventionMethodGroup> listGroups(int page, int size, String sortBy, String sortDir) {
        Sort sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return groupRepository.findAll(pageable);
    }

    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Group not found: " + id);
        }
        groupRepository.deleteById(id);
    }

    // Members management removed: a Method now belongs to exactly one Group
}


