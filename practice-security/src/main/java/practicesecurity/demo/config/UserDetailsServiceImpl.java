package practicesecurity.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import practicesecurity.demo.Repository.MemberRepository;
import practicesecurity.demo.domain.Member;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member getMember = memberRepository.findByEmail(email);
        if(getMember == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }
        return new SecurityUser(getMember);
    }
}
