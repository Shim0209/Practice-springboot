package practicesecurity.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import practicesecurity.demo.config.SecurityUser;
import practicesecurity.demo.domain.KakaoProfile;
import practicesecurity.demo.domain.OAuthToken;

import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoLogin", "https://kauth.kakao.com/oauth/authorize?client_id=f06d8c3ed860de56bd565436c57cc13a\n" +
                "&redirect_uri=http://localhost:8080/login/oauth2/callback&response_type=code&scope=account_email,gender,age_range,birthday");
        return "login";
    }

    @GetMapping("/login/oauth2/callback")
    @ResponseBody
    public String kakaoCallback(String code) throws JsonProcessingException {
        // http요청을 편히 할수있는 라이브러리
        RestTemplate rt = new RestTemplate();

        // HttpHeader 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","f06d8c3ed860de56bd565436c57cc13a");
        params.add("redirect_uri","http://localhost:8080/login/oauth2/callback");
        params.add("code",code);

        // HttpHeader와 HttpBody를 하나의 객체에 담기 (RestTemplate의 exchange함수가 HttpEntity타입 파라미터를 받기 때문에)
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청(Post) 및 응답받기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",  // 요청 주소
                HttpMethod.POST,                            // 요청 방식
                kakaoTokenRequest,                          // 요청 데이터
                String.class                                // 응답받을 타입
        );

        // 응답받은 토큰객체를 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);


        // 사용자 정보 조회
        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, Object>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",  // 요청 주소
                HttpMethod.POST,                            // 요청 방식
                kakaoProfileRequest,                          // 요청 데이터
                String.class                                // 응답받을 타입
        );

        KakaoProfile kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);

        System.out.println(kakaoProfile.getKakao_account().getEmail());

        return response2.getBody();
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
