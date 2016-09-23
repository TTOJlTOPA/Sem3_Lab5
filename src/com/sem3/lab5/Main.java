package com.sem3.lab5;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            findTags();
            findWords();
        } catch (IOException e) {
            System.out.println("FIle not found!");
        }
    }

    private static void findTags() throws IOException {
        Scanner htmlScanner = new Scanner(new File("d://IntelliJ IDEA Projects/Sem3_Lab5/resources/input1.html"));
        FileWriter tagWriter = new FileWriter("d://IntelliJ IDEA Projects/Sem3_Lab5/resources/output1.out", false);
        List<String> tags = new ArrayList();
        String buf;
        int index = 0;
        while (htmlScanner.hasNextLine()) {
            buf = htmlScanner.nextLine();
            while (index >= 0 && index < buf.length()) {
                index = buf.indexOf('<', index);
                if (index >= 0) {
                    if (buf.charAt(index + 1) != '/'
                            && !tags.contains((buf.substring(index + 1, buf.indexOf('>', index))))) {
                        tags.add(buf.substring(index + 1, buf.indexOf('>', index)));
                    }
                    index = buf.indexOf('>', index) + 1;
                }
            }
            index = 0;
        }
        tags.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
        Iterator<String> iter = tags.iterator();
        while (iter.hasNext()) {
            tagWriter.write(iter.next() + "\n");
        }
        tagWriter.flush();
    }

    private static void findWords() throws IOException {
        FileWriter indexWriter = new FileWriter("d://IntelliJ IDEA Projects/Sem3_Lab5/resources/output2.out");
        FileWriter notFoundWriter = new FileWriter("d://IntelliJ IDEA Projects/Sem3_Lab5/resources/output3.out");
        List<String> words = new ArrayList<>();
        int[] indexes;
        Iterator<String> iter;
        fillWords(words);
        if (words.isEmpty()) {
            System.out.println("input2.in is empty!");
            return;
        }
        indexes = find(words);
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] != -1) {
                words.remove(i);
            }
        }
        iter = words.iterator();
        for (int i = 0; i < indexes.length; i++) {
            indexWriter.append(indexes[i] + "\n");
        }
        while (iter.hasNext()) {
            notFoundWriter.append(iter.next() + "\n");
        }
        indexWriter.flush();
        notFoundWriter.flush();
    }

    private static void fillWords(List<String> words) throws FileNotFoundException {
        Scanner findScanner = new Scanner(new File("d://IntelliJ IDEA Projects/Sem3_Lab5/resources/input2.in"));
        String buf;
        String[] toFind;
        while (findScanner.hasNextLine()) {
            buf = findScanner.nextLine();
            buf = buf.toLowerCase();
            toFind = buf.split("[;]+");
            for (int i = 0; i < toFind.length; i++) {
                if (!words.contains(toFind[i])) {
                    words.add(toFind[i]);
                }
            }
        }
    }

    private static int[] find(List<String> words) throws FileNotFoundException {
        Scanner htmlScanner = new Scanner(new File("d://IntelliJ IDEA Projects/Sem3_Lab5/resources/input1.html"));
        String buf;
        int index = 0;
        int strIndex = 0;
        Iterator<String> iter;
        int[] indexes;
        indexes = new int[words.size()];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = -1;
        }
        while (htmlScanner.hasNextLine()) {
            buf = htmlScanner.nextLine();
            buf = buf.toLowerCase();
            index = 0;
            while (index >= 0 && index < buf.length()) {
                buf = buf.replace(buf.subSequence(buf.indexOf('<'), buf.indexOf('>') + 1), "");
                index = buf.indexOf('<', index);
            }
            iter = words.iterator();
            index = 0;
            while (iter.hasNext()) {
                if (buf.indexOf(iter.next()) != -1 && indexes[index] == -1) {
                    indexes[index] = strIndex;
                }
                index++;
            }
            strIndex++;
        }
        return indexes;
    }
}
