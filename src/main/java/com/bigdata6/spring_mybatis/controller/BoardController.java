package com.bigdata6.spring_mybatis.controller;

import com.bigdata6.spring_mybatis.dto.*;
import com.bigdata6.spring_mybatis.service.BoardService;
import com.bigdata6.spring_mybatis.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;
    private ReplyService replyService;
    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${img.upload.path}")
    private String imgPath;
    public BoardController(BoardService boardService, ReplyService replyService) {
        this.boardService = boardService;
        this.replyService = replyService;
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
        log.info(paging.toString());

        return "/board/list";
    }
    @GetMapping("/{boardNo}/detail.do")
    public String detail(@PathVariable int boardNo,
                         PagingDto paging,
                         HttpServletRequest req,
                         Model model){
        //pathVariable : ??????????????? ????????????????????? ?????? ?????? ?????? ?????? ?????? ??????????????? ????????? ??????
        //pathVariable : required=true ??? ????????? ?????????.
        paging.setQueryString(req.getParameterMap());
        BoardDto board=boardService.detail(boardNo);
        List<ReplyDto> replyList=replyService.boardDetailList(boardNo,paging);
        model.addAttribute("board",board);
        model.addAttribute("replyList",replyList);
        model.addAttribute("paging",paging);
        return  "/board/detail";
    }
    @GetMapping("/register.do")
    public void register(@SessionAttribute UserDto loginUser){

    }
    @PostMapping("/register.do")
    public String register(
            BoardDto board,
            @SessionAttribute UserDto loginUser,
            @RequestParam(required = false, name = "imgFile") MultipartFile [] imgFiles    // ????????? ????????? ?????? List<MultipartFile> ??? ????????? ??? ??????
    ){
        int register = 0;
        if(loginUser.getUser_id().equals(board.getUserId())){
            try {
                List<BoardImgDto> boardImgList = new ArrayList<BoardImgDto>();
                for(MultipartFile imgFile : imgFiles){
                    if(imgFile!=null && !imgFile.isEmpty()){
                        String [] contentsTypes = imgFile.getContentType().split("/");
                        if(contentsTypes[0].equals("image")){
                            try{
                                String fileName="board"+System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentsTypes[1];//?????? ???????????? ??????
                                Path path = Paths.get(imgPath+"/"+fileName);
                                imgFile.transferTo(path);
                                BoardImgDto boardImg = new BoardImgDto();
                                boardImg.setImgPath(fileName);
                                boardImgList.add(boardImg);
                            } catch (Exception e){
                                log.error(e.getMessage());
                            }
                        }
                    }
                }
                board.setBoardImgList(boardImgList);
                register = boardService.register(board);
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
//        log.info(board.toString());
        if(register>0){
            return "redirect:/board/"+board.getBoardNo()+"/detail.do";    //
        } else {
            return "redirect:/board/register.do";
        }
    }
}
