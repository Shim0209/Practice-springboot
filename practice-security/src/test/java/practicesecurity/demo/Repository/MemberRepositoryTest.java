package practicesecurity.demo.Repository;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import practicesecurity.demo.domain.Member;
import practicesecurity.demo.domain.Role;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Commit
    public void 사용자등록() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("member@gmail.com");
        member.setPassword(passwordEncoder.encode("member"));
        member.setEnabled(true);
        member.setRole(Role.ROLE_MEMBER);

        Member manager = new Member();
        manager.setEmail("manager@gmail.com");
        manager.setPassword(passwordEncoder.encode("manager"));
        manager.setEnabled(true);
        manager.setRole(Role.ROLE_MANAGER);

        Member admin = new Member();
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEnabled(true);
        admin.setRole(Role.ROLE_ADMIN);

        // when
        memberRepository.save(member);
        memberRepository.save(manager);
        memberRepository.save(admin);

        //then
        Assert.assertEquals(member, memberRepository.findOne(member.getId()));
        Assert.assertEquals(manager, memberRepository.findOne(manager.getId()));
        Assert.assertEquals(admin, memberRepository.findOne(admin.getId()));
    }

    @Test
    @Commit
    public void 이메일조회() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("member@gmail.com");
        member.setPassword(passwordEncoder.encode("member"));
        member.setEnabled(true);
        member.setRole(Role.ROLE_MEMBER);

        // when
        memberRepository.save(member);

        // then
        Assert.assertEquals(member, memberRepository.findByEmail(member.getEmail()));
    }
}