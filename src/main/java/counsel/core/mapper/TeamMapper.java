package counsel.core.mapper;

import counsel.core.api.dto.teamdto.TeamResp;
import counsel.core.domain.Team.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TeamMapper {
    List<Team> findAll();
    List<TeamResp> findAllWithCount();
    Optional<Team> findById(@Param("id")  Long id);

    int insert(Team t);
    int update(Team t);
    int delete(@Param("id") Long id);

    int unassignAllMembers(@Param("teamId") Long teamId);
    long countMembers(@Param("teamId") Long teamId);
}
