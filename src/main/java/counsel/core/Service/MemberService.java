package counsel.core.Service;

import counsel.core.domain.member.Member;

import java.util.List;
import java.util.Optional;


public interface MemberService {

    void join(Member member);

    Optional<Member> findMember(Long memberId);
    List<Member> findAll();
}
