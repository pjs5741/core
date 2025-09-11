/*
package counsel.core.Repository;

import counsel.core.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryMemberRepository implements MemberRepository {

private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);


    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(sequence.incrementAndGet());
        }
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable( store.get(memberId));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
*/
