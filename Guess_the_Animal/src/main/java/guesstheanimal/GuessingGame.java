package guesstheanimal;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class GuessingGame {

    private final Pattern yesAnswer = Pattern.compile(
        TextHelper.getString("pattern.positiveAnswer"),
        Pattern.CASE_INSENSITIVE
    );
    private final Pattern noAnswer = Pattern.compile(
        TextHelper.getString("pattern.negativeAnswer"),
        Pattern.CASE_INSENSITIVE
    );
    private final Pattern fact = Pattern.compile(
        TextHelper.getString("pattern.fact"),
        Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS
    );
    @SuppressWarnings("unchecked")
    private final UnaryOperator<String> getStatementRest =
        (UnaryOperator<String>) TextHelper.getObject("fun.getStatementRest");
    private final KnowledgeTree knowledgeTree;

    GuessingGame(final KnowledgeTree knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    void play() {
        do {
            System.out.println();
            TextHelper.println("game.think");
            TextHelper.println("game.enter");
            TextHelper.scanner.nextLine();
            TreeNode currentTreeNode = knowledgeTree.getRoot();
            TreeNode parent = null;
            while (true) {
                if (currentTreeNode.isAnimal()) {
                    TextHelper.printf("game.guess", currentTreeNode.data);
                    if (getYesNo()) {
                        TextHelper.println("game.win");
                    } else {
                        TextHelper.println("game.giveUp");
                        createNewFact(currentTreeNode, parent);
                    }
                    break;
                } else {
                    System.out.println(currentTreeNode.question);
                    parent = currentTreeNode;
                    currentTreeNode = getYesNo() ? currentTreeNode.yes : currentTreeNode.no;
                }
            }
            System.out.println();
            TextHelper.println("game.again");
        } while (getYesNo());
    }

    private void createNewFact(final TreeNode oldAnimal, final TreeNode parent) {
        final TreeNode newAnimal = TreeNode.newAnimal();
        final TreeNode fact;
        while (true) {
            TextHelper.printf(
                "statement.prompt",
                new String[] {oldAnimal.data, newAnimal.data}
            );
            final String answer = TextHelper.nextLine();
            if (this.fact.matcher(answer).matches()) {
                fact = TreeNode.newFact(answer);
                break;
            }
            TextHelper.println("statement.error");
        }

        TextHelper.printf("statement.isCorrect", newAnimal.data);
        final boolean factIsTrueForNewAnimal = getYesNo();
        knowledgeTree.insert(fact, parent, oldAnimal, newAnimal, factIsTrueForNewAnimal);

        TextHelper.println("statement.learned");
        final String positiveRest = getStatementRest.apply(fact.data);
        final String negativeRest = getStatementRest.apply(fact.negatedStatement);
        if (factIsTrueForNewAnimal) {
            TextHelper.printf("statement.stateLearned", new String[] {oldAnimal.name, negativeRest});
            TextHelper.printf("statement.stateLearned", new String[] {newAnimal.name, positiveRest});
        } else {
            TextHelper.printf("statement.stateLearned", new String[] {oldAnimal.name, positiveRest});
            TextHelper.printf("statement.stateLearned", new String[] {newAnimal.name, negativeRest});
        }

        TextHelper.print("animal.nice");
        TextHelper.println("animal.learnedMuch");
    }

    private boolean getYesNo() {
        while (true) {
            final String answer = TextHelper.nextLine();
            if (yesAnswer.matcher(answer).matches()) {
                return true;
            }
            if (noAnswer.matcher(answer).matches()) {
                return false;
            }
            TextHelper.println("ask.again");
        }
    }
}
