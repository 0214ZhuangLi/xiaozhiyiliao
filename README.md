# 小智医疗项目

小智医疗是一个基于 LangChain4j 构建的智能问诊与医疗预约平台，集成了大语言模型（LLM）、聊天记忆（Chat Memory）、工具函数调用（Function Calling）、RAG（检索增强生成）等能力，支持用户与智能体自然交流、预约挂号、持久化聊天记录等功能。

---

## 📁 数据库设计

本项目使用 MySQL 作为主数据库，当前的表结构如下：

```sql
create table appointment (
    id          bigint auto_increment primary key,
    username    varchar(50) not null,
    id_card     varchar(18) not null,
    department  varchar(50) not null,
    date        varchar(10) not null,
    time        varchar(10) not null,
    doctor_name varchar(50)
);
```

该表用于记录用户预约信息，包括用户名、身份证号、科室、预约时间、医生姓名等。

---

## 🔍 项目特色

### 1. LangChain4j 智能体（AiService）

使用 `@AiService` 快速封装 LLM 逻辑，无需手动处理底层消息解析与构造，专注业务开发：

```java
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel")
public interface Assistant {
    String chat(String userMessage);
}
```

支持通过 `动态代理` 实现自动封装输入（UserMessage）与输出（AiMessage），简化调用流程。

---

### 2. 聊天记忆（Chat Memory）

通过 `MessageWindowChatMemory` 组件实现上下文记忆，可隔离不同用户会话：

- 支持 memoryId 作为隔离维度
- 支持动态注入 ChatMemoryProvider
- 支持持久化存储（MongoDB）

---

### 3. 持久化聊天记忆（MongoDB）

使用 Spring Boot + MongoTemplate 实现聊天记录存储：

```java
@Document("chat_messages")
public class ChatMessages {
    @Id
    private ObjectId messageId;
    private String memoryId;
    private String content;
}
```

自定义实现 `ChatMemoryStore` 接口，通过 MongoDB 实现 get / update / delete 聊天历史记录，避免聊天上下文丢失。

---

### 4. 提示词工程（Prompt Engineering）

支持使用注解配置 System Prompt 和 User Prompt，例如：

```java
@SystemMessage("今天是{{current_date}}")
@UserMessage("你是医生助理，请帮我回答：{{it}}")
String chat(@MemoryId int memoryId, String userMessage);
```

支持从资源文件引入长提示词模板。

---

### 5. Function Calling（工具调用）

大模型不擅长数学或逻辑计算，可通过 `@Tool` 注解接入外部工具方法：

```java
@Tool(name = "sum", value = "求两个数的和")
double sum(double a, double b);
```

支持多个工具组合调用，增强大模型功能。

---

### 6. 检索增强生成 RAG

集成文档解析与向量数据库 Pinecone，流程如下：

1. 加载本地文档（.txt/.pdf/.docx等）
2. 分割为片段（DocumentSplitter）
3. 使用向量模型生成嵌入
4. 存入向量数据库（如 Pinecone）
5. 用户提问 → 检索相关内容 → 与问题一起发送给大模型回答

---

## 🧠 技术栈

- Java 17+
- Spring Boot 3.x
- LangChain4j
- MongoDB / Spring Data MongoDB
- MySQL
- Pinecone（向量数据库）
- Qwen / Ollama LLM 接入

---

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/your-org/xiaozhi-medical.git
cd xiaozhi-medical
```

### 2. 修改配置

配置文件 `application.properties` 中填写 MongoDB 和模型接入信息：

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/chat_memory_db
langchain4j.open-ai.chat-model.api-key=your-key
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

---

## 📚 示例测试代码

```java
@Autowired
private SeperateChatAssistant assistant;

@Test
public void testChatMemory() {
    String reply1 = assistant.chat(1, "我是小猪");
    String reply2 = assistant.chat(1, "我是谁？");
    System.out.println(reply1);
    System.out.println(reply2);
}
```

---

## 💡 未来规划

- [ ] 支持医生排班系统
- [ ] 支持用户健康档案问答
- [ ] 增强向量数据库性能（支持本地 Milvus）
- [ ] 接入医疗本体知识库，提升医学问答精准度

---

## 🤝 联系方式

如有建议或合作意向，请联系：`li570606551@gmail.com`

---

## 📄 License

该项目采用 MIT 协议开源，欢迎自由使用和修改。
