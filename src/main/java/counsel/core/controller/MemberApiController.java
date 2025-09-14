package counsel.core.controller;

//import counsel.core.Repository.MemberRepository;
import counsel.core.Service.MemberService;
import counsel.core.api.dto.MemberSimpleResp;
import counsel.core.domain.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private static final Logger log = LoggerFactory.getLogger(MemberApiController.class);
    //private final MemberRepository memberRepository;

    /*public MemberApiController(MemberRepository memberRepository) {
       // this.memberRepository = memberRepository;
    }*/

    private final MemberService memberService;

    public MemberApiController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/by-team/{teamId}")
    public List<MemberSimpleResp> getMembersByTeam(@PathVariable Long teamId) {
        var list = memberService.findAllByTeamIdFetch(teamId);
        return list.stream().map(this::toResp).toList();
    }

    private MemberSimpleResp toResp(Member m) {
        var info = m.getServiceInfo();
        String status = (info != null && info.getStatus() != null) ? info.getStatus().name() : "-";
        String teamName = (m.getTeam() != null) ? m.getTeam().getName() : "-";
        String tenure = (info != null && info.getTenureHuman() != null) ? info.getTenureHuman() : "-";
        return new MemberSimpleResp(
                m.getId(),
                m.getName(),
                status,
                teamName,
                tenure,
                (info != null ? info.getStartDate() : null),
                (info != null ? info.getEndDate() : null)
        );
    }
}
