package practicerestapi.practicerestapi.domain;

import lombok.Data;

@Data
public class User {
    private int id;
    private String email;
    private String password;
    private boolean enabled;
}
