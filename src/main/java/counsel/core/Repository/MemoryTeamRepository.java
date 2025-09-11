/*
package counsel.core.Repository;

import counsel.core.domain.Team.Team;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryTeamRepository implements TeamRepository {
    private Map<Long, Team> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Team save(Team team) {
        if (team.getId() == null) {
            team.setId(sequence.incrementAndGet());
        }
        store.put(team.getId(), team);
        return team;
    }

    @Override
    public Optional<Team> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Team findByName(String name) {
        return store.values().stream()
                .filter(t -> t.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public List<Team> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}*/
