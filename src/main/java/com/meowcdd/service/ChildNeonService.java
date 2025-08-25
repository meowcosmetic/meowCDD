package com.meowcdd.service;

import com.meowcdd.entity.neon.ChildNeon;
import com.meowcdd.repository.neon.ChildNeonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChildNeonService {

    private final ChildNeonRepository childNeonRepository;

    public List<ChildNeon> getAllChildren() {
        log.info("Getting all children");
        return childNeonRepository.findAll();
    }

    public Page<ChildNeon> getAllChildrenWithPagination(Pageable pageable) {
        log.info("Getting all children with pagination");
        return childNeonRepository.findAll(pageable);
    }

    public Optional<ChildNeon> getChildById(Long id) {
        log.info("Getting child by ID: {}", id);
        return childNeonRepository.findById(id);
    }



    public List<ChildNeon> getChildrenByParentId(String parentId) {
        log.info("Getting children by parent ID: {}", parentId);
        return childNeonRepository.findByParentId(parentId);
    }

    public List<ChildNeon> getChildrenByStatus(ChildNeon.Status status) {
        log.info("Getting children by status: {}", status);
        return childNeonRepository.findByStatus(status);
    }

    public List<ChildNeon> getChildrenByGender(ChildNeon.Gender gender) {
        log.info("Getting children by gender: {}", gender);
        return childNeonRepository.findByGender(gender);
    }

    public List<ChildNeon> getChildrenByIsPremature(Boolean isPremature) {
        log.info("Getting children by is premature: {}", isPremature);
        return childNeonRepository.findByIsPremature(isPremature);
    }

    public List<ChildNeon> getChildrenByDevelopmentalDisorderDiagnosis(ChildNeon.DevelopmentalDisorderStatus diagnosis) {
        log.info("Getting children by developmental disorder diagnosis: {}", diagnosis);
        return childNeonRepository.findByDevelopmentalDisorderDiagnosis(diagnosis);
    }

    public List<ChildNeon> getChildrenByHasEarlyIntervention(Boolean hasEarlyIntervention) {
        log.info("Getting children by has early intervention: {}", hasEarlyIntervention);
        return childNeonRepository.findByHasEarlyIntervention(hasEarlyIntervention);
    }

    public List<ChildNeon> getChildrenByPrimaryLanguage(String language) {
        log.info("Getting children by primary language: {}", language);
        return childNeonRepository.findByPrimaryLanguage(language);
    }

    public List<ChildNeon> getChildrenByFamilyDevelopmentalIssues(ChildNeon.FamilyDevelopmentalIssues issue) {
        log.info("Getting children by family developmental issues: {}", issue);
        return childNeonRepository.findByFamilyDevelopmentalIssues(issue);
    }

    public List<ChildNeon> getChildrenByDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        log.info("Getting children by birth date between: {} and {}", startDate, endDate);
        return childNeonRepository.findByDateOfBirthBetween(startDate, endDate);
    }

    public List<ChildNeon> getChildrenByAgeRange(Integer minAge, Integer maxAge) {
        log.info("Getting children by age range: {} to {} months", minAge, maxAge);
        return childNeonRepository.findByAgeRange(minAge, maxAge);
    }

    public Page<ChildNeon> searchChildren(String keyword, Pageable pageable) {
        log.info("Searching children with keyword: {}", keyword);
        return childNeonRepository.searchByKeyword(keyword, pageable);
    }

    public Page<ChildNeon> getChildrenByStatusWithPagination(ChildNeon.Status status, Pageable pageable) {
        log.info("Getting children by status: {} with pagination", status);
        return childNeonRepository.findByStatusWithPagination(status, pageable);
    }

    public Page<ChildNeon> getChildrenByParentIdWithPagination(String parentId, Pageable pageable) {
        log.info("Getting children by parent ID: {} with pagination", parentId);
        return childNeonRepository.findByParentIdWithPagination(parentId, pageable);
    }

    public Page<ChildNeon> getChildrenByGenderWithPagination(ChildNeon.Gender gender, Pageable pageable) {
        log.info("Getting children by gender: {} with pagination", gender);
        return childNeonRepository.findByGenderWithPagination(gender, pageable);
    }

    public Page<ChildNeon> getChildrenByIsPrematureWithPagination(Boolean isPremature, Pageable pageable) {
        log.info("Getting children by is premature: {} with pagination", isPremature);
        return childNeonRepository.findByIsPrematureWithPagination(isPremature, pageable);
    }

    public Page<ChildNeon> getChildrenByDevelopmentalDisorderDiagnosisWithPagination(ChildNeon.DevelopmentalDisorderStatus diagnosis, Pageable pageable) {
        log.info("Getting children by developmental disorder diagnosis: {} with pagination", diagnosis);
        return childNeonRepository.findByDevelopmentalDisorderDiagnosisWithPagination(diagnosis, pageable);
    }

    public Page<ChildNeon> getChildrenByHasEarlyInterventionWithPagination(Boolean hasEarlyIntervention, Pageable pageable) {
        log.info("Getting children by has early intervention: {} with pagination", hasEarlyIntervention);
        return childNeonRepository.findByHasEarlyInterventionWithPagination(hasEarlyIntervention, pageable);
    }

    public Page<ChildNeon> getChildrenByPrimaryLanguageWithPagination(String language, Pageable pageable) {
        log.info("Getting children by primary language: {} with pagination", language);
        return childNeonRepository.findByPrimaryLanguageWithPagination(language, pageable);
    }

    public Page<ChildNeon> getChildrenByFamilyDevelopmentalIssuesWithPagination(ChildNeon.FamilyDevelopmentalIssues issue, Pageable pageable) {
        log.info("Getting children by family developmental issues: {} with pagination", issue);
        return childNeonRepository.findByFamilyDevelopmentalIssuesWithPagination(issue, pageable);
    }

    public Page<ChildNeon> getChildrenByDateOfBirthBetweenWithPagination(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("Getting children by birth date between: {} and {} with pagination", startDate, endDate);
        return childNeonRepository.findByDateOfBirthBetweenWithPagination(startDate, endDate, pageable);
    }

    public Page<ChildNeon> getChildrenByAgeRangeWithPagination(Integer minAge, Integer maxAge, Pageable pageable) {
        log.info("Getting children by age range: {} to {} months with pagination", minAge, maxAge);
        return childNeonRepository.findByAgeRangeWithPagination(minAge, maxAge, pageable);
    }

    public ChildNeon createChild(ChildNeon child) {
        log.info("Creating new child: {}", child.getFullName());
        child.calculateCurrentAge();
        return childNeonRepository.save(child);
    }

    public Optional<ChildNeon> updateChild(Long id, ChildNeon child) {
        log.info("Updating child with ID: {}", id);
        return childNeonRepository.findById(id)
                .map(existingChild -> {
                    existingChild.setFullName(child.getFullName());
                    existingChild.setGender(child.getGender());
                    existingChild.setDateOfBirth(child.getDateOfBirth());
                    existingChild.setCurrentAgeMonths(child.getCurrentAgeMonths());
                    existingChild.setIsPremature(child.getIsPremature());
                    existingChild.setGestationalWeek(child.getGestationalWeek());
                    existingChild.setBirthWeightGrams(child.getBirthWeightGrams());
                    existingChild.setSpecialMedicalConditions(child.getSpecialMedicalConditions());
                    existingChild.setDevelopmentalDisorderDiagnosis(child.getDevelopmentalDisorderDiagnosis());
                    existingChild.setHasEarlyIntervention(child.getHasEarlyIntervention());
                    existingChild.setEarlyInterventionDetails(child.getEarlyInterventionDetails());
                    existingChild.setPrimaryLanguage(child.getPrimaryLanguage());
                    existingChild.setFamilyDevelopmentalIssues(child.getFamilyDevelopmentalIssues());
                    existingChild.setHeight(child.getHeight());
                    existingChild.setWeight(child.getWeight());
                    existingChild.setBloodType(child.getBloodType());
                    existingChild.setAllergies(child.getAllergies());
                    existingChild.setMedicalHistory(child.getMedicalHistory());
                    existingChild.setParentId(child.getParentId());
                    existingChild.setStatus(child.getStatus());
                    existingChild.calculateCurrentAge();
                    return childNeonRepository.save(existingChild);
                });
    }

    public boolean deleteChild(Long id) {
        log.info("Deleting child with ID: {}", id);
        if (childNeonRepository.existsById(id)) {
            childNeonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long getChildCountByStatus(ChildNeon.Status status) {
        log.info("Getting child count by status: {}", status);
        return childNeonRepository.countByStatus(status);
    }

    public long getChildCountByGender(ChildNeon.Gender gender) {
        log.info("Getting child count by gender: {}", gender);
        return childNeonRepository.countByGender(gender);
    }

    public long getChildCountByIsPremature(Boolean isPremature) {
        log.info("Getting child count by is premature: {}", isPremature);
        return childNeonRepository.countByIsPremature(isPremature);
    }

    public long getChildCountByDevelopmentalDisorderDiagnosis(ChildNeon.DevelopmentalDisorderStatus diagnosis) {
        log.info("Getting child count by developmental disorder diagnosis: {}", diagnosis);
        return childNeonRepository.countByDevelopmentalDisorderDiagnosis(diagnosis);
    }

    public long getChildCountByHasEarlyIntervention(Boolean hasEarlyIntervention) {
        log.info("Getting child count by has early intervention: {}", hasEarlyIntervention);
        return childNeonRepository.countByHasEarlyIntervention(hasEarlyIntervention);
    }

    public List<Object[]> getChildCountByPrimaryLanguage() {
        log.info("Getting child count by primary language");
        return childNeonRepository.countByPrimaryLanguage();
    }

    public List<Object[]> getChildCountByFamilyDevelopmentalIssues() {
        log.info("Getting child count by family developmental issues");
        return childNeonRepository.countByFamilyDevelopmentalIssues();
    }
}
