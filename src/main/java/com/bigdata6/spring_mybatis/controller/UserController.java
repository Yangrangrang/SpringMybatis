package com.bigdata6.spring_mybatis.controller;

import com.bigdata6.spring_mybatis.dto.PagingDto;
import com.bigdata6.spring_mybatis.dto.UserDto;
import com.bigdata6.spring_mybatis.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("list.do")
    public String list(
            //@RequestParam(defaultValue = "1") int page,
            //@RequestParam(defaultValue = "10") int rows,
            //@RequestParam(defaultValue = "user_id") String orderField,
            //@RequestParam(defaultValue = "DESC") String direct,
            PagingDto paging,
            Model model,
            HttpServletRequest req){
//        System.out.println(page+","+rows);
//        PagingDto pagingDto = new PagingDto(page,rows,orderField,direct);
//        if(paging.getOrderField()==null)paging.setOrderField("user_id");      //다이나믹 쿼리로 처리
        paging.setQueryString(req.getParameterMap());
        System.out.println(paging);
        List<UserDto> userDtoList = userService.list(paging);
        model.addAttribute("userList",userDtoList);
        model.addAttribute("paging",paging);
        return "/user/list";
    }
    @GetMapping("/detail.do")
    public String detail(){

        return "/user/detail";
    }
}
