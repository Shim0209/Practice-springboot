package practicerestapi.practicerestapi.domain;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private int id;
    private String email;
    private String password;
    private boolean enabled;
    private List<Board> boards;
}
