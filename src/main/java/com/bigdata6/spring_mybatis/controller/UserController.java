package com.bigdata6.spring_mybatis.controller;

import com.bigdata6.spring_mybatis.dto.PagingDto;
import com.bigdata6.spring_mybatis.dto.UserDto;
import com.bigdata6.spring_mybatis.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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
    public ModelAndView detail(
            @RequestParam(name="user_id")String userId,
            HttpServletResponse resp,
            ModelAndView model
        ) throws IOException {
        if(userId.trim().equals("")) resp.sendError(400);   // 아이디에 공백이 있으면 400에러
//        System.out.println(userId);
        UserDto user = userService.detail(userId);
//        System.out.println(user);
        model.addObject("user",user);
        model.setViewName("/user/detail");
        return model;
    }
    @GetMapping("/modify.do")
    public void modify(
            @RequestParam(name="user_id")String userId,
            Model model
    ) {
        UserDto user = userService.detail(userId);
        model.addAttribute("user",user);
    }
    @PostMapping("/modify.do")
    public String modify(UserDto user){
        int modify = 0;
        System.out.println(user);
        modify= userService.adminModify(user);
        if(modify>0){
            return "redirect:/user/detail.do?user_id="+user.getUser_id();
        } else {
            return "redirect:/user/modify.do?user_id="+user.getUser_id();
        }
    }
    @GetMapping("/delete.do")
    public String delete(@RequestParam(name="user_id")String userId){
        int delete = 0;
        delete= userService.remove(userId);
        if(delete>0){
            return "redirect:/user/list.do";
        } else {
            return "redirect:/user/modify.do?user_id" + userId;
        }
    }

}
