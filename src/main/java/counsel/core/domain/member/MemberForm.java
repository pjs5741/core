package counsel.core.domain.member;

import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import counsel.core.domain.member.ServiceInfo.ServiceInfo.Status;
import jakarta.validation.constraints.NotBlank;

public class MemberForm {

    private Long id;                    // 수정 시 사용

    @NotBlank
    private String name;

    @NotNull
    private Status status;              // ACTIVE/IDLE/LEAVE

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;          // 재직 중이면 null

    private Long teamId;                // (미배정) 허용 → null

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
}