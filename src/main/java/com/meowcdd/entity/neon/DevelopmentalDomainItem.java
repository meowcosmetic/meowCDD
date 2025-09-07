package com.meowcdd.entity.neon;

import com.meowcdd.config.converter.JsonStringConverter;
import com.meowcdd.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

import java.util.UUID;

/**
 * Item thuộc lĩnh vực phát triển (Developmental Domain Item)
 * - code: mã ngắn (VD: "1", "2")
 * - title: đa ngôn ngữ, lưu string JSON
 */
@Entity
@Table(name = "developmental_domain_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DevelopmentalDomainItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "domain_id", nullable = false, columnDefinition = "UUID")
    private DevelopmentalDomain domain;

    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    @Convert(converter = JsonStringConverter.class)
    private Map<String, Object> title;
}


