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

        // 1) Question simple
        poser(model, "Quelle est la capitale du Maroc ?");

        // 2) L'heure qu'il est
        poser(model, "Quelle heure est-il ?");

        // 3) Deux "questions" indépendantes : le modèle n'a pas de mémoire entre deux appels
        poser(model, "Bonjour, je m'appelle Hajar.");
        poser(model, "Quel est mon nom ?");

        // 4) Question du Test 5 SANS RAG (pour comparer avec la réponse du Test 5)
        poser(model, "Comment s'appelle le chat de Pierre ?");
    }

    private static void poser(ChatModel model, String question) {
        System.out.println("==================================================");
        System.out.println("Question : " + question);
        String reponse = model.chat(question);
        System.out.println("Réponse  : " + reponse);
    }
}