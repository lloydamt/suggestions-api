package com.lamt.suggestionsapi.mapper;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.model.CommentDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto mapToDto(Comment entity);

    Comment mapToEntity(CommentDto dto);

    List<CommentDto> mapAllToDto(List<Comment> entities);
}
