package com.meowcdd.entity.neon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meowcdd.config.converter.JsonStringConverter;
import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "cdd_test_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CDDTestCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 100)
    private String code;

    @Column(name = "displayed_name", columnDefinition = "TEXT", nullable = false)
    @Convert(converter = JsonStringConverter.class)
    private Map<String, Object> displayedName;

    @Column(name = "description", columnDefinition = "TEXT")
    @Convert(converter = JsonStringConverter.class)
    private Map<String, Object> description;
}


