package crawler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import crawler.entity.RedditPost;
import crawler.entity.Sentence;
import crawler.entity.WordDependency;
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
import vader.lexicon.English;

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

public class DictionaryGenerator {

	public static void main(String[] args) {

		try {
			new RedditProperties();
			List<String> authorList = RedditManager.getAllAuthorsByParentId("t3_5qqa51");
			
			int authorCount = 0, postCount = 0;
			for (String author : authorList) {
				List<String> targetList = new ArrayList();
				List<String> opinionList = new ArrayList();
				authorCount = authorCount + 1;
				//System.out.println("Autor " + authorCount + " de: " + authorList.size());

				if (author.equals("[deleted]")) {
					System.out.println("Autor Removido.");
					continue;
				}

				List<RedditPost> postList = RedditManager.getAllPostsByAuthor(author);
				postCount = 0;
				for (RedditPost post : postList) {
					try {
						postCount = postCount + 1;
						System.out.println("Passo 1: Post " + postCount + " de: " + postList.size());

						List<Sentence> sentenceList = RedditManager.getAllSentencesByPost(post.getId());

						for (Sentence dbSentence : sentenceList) {
							System.out.println(dbSentence.getId() + " - " + dbSentence.getSentence());
							targetList.addAll(extractTargetsUsingOpinions(dbSentence));

						}
					} catch (OutOfMemoryError e) {
						System.out.println(e);
					}
				}
				postCount = 0;
				for (RedditPost post : postList) {
					try {
						postCount = postCount + 1;
						System.out.println("Passo 2: Post " + postCount + " de: " + postList.size());
						List<Sentence> sentenceList = RedditManager.getAllSentencesByPost(post.getId());

						for (Sentence dbSentence : sentenceList) {
							System.out.println(dbSentence.getId() + " - " + dbSentence.getSentence());
							opinionList = extractOpinionsUsingTargets(dbSentence, targetList);

						}
					} catch (OutOfMemoryError e) {
						System.out.println(e);
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		RedditManager.shutdown();
	}

	/*TODO -> Refazer levando em consideração o tipo de dependencia*/
	private static List<String> extractTargetsUsingOpinions(Sentence dbSentence) {

		List<String> ruleList = new ArrayList();
		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if (English.WORD_VALENCE_DICTIONARY.containsKey(wd.getDependent())) {
				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());

				// Regra 1.1
				if (sfSentence.posTag(Integer.parseInt(wd.getGovernorId()) - 1).compareTo("NN") == 0) {
					System.out.println("Regra 1.1: " + wd.getGovernor() + " -> " + wd.getDependent());
					ruleList.add(wd.getGovernor());
				}

				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if ((wd2.getGovernorId().compareTo(wd.getGovernorId())==0) && (wd2.getId() != wd.getId())) {
						try{
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("NN") == 0) {
								System.out.println("Regra 1.2: " + wd.getDependent() + " -> " + wd.getGovernor() + " <- "
										+ wd2.getDependent());
								ruleList.add(wd2.getDependent());
								
								
							}
						}catch(IndexOutOfBoundsException e){
							System.out.println(e.toString());
						}

					}

				}

			}
		}
		return ruleList;
	}

	/*TODO -> Refazer levando em consideração o tipo de dependencia*/
	private static List<String> extractOpinionsUsingTargets(Sentence dbSentence, List<String> targetList) {

		List<String> ruleList = new ArrayList();
		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if ((targetList.contains(wd.getGovernor())) && (!English.WORD_VALENCE_DICTIONARY.containsKey(wd.getDependent()))){
				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());

				// Regra 1.1
				if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0) {
					System.out.println("Regra 2.1: " + wd.getGovernor() + " -> " + wd.getDependent());
					ruleList.add(wd.getDependent());
				}

				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if ((wd2.getGovernorId().compareTo(wd.getGovernorId())==0) && (wd2.getId() != wd.getId())) {
						try{
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("JJ") == 0) {
								System.out.println("Regra 2.2: " + wd.getDependent() + " -> " + wd.getGovernor() + " <- "
										+ wd2.getDependent());
								ruleList.add(wd.getDependent());
							}
						}catch(IndexOutOfBoundsException e){
							System.out.println(e.toString());
						}

					}

				}

			}
		}
		return ruleList;
	}
}
