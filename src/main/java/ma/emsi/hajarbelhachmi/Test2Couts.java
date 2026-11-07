package ma.emsi.hajarbelhachmi;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.output.TokenUsage;

/**
 * Test 2 : coût en tokens d'une requête.
 */
public class Test2Couts {

    // Prix en dollars pour 1 million de tokens (gemini-3.5-flash-lite, au 21/10/2026)
    private static final double PRIX_ENTREE = 0.30;
    private static final double PRIX_SORTIE = 2.50;

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_KEY");
        if (llmKey == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        // Même question que dans le Test 1
        String question = "Quelle est la capitale du Maroc ?";
        ChatResponse response = model.chat(UserMessage.from(question));

        System.out.println("Réponse : " + response.aiMessage().text());

        TokenUsage usage = response.tokenUsage();
        int entree = usage.inputTokenCount();
        int sortie = usage.outputTokenCount();
        System.out.println("Tokens en entrée : " + entree);
        System.out.println("Tokens en sortie : " + sortie);
        System.out.println("Tokens total     : " + usage.totalTokenCount());

        double cout = (entree * PRIX_ENTREE + sortie * PRIX_SORTIE) / 1_000_000.0;
        System.out.printf("Coût de la requête : %.8f $%n", cout);
        System.out.printf("Nombre de requêtes similaires pour dépenser 1 $ : %.0f%n", 1.0 / cout);
    }
}
