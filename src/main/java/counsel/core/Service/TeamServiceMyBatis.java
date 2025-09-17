package counsel.core.Service;

import counsel.core.api.dto.teamdto.TeamResp;
import counsel.core.domain.Team.Team;
import counsel.core.mapper.TeamMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TeamServiceMyBatis implements TeamService{

    private final TeamMapper teamMapper;

    public TeamServiceMyBatis(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public List<TeamResp> findAllWithCount() { return teamMapper.findAllWithCount(); }

    @Override
    public Team join(Team team) {
        if (team == null || team.getName() == null || team.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("팀명이 비어 있습니다.");
        }
        team.setName(team.getName().trim());
        teamMapper.insert(team);
        return team;
    }

    @Override
    public Optional<Team> findTeam(Long teamId) {
        return Optional.empty();
    }

    @Override
    public List<Team> findAll() {
        return teamMapper.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamMapper.findById(id);
    }

    @Override
    public void update(Long id, String name) {
        Team t = new Team();
        t.setId(id);
        t.setName(name);
        teamMapper.update(t);
    }




    @Override
    public void deleteTeamSafely(Long id) {
        teamMapper.unassignAllMembers(id);
        teamMapper.delete(id);
    }

    @Override
    public void delete(Long id) {
        teamMapper.delete(id);
    }
}
