package com.meowcdd.service;

import com.meowcdd.entity.supabase.ChildSupabase;
import com.meowcdd.repository.supabase.ChildSupabaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChildSupabaseService {

    private final ChildSupabaseRepository childRepository;

    /**
     * Tạo trẻ mới
     */
    public ChildSupabase createChild(ChildSupabase child) {
        log.info("Creating new child: {}", child.getFullName());
        return childRepository.save(child);
    }

    /**
     * Cập nhật thông tin trẻ
     */
    public ChildSupabase updateChild(Long id, ChildSupabase childDetails) {
        log.info("Updating child with id: {}", id);
        
        ChildSupabase existingChild = childRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Child not found with id: " + id));

        // Cập nhật các trường
        existingChild.setParentId(childDetails.getParentId());
        existingChild.setFullName(childDetails.getFullName());
        existingChild.setGender(childDetails.getGender());
        existingChild.setDateOfBirth(childDetails.getDateOfBirth());
        existingChild.setIsPremature(childDetails.getIsPremature());
        existingChild.setGestationalWeek(childDetails.getGestationalWeek());
        existingChild.setBirthWeightGrams(childDetails.getBirthWeightGrams());
        existingChild.setSpecialMedicalConditions(childDetails.getSpecialMedicalConditions());
        existingChild.setDevelopmentalDisorderDiagnosis(childDetails.getDevelopmentalDisorderDiagnosis());
        existingChild.setHasEarlyIntervention(childDetails.getHasEarlyIntervention());
        existingChild.setEarlyInterventionDetails(childDetails.getEarlyInterventionDetails());
        existingChild.setPrimaryLanguage(childDetails.getPrimaryLanguage());
        existingChild.setFamilyDevelopmentalIssues(childDetails.getFamilyDevelopmentalIssues());
        existingChild.setHeight(childDetails.getHeight());
        existingChild.setWeight(childDetails.getWeight());
        existingChild.setBloodType(childDetails.getBloodType());
        existingChild.setAllergies(childDetails.getAllergies());
        existingChild.setMedicalHistory(childDetails.getMedicalHistory());
        existingChild.setStatus(childDetails.getStatus());

        return childRepository.save(existingChild);
    }

    /**
     * Lấy trẻ theo ID
     */
    @Transactional(readOnly = true)
    public Optional<ChildSupabase> getChildById(Long id) {
        return childRepository.findById(id);
    }



    /**
     * Lấy tất cả trẻ
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> getAllChildren() {
        return childRepository.findAll();
    }

    /**
     * Lấy trẻ theo parent ID
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> getChildrenByParentId(String parentId) {
        return childRepository.findByParentId(parentId);
    }

    /**
     * Tìm trẻ theo tên
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenByName(String name) {
        return childRepository.findByFullNameContainingIgnoreCase(name);
    }

    /**
     * Tìm trẻ theo giới tính
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenByGender(ChildSupabase.Gender gender) {
        return childRepository.findByGender(gender);
    }

    /**
     * Tìm trẻ theo độ tuổi
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenByAgeRange(Integer minAge, Integer maxAge) {
        return childRepository.findByAgeRange(minAge, maxAge);
    }

    /**
     * Tìm trẻ sinh non
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findPrematureChildren() {
        return childRepository.findByIsPrematureTrue();
    }

    /**
     * Tìm trẻ có rối loạn phát triển
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenWithDevelopmentalDisorder(ChildSupabase.DevelopmentalDisorderStatus status) {
        return childRepository.findByDevelopmentalDisorderDiagnosis(status);
    }

    /**
     * Tìm trẻ có can thiệp sớm
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenWithEarlyIntervention() {
        return childRepository.findByHasEarlyInterventionTrue();
    }

    /**
     * Xóa trẻ
     */
    public void deleteChild(Long id) {
        log.info("Deleting child with id: {}", id);
        childRepository.deleteById(id);
    }

    /**
     * Thống kê theo giới tính
     */
    @Transactional(readOnly = true)
    public long countByGender(ChildSupabase.Gender gender) {
        return childRepository.countByGender(gender);
    }

    /**
     * Thống kê theo trạng thái
     */
    @Transactional(readOnly = true)
    public long countByStatus(ChildSupabase.Status status) {
        return childRepository.countByStatus(status);
    }

    /**
     * Tìm trẻ theo khoảng ngày sinh
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        return childRepository.findByDateOfBirthBetween(startDate, endDate);
    }

    /**
     * Tìm trẻ theo cân nặng khi sinh
     */
    @Transactional(readOnly = true)
    public List<ChildSupabase> findChildrenByBirthWeightRange(Integer minWeight, Integer maxWeight) {
        return childRepository.findByBirthWeightBetween(minWeight, maxWeight);
    }
}
