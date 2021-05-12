package practicerestapi.practicerestapi.controller;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practicerestapi.practicerestapi.domain.User;
import practicerestapi.practicerestapi.mapper.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("")
    public int post(@RequestBody User user) {
        return userMapper.insert(user);
    }

    @GetMapping("")
    public List<User> getAll(){
        return userMapper.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") int id){
        return userMapper.getById(id);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody User user) {
        return userMapper.update(user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable("id") int id){
        return userMapper.deleteById(id);
    }
}
