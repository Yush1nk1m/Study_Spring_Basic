package yushin.yushinspringbasic.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import yushin.yushinspringbasic.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;    // 데이터가 존재하는 근원지가 스프링에 의해 객체로 주입된다

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;   // 스프링이 주입해준 데이터 소스를 주입한다
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";      // SQL 명령어에서 실제로 받은 데이터가 위치할 values의 parameter를 ?로 비운다

        Connection conn = null;     // 데이터베이스와의 연결 정보를 담는 객체
        PreparedStatement pstmt = null;     // 데이터베이스에 줄 쿼리를 담는 객체
        ResultSet rs = null;        // 쿼리 실행 결과를 담는 객체

        try {   // 데이터베이스에 줄 쿼리가 잘못되었을 경우를 대비한 예외처리 로직이 반드시 필요하다
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);    // 쿼리 실행 후 생성된 키(id)를 반환하게 한다

            pstmt.setString(1, member.getName());   // 쿼리의 parameter를 회원의 이름으로 설정한다

            pstmt.executeUpdate();      // 쿼리를 데이터베이스에 실행한다
            rs = pstmt.getGeneratedKeys();      // 쿼리 실행 결과를 객체에 저장한다

            if (rs.next()) {        // 결과로 받은 객체가 존재하면
                member.setId(rs.getLong(1));    // 그 결과의 첫 번째 열(id)를 회원 객체의 id로 저장한다
            } else {                // 결과로 받은 객체가 존재하지 않으면
                throw new SQLException("ID 조회 실패");     // 쿼리가 실패했다는 의미이므로 예외 메시지를 출력한다
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);     // 쿼리의 성공 여부와 상관없이 일이 끝나면 연결을 종료한다
        }

    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";   // 파라미터가 있는 쿼리 문자열 선언

        Connection conn = null;             // 연결 객체 선언
        PreparedStatement pstmt = null;     // 쿼리 객체 선언
        ResultSet rs = null;                // 결과 객체 선언

        try {
            conn = getConnection();                 // 연결 성립
            pstmt = conn.prepareStatement(sql);     // 파라미터가 있는 쿼리를 준비
            pstmt.setLong(1, id);       // 파라미터를 메소드의 매개변수 id로 대체

            rs = pstmt.executeQuery();      // 데이터베이스에 쿼리 실행하고 결과 저장

            if (rs.next()) {        // 결과가 존재하면
                Member member = new Member();                       // 새로운 회원 객체 생성
                member.setId(rs.getLong("id"));         // 열 이름이 id인 열의 값 가져와서 저장
                member.setName(rs.getString("name"));   // 열 이름이 name인 열의 값 가져와서 저장

                return Optional.of(member);     // nullable인 회원 객체 반환
            } else {                // 결과가 존재하지 않으면
                return Optional.empty();    // null 반환
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";     // 파라미터가 있는 쿼리 문자열 선언

        Connection conn = null;             // 연결 객체 선언
        PreparedStatement pstmt = null;     // 쿼리 객체 선언
        ResultSet rs = null;                // 결과 객체 선언

        try {
            conn = getConnection();     // 데이터베이스와 연결
            pstmt = conn.prepareStatement(sql);     // 연결 객체에 쿼리를 연결
            pstmt.setString(1, name);   // 쿼리의 파라미터를 회원 이름으로 수정

            rs = pstmt.executeQuery();      // 쿼리를 실행하고 결과를 결과 객체에 저장

            if (rs.next()) {        // 결과가 존재하면
                Member member = new Member();   // 새로운 멤버 객체 선언
                member.setId(rs.getLong("id"));     // 결과의 행에서 id 열의 데이터를 추출
                member.setName(rs.getString("name"));   // 결과의 행에서 name 열의 데이터를 추출
                return Optional.of(member);     // nullable 멤버 객체를 반환
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";    // 쿼리

        Connection conn = null;             // 연결 객체
        PreparedStatement pstmt = null;     // 쿼리 객체
        ResultSet rs = null;                // 결과 객체

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }

            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
