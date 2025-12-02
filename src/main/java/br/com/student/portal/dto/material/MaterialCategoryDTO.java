package br.com.student.portal.dto.material;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MaterialCategoryDTO {
    private String name;
    private String displayName;
}