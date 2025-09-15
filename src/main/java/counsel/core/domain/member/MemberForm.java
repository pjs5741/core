package counsel.core.domain.member;

import counsel.core.domain.Team.Team;
import counsel.core.domain.member.ServiceInfo.ServiceInfo;
import counsel.core.domain.member.ServiceInfo.ServiceInfo.Status;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class MemberForm {

    private Long id;

    @NotBlank
    private String name;

    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private Long teamId;

    // 기본 생성자 (스프링이 반드시 필요)
    public MemberForm() {}

    // 생성자 (편의용, 필요시)
    public MemberForm(Long id, String name, Status status, LocalDate startDate, LocalDate endDate, Long teamId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamId = teamId;
    }

    /** 엔티티 -> Form (조회/수정 폼용) */
    public static MemberForm from(Member m) {
        ServiceInfo info = m.getServiceInfo();
        return new MemberForm(
                m.getId(),
                m.getName(),
                (info != null ? info.getStatus() : null),
                (info != null ? info.getStartDate() : null),
                (info != null ? info.getEndDate() : null),
                (m.getTeam() != null ? m.getTeam().getId() : null)
        );
    }

    /** Form + Team -> 새 Member 엔티티 생성 */
    public Member toEntity(Team team) {
        ServiceInfo info = new ServiceInfo(
                this.status != null ? this.status : Status.ACTIVE,
                this.startDate,
                this.endDate
        );
        return new Member(this.id, this.name, team, info);
    }

    /** 기존 Member 엔티티에 Form 값 적용 */
    public void applyTo(Member m, Team team) {
        m.setName(this.name);

        if (m.getServiceInfo() == null) {
            m.setServiceInfo(new ServiceInfo());
        }
        m.getServiceInfo().setStatus(this.status != null ? this.status : Status.ACTIVE);
        m.getServiceInfo().setStartDate(this.startDate);
        m.getServiceInfo().setEndDate(this.endDate);

        m.setTeam(team);
    }

    // 🔽 Getter / Setter (스프링 폼 바인딩용)
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
