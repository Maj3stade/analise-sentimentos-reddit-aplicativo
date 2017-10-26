package runnables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import crawler.entity.AuthorOpinion;
import crawler.entity.RedditPost;
import crawler.entity.Sentence;
import crawler.entity.WordDependency;
import crawler.properties.RedditProperties;
import dictionary.DictionaryGenerator;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.DependencyParseAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import manager.RedditManager;
import vader.SentimentAnalysis;
import vader.lexicon.English;
import vader.lexicon.EnglishAuthor;
import vader.lexicon.EnglishDomain;
import vader.text.TokenizerEnglish;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GenerateValenceScoreBySentence {

	
	public static void main(String[] args) {

		try {
			new RedditProperties();
			List<RedditPost> postList = new ArrayList();
			for (String target : RedditProperties.SELECTED_THREADS){
				postList.addAll(RedditManager.getAllPostsByThreadId(target));
			}
			List<AuthorOpinion> opinionList = new ArrayList<>(); 
			SentimentAnalysis domainAnalysis = new SentimentAnalysis(new English(), new TokenizerEnglish());

			for (RedditPost post : postList) {
				List<Sentence> sl = RedditManager.getAllSentencesByPost(post.getId());
				for (String target : RedditProperties.SELECTED_TARGETS){
					String targetOpinion = "";
					for (Sentence sentence : sl){
						String[] sentenceVector = new String[200];
						sentenceVector = getWordDependent(sentenceVector, sentence.getWordDependencyList(), target);
						targetOpinion = targetOpinion + joinArray(sentenceVector);
					}
					if(!targetOpinion.trim().isEmpty()){
						AuthorOpinion opinion = new AuthorOpinion();
						opinion.setAuthor(post.getAuthor());
						opinion.setPostId(post.getId());
						opinion.setTarget(target);
						opinion.setOpinion(new Double (domainAnalysis.getSentimentAnalysis(targetOpinion).get("compound")));
						if(opinion.getOpinion() != 0){
							System.out.println(target + ":" + domainAnalysis.getSentimentAnalysis(targetOpinion).get("compound")+ ":" +  post.getBody() + ":"  + post.getId());
							opinionList.add(opinion);
						}
					}
				}
				
				
				
				
			}
			RedditManager.saveOpinions(opinionList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		RedditManager.shutdown();
	}
	private static String[] getWordDependent(String[] sentenceVector, List<WordDependency> wDList, String word){
		for(WordDependency wd:wDList){
			if(wd.getGovernor().equalsIgnoreCase(word)){
				sentenceVector[Integer.parseInt(wd.getGovernorId()) - 1] = wd.getGovernor();
				if((sentenceVector[Integer.parseInt(wd.getDependentId()) - 1] == null) || (!sentenceVector[Integer.parseInt(wd.getDependentId()) - 1].equalsIgnoreCase(wd.getDependent()))){
					sentenceVector[Integer.parseInt(wd.getDependentId()) - 1] = wd.getDependent();
					sentenceVector = getWordDependent(sentenceVector, wDList, wd.getDependent());
				}
			}
		}
		return sentenceVector;
		
		
	}
	
	private static String joinArray(String[] arrayToJoin){
		String returnString = "";
		for (String string : arrayToJoin) {
			if(string == null || string.trim().isEmpty())
				continue;
			returnString = returnString + " " + string;
		}
		return returnString;
	}
	
}
