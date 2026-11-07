package ma.emsi.hajarbelhachmi;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

/**
 * Test 3 : un traducteur avec PromptTemplate et Prompt.
 */
public class Test3Traducteur {

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

        // Template avec 1 variable : le texte à traduire (traduction vers l'anglais)
        PromptTemplate template1 = PromptTemplate.from(
                "Traduis le texte suivant en anglais : {{texte}}");
        Prompt prompt1 = template1.apply(Map.of("texte", "Bonjour, comment allez-vous aujourd'hui ?"));
        System.out.println("Prompt 1 : " + prompt1.text());
        System.out.println("Réponse  : " + model.chat(prompt1.text()));

        System.out.println("==================================================");

        // Template avec 2 variables : la langue et le texte
        PromptTemplate template2 = PromptTemplate.from(
                "Traduis le texte suivant en {{langue}} : {{texte}}");
        Prompt prompt2 = template2.apply(Map.of(
                "langue", "espagnol",
                "texte", "Je voudrais réserver une table pour deux personnes."));
        System.out.println("Prompt 2 : " + prompt2.text());
        System.out.println("Réponse  : " + model.chat(prompt2.text()));
    }
}
