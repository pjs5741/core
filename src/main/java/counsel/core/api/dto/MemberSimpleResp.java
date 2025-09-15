// src/main/java/counsel/core/api/dto/MemberSimpleResp.java
package counsel.core.api.dto;

import counsel.core.domain.member.Member;

import java.time.LocalDate;

public record MemberSimpleResp(
        Long id,
        String name,
        String status,
        String teamName,
        String tenureHuman,
        LocalDate startDate,
        LocalDate endDate
)
{
    public static MemberSimpleResp from(Member m) {
        var info = m.getServiceInfo();
        String status = (info != null && info.getStatus() != null) ? info.getStatus().name() : "-";
        String teamName = (m.getTeam() != null) ? m.getTeam().getName() : "-";
        String tenure = (info != null && info.getTenureHuman() != null) ? info.getTenureHuman() : "-";

        return new MemberSimpleResp(
                m.getId(),
                m.getName(),
                status,
                teamName,
                tenure,
                (info != null ? info.getStartDate() : null),
                (info != null ? info.getEndDate() : null)
        );
    }
}
