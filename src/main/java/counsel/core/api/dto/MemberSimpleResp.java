// src/main/java/counsel/core/api/dto/MemberSimpleResp.java
package counsel.core.api.dto;

import java.time.LocalDate;

public record MemberSimpleResp(
        Long id,
        String name,
        String status,
        String teamName,
        String tenureHuman,
        LocalDate startDate,
        LocalDate endDate
) {}
