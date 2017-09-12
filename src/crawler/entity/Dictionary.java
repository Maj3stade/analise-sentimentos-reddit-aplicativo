package crawler.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Dictionary {
	private List<WordScore> dictionary = new ArrayList<>();
	
	public List<WordScore> getDictionary() {
		return dictionary;
	}

	public void setDictionary(List<WordScore> dictionary) {
		this.dictionary = dictionary;
	}

	public void addWord(String word, String rule){
		WordScore ws = new WordScore();
		ws.setWord(word);
		ws.setRule(rule);
		dictionary.add(ws);
	}
	public Boolean contains(String word){
		Boolean contains = false;
		for (WordScore ws:dictionary){
			if (ws.getWord().compareTo(word) == 0){
				contains = true;
			}
		}
		return contains;
	}
	

	
	
	
	
}
