package yushin.yushinspringbasic.repository;

import org.springframework.stereotype.Repository;
import yushin.yushinspringbasic.domain.Member;

import java.util.*;

//@Repository     // 스프링 컨테이너가 찾아서 리포지토리를 등록할 수 있게 함
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();   // 실무에서는 ConcurrentHashMap을 활용하여 동시성 문제를 해결해야 함
    private static long sequence = 0L;      // key 값을 생성해 주는 변수, 실무에서는 AtomicLong을 활용하여 동시성 문제를 해결해야 함
    @Override
    public Member save(Member member) {
        member.setId(++sequence);           // id 설정
        store.put(member.getId(), member);  // 메모리 저장소에 저장(사상)
        return member;                      // 저장된 객체 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }

}
