package counsel.core.controller;

//import counsel.core.Repository.MemberRepository;
import counsel.core.Repository.TeamRepository;
import counsel.core.Service.MemberService;
import counsel.core.Service.MemberServiceMyBatis;
import counsel.core.Service.TeamService;
import counsel.core.api.dto.TeamResp;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import counsel.core.mapper.TeamMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final TeamService teamService;
    private final MemberService memberService;

    public HomeController(TeamService teamService,MemberService memberService) {
        this.teamService = teamService;
        this.memberService = memberService;
    }


    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<TeamResp> teams = teamService.findAllWithCount();
        List<Member> members = memberService.findAll();


        model.addAttribute("teams", teams);
        model.addAttribute("members", members);

        return "home";
    }

    public void JPAConstructor(){
    /*        private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public HomeController(TeamRepository teamRepository,
                          MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }*/
    }
    public void JPAMemberList(){
         /*List<Member> members = memberRepository.findAll();

        List<TeamResp> teamDtos = teams.stream()
                .map(t -> new TeamResp(
                        t.getId(),
                        t.getName(),
                        (int) members.stream()
                                .filter(m -> m.getTeam() != null && m.getTeam().getId().equals(t.getId()))
                                .count()
                ))
                .collect(Collectors.toList());*/
    }

}