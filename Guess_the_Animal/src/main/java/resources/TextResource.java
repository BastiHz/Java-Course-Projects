import java.util.ListResourceBundle;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public class TextResource extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
            {"welcome", "Welcome to the animals expert system!"},
            {"farewell", new String[] {
                "Bye!",
                "Bye, bye!",
                "See you later!",
                "See you soon!",
                "Talk to you later!",
                "I'm off!",
                "It was nice seeing you!",
                "See ya!",
                "See you later, alligator!",
                "In a while, crocodile!",
                "Hasta la vista, baby!",
                "Au revoir!",
                "Adieu!",
                "Have a nice one!",
            }},
            {"ask.again", new String[] {
                "Please enter yes or no.",
                "Funny, I don't understand. Is it yes or no?",
                "Sorry, it must be yes or no.",
                "Let's try again: yes or no?",
                "I'm not sure I caught you. Was it yes or no?",
                "Oh, no, don't try to confuse me. Please say yes or no.",
                "Could you please simply say yes or no?",
                "Sorry, may I ask you again: was it yes or no?"
            }},
            {"greeting.early", "Hi Early Bird!"},
            {"greeting.morning", "Good morning!"},
            {"greeting.afternoon", "Good afternoon!"},
            {"greeting.evening", "Good evening!"},
            {"greeting.night", "Hi Night Owl!"},
            {"menu.title", "What do you want to do:"},
            {"menu.entry.play", "1. Play the guessing game"},
            {"menu.entry.list", "2. List of all animals"},
            {"menu.entry.search", "3. Search for an animal"},
            {"menu.entry.statistics", "4. Calculate statistics"},
            {"menu.entry.print", "5. Print the Knowledge Tree"},
            {"menu.entry.exit", "0. Exit"},
            {"menu.error", "Please enter the number from 0 up to 5."},
            {"invalidType", "Invalid value for -type argument."},
            {"animal.wantLearn", "I want to learn about animals."},
            {"animal.askFavorite", "Which animal do you like most?"},
            {"animal.nice", new String[] {
                "Nice!", "Great!", "Wonderful!", "Awesome!", "Excellent!", "Marvelous!"
            }},
            {"animal.learnedMuch", " I've learned so much about animals!"},
            {"tree.list.animals", "Here are the animals I know:"},
            {"tree.search.prompt", "Enter the animal:"},
            {"tree.search.facts", "Facts about the %s:%n"},
            {"tree.search.noFacts", "No facts about the %s.%n"},
            {"tree.stats.title", "The Knowledge Tree stats"},
            {"tree.stats.root", "- root node                    %s%n"},
            {"tree.stats.nodes", "- total number of nodes        %d%n"},
            {"tree.stats.animals", "- total number of animals      %d%n"},
            {"tree.stats.statements", "- total number of statements   %d%n"},
            {"tree.stats.height", "- height of the tree           %d%n"},
            {"tree.stats.minimum", "- minimum depth                %d%n"},
            {"tree.stats.average", "- average depth                %.1f%n"},
            {"game.think", "You think of an animal, and I guess it."},
            {"game.enter", "Press enter when you're ready."},
            {"game.guess", "Is it %s?%n"},
            {"game.win", new String[] {
                "It's great that I got it right!",
                "Awesome! That was fun."
            }},
            {"game.giveUp", "I give up. What animal do you have in mind?"},
            {"game.again", new String[] {
                "Want to try again?",
                "Would you like to play again?",
                "Do you want to repeat?",
                "Want to play again?",
                "One more game?",
                "Do you want to play again?"
            }},
            {"statement.prompt",
                "Specify a fact that distinguishes %s from %s.%n" +
                "The sentence should be of the format: 'It can/has/is ...'.%n"
            },
            {"statement.error",
                "The examples of a statement:\n" +
                " - It can fly\n" +
                " - It has horns\n" +
                " - It is a mammal"
            },
            {"statement.isCorrect", "Is the statement correct for %s?%n"},
            {"statement.learned", "I learned the following facts about animals:"},
            {"statement.it", "It"},
            {"statement.stateLearned", " - The %s %s%n"},
            {"itCanHasIs.negate", Map.of(
                "It can", "can't",
                "It has", "doesn't have",
                "It is", "isn't"
            )},
            {"itCanHasIs.question", Map.of(
                "It can", "Can it",
                "It has","Does it have",
                "It is", "Is it"
            )},
            {"pattern.positiveAnswer",
                "(y|yes|yeah|yep|sure|right|affirmative|correct|indeed|you bet|exactly|you said it)[.!]?"
            },
            {"pattern.negativeAnswer", "(n|no( way)?|nah|nope|negative|i don't think so|yeah no)[.!]?"},
            {"pattern.fact", "It (can|has|is)\\s\\w+.*"},
            {"pattern.splitFact", "(?<=It is)\\s|(?<=It can)\\s|(?<=It has)\\s"},
            {"fun.getArticle", (UnaryOperator<String>) name -> name.matches("[aeiou].*") ? "an" : "a"},
            {"fun.splitName", (Function<String, String[]>) data -> data.split("(?<=^(a|an))\\s")},
            {"fun.nameHasArticle", (Function<String, Boolean>) name -> name.matches("(a|an)\\s+.+")},
            {"fun.getStatementRest", (UnaryOperator<String>) str -> str.substring(3)},
        };
    }
}
