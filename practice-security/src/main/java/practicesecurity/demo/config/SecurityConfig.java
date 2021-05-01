package practicesecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security.authorizeRequests()
                .antMatchers("/").permitAll()           // 누구나 접근 가능
                .antMatchers("/main").authenticated()   // main 패턴 스프링 시큐리티에 의해 로그인 되었어? 그럼 접근 가능
                .antMatchers("/member/**").authenticated()  // member/** 패턴 스프링 시큐리티에 의해 로그인 되었어? 그럼 접근 가능
                .antMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")  // manager/** 패턴은 ROLE_MANAGER, ROLE_ADMIN 권한 유지 접근 가능
                .antMatchers("/admin/**").hasRole("ADMIN"); // admin/** 패턴은 ROLE_ADMIM 권한 유저 접근 가능

        security.csrf().disable();

        security.formLogin()
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
