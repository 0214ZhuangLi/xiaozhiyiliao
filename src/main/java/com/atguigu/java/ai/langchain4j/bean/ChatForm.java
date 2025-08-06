package com.atguigu.java.ai.langchain4j.bean;


import lombok.Data;


/*
* 接收来自前端的消息
* */
@Data
public class ChatForm {

    private Long memoryId;

    private String message;
}
