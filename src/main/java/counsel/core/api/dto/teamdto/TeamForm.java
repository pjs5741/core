package counsel.core.api.dto.teamdto;

import counsel.core.domain.Team.Team;
import jakarta.validation.constraints.NotNull;

public record TeamForm(

        Long id,
        @NotNull
        String name
){
    public static TeamForm from(Team team){
        return new TeamForm(team.getId(),team.getName());
    }
}
