package hello.hello_spring;

import hello.hello_spring.repository.JdbcMemberRepository;
import hello.hello_spring.repository.JdbcTemplateMemberRepository;
import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

//    private final DataSource dataSource;
//    private EntityManager entityManager;
    private final MemberRepository memberRepository;

    @Autowired//하나면 생략 가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    @Autowired//스프링데이터JPA로 인한 변경
//    public SpringConfig(DataSource dataSource, EntityManager entityManager){
////        this.dataSource = dataSource;
//        this.entityManager = entityManager;
//    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
//        return new MemberService(memberRepository());//스프링데이터JPA로 인한 변경
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new JpaMemberRepository(entityManager);//스프링데이터 JPA 쓰니까 지움. 어차피 구현체 자동으로 만들어줌
//        return new JdbcTemplateMemberRepository(dataSource);//JPA 쓰게 지움
//        return new JdbcMemberRepository(dataSource);//DB에 넣게 지움
//        return new MemoryMemberRepository();
//    }
}