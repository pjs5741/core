package counsel.core.mapper;

import counsel.core.api.dto.TeamResp;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    List<Member> findAll();
    Optional<Member> findById(@Param("id")  Long id);
    Optional<Member> findMember(Long id);
    List<Member> findAllByTeamIdFetch(@Param("teamId") Long teamId);

    int insert(Member m);
    int update(Member m);
    int delete(@Param("id") Long id);
}
