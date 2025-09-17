package counsel.core.controller;

import counsel.core.Service.MemberService;
import counsel.core.Service.TeamService;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import counsel.core.api.dto.memberdto.MemberForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;

    public MemberController(MemberService memberService, TeamService teamService) {
        this.memberService = memberService;
        this.teamService = teamService;
    }

    /** 생성 폼 */
    @GetMapping("/new")
    public String newForm(Model model) {


        MemberForm memberForm = new MemberForm(1L,"홍길동",null,null,null,null);

        model.addAttribute("member", memberForm);
        model.addAttribute("teams", teamService.findAll());
        return "members/new";
    }

    /** 생성 저장 */
    @PostMapping("/new")
    @Transactional
    public String create(@Valid @ModelAttribute("member") MemberForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            return "members/new";
        }

        Team team = (form.teamId() != null)
                ? teamService.findById(form.teamId())
                .orElseThrow(() -> new NoSuchElementException("팀 없음"))
                : null;

        Member m = form.toEntity(team);
        memberService.join(m);
        return "redirect:/home";
    }

    /** 수정 폼 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member m = memberService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        MemberForm form = MemberForm.from(m);

        System.out.println("DEBUG MemberId=" + form.id());
        System.out.println("DEBUG startDate=" + form.startDate());
        System.out.println("DEBUG endDate=" + form.endDate());


        model.addAttribute("member", form);
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

        Team team = (form.teamId() != null)
                ? teamService.findById(form.teamId())
                .orElseThrow(() -> new NoSuchElementException("팀 없음"))
                : null;

        form.applyTo(m, team);
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
}
