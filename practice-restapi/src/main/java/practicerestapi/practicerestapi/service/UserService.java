package practicerestapi.practicerestapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicerestapi.practicerestapi.domain.User;
import practicerestapi.practicerestapi.mapper.BoardMapper;
import practicerestapi.practicerestapi.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BoardMapper boardMapper;

    // 사용안함. => UserMapper의 서브쿼리로 대체
    public List<User> getAll() {
        List<User> userList = userMapper.getAll();
        if(userList != null && userList.size() > 0 ){
            for(User user: userList){
                user.setBoards(boardMapper.getByUserId(user.getId()));
            }
        }
        return userList;
    }
}
