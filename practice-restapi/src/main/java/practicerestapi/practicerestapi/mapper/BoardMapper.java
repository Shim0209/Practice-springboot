package practicerestapi.practicerestapi.mapper;

import org.apache.ibatis.annotations.*;
import practicerestapi.practicerestapi.domain.Board;

import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 게시물 등록
     * @param board 등록할 게시물 정보
     * @return 1 = 성공 / 0 = 실패
     */
    @Insert("INSERT INTO board(board_title, board_content, user_id, registed_date) " +
            "VALUES(#{board.title},#{board.content},#{board.userId},#{board.registedDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("board") Board board);

    /**
     * 모든 게시물 조회
     * @return 모든 게시물정보
     */
    @Select("SELECT * FROM board")
    @Results(id = "BoardMap", value = {
            @Result(property = "title", column = "board_title"),
            @Result(property = "content", column = "board_content"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "registedDate", column = "registed_date")
    })
    List<Board> getAll();

    /**
     * 유저아이디로 게시물 조회
     * @param userId 조회할 유저의 id
     * @return 해당 유저가 작성한 게시물정보
     */
    @Select("SELECT * FROM board WHERE user_id = #{id}")
    @ResultMap("BoardMap")
    List<Board> getByUserId(@Param("id") int userId);

    /**
     * 게시물 수정
     * @param board 수정할 게시물정보
     * @return true = 성공 / false = 실패
     */
    @Update("UPDATE board SET board_title=#{board.title}, board_content=#{board.content} " +
            "WHERE id = #{board.id}")
    boolean update(@Param("board") Board board);

    /**
     * 게시물 비활성화
     * @param id 비활성화할 게시물의 테이블 id
     * @return true = 성공 / false = 실패
     */
    @Update("UPDATE board SET enabled=true WHERE id = #{id}")
    boolean disabled(@Param("id") int id);

    /**
     * 게시물 삭제
     * @param id 삭제할 게시물의 테이블 id
     * @return true = 성공 / false = 실패
     */
    @Delete("DELETE FROM board WHERE id=#{id}")
    boolean deleteById(@Param("id") int id);
}
