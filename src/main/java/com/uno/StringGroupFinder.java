package com.uno;

import java.io.*;
import java.util.*;

public class StringGroupFinder {
    private final UnionFind unionFind;
    private final Map<String, Integer> positionMap;
    private final Map<Integer, Set<String>> finalGroups;

    public StringGroupFinder() {
        this.unionFind = new UnionFind();
        this.positionMap = new HashMap<>();
        this.finalGroups = new HashMap<>();
    }

    public void readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int index = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                unionFind.addNewElement(index);
                String[] array = line.split(";");
                for (int i = 0; i < array.length; i++) {
                    String number = array[i];
                    if (number.startsWith("\"") && number.endsWith("\"")) {
                        number = number.substring(1, number.length() - 1);
                    }
                    if (number.contains("\"")) {
                        break;
                    }
                    if (!number.isBlank()) {
                        String key = number + "_" + i;
                        if (positionMap.containsKey(key)) {
                            unionFind.union(index, positionMap.get(key));
                        } else {
                            positionMap.put(key, index);
                        }
                    }
                }
                index++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        positionMap.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int index = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                int root = unionFind.findRoot(index);
                if (!finalGroups.containsKey(root)) {
                    finalGroups.put(root, new HashSet<>());
                }
                finalGroups.get(root).add(line);
                index++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<Set<String>> sortedGroups = new ArrayList<>(finalGroups.values());
        sortedGroups.removeIf(group -> group.size() <= 1);
        sortedGroups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));

        finalGroups.clear();

        try (Writer writer = new FileWriter("output.txt")) {
            writer.write("Всего групп с более чем одним элементом: " + sortedGroups.size() + "\n");
            int groupId = 1;
            for (Set<String> entry : sortedGroups) {
                writer.write("Группа " + groupId++ + "\n");
                for (String string : entry) {
                    writer.write(string + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
