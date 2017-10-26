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
import javax.persistence.Transient;
@Entity
public class AuthorOpinion {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	private String postId;

	private String target;

	
	private String author;
	
	private Double opinion;
	
	private Boolean isCorrect;

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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getOpinion() {
		return opinion;
	}

	public void setOpinion(Double opinion) {
		this.opinion = opinion;
	}

	
	
	
}
