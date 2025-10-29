package com.meowcdd.repository.neon;

import com.meowcdd.entity.neon.CollaboratorSpecialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollaboratorSpecializationRepository extends JpaRepository<CollaboratorSpecialization, UUID> {
    
    List<CollaboratorSpecialization> findByCollaborator_CollaboratorId(UUID collaboratorId);
    
    List<CollaboratorSpecialization> findBySpecialization_SpecializationId(UUID specializationId);
    
    List<CollaboratorSpecialization> findBySpecialization_SpecializationName(String specializationName);
    
    List<CollaboratorSpecialization> findBySpecialization_SpecializationType(String specializationType);
    
    List<CollaboratorSpecialization> findByProficiencyLevel(CollaboratorSpecialization.ProficiencyLevel proficiencyLevel);
    
    List<CollaboratorSpecialization> findByStatus(CollaboratorSpecialization.Status status);
    
    List<CollaboratorSpecialization> findByIsPrimary(Boolean isPrimary);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.collaborator.collaboratorId = :collaboratorId AND cs.isPrimary = true")
    List<CollaboratorSpecialization> findPrimarySpecializationsByCollaboratorId(@Param("collaboratorId") UUID collaboratorId);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.collaborator.collaboratorId = :collaboratorId AND cs.status = :status")
    List<CollaboratorSpecialization> findByCollaboratorIdAndStatus(@Param("collaboratorId") UUID collaboratorId, @Param("status") CollaboratorSpecialization.Status status);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.specialization.specializationId = :specializationId AND cs.status = :status")
    List<CollaboratorSpecialization> findBySpecializationIdAndStatus(@Param("specializationId") UUID specializationId, @Param("status") CollaboratorSpecialization.Status status);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.collaborator.collaboratorId = :collaboratorId ORDER BY cs.isPrimary DESC, cs.yearsOfExperience DESC")
    List<CollaboratorSpecialization> findByCollaboratorIdOrderByPrimaryAndExperience(@Param("collaboratorId") UUID collaboratorId);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.specialization.specializationId = :specializationId ORDER BY cs.proficiencyLevel DESC, cs.yearsOfExperience DESC")
    List<CollaboratorSpecialization> findBySpecializationIdOrderByProficiencyAndExperience(@Param("specializationId") UUID specializationId);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.collaborator.collaboratorId = :collaboratorId AND cs.specialization.specializationId = :specializationId")
    Optional<CollaboratorSpecialization> findByCollaboratorIdAndSpecializationId(@Param("collaboratorId") UUID collaboratorId, @Param("specializationId") UUID specializationId);
    
    @Query("SELECT cs FROM CollaboratorSpecialization cs WHERE cs.specialization.specializationName LIKE %:name% OR cs.notes LIKE %:notes%")
    List<CollaboratorSpecialization> searchBySpecializationNameOrNotes(@Param("name") String name, @Param("notes") String notes);
    
    Page<CollaboratorSpecialization> findByCollaborator_CollaboratorId(UUID collaboratorId, Pageable pageable);
    
    Page<CollaboratorSpecialization> findBySpecialization_SpecializationId(UUID specializationId, Pageable pageable);
}
