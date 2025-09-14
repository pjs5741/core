package counsel.core.controller;

//import counsel.core.Repository.MemberRepository;
import counsel.core.Repository.TeamRepository;
import counsel.core.Service.*;
import counsel.core.domain.member.Member;
import counsel.core.domain.member.ConsultantState;
import counsel.core.domain.member.MemberForm;
import counsel.core.domain.member.ServiceInfo.ServiceInfo;
import counsel.core.domain.Team.Team;
import counsel.core.Service.MemberService;
import counsel.core.Repository.TeamRepository;
import counsel.core.mapper.MemberMapper;
import counsel.core.mapper.TeamMapper;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import counsel.core.domain.member.ServiceInfo.ServiceInfo.Status;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;

    MemberController(MemberService memberService,TeamService teamService){
        this.memberService = memberService;
        this.teamService = teamService;

    }


    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("member", new MemberForm());
        model.addAttribute("teams", teamService.findAll());
        //model.addAttribute("teams", teamRepository.findAll());
        return "members/new";
    }


    @PostMapping("/new")
    @Transactional
    public String create(@Valid @ModelAttribute("member") MemberForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            return "members/new";
        }
        Member m = toEntity(form);

        memberService.join(m);
        return "redirect:/home";
    }

    /** 수정 폼 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member m = memberService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));
        model.addAttribute("member", toForm(m));
        model.addAttribute("teams", teamService.findAll());
        return "members/edit";
    }

    /** 수정 저장 */
    @PostMapping("/{id}/edit")
    @Transactional
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("member") MemberForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            return "members/edit";
        }
        Member m = memberService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));
        apply(m, form);
        memberService.update(m);
        return "redirect:/home";
    }

    /** 삭제 */
    @PostMapping("/{id}/delete")
    @Transactional
    public String delete(@PathVariable Long id) {
       memberService.delete(id);
        return "redirect:/home";
    }


    private Member toEntity(MemberForm f) {
        Team team = null;
        if (f.getTeamId() != null) {
            team = teamService.findById(f.getTeamId())
                    .orElseThrow(() -> new NoSuchElementException("팀 없음"));
        }
        ServiceInfo info = new ServiceInfo(
                f.getStatus() != null ? f.getStatus() : Status.ACTIVE,
                f.getStartDate(),
                f.getEndDate()
        );
        return new Member(f.getId(), f.getName(), team, info);
    }

    private MemberForm toForm(Member m) {
        MemberForm f = new MemberForm();
        f.setId(m.getId());
        f.setName(m.getName());
        if (m.getServiceInfo() != null) {
            f.setStatus(m.getServiceInfo().getStatus());
            f.setStartDate(m.getServiceInfo().getStartDate());
            f.setEndDate(m.getServiceInfo().getEndDate());
        }
        f.setTeamId(m.getTeam() != null ? m.getTeam().getId() : null);
        return f;
    }

    private void apply(Member m, MemberForm f) {
        m.setName(f.getName());
        if (m.getServiceInfo() == null) {
            m.setServiceInfo(new ServiceInfo());
        }
        m.getServiceInfo().setStatus(f.getStatus());
        m.getServiceInfo().setStartDate(f.getStartDate());
        m.getServiceInfo().setEndDate(f.getEndDate());

        if (f.getTeamId() != null) {
            Team t = teamService.findById(f.getTeamId())
                    .orElseThrow(() -> new NoSuchElementException("팀 없음"));
            m.setTeam(t);
        } else {
            m.setTeam(null);
        }
    }

    void JPAConstructor(){
       /* private final MemberService memberService;
        private final MemberRepository memberRepository;
        private final TeamRepository teamRepository;

         public MemberController(MemberService memberService,
                MemberRepository memberRepository,
                TeamRepository teamRepository) {
            this.memberService = memberService;
            this.memberRepository = memberRepository;
            this.teamRepository = teamRepository;
        }*/
    }
}