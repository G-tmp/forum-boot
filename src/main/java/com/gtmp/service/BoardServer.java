package com.gtmp.service;

import com.gtmp.POJO.Board;
import com.gtmp.mapper.BoardMapper;
import com.gtmp.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BoardServer {

    @Autowired
    private BoardMapper boardMapper;


    @Autowired
    RedisTemplate redisTemplate;


    // list all board
    public List<Board> listAllBoard() {
//        redisTemplate.opsForValue();
        String redisKey = RedisKeyUtil.getBoardKey("all");
        if (!redisTemplate.hasKey(redisKey)) {
            List<Board> boards = boardMapper.listAllBoard();
            redisTemplate.opsForValue().set(redisKey, boards);
        }

        List<Board> boards = (List<Board>) redisTemplate.opsForValue().get(redisKey);
        return boards;
    }

    //find board via ename
    public Board selectBoardByEname(String ename) {
        String key = RedisKeyUtil.getBoardKey(ename);
        Board board = (Board) redisTemplate.opsForValue().get(key);
        if (board != null)
            return board;

        Board dbBoard = boardMapper.selectBoardByEname(ename);
        redisTemplate.opsForValue().set(key, dbBoard);
        return dbBoard;
    }

    public Board selectBoardById(Integer id) {
        return boardMapper.selectBoardById(id);
    }

    public Integer insertBoard(Board board) {
        String all = RedisKeyUtil.getBoardKey("all");
        String b = RedisKeyUtil.getBoardKey(board.getEname());

        redisTemplate.delete(all);
        redisTemplate.delete(b);
        return boardMapper.insertBoard(board);
    }

    public Integer updateBoard(Board board) {
        String all = RedisKeyUtil.getBoardKey("all");
        String b = RedisKeyUtil.getBoardKey(board.getEname());

        redisTemplate.delete(all);
        redisTemplate.delete(b);
        return boardMapper.updateBoard(board);
    }

}
