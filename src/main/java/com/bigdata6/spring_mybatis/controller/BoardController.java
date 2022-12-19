package com.bigdata6.spring_mybatis.controller;

import com.bigdata6.spring_mybatis.dto.BoardDto;
import com.bigdata6.spring_mybatis.dto.PagingDto;
import com.bigdata6.spring_mybatis.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       PagingDto paging,
                       HttpServletRequest req){
        //  PagingDto(param) : page, rows, orderField, direct
        //  PagingDto(nav) : tatalRows(set), totalPage, startPage, endPage, nextPage, prewPage, next, prev
        if(paging.getOrderField()==null)paging.setOrderField("board_no");
        paging.setQueryString(req.getParameterMap());
        List<BoardDto> boardList = boardService.list(paging);
        model.addAttribute("boardList",boardList);
        model.addAttribute("paging",paging);
        return "/board/list";
    }
}
