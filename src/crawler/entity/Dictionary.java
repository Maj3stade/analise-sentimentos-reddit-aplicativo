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

import org.apache.commons.lang3.ArrayUtils;

public class Dictionary {
	private List<WordScore> dictionary = new ArrayList<>();

	public List<WordScore> getDictionary() {
		return dictionary;
	}

	public void setDictionary(List<WordScore> dictionary) {
		this.dictionary = dictionary;
	}

	public void addWord(String word, String rule) {
		WordScore ws = new WordScore();
		ws.setWord(word);
		ws.setRule(rule);
		dictionary.add(ws);
	}

	public void addWord(String word, Float score, String rule) {
		WordScore ws = new WordScore();
		ws.setWord(word);
		ws.setRule(rule);
		ws.setScore(score);
		dictionary.add(ws);
	}

	public void addWord(String word, Float score, String rule, int sentenceId) {
		WordScore ws = new WordScore();
		ws.setWord(word);
		ws.setRule(rule);
		ws.setScore(score);
		ws.setSentenceId(sentenceId);
		dictionary.add(ws);
	}

	public void removeWord(String word) {

		for (int j = dictionary.size() - 1; j >= 0; j--) {
			WordScore ws = dictionary.get(j);
			if (ws.getWord().equalsIgnoreCase(word)) {
				//System.out.println("removido:" + ws.getWord());
				dictionary.remove(j);
			}
		}

	}

	public void addTargetWord(String word, Float score, String rule, int sentenceId, List<Sentence> dbSentences) {
		int t1 = 0, t2 = 0;

		String sameTarget = getSameSentenceTarget(sentenceId);

		/*if (word.compareTo("attention") == 0) {
			if (sameTarget == null) {
				System.out.println("Same target = null");
			} else
				System.out.println("Same target:" + sameTarget);

		}*/
		if (sameTarget != null) {
			for (Sentence dbSentence : dbSentences) {
				if (dbSentence.getSentence().contains(sameTarget))
					t2++;
				if (dbSentence.getSentence().contains(word))
					t1++;
			}

			if (t1 > t2) {
				removeWord(sameTarget);
				WordScore ws = new WordScore();
				ws.setWord(word);
				ws.setRule(rule);
				ws.setScore(score);
				ws.setSentenceId(sentenceId);
				dictionary.add(ws);
			}

		} else {
			WordScore ws = new WordScore();
			ws.setWord(word);
			ws.setRule(rule);
			ws.setScore(score);
			ws.setSentenceId(sentenceId);
			dictionary.add(ws);
		}

	}

	public Boolean contains(String word) {
		Boolean contains = false;
		for (WordScore ws : dictionary) {
			if (ws.getWord().equalsIgnoreCase(word)) {
				contains = true;
			}
		}
		return contains;
	}

	public Float getScore(String word) {
	
		for (WordScore ws : dictionary) {
			if (ws.getWord().equalsIgnoreCase(word)) {
				return ws.getScore();
			}
		}

		System.out.println("RETURN NULL VERIFICAR!!!! " + word);
		return null;
	}

	public String getSameSentenceTarget(int sentenceId) {
		for (WordScore ws : dictionary) {
			if (ws.getSentenceId() == sentenceId) {
				return ws.getWord();
			}
		}
		return null;
	}

}
