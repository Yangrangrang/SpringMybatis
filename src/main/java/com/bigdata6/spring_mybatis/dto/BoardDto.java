package com.bigdata6.spring_mybatis.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BoardDto {
    private int boardNo; //board_no
    private String title;   //title
    private String contents;    //contents
    private Date postTime;    //post_time
    private String userId;   //user_id  // fetch.lazy에서 User를 호출하지 않기 위해
    private UserDto user;   // Board : User = N : 1  (fk user_id)
    private int views;   //views
    private List<ReplyDto> replyList;
    private List<BoardImgDto> boardImgList;
}
