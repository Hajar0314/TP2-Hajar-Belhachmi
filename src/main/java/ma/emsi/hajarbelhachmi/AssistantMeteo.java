package ma.emsi.hajarbelhachmi;

/**
 * Assistant IA qui peut utiliser les outils météo (implémenté par LangChain4j via AiServices).
 */
public interface AssistantMeteo {
    String chat(String userMessage);
}