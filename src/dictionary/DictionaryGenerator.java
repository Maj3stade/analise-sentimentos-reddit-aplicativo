package dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import crawler.entity.WordScore;
import dictionary.properties.DictionaryProperties;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import crawler.entity.Dictionary;
import crawler.entity.RedditPost;
import crawler.entity.Sentence;
import crawler.entity.WordDependency;
import manager.RedditManager;
import vader.lexicon.English;

public class DictionaryGenerator {

	private List<Sentence> sentenceList = new ArrayList<>();
	private List<String> targetList = new ArrayList<>();
	private Dictionary dictionary = new Dictionary();

	public void generateDictionaryFromSentences(Boolean adNauseum) {

		// System.out.println("Pesquisando todas sentenças...");
		// sentenceList = RedditManager.getAllSentences();

		int authorCount = 0, postCount = 0;

		/*
		 * for (RedditPost post :
		 * RedditManager.getAllPostsByThread("t3_5qqa51")) {
		 * sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId())
		 * ); }
		 */

		int targetListSize = 0, dictionarySize = 0, sentenceNumber = 0;
		sentenceList = new ArrayList<>();
		targetList = new ArrayList<>();
		dictionary = new Dictionary();

		authorCount = authorCount + 1;

		
		
		for (RedditPost post : RedditManager.getAllPostsByThread("t3_4d75i7")) {
			sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
		}
		for (RedditPost post : RedditManager.getAllPostsByThread("t3_5exz2e")) {
			sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
		}
		for (RedditPost post : RedditManager.getAllPostsByThread("t3_67ivae")) {
			sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
		}
		for (RedditPost post : RedditManager.getAllPostsByThread("t3_6cqdye")) {
			sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
		}
		for (RedditPost post : RedditManager.getAllPostsByThread("t3_5uzetf")) {
			sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
		}
		for (RedditPost post : RedditManager.getAllPostsByThread("t3_5qqa51")) {
			sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
		}

		System.out.println("Lendo o dicionário padrão...");
		dictionary.setDictionary(English.getWordValenceDictionaryObject());
		System.out.println("Processando sentenças...");

		targetListSize = targetList.size();
		dictionarySize = dictionary.getDictionary().size();
		System.out.println("Número de Targets: " + targetListSize);
		System.out.println("Número de Palavras: " + dictionarySize);
		adNauseum = true;
		while (adNauseum) {

			targetListSize = targetList.size();
			dictionarySize = dictionary.getDictionary().size();
			sentenceNumber = 0;
			System.out.println("Sentença: " + sentenceNumber + " de: " + sentenceList.size());
			for (Sentence dbSentence : sentenceList) {

				sentenceNumber = sentenceNumber + 1;
				if (sentenceNumber % 1000 == 0) {
					System.out.println("Sentença: " + sentenceNumber + " de: " + sentenceList.size());
				}
				try {
					targetList = (extractTargetsUsingOpinions(dbSentence, targetList));
					dictionary = (extractOpinionsUsingTargets(dbSentence, dictionary));
					targetList = (extractTargetsUsingTargets(dbSentence, targetList));
					dictionary = (extractOpinionsUsingOpinions(dbSentence, dictionary));
				} catch (java.lang.IndexOutOfBoundsException e) {
					System.out.println(dbSentence.getId() + " - " + dbSentence.getSentence());
					System.out.println(e);
				}

			}

			System.out.println("Número de Targets Anterior: " + targetListSize);
			System.out.println("Número de Palavras Anterior: " + dictionarySize);
			System.out.println("Número de Targets: " + targetList.size());
			System.out.println("Número de Palavras: " + dictionary.getDictionary().size());

			if (targetList.size() != targetListSize || dictionarySize != dictionary.getDictionary().size()) {
				System.out.println("Continuando...");
			} else {
				System.out.println("Limite Alcançado...Parando o processo...");
				adNauseum = false;
			}

			System.out.println("---------------------------------------------");
		}
		RedditManager.saveDictionary(dictionary);

	}

	private List<String> extractTargetsUsingOpinions(Sentence dbSentence, List<String> targetList) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if (dictionary.contains(wd.getDependent())) {

				if (targetList.contains(wd.getGovernor()))
					continue;

				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());

				// Regra 1.1
				if ((sfSentence.posTag(Integer.parseInt(wd.getGovernorId()) - 1).compareTo("NN") == 0)
						&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {
					// System.out.println("Regra 1.1: " + wd.getGovernor() + "
					// -> " + wd.getDependent());

					targetList.add(wd.getGovernor());
				}

				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if (targetList.contains(wd2.getDependent()))
						continue;

					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("NN") == 0) {
								/*
								 * System.out.println("Regra 1.2: " +
								 * wd.getDependent() + " -> " + wd.getGovernor()
								 * + " <- " + wd2.getDependent());
								 */
								targetList.add(wd2.getDependent());

							}
						} catch (IndexOutOfBoundsException e) {
							System.out.println(e.toString());
						}

					}

				}

			}
		}
		return targetList;
	}

	private Dictionary extractOpinionsUsingTargets(Sentence dbSentence, Dictionary dictionary) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if ((targetList.contains(wd.getGovernor())) && (!dictionary.contains(wd.getDependent()))) {
				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());

				// Regra 1.1
				if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0
						&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {
					// System.out.println("Regra 2.1: " + wd.getGovernor() + "
					// -> " + wd.getDependent());
					dictionary.addWord(wd.getDependent(), "r21" + wd.getType());
					continue;
				}

				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {

					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0) {
								/*
								 * System.out.println("Regra 2.2: " +
								 * wd.getDependent() + " -> " + wd.getGovernor()
								 * + " <- " + wd2.getDependent());
								 */
								dictionary.addWord(wd.getDependent(), "r22" + wd.getType());
							}
						} catch (IndexOutOfBoundsException e) {
							System.out.println(e.toString());
						}

					}

				}

			}
		}
		return dictionary;
	}

	private List<String> extractTargetsUsingTargets(Sentence dbSentence, List<String> targetList) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if (targetList.contains(wd.getGovernor()) && !targetList.contains(wd.getDependent())) {
				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());

				// Regra 3.1
				if ((sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("NN") == 0)
						&& (DictionaryProperties.conjDependency.contains(wd.getType()))) {
					// System.out.println("Regra 3.1: " + wd.getGovernor() + "
					// -> " + wd.getDependent());

					targetList.add(wd.getDependent());
				}
			}
			if (targetList.contains(wd.getDependent())) {
				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());
				// Regra 3.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if (targetList.contains(wd2.getDependent()))
						continue;

					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (wd2.getType() == wd.getType())) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("NN") == 0) {
								/*
								 * System.out.println("Regra 3.2: " +
								 * wd.getDependent() + " -> " + wd.getGovernor()
								 * + " <- " + wd2.getDependent());
								 */
								targetList.add(wd2.getDependent());

							}
						} catch (IndexOutOfBoundsException e) {
							System.out.println(e.toString());
						}

					}

				}

			}
		}
		return targetList;
	}

	private Dictionary extractOpinionsUsingOpinions(Sentence dbSentence, Dictionary dictionary) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if ((dictionary.contains(wd.getGovernor())) && (!dictionary.contains(wd.getDependent()))) {
				edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
						dbSentence.getSentence());

				// Regra 1.1
				if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0
						&& (DictionaryProperties.conjDependency.contains(wd.getType()))) {
					// System.out.println("Regra 4.1: " + wd.getGovernor() + "
					// -> " + wd.getDependent());
					dictionary.addWord(wd.getDependent(), "r41" + wd.getType());
				}
			}
			if (dictionary.contains(wd.getDependent())) {
				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
							dbSentence.getSentence());
					if (dictionary.contains(wd2.getDependent()))
						continue;

					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (wd.getType() == wd2.getType())) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("JJ") == 0) {
								/*
								 * System.out.println("Regra 4.2: " +
								 * wd.getDependent() + " -> " + wd.getGovernor()
								 * + " <- " + wd2.getDependent());
								 */
								dictionary.addWord(wd2.getDependent(), "r42" + wd.getType());
							}
						} catch (IndexOutOfBoundsException e) {
							System.out.println(e.toString());
						}

					}

				}

			}
		}
		return dictionary;
	}

}
