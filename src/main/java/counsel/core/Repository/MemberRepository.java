package counsel.core.Repository;

import counsel.core.domain.member.Member;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Member m set m.team = null where m.team.id = :teamId")
    int unassignAllByTeamId(@Param("teamId") Long teamId);

    long countByTeam_Id(Long teamId);

    List<Member> findByTeam_Id(Long teamId);

    // ⬇ 우측/아래 표용 (팀도 함께 로딩)
    @Query("""
           select m
           from Member m
           join fetch m.team
           where m.team.id = :teamId
           order by m.id
           """)
    List<Member> findAllByTeamIdFetch(@Param("teamId") Long teamId);
}
