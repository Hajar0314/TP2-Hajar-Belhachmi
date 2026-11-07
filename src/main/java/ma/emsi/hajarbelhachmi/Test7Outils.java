package ma.emsi.hajarbelhachmi;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import ma.emsi.hajarbelhachmi.outil.meteo.MeteoTool;

import java.util.Scanner;

/**
 * Test 7 : utilisation d'outils (météo) par le LLM, avec logging des requêtes et réponses.
 */
public class Test7Outils {

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_KEY");
        if (llmKey == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-3.5-flash")
                .temperature(0.3)
                .logRequestsAndResponses(true)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20)) // L'utilisation des outils génère des messages
                        .tools(new MeteoTool())  // Ajout de l'outil
                        .build();

        conversationAvec(assistant);
    }

    private static void conversationAvec(AssistantMeteo assistant) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();
                if (question.isBlank()) {
                    continue;
                }
                System.out.println("==================================================");
                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }
}