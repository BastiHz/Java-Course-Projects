import java.util.ListResourceBundle;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public class TextResource_eo extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
            {"welcome", "Bonvenon al la sperta sistemo de la besto!"},
            {"farewell", new String[] {"Ĝis!", "Ĝis revido!", "Estis agrable vidi vin!"}},
            {"ask.again", new String[] {
                "Bonvolu enigi jes aŭ ne.",
                "Amuze, mi ankoraŭ ne komprenas, ĉu jes aŭ ne?",
                "Pardonu, devas esti jes aŭ ne.",
                "Ni provu denove: ĉu jes aŭ ne?",
                "Pardonu, ĉu mi rajtas demandi vin denove: ĉu tio estis jes aŭ ne?"
            }},
            {"greeting.early", "Saluton Frua Birdo!"},
            {"greeting.morning", "Bonan matenon!"},
            {"greeting.afternoon", "Bonan tagon!"},
            {"greeting.evening", "Bonan vesperon!"},
            {"greeting.night", "Saluton Nokta Strigo!"},
            {"menu.title", "Kion vi volas fari:"},
            {"menu.entry.play", "1. Ludi la divenludon"},
            {"menu.entry.list", "2. Listo de ĉiuj bestoj"},
            {"menu.entry.search", "3. Serĉi beston"},
            {"menu.entry.statistics", "4. Kalkuli statistikojn"},
            {"menu.entry.print", "5. Printi la Sciarbon"},
            {"menu.entry.exit", "0. Eliri"},
            {"menu.error", "Bonvolu enigi numeron de 0 ĝis 5."},
            {"invalidType", "Nevalida valoro por -tipa argumento."},
            {"animal.wantLearn", "Mi volas lerni pri bestoj."},
            {"animal.askFavorite", "Kiun beston vi plej ŝatas?"},
            {"animal.nice", new String[] {
                "Bela!", "Mirinde!", "Mojosa!", "Bonege!", "Mirinda!"
            }},
            {"animal.learnedMuch", " Mi lernis tiom multe pri bestoj!"},
            {"tree.list.animals", "Jen la bestoj, kiujn mi konas:"},
            {"tree.search.prompt", "Enigu la nomon de besto:"},
            {"tree.search.facts", "Faktoj pri la %s:%n"},
            {"tree.search.noFacts", "Neniuj faktoj pri la %s.%n"},
            {"tree.stats.title", "La statistiko de la Scio-Arbo"},
            {"tree.stats.root", "- radika nodo                 %s%n"},
            {"tree.stats.nodes", "- totala nombro de nodoj      %d%n"},
            {"tree.stats.animals", "- totala nombro de bestoj     %d%n"},
            {"tree.stats.statements", "- totala nombro de deklaroj   %d%n"},
            {"tree.stats.height", "- alteco de la arbo           %d%n"},
            {"tree.stats.minimum", "- minimuma profundo           %d%n"},
            {"tree.stats.average", "- averaĝa profundo            %.1f%n"},
            {"game.think", "Vi pensu pri besto, kaj mi divenos ĝin."},
            {"game.enter", "Premu enen kiam vi pretas."},
            {"game.guess", "Ĉu ĝi estas %s?%n"},
            {"game.win", "Bonege, ke mi trafis ĝin ĝuste!"},
            {"game.giveUp", "Mi rezignas. Kiun beston vi havas en la kapo?"},
            {"game.again", new String[] {
                "Ĉu vi volas provi denove?",
                "Ĉu vi ŝatas ludi denove?",
                "Ĉu vi volas ripeti?",
                "Ĉu vi volas ludi denove?",
                "Ankoraŭ unu ludo?",
                "Ĉu vi volas ludi denove?"
            }},
            {"statement.prompt",
                "Indiku fakton, kiu distingas %s de %s.%n" +
                "La frazo devas esti de la formato: 'Ĝi ...'.%n"},
            {"statement.error",
                "La ekzemploj de aserto:\n" +
                " - Ĝi povas flugi\n" +
                " - Ĝi havas kornojn\n" +
                " - Ĝi estas mamulo"
            },
            {"statement.isCorrect", "Ĉu la aserto ĝustas por la %s?%n"},
            {"statement.learned", "Mi lernis la jenajn faktojn pri bestoj:"},
            {"statement.it", "Ĝi"},
            {"statement.stateLearned", " - La %s %s%n"},
            {"itCanHasIs.negate", Map.of("Ĝi", "ne")},
            {"itCanHasIs.question", Map.of("Ĝi", "Ĉu ĝi")},
            {"pattern.positiveAnswer", "(j|jes|certe)!?"},
            {"pattern.negativeAnswer", "(n|ne)!?"},
            {"pattern.fact", "Ĝi\\s\\w+.*"},
            {"pattern.splitFact", "(?<=Ĝi)\\s"},
            {"fun.getArticle", (UnaryOperator<String>) name -> ""},
            {"fun.splitName", (Function<String, String[]>) data -> new String[] {"", data}},
            {"fun.nameHasArticle", (Function<String, Boolean>) name -> false},
            {"fun.getStatementRest", (UnaryOperator<String>) str -> str.substring(3)},
        };
    }
}
