package com.bigdata6.spring_mybatis.mapper;

import com.bigdata6.spring_mybatis.dto.BoardDto;
import com.bigdata6.spring_mybatis.dto.PagingDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {
    @Autowired  // 컴포넌트로 정리하길 원해 BoardMapper에 @Mapper 정의
    private BoardMapper boardMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Test
    void findAll() {
//        System.out.println(boardMapper.findAll());
        // log.info(boardMapper.findAll().toString());
        // fetch.lazy의 트리거가 되기 때문에 board 출력시 무조건 user를 호출한다.
        boardMapper.findAll();
    }

    @Test
    void findPaging() {
        PagingDto paging = new PagingDto(1,5,"board_no","DESC");
        boardMapper.findPaging(paging);
    }

    @Test
    void count() {
        PagingDto paging = new PagingDto(1,5,"board_no","DESC");
        boardMapper.count(paging);
    }

    @Test
    void findById() {

    }

    @Test
    void deleteById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void updateViews() {
    }

    @Test
    void insert() {
        BoardDto board = new BoardDto();
        board.setTitle("테스트ddd");
        board.setUserId("user1000ddd");
        board.setContents("내용내용내뇽testtestt");
        boardMapper.insert(board);
        log.info(board.toString());
    }
}