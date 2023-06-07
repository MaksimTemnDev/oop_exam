package gameLogic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionary {
    public enum WordStatus{
        UNKNOWN,
        NOTED,
        USED
    }
    private String[] startConfigs5 = {"объем"};//, "слово", "локон"
    private String[] startConfigs7 = {"объемно", "тягость", "атавизм", "люкарна", "трущоба"};
    private List<String> possibleWrds = new ArrayList<>();
    private List<Lexicon> lexicons = new ArrayList<>();
    public String lexiconStatus = "not created yet";

    public Dictionary() {
        updateList();
    }

    /**
     * Добавление лексикона в словарь.
     * <p>
     *
     * @param lexicon экземпляр лексикона.
     */
    public void addLexicon(Lexicon lexicon) {
        this.lexicons.add(lexicon);
        lexiconStatus = "created lexicon" + (lexicons.size() - 1) + ": " + lexicon.showAll();
    }

    /**
     * Создание стартового слова на поле.
     * <p>
     *
     * @param size размер поля.
     * @return Стартовое слово.
     */
    public String generateWordConfiguration(int size) {
        Random random = new Random();
        int currentId = random.nextInt(0, startConfigs5.length);
        if(size == 7){
            currentId = random.nextInt(0, startConfigs7.length);
            return startConfigs7[currentId];
        }else {
            return startConfigs5[currentId];
        }
    }

    public void setWordToLexicon(String word, int playerId) {
        lexicons.get(playerId).addWord(word);
    }

    /**
     * Поиск слова в словаре.
     * <p>
     *
     * @param subsequence предпологаемое слово.
     * @return результат поиска.
     */
    public WordStatus findWord(String subsequence) {
        for (int i = 0; i < possibleWrds.size(); i++) {
            if (possibleWrds.get(i).equals(subsequence)) {
                if (lexicons.size() == 2) {
                    if (!lexicons.get(0).showAll().contains(subsequence) && !lexicons.get(1).showAll().contains(subsequence)) {
                        return WordStatus.NOTED;
                    }else {
                        return WordStatus.USED;
                    }
                }
            }
        }
        return WordStatus.UNKNOWN;
    }

    public void addWord(String word) {
        try (FileWriter writer = new FileWriter("words.txt", true)) {
            // запись всей строки
            writer.write(word);
            // запись разделителя
            writer.append('\n');
            writer.flush();
            updateList();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateList() {
        try(BufferedReader br = new BufferedReader(new FileReader("words.txt")))
        {
            String s;
            while((s=br.readLine())!=null){
                if(!possibleWrds.contains(s)){
                    possibleWrds.add(s);
                }
            }
            System.out.println(possibleWrds);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
