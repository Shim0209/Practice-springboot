package practicerestapi.practicerestapi.domain;

import lombok.Data;

import java.util.Date;


@Data
public class Board {
    private int id;
    private String title;
    private String content;
    private int userId;
    private boolean enabled;
    private Date registeredDate;
}
