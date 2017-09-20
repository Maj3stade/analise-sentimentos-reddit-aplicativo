package runnables;

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

public class CustomSentiment {

	public static void main(String[] args) {

		try {
			new RedditProperties();
			List<RedditPost> postList = RedditManager.getAllPostsByThread("t3_5qqa51");
			
			SentimentAnalysis domainAnalysis = new SentimentAnalysis(new EnglishDomain(), new TokenizerEnglish());
			

			for (RedditPost post : postList) {
				SentimentAnalysis authorAnalysis = new SentimentAnalysis(new EnglishAuthor(post.getAuthor()), new TokenizerEnglish());
			    System.out.println(post.getBody());
			    System.out.println(sa.getSentimentAnalysis(sentence).toString());
			}
			
			
			int authorCount = 0, postCount = 0;
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, pos, depparse");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

			for (String author : authorList) {
				authorCount = authorCount + 1;
				System.out.println("Autor " + authorCount + " de: " + authorList.size());

				if (author.equals("[deleted]")) {
					System.out.println("Autor Removido.");
					continue;
				}

				List<RedditPost> postList = RedditManager.getNotOkPostsByAuthor(author);
				postCount = 0;
				for (RedditPost post : postList) {
					try {
						postCount = postCount + 1;
						/*System.out.println("Post " + postCount + " de: " + postList.size());
						System.out.println(post.getId() + " : " + post.getBody());*/
						/*
						 * Deverá ser extraída a entidade e a opinião
						 * relacionada com aquela entidade através do dependency
						 * parser
						 */
						/*
						 * https://stackoverflow.com/questions/7443330/how-do-i-
						 * do- dependency-parsing-in-nltk
						 */
						Annotation document = new Annotation(post.getBody());
						pipeline.annotate(document);
						List<CoreMap> sentences = document.get(SentencesAnnotation.class);
						List<Sentence> dbSentences = new ArrayList<>();
						for (CoreMap sentence : sentences) {
							/*
							 * for (CoreLabel token :
							 * sentence.get(TokensAnnotation.class)) { String
							 * word = token.get(TextAnnotation.class); String
							 * pos = token.get(PartOfSpeechAnnotation.class);
							 * String ne =
							 * token.get(NamedEntityTagAnnotation.class);
							 * System.out.println(word + "->" + pos + "|" + ne);
							 * } Tree tree = sentence.get(TreeAnnotation.class);
							 */

							// this is the Stanford dependency graph of the
							// current
							// sentence
							SemanticGraph sg = sentence
									.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
							Sentence dbSentence = new Sentence();
							dbSentence.setPostId(post.getId());
							dbSentence.setSentence(sentence.toString());
							dbSentence.setWordDependencyList(
									parseXML(sg.toString(SemanticGraph.OutputFormat.XML), dbSentence));
							dbSentences.add(dbSentence);

						}
						/*System.out.println("Salvando sentença e dependencia.");*/
						RedditManager.saveSentenceDependency(dbSentences);
					} catch (OutOfMemoryError  e) {
						System.out.println(e);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		RedditManager.shutdown();

	}

	private static List<WordDependency> parseXML(String xml, Sentence parentSentence) {
		List<WordDependency> wordDep = new ArrayList<>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name())));

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("dep");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					WordDependency word = new WordDependency();
					Element eElement = (Element) nNode;
					word.setType(eElement.getAttributes().getNamedItem("type").getNodeValue());
					word.setDependent(eElement.getElementsByTagName("dependent").item(0).getTextContent());
					word.setDependentId(eElement.getElementsByTagName("dependent").item(0).getAttributes()
							.getNamedItem("idx").getNodeValue());
					word.setGovernor(eElement.getElementsByTagName("governor").item(0).getTextContent());
					word.setGovernorId(eElement.getElementsByTagName("governor").item(0).getAttributes()
							.getNamedItem("idx").getNodeValue());
					word.setSentence(parentSentence);
					wordDep.add(word);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordDep;
	}

}
