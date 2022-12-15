package com.bigdata6.spring_mybatis.service;

import com.bigdata6.spring_mybatis.dto.PagingDto;
import com.bigdata6.spring_mybatis.dto.UserDto;
import com.bigdata6.spring_mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// SPRING BEAN 컨테이너(팩터리)에서 관히 받는다. @Component(최상위 컨테이너) > 그중에 @Mapper, @Service(명시적)
@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserMapper userMapper;

    public UserServiceImp (UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public List<UserDto> listAllTest() {
        return userMapper.findAll();
//        PagingDto page = new PagingDto(10,5, "user_id","DESC");
//        return userMapper.findPaging(page);
    }

    @Override
    public int signup(UserDto user) {
        return 0;
    }

    @Override
    public int register(UserDto user) {
        return 0;
    }

    @Override
    public int userModify(UserDto user) {
        return 0;
    }

    @Override
    public int adminModify(UserDto user) {
        return 0;
    }

    @Override
    public UserDto login(String id, String pw) {
        return null;
    }

    @Override
    public List<UserDto> list(PagingDto paging) {
        return null;
    }

    @Override
    public UserDto detail(String id) {
        return null;
    }

    @Override
    public int idCheck(String id) {
        return 0;
    }
}