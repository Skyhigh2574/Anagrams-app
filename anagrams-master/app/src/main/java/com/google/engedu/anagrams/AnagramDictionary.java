

package com.google.engedu.anagrams;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static int lengthOfAnagram = 4;

    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<String>();
    private HashMap<String,HashSet<String>> wordMap = new HashMap<String, HashSet<String>>();
    private HashMap<Integer,ArrayList<String>> sizedWords = new HashMap<Integer, ArrayList<String>>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);

            Integer keyByLength = word.length();
            if(sizedWords.get(keyByLength) == null){
                ArrayList<String> tempArratList = new ArrayList<String>();
                tempArratList.add(word);
                sizedWords.put(keyByLength,tempArratList);
            }else{
                sizedWords.get(keyByLength).add(word);
            }


            String key = sortString(word);

            if(wordMap.get(key)==null){
                HashSet<String> h = new HashSet<String>();
                h.add(word);
                wordMap.put(key,h);
            }else{
                wordMap.get(key).add(word);
            }
        }
        Log.v("dictonary","wordList ready");
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !word.contains(base))
            return true;
        return false;
    }

    public Set<String> getAnagrams(String targetWord) {

        String key = sortString(targetWord);
        Set<String> temp =new HashSet<String>();
        if(wordSet.contains(targetWord)) {
            temp = wordMap.get(key);
        }

        //List<String> result = new ArrayList<String>(temp);
        //return result;
          //  Context c = AnagramsActivity.getContext();
            Log.v("debug","getAnagrams Method Called for : " + targetWord);

        return temp;
    }

    public Set<String> getAnagramsWithOneMoreLetter(String word) {
        Set<String> temp = new HashSet<String>();

        temp.addAll(getAnagrams(word));


        char ch;
        String originalWord = word;
        StringBuilder wordBuilder = new StringBuilder(word);
        for(ch = 'a'; ch <'z'; ch++){
            wordBuilder.append(ch);
            Log.d("answers","calling getAnagrams for "+ wordBuilder);
            temp.addAll(getAnagrams(wordBuilder.toString()));
            wordBuilder = new StringBuilder(originalWord);
        }
        word = wordBuilder.toString();
//        List<String> result = new ArrayList<String>(temp);
//        return result;
        for(String a : temp) {
            Log.d("answers", a);
        }
        Log.v("debug","getAnagramsOneMoreLetter Called");
        return temp;

    }

    public String pickGoodStarterWord() {
        String s;
        while(true) {
            int i = random.nextInt(wordList.size());
            s = wordList.get(i);
            if(s.length() <= lengthOfAnagram)
                if(wordMap.get(sortString(s)).size() > 4)
                    break;


        }
        lengthOfAnagram++;
        return s;

//        return "stop";
    }

    private String sortString(String word){
        char[] c = word.toCharArray();
        Arrays.sort(c);
        return Arrays.toString(c);
    }

    public Set<String> checkAnagrams(Set<String> anagrams,String base){

        Iterator<String> iter = anagrams.iterator();

        while (iter.hasNext()) {
            String str = iter.next();

            if (!isGoodWord(str,base))
                iter.remove();
        }

        return anagrams;
    }
}
