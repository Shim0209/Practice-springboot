package practicesecurity.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import practicesecurity.demo.config.SecurityUser;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main(Authentication authentication, ModelMap modelMap) {

        // authentication.getPrincipal()로 인증된 사용자 정보를 얻을 수 있다.
        SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
        modelMap.addAttribute("user", securityUser);

        return "main";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
