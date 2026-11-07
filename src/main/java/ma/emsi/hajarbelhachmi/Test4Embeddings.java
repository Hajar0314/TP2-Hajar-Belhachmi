package ma.emsi.hajarbelhachmi;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
// import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel; // BGE : en commentaire depuis le Test 5
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

/**
 * Test 4 : similarité de phrases avec 2 modèles d'embeddings.
 * Le modèle intégré BGE est en commentaire depuis le Test 5 (conflit avec easy-rag).
 */
public class Test4Embeddings {

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_KEY");
        if (llmKey == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        String[][] couples = {
                {"Le chat dort sur le canapé.", "Un félin fait la sieste sur le sofa."},          // similaires
                {"Il fait très chaud aujourd'hui.", "Il fait très froid aujourd'hui."},            // opposées
                {"J'aime beaucoup la pizza.", "Je déteste la pizza."},                             // opposées
                {"Le train part à huit heures.", "La capitale de l'Italie est Rome."},             // sans rapport
                {"Je programme en Java.", "Je développe des applications avec Java."},             // similaires
                {"La voiture roule vite sur l'autoroute.", "Les bananes sont riches en potassium."} // sans rapport
        };

        // ---- Modèle intégré (anglais) : en commentaire depuis le Test 5 ----
        // EmbeddingModel bge = new BgeSmallEnV15EmbeddingModel();
        // afficher("BGE small en v1.5 (intégré)", bge, couples);
        // ---- fin du bloc BGE ----

        // ---- Modèle externe de Google ----
        EmbeddingModel google = GoogleGenAiEmbeddingModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-embedding-001")
                .taskType(GoogleGenAiEmbeddingModel.TaskTypeEnum.SEMANTIC_SIMILARITY)
                .timeout(Duration.ofSeconds(10)) // 100 ms peut être trop court
                .build();
        afficher("Google gemini-embedding-001", google, couples);
    }

    private static void afficher(String nomModele, EmbeddingModel model, String[][] couples) {
        System.out.println("==================================================");
        System.out.println("Modèle : " + nomModele);
        for (String[] couple : couples) {
            Embedding e1 = model.embed(couple[0]).content();
            Embedding e2 = model.embed(couple[1]).content();
            double similarite = CosineSimilarity.between(e1, e2);
            System.out.printf("%.4f  |  %s  <->  %s%n", similarite, couple[0], couple[1]);
        }
    }
}