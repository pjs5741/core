package counsel.core.Service;

import counsel.core.api.dto.TeamResp;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TeamService {

    Team join(Team team);

    Optional<Team> findTeam(Long id);
    List<Team> findAll();
    void update(Long id, String name);

    Optional<Team> findById(Long id);
    void deleteTeamSafely(Long id);
    void delete(Long id);
    List<TeamResp> findAllWithCount();

}
