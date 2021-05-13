package practicerestapi.practicerestapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practicerestapi.practicerestapi.domain.Board;
import practicerestapi.practicerestapi.domain.User;
import practicerestapi.practicerestapi.mapper.BoardMapper;
import practicerestapi.practicerestapi.mapper.UserMapper;

import java.util.Date;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int addNewBoard(Board board){
        /* 유저 체크 */
        User searchedUser = userMapper.getById(board.getUserId());
        if(searchedUser == null) {
            return 0;
        }
        /* 등록날짜 주입 */
        board.setRegisteredDate(new Date());
        return boardMapper.insert(board);
    }
}
