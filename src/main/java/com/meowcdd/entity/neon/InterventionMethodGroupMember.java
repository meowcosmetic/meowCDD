package com.meowcdd.entity.neon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "intervention_method_group_members",
       uniqueConstraints = @UniqueConstraint(name = "uq_imgm_group_method", columnNames = {"group_id", "method_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterventionMethodGroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false, foreignKey = @ForeignKey(name = "fk_imgm_group"))
    private InterventionMethodGroup group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "method_id", nullable = false, foreignKey = @ForeignKey(name = "fk_imgm_method"))
    private InterventionMethod method;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "notes", columnDefinition = "jsonb")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}


