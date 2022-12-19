package com.bigdata6.spring_mybatis.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {
    @Autowired  // 컴포넌트로 정리하길 원해 BoardMapper에 @Mapper 정의
    private BoardMapper boardMapper;
    @Test
    void findAll() {
        System.out.println(boardMapper.findAll());
    }

    @Test
    void findPaging() {
    }

    @Test
    void count() {
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
    }
}