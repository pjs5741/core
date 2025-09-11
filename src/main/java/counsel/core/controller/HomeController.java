package counsel.core.controller;

import counsel.core.Repository.MemberRepository;
import counsel.core.Repository.TeamRepository;
import counsel.core.Service.TeamService;
import counsel.core.api.dto.TeamResp;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final TeamRepository teamRepository;      // ⬅ 추가
    private final MemberRepository memberRepository;

    public HomeController(TeamRepository teamRepository,   // ⬅ 생성자 주입
                          MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Team> teams = teamRepository.findAll();
        List<Member> members = memberRepository.findAll();

        // DTO 변환 (팀 id, 이름, 인원수)
        List<TeamResp> teamDtos = teams.stream()
                .map(t -> new TeamResp(
                        t.getId(),
                        t.getName(),
                        (int) members.stream()
                                .filter(m -> m.getTeam() != null && m.getTeam().getId().equals(t.getId()))
                                .count()
                ))
                .collect(Collectors.toList());

        model.addAttribute("teams", teamDtos);
        model.addAttribute("members", members);

        return "home";
    }
}