package com.bigdata6.spring_mybatis.mapper;

import com.bigdata6.spring_mybatis.dto.PagingDto;
import com.bigdata6.spring_mybatis.dto.UserDto;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Component  //자동으로 객체를 생성하는것 Object처럼 부모같은 느낌
@Mapper //
public interface UserMapper extends CRUD<UserDto,String> {
    List<UserDto> findAll();
    UserDto findByUserIdAndPw(String userId, String pw);

    List<UserDto> findPaging(PagingDto paging);

    int count(PagingDto paging);

    UserDto findById(String id);

    int deleteById(String id);

    int updateById(UserDto dto);

    int insert(UserDto dto);
}
