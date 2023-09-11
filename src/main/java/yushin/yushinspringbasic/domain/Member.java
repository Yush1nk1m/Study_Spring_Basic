package yushin.yushinspringbasic.domain;

import javax.persistence.*;

// @Entity: JPA가 관리하는 개체라는 것을 표시한다
@Entity
public class Member {

    // @Id: 해당 멤버가 Primary Key임을 표시한다
    // @GeneratedValue: DB가 자동 생성(Identity strategy)해주는 값임을 표시한다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB에서 username이라는 이름을 가진 column에 데이터가 저장됨을 표시한다
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
