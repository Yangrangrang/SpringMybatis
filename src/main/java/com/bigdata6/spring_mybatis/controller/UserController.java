package com.bigdata6.spring_mybatis.controller;

import com.bigdata6.spring_mybatis.dto.PagingDto;
import com.bigdata6.spring_mybatis.dto.UserDto;
import com.bigdata6.spring_mybatis.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
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
//        System.out.println(paging);
        log.info(paging.toString());
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
            Model model,
            @SessionAttribute UserDto loginUser
    ) {
        UserDto user = userService.detail(userId);
        model.addAttribute("user",user);
    }
    @PostMapping("/modify.do")
    public String modify(UserDto user,
                         @SessionAttribute UserDto loginUser){
        int modify = 0;
        System.out.println(user);
        try {
            modify= userService.adminModify(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(modify>0){
            return "redirect:/user/detail.do?user_id="+user.getUser_id();
        } else {
            return "redirect:/user/modify.do?user_id="+user.getUser_id();
        }
    }
    @GetMapping("/delete.do")
    public String delete(@RequestParam(name="user_id")String userId,
                         @SessionAttribute UserDto loginUser){
        int delete = 0;
        try {
            delete= userService.remove(userId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(delete>0){
            return "redirect:/user/list.do";
        } else {
            return "redirect:/user/modify.do?user_id" + userId;
        }
    }
    @GetMapping("/register.do")
    public void register(@SessionAttribute UserDto loginUser) {

    }
    @PostMapping("/register.do")
    public String register(UserDto user,
                           @SessionAttribute UserDto loginUser){
        System.out.println(user);
        int register = 0;
        try{
            register=userService.register(user);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (register>0){
            return "redirect:/user/detail.do?user_id="+user.getUser_id();
        } else {
            return "redirect:/user/register.do";
        }
    }
    @GetMapping("/login.do")
    public void login(){

    }
    @PostMapping ("/login.do")
    public String login(@RequestParam(name="user_id") String userId,
                        String pw,
                        HttpSession session){
        System.out.println(userId);
        System.out.println(pw);
        UserDto user = userService.login(userId,pw);
        session.setAttribute("loginUser",user);
        if(user==null){
            return "redirect:/user/login.do";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout.do")
    public String logout(HttpSession session){
//        session.invalidate();   //전체 만료
        session.removeAttribute("loginUser");
        return "redirect:/";

    }
}
