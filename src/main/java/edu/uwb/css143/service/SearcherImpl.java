package edu.uwb.css143.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearcherImpl implements Searcher {
    /*
    Extra credit
    this search won't work. why?
    TODO: add your answer here for extra credit
     */
    // public List<Integer> search(String keyPhrase, List<String> docs) {
    //
    //     List<Integer> result = new ArrayList<>();
    //
    //     for (int i = 0; i < docs.size(); i++) {
    //         if (docs.get(i).contains(keyPhrase)) {
    //             result.add(i);
    //         }
    //     }
    //     return result;
    // }

    // a naive search
    // DO NOT CHANGE
    public List<Integer> search(String searchPhrase, List<String> docs) {
        List<Integer> result = new ArrayList<>();
        String[] searchWords = searchPhrase.trim().toLowerCase().split("\\s+");

        // search in each doc for consecutive matches of each word in the search phrase
        for (int i = 0; i < docs.size(); i++) {
            String doc = docs.get(i).trim();
            if (doc.isEmpty()) {
                continue;
            }
            String[] wordsInADoc = doc.split("\\s+");

            for (int j = 0; j < wordsInADoc.length; j++) {
                boolean matchFound = true;
                for (int k = 0; k < searchWords.length; k++) {
                    if (j + k >= wordsInADoc.length || !searchWords[k].equals(wordsInADoc[j + k])) {
                        matchFound = false;
                        break;
                    }
                }
                if (matchFound) {
                    result.add(i);
                    break;
                }
            }
        }
        return result;
    }

    /*
    TODO: Team member names
     */
    public List<Integer> search(String searchPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> searchResult = new ArrayList<>(); // do not change
        if (searchPhrase==null||searchPhrase.trim().isEmpty()) {
            return searchResult;
        }
        String[] searchWords=searchPhrase.trim().split("\\s+");
        List<Integer> commonDocs=getCommonDocs(words,index);
        for(Integer doc:commonDocs){
            List<List<Integer>> locations=new ArrayList<>();
            for(String word:words){
                locations.add(index.get(word).get(doc));
            }
            if(hasMatch(locations)){
                searchResult.add(doc);
            }
            // boolean matchFound=true;
            // for(int i=0;i<searchWords.length;i++){
            //     if(!index.containsKey(searchWords[i])||!index.get(searchWords[i]).get(0).contains(doc)){
            //         matchFound=false;
            //         break;
            //     }
            // }
            // if(matchFound){
            //     searchResult.add(doc);
            // }
        }
        /*
         TODO: add your code
         */
        return searchResult; // do not change. variable "index" is the result that this function should return
    }
    private boolean hasMatch(List<List<Integer>> locations){
        for(int i=0;i<locations.size();i++){
            if(locations.get(i).isEmpty()){
                return false;
            }
        }
        for(int i=0;i<locations.size()-1;i++){
            for(int j=0;j<locations.get(i).size();j++){
                for(int k=0;k<locations.get(i+1).size();k++){
                    if(locations.get(i+1).get(k)-locations.get(i).get(j)==1){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private List<Integer> getCommonDocs(String[] words, Map<String, List<List<Integer>>> index){
        List <Integer> result=new ArrayList<>();
        Map<Integer,Integer> docToFreq=new TreeMap<>();

        for(String word:words){
            List<List<Integer>> indexForWords=index.get(word);
            for(int docId=0;docId<indexForWords.size();docId++){
                if(indexForWords.get(docId).isEmpty()){
                    continue;
                }

                if(docToFreq.containsKey(docId)){
                    docToFreq.put(docId,docToFreq.get(docId)+1);
                }else{
                    docToFreq.put(docId,1);
                }
            }

            if(index.containsKey(word)){
                for(Integer docId:index.get(word).get(0)){
                    if(docToFreq.containsKey(docId)){
                        docToFreq.put(docId,docToFreq.get(docId)+1);
                    }else{
                        docToFreq.put(docId,1);
                    }
                }
            }
        }
        for(Map.Entry<Integer,Integer> entry:docToFreq.entrySet()){
            Integer docId=entry.getKey();
            Integer freq=entry.getValue();
            if(entry.getValue()==words.length){
                result.add(docId);
            }
        }
        // List<Integer> commonDocs=new ArrayList<>();
        // for(String word:words){
        //     if(index.containsKey(word)){
        //         if(commonDocs.isEmpty()){
        //             commonDocs.addAll(index.get(word).get(0));
        //         }else{
        //             commonDocs.retainAll(index.get(word).get(0));
        //         }
        //     }
        // }
        // return commonDocs;
        return result;
    }

    /*
    Extra credit
    TODO (Optional): Does your search beat the native search performance?
    TODO (Optional): If yes, search "and a" in your local website, and then copy & paste the run times from the search page here as a proof.
     */
}
