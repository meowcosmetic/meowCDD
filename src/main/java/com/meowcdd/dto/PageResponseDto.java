package com.meowcdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDto<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    
    public static <T> PageResponseDto<T> of(List<T> content, int pageNumber, int pageSize, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        return PageResponseDto.<T>builder()
                .content(content)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(pageNumber < totalPages - 1)
                .hasPrevious(pageNumber > 0)
                .isFirst(pageNumber == 0)
                .isLast(pageNumber >= totalPages - 1)
                .build();
    }
}
