package crawler.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Sentence {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	private String postId;
	
	private String sentence;
	
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	@OneToMany(mappedBy="id",cascade = CascadeType.ALL)
    private List<WordDependency> wordDependencyList = new ArrayList<WordDependency>();

	public List<WordDependency> getWordDependencyList() {
		return wordDependencyList;
	}

	public void setWordDependencyList(List<WordDependency> wordDependencyList) {
		this.wordDependencyList = wordDependencyList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	
	
}
