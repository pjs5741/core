package counsel.core.Service;

import counsel.core.Repository.MemberRepository;
import counsel.core.Repository.TeamRepository;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService{


    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Team join(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> findTeam(Long memberId) {
        return Optional.empty();
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public void update(Long id, String name) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("팀 없음"));
        team.setName(name);
        teamRepository.save(team);
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다. id=" + id));
    }

    @Override
    public void delete(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 팀이 없습니다. id=" + id));
        teamRepository.deleteById(id);
    }

    @Transactional
    public void deleteTeamSafely(Long teamId) {
        // 1) 소속 해제
        memberRepository.unassignAllByTeamId(teamId);
        // 2) 팀 삭제
        teamRepository.deleteById(teamId);
    }
}
