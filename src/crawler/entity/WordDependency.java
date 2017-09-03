package crawler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WordDependency {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String dependent;
	private String dependentId;
	private String governor;
	private String governorId;
	
	@ManyToOne
	@JoinColumn(name="sentenceId")
	private Sentence sentence;
	
	public Sentence getSentence() {
		return sentence;
	}
	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDependent() {
		return dependent;
	}
	public void setDependent(String dependent) {
		this.dependent = dependent;
	}
	public String getDependentId() {
		return dependentId;
	}
	public void setDependentId(String dependentId) {
		this.dependentId = dependentId;
	}
	public String getGovernor() {
		return governor;
	}
	public void setGovernor(String governor) {
		this.governor = governor;
	}
	public String getGovernorId() {
		return governorId;
	}
	public void setGovernorId(String governorId) {
		this.governorId = governorId;
	}
	

}
