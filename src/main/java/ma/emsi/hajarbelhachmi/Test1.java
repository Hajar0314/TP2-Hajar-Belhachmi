package ma.emsi.hajarbelhachmi;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

/**
 * Test 1 : pour se chauffer.
 */
public class Test1 {

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_KEY");
        if (llmKey == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-3.5-flash")
                .temperature(0.7)
                .build();

        // 5) Question météo SANS outils (Test 7)
        poser(model, "Je dois partir dans 2 jours à Paris ; j'y resterai 3 jours. Est-ce que je dois mettre un parapluie dans mes valises ?");
    }

    private static void poser(ChatModel model, String question) {
        System.out.println("==========S========================================");
        System.out.println("Question : " + question);
        String reponse = model.chat(question);
        System.out.println("Réponse  : " + reponse);
    }
}