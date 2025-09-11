package counsel.core.domain.Team;


import jakarta.persistence.*;

@Entity
@Table(name = "teams", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        private String name;

    protected Team() {}
    public Team(String name) {
        this(null, name);
    }

    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
