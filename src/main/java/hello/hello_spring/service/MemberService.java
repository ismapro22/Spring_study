package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service //자바코드로 직접 빈 등록하여 주석 처리
@Transactional //JPA를 통한 데이터 변경은 트랜잭션 안에서 실행해야 함
//회원가입에만 쓸 거니 join에만 달아도 뭐 ㄱㅊ
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired private MemberRepository memberRepository; //필드주입

//    @Autowired //자바코드로 직접 빈 등록하여 주석 처리
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }//생성자 주입

//setter는 호출에 열려있다는 단점이 존재함
//    private MemberRepository memberRepository;
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


    //회원 가입
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원
        validateDuplicateMember(member); //회원 중복 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //이미 값이 존재하면
                    throw new IllegalStateException("이미 존재하는 회원임");
                });
    }


//전체 회원 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}