package guesstheanimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;


class KnowledgeTree {

    private TreeNode root;
    private final String filename;
    private final ObjectMapper objectMapper;
    private int numberOfAnimals;
    private int numberOfFacts;
    private int treeHeight;  // max num of edges between root and leaf
    private int totalAnimalDepth;  // depth = num of nodes between root and animal + 1
    private int minAnimalDepth;

    KnowledgeTree(final String mapperType) {
        final String extension;
        switch (mapperType) {
            case "json":
                objectMapper = new JsonMapper();
                extension = ".json";
                break;
            case "xml":
                objectMapper = new XmlMapper();
                extension = ".xml";
                break;
            case "yaml":
                objectMapper = new YAMLMapper();
                extension = ".yaml";
                break;
            default:
                throw new IllegalArgumentException(TextHelper.getString("invalidType"));
        }
        filename = TextHelper.localizeFilename(extension);
    }

    TreeNode getRoot() {
        return root;
    }

    void insert(
            final TreeNode fact,
            final TreeNode parent,
            final TreeNode oldAnimal,
            final TreeNode newAnimal,
            final boolean factIsTrueForNewAnimal) {
        if (parent == null) {
            root = fact;
        } else if (oldAnimal == parent.yes) {
            parent.yes = fact;
        } else {
            parent.no = fact;
        }

        if (factIsTrueForNewAnimal) {
            fact.yes = newAnimal;
            fact.no = oldAnimal;
        } else {
            fact.yes = oldAnimal;
            fact.no = newAnimal;
        }
    }

    void load() {
        try {
            root = objectMapper.readValue(new File(filename), TreeNode.class);
        } catch (final IOException e) {
            root = null;
        }
        if (root == null || root.data == null) {
            TextHelper.println("animal.wantLearn");
            TextHelper.println("animal.askFavorite");
            root = TreeNode.newAnimal();
            TextHelper.print("animal.nice");
            TextHelper.println("animal.learnedMuch");
            System.out.println();
        } else {
            root.initOtherFields();
        }
    }

    void save() {
        try {
            objectMapper
                 .writerWithDefaultPrettyPrinter()
                 .writeValue(new File(filename), root);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    void printAllAnimalNames() {
        final List<String> names = new ArrayList<>();
        getAllAnimalNames(root, names);
        Collections.sort(names);
        TextHelper.println("tree.list.animals");
        for (final String name : names) {
            System.out.println(" - " + name);
        }
    }

    private static void getAllAnimalNames(final TreeNode node, final List<String> list) {
        if (node.isAnimal()) {
            list.add(node.name);
        } else {
            getAllAnimalNames(node.yes, list);
            getAllAnimalNames(node.no, list);
        }
    }

    void printAllFactsForAnimal() {
        TextHelper.println("tree.search.prompt");
        final TreeNode animal = TreeNode.newAnimal();
        final Deque<String> facts = new ArrayDeque<>();
        if (getAllFactsForAnimal(root, facts, animal.data)) {
            TextHelper.printf("tree.search.facts", animal.name);
            while (!facts.isEmpty()) {
                System.out.println(" - " + facts.pollLast());
            }
        } else {
            TextHelper.printf("tree.search.noFacts", animal.name);
        }
    }

    private static boolean getAllFactsForAnimal(final TreeNode node,
                                         final Deque<String> facts,
                                         final String data) {
        if (node.isAnimal()) {
            return node.data.equals(data);
        }
        if (getAllFactsForAnimal(node.yes, facts, data)) {
            facts.addLast(node.data);
        } else if (getAllFactsForAnimal(node.no, facts, data)) {
            facts.addLast(node.negatedStatement);
        } else {
            return false;
        }
        return true;
    }

    void printStatistics() {
        numberOfAnimals = 0;
        numberOfFacts = 0;
        treeHeight = 0;
        totalAnimalDepth = 0;
        minAnimalDepth = Integer.MAX_VALUE;
        getTreeStats(root, 0);

        TextHelper.println("tree.stats.title");
        TextHelper.printf("tree.stats.root", root.data);
        TextHelper.printf("tree.stats.nodes", numberOfAnimals + numberOfFacts);
        TextHelper.printf("tree.stats.animals", numberOfAnimals);
        TextHelper.printf("tree.stats.statements", numberOfFacts);
        TextHelper.printf("tree.stats.height", treeHeight);
        TextHelper.printf("tree.stats.minimum", minAnimalDepth);
        TextHelper.printf("tree.stats.average", (float) totalAnimalDepth / numberOfAnimals);
    }

    private void getTreeStats(final TreeNode node, final int depth) {
        if (node.isAnimal()) {
            numberOfAnimals++;
            if (depth < minAnimalDepth) {
                minAnimalDepth = depth;
            }
            totalAnimalDepth += depth;
        } else {
            numberOfFacts++;
            getTreeStats(node.yes, depth + 1);
            getTreeStats(node.no, depth + 1);
        }
        if (depth > treeHeight) {
            treeHeight = depth;
        }
    }

    void print() {
        System.out.println();
        System.out.println(" └ " + root.data);
        if (!root.isAnimal()) {
            printChildNode(root, "  ");
        }
    }

    private static void printChildNode(final TreeNode node, final String prefix) {
        if (node.yes.isAnimal()) {
            System.out.println(prefix + "├ " + node.yes.data);
        } else {
            System.out.println(prefix + "├ " + node.yes.question);
            printChildNode(node.yes, prefix + "│");
        }
        if (node.no.isAnimal()) {
            System.out.println(prefix + "└ " + node.no.data);
        } else {
            System.out.println(prefix + "└ " + node.no.question);
            printChildNode(node.no, prefix + " ");
        }
    }
}
