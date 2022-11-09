package SequenceProcessing.Sequence;

import Corpus.Corpus;
import Corpus.Sentence;
import Util.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SequenceCorpus extends Corpus {

    /**
     * Constructor which takes a file name {@link String} as an input and reads the file line by line. It takes each word of the line,
     * and creates a new {@link EmbeddedWord} with current word and its label. It also creates a new {@link Sentence}
     * when a new sentence starts, and adds each word to this sentence till the end of that sentence.
     *
     * @param fileName File which will be read and parsed.
     */
    public SequenceCorpus(String fileName) {
        super();
        String line, word;
        EmbeddedWord newWord;
        Sentence newSentence = null;
        try {
            InputStreamReader fr = new InputStreamReader(FileUtils.getInputStream(fileName));
            BufferedReader br = new BufferedReader(fr);
            line = br.readLine();
            while (line != null) {
                String[] items = line.split(" ");
                word = items[0];
                if (word.equals("<S>")) {
                    if (items.length == 2){
                        newSentence = new LabelledSentence(items[1]);
                    } else {
                        newSentence = new Sentence();
                    }
                } else {
                    if (word.equals("</S>")) {
                        addSentence(newSentence);
                    } else {
                        if (items.length == 2) {
                            newWord = new LabelledEmbeddedWord(word, items[1]);
                        } else {
                            newWord = new EmbeddedWord(word);
                        }
                        if (newSentence != null){
                            newSentence.addWord(newWord);
                        }
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}