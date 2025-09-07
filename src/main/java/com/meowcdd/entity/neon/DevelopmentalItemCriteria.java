package com.meowcdd.entity.neon;

import com.meowcdd.config.converter.JsonStringConverter;
import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

@Entity
@Table(name = "developmental_item_criteria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DevelopmentalItemCriteria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private DevelopmentalDomainItem item;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    @Convert(converter = JsonStringConverter.class)
    private Map<String, Object> description; // JSON object (e.g., {"vi":"...","en":"..."})

    @Column(name = "min_age_months")
    private Integer minAgeMonths;

    @Column(name = "max_age_months")
    private Integer maxAgeMonths;

    @Column(name = "level")
    private Integer level;
}


