package counsel.core.Service;

import counsel.core.domain.member.Member;

import java.util.List;
import java.util.Optional;


public interface MemberService {

    Member join(Member member);

    Optional<Member> findById(Long id);
    List<Member> findAll();
    void update(Member m);
    void delete(Long id);
    public List<Member> findAllByTeamIdFetch(Long teamId);
}
