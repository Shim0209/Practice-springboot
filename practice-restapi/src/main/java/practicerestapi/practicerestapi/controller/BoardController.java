package practicerestapi.practicerestapi.controller;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practicerestapi.practicerestapi.domain.Board;
import practicerestapi.practicerestapi.mapper.BoardMapper;
import practicerestapi.practicerestapi.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private BoardService boardService;

    @PostMapping("")
    public int post(@RequestBody Board board) {
        return boardService.addNewBoard(board);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Board board){
        return boardMapper.update(board);
    }

    @PostMapping("/disabled")
    public boolean disabled(@RequestBody Board board) {
        return boardMapper.disabled(board.getId());
    }

    @GetMapping("")
    public List<Board> getAll() {
        return boardMapper.getAll();
    }

    @GetMapping("/{id}")
    public List<Board> getByUserId(@PathVariable("id") int id){
        return boardMapper.getByUserId(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable("id") int id) {
        return boardMapper.deleteById(id);
    }

}
