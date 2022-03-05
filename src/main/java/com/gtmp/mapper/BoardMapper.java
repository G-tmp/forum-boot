package com.gtmp.mapper;

import com.gtmp.POJO.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BoardMapper {
    // list all Board
    List<Board> listAllBoard();

    //find Board via ename
    Board selectBoardByEname(@Param("ename") String ename);

    Board selectBoardById(@Param("id")Integer id);

    Integer insertBoard(Board board);

    Integer updateBoard(Board board);

}
