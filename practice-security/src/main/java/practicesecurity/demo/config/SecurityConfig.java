package practicesecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity // 필터 체인에 등록 (스프링 시큐리티 활성화)
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")을 메서드에 걸어서 (함수시작전 수행) 여러 권한을 간편하게 그리고 한번에 설정 할 수 있다.
// 특정 주소 접근시 권한 및 인증을 메서드 수행전 체크
// @securedEnabled = true 요즘에는 이걸 많이 사용한다??
// 위와 같은건 간단하게 메서드 하나에만 설정할 때 사용하는 것.
// 글로벌 설정은 SecurityConfig를 통해서 설정할 것.
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /* 스프링시큐리티 앞단 설정들을 할 수 있다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 여기에 접근 허용 설정을 해버리면 HttpSecurity 설정을 전부 무시해버린다.
    }
    */

    /**
     *  스프링시큐리티의 설정을 할 수 있다.
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security.authorizeRequests()
                .antMatchers("/").permitAll()           // 누구나 접근 가능
                .antMatchers("/main").authenticated()   // main 패턴 스프링 시큐리티에 의해 로그인 되었어? 그럼 접근 가능
                .antMatchers("/member/**").authenticated()  // member/** 패턴 스프링 시큐리티에 의해 로그인 되었어? 그럼 접근 가능
                .antMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")  // manager/** 패턴은 ROLE_MANAGER, ROLE_ADMIN 권한 유지 접근 가능
                .antMatchers("/admin/**").hasRole("ADMIN"); // admin/** 패턴은 ROLE_ADMIM 권한 유저 접근 가능

        security.csrf().disable();

        security.formLogin() //로그인 페이지를 제공하는 URL을 설정
                .loginPage("/login").defaultSuccessUrl("/main", true)  // 로그인 페이지와 성공했을때 어디로 넘길래?
                .loginProcessingUrl("/loginAction").defaultSuccessUrl("/main", true);    // 로그인 액션 프로세스 반드시 POST 그리고 성공시 URL

        security.exceptionHandling().accessDeniedPage("/accessDenied"); // 권한 없을때 접근하였을 경우

        security.logout().logoutUrl("/logout").logoutSuccessUrl("/");   // 로그아웃 URL

        security.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
