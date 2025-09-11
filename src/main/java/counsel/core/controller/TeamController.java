package counsel.core.controller;

import counsel.core.domain.Team.Team;
import counsel.core.Service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 등록 폼
    @GetMapping("/teams/new")
    public String newForm() {
        return "teams/form";  // templates/teams/form.html
    }


    @PostMapping("/teams/new")
    public String create(TeamForm form) {
        String name = form.getName();
        if (name != null && !name.trim().isEmpty()) {
            Team team = new Team(name.trim());
            teamService.join(team);
        }
        return "redirect:/home";
    }

    @GetMapping("/teams/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("team", teamService.findById(id));
        return "teams/editform";
    }

    @PostMapping("/teams/{id}/edit")
    public String update(@PathVariable Long id, @RequestParam String name) {
        teamService.update(id, name);
        return "redirect:/home";
    }

    @PostMapping("/teams/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        teamService.deleteTeamSafely(id);   // 안전 삭제 호출
        ra.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/home";
    }
}