package com.atguigu.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.assistant.SeperateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsTest {
    @Autowired
    private SeperateChatAssistant seperateChatAssistant;
    @Test
    public void testCalculatorTools() {

        String answer = seperateChatAssistant.chat(1,"1+2等于几，475695037565的平方根是多\n" +
                "少？");
        //答案：3，689706.4865
        System.out.println(answer);
    }
}