package practicerestapi.practicerestapi.mapper;

import org.apache.ibatis.annotations.*;
import practicerestapi.practicerestapi.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 신규유저 추가
     * @param user 신규유저 정보
     * @return 1 = 성공 / 0 = 실패
     */
    @Insert("INSERT INTO user(user_email, user_password) VALUES(#{user.email}, #{user.password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("user") User user);

    /**
     * 모든유저 조회
     * @return 모든 유저정보
     */
    @Select("SELECT * FROM user WHERE enabled=0")
    @Results(id="UserMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "user_email"),
            @Result(property = "password", column = "user_password"),
            @Result(property = "boards", column = "id", many = @Many(select = "practicerestapi.practicerestapi.mapper.BoardMapper.getByUserId"))
    })
    List<User> getAll();

    /**
     * 유저테이블 id로 유저조회
     * @param id 유저테이블 id
     * @return 해당유저정보
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    @ResultMap("UserMap")
    User getById(@Param("id") int id);

    /**
     * 유저정보 수정
     * @param user 수정할 유저정보
     * @return true = 성공 / false = 실패
     */
    @Update("UPDATE user SET user_email=#{user.email}, user_password=#{user.password} WHERE id=#{user.id}")
    boolean update(@Param("user") User user);

    /**
     * 유저정보 삭제
     * @param id 삭제할 유저의 테이블 id
     * @return true = 성공 / false = 실패
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    boolean deleteById(@Param("id") int id);

    /**
     * 유저 비활성화
     * @param id 비활성화할 유저의 테이블 id
     * @return true = 성공 / false = 실패
     * 서비스에서 해당유저에 관련된 모든 정보 비활성화 작업과 함께 사용할것.
     */
    @Update("UPDATE user SET enabled=false WHERE id = #{id}")
    boolean disabled(@Param("id") int id);
}
