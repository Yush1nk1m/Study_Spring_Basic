package yushin.yushinspringbasic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import yushin.yushinspringbasic.domain.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;    // Dependency Injection을 받을 순 없음

    // 생성자가 하나만 있으므로 @Autowired 생략하고도 DI 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {    // JdbcTemplate 객체의 생성자에 데이터 소스를 넘겨야 한다
        jdbcTemplate = new JdbcTemplate(dataSource);    // 파라미터 데이터 소스를 기반으로 새로운 JdbcTemplate 객체를 생성한다
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);   // 데이터베이스에 데이터를 삽입하기 위한 객체
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");      // 객체에 데이터를 삽입할 테이블과 자동 생성 키를 가진 컬럼을 명시

        Map<String, Object> parameters = new HashMap<>();       // 삽입할 데이터의 속성(매개변수)들을 매핑할 맵 객체 정의
        parameters.put("name", member.getName());               // 맵 객체의 원소로 속성 이름과 데이터 추가

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));     // 쿼리를 실행하고 키(id)를 반환한다
        member.setId(key.longValue());      // 회원 객체의 ID 설정

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {       // 데이터베이스의 쿼리 처리 결과로 나온 ResultSet 데이터의 특정 행을 추출하는 메소드
        return (rs, rowNum) -> {        // 익명 함수를 반환한다, 매개변수는 (ResultSet, int)
            // ResultSet의 정보로부터 멤버 객체를 설정
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            return member;  // 설정된 멤버 객체 반환
        };
    }
}
