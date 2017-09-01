package crawler;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import crawler.entity.RedditPost;
import crawler.properties.RedditProperties;
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

public class DictionaryGenerator {

	
	public static void main(String[] args) {
		
		try {
			new RedditProperties();
			RedditPost teste = RedditManager.getAllPosts().get(3);
			/*Deverá ser extraída a entidade e a opinião relacionada com aquela entidade através do dependency parser*/
			/*https://stackoverflow.com/questions/7443330/how-do-i-do-dependency-parsing-in-nltk*/
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, depparse");
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	        Annotation document = new Annotation(teste.getBody());
	        pipeline.annotate(document);
			RedditManager.shutdown();
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    // this is the NER label of the token
			    String ne = token.get(NamedEntityTagAnnotation.class);
			    System.out.println(word + "->" + pos + "|" + ne);
			  }

			  // this is the parse tree of the current sentence
			  Tree tree = sentence.get(TreeAnnotation.class);

			  // this is the Stanford dependency graph of the current sentence
			  SemanticGraph sg = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
		      System.out.println(IOUtils.eolChar + sg.toString(SemanticGraph.OutputFormat.LIST));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
