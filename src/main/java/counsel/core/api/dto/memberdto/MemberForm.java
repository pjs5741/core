package counsel.core.api.dto.memberdto;

import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import counsel.core.domain.member.ServiceInfo.ServiceInfo;
import counsel.core.domain.member.ServiceInfo.ServiceInfo.Status;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record MemberForm (

    Long id,
    @NotBlank
    String name,
    Status status,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate,

    Long teamId
)
{
    public static MemberForm  from(Member m) {
        ServiceInfo info = m.getServiceInfo();



        return new MemberForm(m.getId(),
                m.getName(),
                info != null ? info.getStatus() : null,
                info != null ? info.getStartDate() : null,
                info != null ? info.getEndDate() : null,
                m.getTeam() != null ? m.getTeam().getId() : null
        );
    }


    public Member toEntity(Team team) {
        ServiceInfo info = new ServiceInfo(
                this.status != null ? this.status : Status.ACTIVE,
                this.startDate,
                this.endDate
        );
        return new Member(this.id, this.name, team, info);
    }

    public void applyTo(Member m, Team team) {
        m.setName(this.name);

        if (m.getServiceInfo() == null) {
            //ServiceInfo info = new ServiceInfo();
            m.setServiceInfo(new ServiceInfo());

        }
/*        Status status =this.status != null ? this.status : Status.ACTIVE;
        LocalDate startDate = this.startDate;
        LocalDate endDate = this.endDate;*/

        m.getServiceInfo().setStatus(this.status != null ? this.status : Status.ACTIVE);
        m.getServiceInfo().setStartDate(this.startDate);
        m.getServiceInfo().setEndDate(this.endDate);

        m.setTeam(team);

    }

}


