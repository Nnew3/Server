package mju.nnews3.api.dto;

import java.util.Date;

public record NewsRes (
        Long id,
        String title,
        String content,
        String publisher,
        String newsTime,
        String imgUrl
){
}
