package runnables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GenerateValenceScore {

	
	public static void main(String[] args) {

		try {
			new RedditProperties();
			List<RedditPost> postList = RedditManager.getAllPostsByThread("t3_5qqa51");
			
			SentimentAnalysis domainAnalysis = new SentimentAnalysis(new EnglishDomain(), new TokenizerEnglish());
			SentimentAnalysis normalAnalysis = new SentimentAnalysis(new English(), new TokenizerEnglish());
			

			for (RedditPost post : postList) {
				SentimentAnalysis authorAnalysis = new SentimentAnalysis(new EnglishAuthor(post.getAuthor()), new TokenizerEnglish());
			    System.out.println(post.getBody());
			    System.out.println(normalAnalysis.getSentimentAnalysis(post.getBody()).get("compound").toString() + " - " + authorAnalysis.getSentimentAnalysis(post.getBody()).get("compound").toString() + " - " + domainAnalysis.getSentimentAnalysis(post.getBody()).get("compound").toString());
			    post.setValiantScoreAuthorDomain(new Double (authorAnalysis.getSentimentAnalysis(post.getBody()).get("compound")));
			    post.setValiantScoreDomain(new Double (domainAnalysis.getSentimentAnalysis(post.getBody()).get("compound")));
			}
			RedditManager.updateRedditPost(postList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		RedditManager.shutdown();
	}
}
