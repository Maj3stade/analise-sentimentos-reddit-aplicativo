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
			/*List<String> sentences = new LinkedList<>() {{
			    add("VADER is smart, handsome, and funny.");
			    add("VADER is smart, handsome, and funny!");
			    add("VADER is very smart, handsome, and funny.");
			    add("VADER is VERY SMART, handsome, and FUNNY.");
			    add("VADER is VERY SMART, handsome, and FUNNY!!!");
			    add("VADER is VERY SMART, really handsome, and INCREDIBLY FUNNY!!!");
			    add("The book was good.");
			    add("The book was kind of good.");
			    add("The plot was good, but the characters are uncompelling and the dialog is not great.");
			    add("A really bad, horrible book.");
			    add("At least it isn't a horrible book.");
			    add(":) and :D");
			    add("");
			    add("Today sux");
			    add("Today sux!");
			    add("Today SUX!");
			    add("Today kinda sux! But I'll get by, lol");
			}};

			SentimentAnalysis sa = new SentimentAnalysis(new English(), new TokenizerEnglish());

			for (String sentence : sentences) {
			    System.out.println(sentence);
			    System.out.println(sa.getSentimentAnalysis(sentence).toString());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		RedditManager.shutdown();
	}
}
