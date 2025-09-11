package counsel.core.domain.member;

import counsel.core.domain.Team.Team;
import counsel.core.domain.member.ServiceInfo.ServiceInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // null 허용 → 미배정 가능
    private Team team;

    @Embedded
    private ServiceInfo serviceInfo;

    protected Member() {} // JPA 기본 생성자

    public Member(Long id, String name, Team team, ServiceInfo serviceInfo) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.serviceInfo = serviceInfo;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Team getTeam() { return team; }
    public ServiceInfo getServiceInfo() { return serviceInfo; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTeam(Team team) { this.team = team; }
    public void setServiceInfo(ServiceInfo serviceInfo) { this.serviceInfo = serviceInfo; }
}