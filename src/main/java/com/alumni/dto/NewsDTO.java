package com.alumni.dto;

import lombok.Data;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String category;
    private String summary;
    private String content;
    private String coverImage;
    private Integer status;
}
