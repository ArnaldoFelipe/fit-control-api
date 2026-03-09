package Evolua.application.arnold.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Evolua.application.arnold.ArnoldAiService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@Configuration
public class ArnoldConfig {
    
    @Bean
    public ChatLanguageModel chatLanguageModel(
        @Value("${langchain4j.open-ai.chat-model.api-key}") String apiKey,
        @Value("${langchain4j.open-ai.chat-model.base-url}") String baseUrl,
        @Value("${langchain4j.open-ai.chat-model.model-name}") String modelName){

            return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();
        }

    @Bean
    public ArnoldAiService arnoldAiService(ChatLanguageModel chatLanguageModel){
        return AiServices.builder(ArnoldAiService.class)
                .chatLanguageModel(chatLanguageModel)
                .build();
    }
}
