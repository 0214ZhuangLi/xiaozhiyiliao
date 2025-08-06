package com.atguigu.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
    * 插入一个文档
    * */
    @Test
    public void testInsert1(){
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("聊天记录内容");
        mongoTemplate.insert(chatMessages);
    }

    @Test
    public void testFindById(){
        ChatMessages chatMessages = mongoTemplate.findById("688b15c0c66bd657a4439c96",ChatMessages.class);
        System.out.println(chatMessages);
    }


    /*
    * 修改或者新增文档，id存在则是修改操作，id不存在则是新增操作。
    * */
    @Test
    public void testUpdate(){
        Criteria criteria = Criteria.where("_id").is("688b15c0c66bd657a4439c96");
        Query query= new Query(criteria);
        Update update = new Update();
        update.set("content","新的聊天记录内容");

        mongoTemplate.upsert(query,update,ChatMessages.class);
    }


    /*
    * 删除一个文档
    * */

    @Test
    public void testDelete(){
        Criteria criteria = Criteria.where("_id").is("688b19356b572744a439ee94");
        Query query = new Query(criteria);
        mongoTemplate.remove(query,ChatMessages.class);
    }

}
