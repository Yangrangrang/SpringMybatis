package com.bigdata6.spring_mybatis.mapper;

import com.bigdata6.spring_mybatis.dto.BoardImgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardImgMapper {
    List<BoardImgDto> findByBoardNo(int boardNo);

    BoardImgDto findOne(int boardImgNo);    // 게시글 수정에서 삭제할 이미지를 찾을 떄

    int deleteOne(int boardImgNo);

    int insertOne(BoardImgDto dto);
}
