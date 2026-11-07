# IA.md : Utilisation de l'IA (TP 2 - LangChain4j / Gemini)

Ce fichier décrit l'utilisation de l'IA (Claude) pour les **tests 1 à 4** du TP 2.

---

## Utilisation n°1 : Écriture des classes Test1, Test2Couts, Test3Traducteur et Test4Embeddings

### Problème rencontré

Je ne savais pas par où commencer avec LangChain4j. Je ne connaissais ni la syntaxe du pattern builder pour `GoogleGenAiChatModel` et `GoogleGenAiEmbeddingModel`, ni la façon de récupérer le nombre de tokens d'une réponse (`ChatResponse`, `TokenUsage`). Je ne savais pas non plus utiliser `PromptTemplate` / `Prompt` ni calculer une similarité avec `CosineSimilarity`.

### Raison de l'utilisation de l'IA

J'avais lu l'énoncé et le support de cours, mais je n'arrivais pas à assembler les classes (dépendances Maven, imports, noms de méthodes). L'IA m'a servi à avoir un point de départ et surtout à comprendre le rôle de chaque paramètre.

### Prompt utilisé

> donne moi le code des 4 tests premiers et explique pour moi les parametres

(accompagné du texte de l'énoncé du TP 2)

### Réponse de l'IA (extraits pertinents)

**Dépendances Maven :** `langchain4j` et `langchain4j-google-genai`, avec le même numéro de version, plus `langchain4j-embeddings-bge-small-en-v15` pour le modèle d'embeddings local du test 4.

**Test 1 :** création du modèle avec le builder, puis un appel à `chat()` :

```java
ChatModel modele = GoogleGenAiChatModel.builder()
        .apiKey(System.getenv("GEMINI_KEY"))
        .modelName("gemini-3.5-flash")
        .temperature(0.7)
        .build();
String reponse = modele.chat("Quelle est la capitale du Maroc ?");
```

**Test 2 :** utilisation de `ChatResponse` et de `tokenUsage()` :

```java
ChatResponse reponse = modele.chat(UserMessage.from("..."));
TokenUsage usage = reponse.tokenUsage();
int entree = usage.inputTokenCount();
int sortie = usage.outputTokenCount();
double cout = (entree * prixEntree + sortie * prixSortie) / 1_000_000.0;
```

**Test 3 :** templates de prompt avec variables entre doubles accolades :

```java
PromptTemplate template = PromptTemplate.from("Traduis le texte suivant en {{langue}} : {{texte}}");
Prompt prompt = template.apply(Map.of("langue", "espagnol", "texte", "..."));
modele.chat(prompt.text());
```

**Test 4 :** embeddings des deux phrases, puis similarité cosinus, avec le modèle local puis le modèle Google :

```java
Embedding e1 = modele.embed(phrase1).content();
Embedding e2 = modele.embed(phrase2).content();
double sim = CosineSimilarity.between(e1, e2);
```

**Explication des paramètres :**

- `apiKey` : la clé Gemini qui identifie l'utilisateur. Elle doit être lue dans une variable d'environnement et jamais écrite en dur dans le code, sinon elle partirait sur GitHub.
- `modelName` : le modèle utilisé (ex. `gemini-3.5-flash`, rapide et peu cher).
- `temperature` : le degré d'aléatoire de la réponse. Proche de 0, la réponse est quasi déterministe. À 0,7, elle est équilibrée. Plus haut, elle est plus créative mais moins fiable.
- `taskType` (embeddings) : à quoi serviront les vecteurs. `SEMANTIC_SIMILARITY` sert à comparer le sens de phrases.
- `timeout` (embeddings) : durée maximale d'attente de la réponse (`Duration`). Il faut l'augmenter si on obtient une erreur de timeout.
- `{{variable}}` dans un `PromptTemplate` : emplacement remplacé par la valeur donnée dans `apply(Map.of(...))`.
- `tokenUsage()` : nombre de tokens d'entrée, de sortie et total. La facturation se fait par million de tokens.
- `CosineSimilarity.between` : valeur entre -1 et 1. Plus elle est proche de 1, plus les phrases ont un sens proche.

**Pour l'email (test 1) :**

- Le LLM n'a pas d'horloge, donc il ne peut pas donner l'heure exacte.
- Chaque appel à `chat()` est indépendant : le modèle n'a pas de mémoire entre deux appels et ne retrouve pas mon nom donné dans la première question.

### Ce que j'ai appris

> À reformuler avec mes propres mots avant de rendre le fichier.

- Un modèle de chat se configure avec le pattern builder (clé, nom du modèle, température). La clé API se met dans une variable d'environnement et pas dans le code.
- La température contrôle le caractère aléatoire de la réponse.
- Un appel à `chat()` n'a aucune mémoire : pour qu'un LLM se souvienne, il faut lui renvoyer l'historique de la conversation.
- Le LLM ne connaît pas l'heure actuelle, car il ne fait que prédire du texte.
- Le coût d'une requête dépend du nombre de tokens en entrée et en sortie, avec un prix par million de tokens pour chacun.
- Un `PromptTemplate` permet de réutiliser un même prompt avec des variables.
- Un embedding transforme une phrase en vecteur, et la similarité cosinus mesure la proximité de sens. Ce qui compte, c'est le classement entre les couples (les phrases proches doivent avoir un score plus élevé que les phrases sans rapport), pas la valeur absolue.
- Le modèle d'embeddings local est entraîné en anglais, donc moins fiable en français que le modèle de Google.

---

## Utilisation de l'IA pour le reste du TP

> À compléter (tests 5 à 7, partie Web) si j'utilise l'IA, ou écrire « Je n'ai pas utilisé l'IA ».
