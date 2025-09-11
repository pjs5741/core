// src/main/java/counsel/core/api/dto/MemberSimpleResp.java
package counsel.core.api.dto;

import java.time.LocalDate;

public record MemberSimpleResp(
        Long id,
        String name,
        String status,        // "ACTIVE"/"IDLE"/"LEAVE" or "-"
        String teamName,      // 팀명 (없으면 "-")
        String tenureHuman,   // "2년 1개월" 등 (없으면 "-")
        LocalDate startDate,
        LocalDate endDate
) {}
