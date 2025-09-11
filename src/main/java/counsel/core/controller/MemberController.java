package counsel.core.controller;

import counsel.core.Repository.MemberRepository;
import counsel.core.Repository.TeamRepository;
import counsel.core.Service.MemberService;
import counsel.core.domain.member.Member;
import counsel.core.domain.member.ConsultantState;
import counsel.core.domain.member.MemberForm;
import counsel.core.domain.member.ServiceInfo.ServiceInfo;
import counsel.core.domain.Team.Team;
import counsel.core.Service.MemberService;
import counsel.core.Repository.TeamRepository;
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
    private final MemberRepository memberRepository; // (업데이트 저장에 사용)
    private final TeamRepository teamRepository;

    public MemberController(MemberService memberService,
                            MemberRepository memberRepository,
                            TeamRepository teamRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    /** 신규 폼 */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("member", new MemberForm());
        model.addAttribute("teams", teamRepository.findAll());
        return "members/new";
    }

    /** 신규 저장 */
    @PostMapping("/new")
    @Transactional
    public String create(@Valid @ModelAttribute("member") MemberForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamRepository.findAll());
            return "members/new";
        }
        Member m = toEntity(form);
        // 당신의 MemberService는 join만 있을 수 있음 → join으로 persist/merge 둘 다 처리 가능
        memberService.join(m);
        return "redirect:/home";
    }

    /** 수정 폼 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member m = memberService.findMember(id)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));
        model.addAttribute("member", toForm(m));
        model.addAttribute("teams", teamRepository.findAll());
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
            model.addAttribute("teams", teamRepository.findAll());
            return "members/edit";
        }
        Member m = memberService.findMember(id)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));
        apply(m, form);                  // 엔티티에 값 반영
        memberRepository.save(m);        // JpaMemberRepository.save: id 있으면 merge
        return "redirect:/home";
    }

    /** 삭제 */
    @PostMapping("/{id}/delete")
    @Transactional
    public String delete(@PathVariable Long id) {
        memberRepository.findById(id).ifPresent(memberRepository::delete);
        return "redirect:/home";
    }

    /* ---------- 변환 유틸 ---------- */

    private Member toEntity(MemberForm f) {
        Team team = null;
        if (f.getTeamId() != null) {
            team = teamRepository.findById(f.getTeamId())
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
        // serviceInfo 없으면 생성
        if (m.getServiceInfo() == null) {
            m.setServiceInfo(new ServiceInfo());
        }
        m.getServiceInfo().setStatus(f.getStatus());
        m.getServiceInfo().setStartDate(f.getStartDate());
        m.getServiceInfo().setEndDate(f.getEndDate());

        if (f.getTeamId() != null) {
            Team t = teamRepository.findById(f.getTeamId())
                    .orElseThrow(() -> new NoSuchElementException("팀 없음"));
            m.setTeam(t);
        } else {
            m.setTeam(null);
        }
    }
}