package practicesecurity.demo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import practicesecurity.demo.domain.Member;

@Getter @Setter
@ToString
public class SecurityUser extends User {

    private Member member;

    public SecurityUser(Member member) {
        super(member.getEmail(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));

        this.member = member;
    }
}
