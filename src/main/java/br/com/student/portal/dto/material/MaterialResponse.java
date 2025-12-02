package br.com.student.portal.dto.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialResponse {

    private String id;
    private String name;
    private String description;
    private String filename;        // ✅ ADICIONE ESTE CAMPO
    private String category;
    private String uploaderId;      // ✅ TROQUE courseId por uploaderId
    private String uploaderName;    // ✅ ADICIONE ESTE CAMPO
    private Long downloads;         // ✅ ADICIONE ESTE CAMPO
    private String uploadDate;
}