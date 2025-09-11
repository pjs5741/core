package counsel.core.Repository;

import counsel.core.domain.Team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team,Long> {
    boolean existsByName(String name);
   /* Team save(Team team);
    Optional<Team>  findById(Long id);
    Team findByName(String name);
    List<Team> findAll();
    void deleteById(Long id);*/
}
