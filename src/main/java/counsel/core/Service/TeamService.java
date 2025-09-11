package counsel.core.Service;

import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TeamService {

    Team join(Team team);

    Optional<Team> findTeam(Long teamId);
    List<Team> findAll();
    void update(Long id, String name);

    Team findById(Long id);
    void delete(Long id);
    void deleteTeamSafely(Long teamId);
}
