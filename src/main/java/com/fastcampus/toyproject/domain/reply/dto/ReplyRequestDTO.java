package com.fastcampus.toyproject.domain.reply.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequestDTO {

    @NotBlank(message = "댓글을 채워주세요.")
    private String content;


}
