package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Lexicon {
    private List<String> playersWords = new ArrayList<>();

    /**
     * Показвть все слова в лексиконе.
     * <p>
     *
     * @return список слов.
     */
    public List<String> showAll(){
        List<String> res = playersWords;
        return res;
    }

    /**
     * Получить количество очков.
     * <p>
     *
     * @return очки по данному лексикону.
     */
    public int getScore(){
        int res = 0;
        for(int i = 0; i <this.playersWords.size(); i++){
            res+=this.playersWords.get(i).length();
        }
        return res;
    }

    /**
     * Добавить слово.
     * <p>
     *
     * @param word слово.
     */
    public void addWord(String word){
        this.playersWords.add(word);
    }
}
