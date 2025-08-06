package com.atguigu.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.assistant.Assistant;
import com.atguigu.java.ai.langchain4j.assistant.ChatMemoryAssistant;
import com.atguigu.java.ai.langchain4j.assistant.SeperateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatMemoryTest {
    @Autowired
    QwenChatModel qwenChatModel;
    @Test
    public void testChatMemory1(){
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        Assistant assistant = AiServices
                .builder(Assistant.class)
                .chatLanguageModel(qwenChatModel)
                .chatMemory(chatMemory)
                .build();

        String answer1 = assistant.chat("我是小猪");
        String answer2 = assistant.chat("我是谁？");
        System.out.println(answer1);
        System.out.println(answer2);
    }



    @Autowired
    private ChatMemoryAssistant chatMemoryAssistant;
    @Test
    public void testChatMemory2(){


        String answer1 = chatMemoryAssistant.chat("我是小猪");
        String answer2 = chatMemoryAssistant.chat("我是谁？");
        System.out.println(answer1);
        System.out.println(answer2);
    }


    @Autowired
    private SeperateChatAssistant seperateChatAssistant;
    @Test
    public void testChatMemory3(){


        String answer1 = seperateChatAssistant.chat(1,"我是小猪");
        String answer2 = seperateChatAssistant.chat(1,"我是谁？");
        String answer3 = seperateChatAssistant.chat(2,"我是谁？");
        System.out.println(answer1);
        System.out.println(answer2);
        System.out.println(answer3);
    }

    @Test
    public void testChatMemory4(){


        String answer1 = seperateChatAssistant.chat(1,"我是小猪");
        String answer2 = seperateChatAssistant.chat(1,"我是谁？");
        String answer3 = seperateChatAssistant.chat(2,"我是小鸟");
        System.out.println(answer1);
        System.out.println(answer2);
        System.out.println(answer3);
    }

    @Test
    public void testChatMemory5(){

        String answer1 = seperateChatAssistant.chat(1,"我是谁？");
        String answer2 = seperateChatAssistant.chat(2,"我是谁？");
        System.out.println(answer1);
        System.out.println(answer2);
    }

    @Test
    public void testChatMemory6(){

        String answer1 = seperateChatAssistant.chat(3,"今天是几月几日");

        System.out.println(answer1);

    }
}
