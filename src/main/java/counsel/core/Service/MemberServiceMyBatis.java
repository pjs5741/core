package counsel.core.Service;

import counsel.core.domain.member.Member;
import counsel.core.mapper.MemberMapper;
import counsel.core.mapper.TeamMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceMyBatis implements MemberService{

    private final MemberMapper memberMapper;

    public MemberServiceMyBatis(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Member join(Member member) {
        if (member == null || member.getName() == null || member.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("팀명이 비어 있습니다.");
        }
        member.setName(member.getName().trim());
        memberMapper.insert(member);
        return member;
    }



    @Override
    public Optional<Member> findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public void update(Member m) {
        memberMapper.update(m);
    }

    @Override
    public void delete(Long id) {
        memberMapper.findById(id)
                .ifPresent(m -> memberMapper.delete(m.getId()));
    }

    @Override
    public List<Member> findAllByTeamIdFetch(Long teamId) {
        return memberMapper.findAllByTeamIdFetch(teamId);
    }
}
