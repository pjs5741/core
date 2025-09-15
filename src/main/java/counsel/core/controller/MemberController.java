package counsel.core.controller;

import counsel.core.Service.MemberService;
import counsel.core.Service.TeamService;
import counsel.core.domain.Team.Team;
import counsel.core.domain.member.Member;
import counsel.core.domain.member.MemberForm;
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
        model.addAttribute("member", new MemberForm()); // 기본 생성자 가능
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

        Team team = (form.getTeamId() != null)
                ? teamService.findById(form.getTeamId())
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
        model.addAttribute("member", MemberForm.from(m)); // 엔티티 -> 폼
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

        Team team = (form.getTeamId() != null)
                ? teamService.findById(form.getTeamId())
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
