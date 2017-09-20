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
import vader.SentimentAnalysis;
import vader.lexicon.English;
import vader.text.TokenizerEnglish;

public class DictionaryGenerator {

	private List<Sentence> sentenceList = new ArrayList<>();
	private Dictionary targetList = new Dictionary();
	private Dictionary dictionary = new Dictionary();
	private SentimentAnalysis sa = new SentimentAnalysis(new English(), new TokenizerEnglish());

	public void generateDictionaryFromSentences() {

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
		Boolean status = false;
		sentenceList = new ArrayList<>();
		targetList = new Dictionary();
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

		targetListSize = targetList.getDictionary().size();
		dictionarySize = dictionary.getDictionary().size();
		System.out.println("Número de Targets: " + targetListSize);
		System.out.println("Número de Palavras: " + dictionarySize);
		Boolean adNauseum = true;
		while (adNauseum) {
			
			targetListSize = targetList.getDictionary().size();
			dictionarySize = dictionary.getDictionary().size();
			sentenceNumber = 0;
			System.out.println("Sentença: " + sentenceNumber + " de: " + sentenceList.size());
			for (Sentence dbSentence : sentenceList) {
				
				sentenceNumber = sentenceNumber + 1;
				if (sentenceNumber % 1000 == 0) {
					System.out.println("Sentença: " + sentenceNumber + " de: " + sentenceList.size());
				}
				try {

					edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
							dbSentence.getSentence());
					targetList = (extractTargetsUsingOpinions(dbSentence, sfSentence, targetList));
					dictionary = (extractOpinionsUsingTargets(dbSentence, sfSentence, dictionary));
					targetList = (extractTargetsUsingTargets(dbSentence, sfSentence, targetList));
					dictionary = (extractOpinionsUsingOpinions(dbSentence, sfSentence, dictionary));
				} catch (java.lang.IndexOutOfBoundsException e) {
					System.out.println(dbSentence.getId() + " - " + dbSentence.getSentence());
					System.out.println(e);
				}

			}

			System.out.println("Número de Targets Anterior: " + targetListSize);
			System.out.println("Número de Palavras Anterior: " + dictionarySize);
			System.out.println("Número de Targets: " + targetList.getDictionary().size());
			System.out.println("Número de Palavras: " + dictionary.getDictionary().size());

			if (targetList.getDictionary().size() != targetListSize
					|| dictionarySize != dictionary.getDictionary().size()) {
				System.out.println("Continuando...");
			} else {
				System.out.println("Limite Alcançado...Parando o processo...");
				adNauseum = false;
			}
			System.out.println("---------------------------------------------");
		}
		RedditManager.saveDictionary(dictionary);

	}

	public void generateDictionaryFromSentencesByAuthor() {

		// System.out.println("Pesquisando todas sentenças...");
		// sentenceList = RedditManager.getAllSentences();

		int authorCount = 0, postCount = 0;

		/*
		 * for (RedditPost post :
		 * RedditManager.getAllPostsByThread("t3_5qqa51")) {
		 * sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId())
		 * ); }
		 */

		

		List<String> authorList = RedditManager.getAllAuthorsByParentId("t3_5qqa51");
		for (String author : authorList) {
			authorCount = authorCount + 1;
			sentenceList = new ArrayList<>();
			targetList = new Dictionary();
			dictionary = new Dictionary();
			System.out.println("Autor: " + authorCount + " de " + authorList.size());
			
			if(author.equalsIgnoreCase("[deleted]"))
				continue;
			int targetListSize = 0, dictionarySize = 0, sentenceNumber = 0;
			List<RedditPost> postList = RedditManager.getAllNotOkPostsByAuthor(author);
			for (RedditPost post : postList) {
				sentenceList.addAll(RedditManager.getAllSentencesByPost(post.getId()));
			}

			/*List<Sentence> sentenceList = RedditManager.getAllNotOkSentencesByAuthor(author);
			*/
			System.out.println("Lendo o dicionário padrão...");
			dictionary.setDictionary(English.getWordValenceDictionaryObject());
			System.out.println("Processando sentenças...");

			targetListSize = targetList.getDictionary().size();
			dictionarySize = dictionary.getDictionary().size();
			System.out.println("Número de Targets: " + targetListSize);
			System.out.println("Número de Palavras: " + dictionarySize);
			Boolean adNauseum = true;
			while (adNauseum) {

				targetListSize = targetList.getDictionary().size();
				dictionarySize = dictionary.getDictionary().size();
				sentenceNumber = 0;
				System.out.println("Sentença: " + sentenceNumber + " de: " + sentenceList.size());
				for (Sentence dbSentence : sentenceList) {

					sentenceNumber = sentenceNumber + 1;
					if (sentenceNumber % 1000 == 0) {
						System.out.println("Sentença: " + sentenceNumber + " de: " + sentenceList.size());
					}
					try {
						edu.stanford.nlp.simple.Sentence sfSentence = new edu.stanford.nlp.simple.Sentence(
								dbSentence.getSentence());
						targetList = (extractTargetsUsingOpinions(dbSentence, sfSentence, targetList));
						dictionary = (extractOpinionsUsingTargets(dbSentence, sfSentence, dictionary));
						targetList = (extractTargetsUsingTargets(dbSentence, sfSentence, targetList));
						dictionary = (extractOpinionsUsingOpinions(dbSentence, sfSentence, dictionary));
					} catch (java.lang.IndexOutOfBoundsException e) {
						System.out.println(dbSentence.getId() + " - " + dbSentence.getSentence());
						System.out.println(e);
					}

				}

				System.out.println("Número de Targets Anterior: " + targetListSize);
				System.out.println("Número de Palavras Anterior: " + dictionarySize);
				System.out.println("Número de Targets: " + targetList.getDictionary().size());
				System.out.println("Número de Palavras: " + dictionary.getDictionary().size());

				if (targetList.getDictionary().size() != targetListSize
						|| dictionarySize != dictionary.getDictionary().size()) {
					System.out.println("Continuando...");
				} else {
					System.out.println("Limite Alcançado...Parando o processo...");
					adNauseum = false;
				}

				System.out.println("---------------------------------------------");
			}
			System.out.println("Dicionario: " + dictionary.getDictionary().size());
			if (sentenceList.size() > 0)
				RedditManager.saveDictionary(dictionary, author);
		}

	}

	private Dictionary extractTargetsUsingOpinions(Sentence dbSentence, edu.stanford.nlp.simple.Sentence sfSentence, Dictionary targetList) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if (dictionary.contains(wd.getDependent())) {

				if (targetList.contains(wd.getGovernor()))
					continue;


				// Regra 1.1
				if ((sfSentence.posTag(Integer.parseInt(wd.getGovernorId()) - 1).compareTo("NN") == 0)
						&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {
					/* System.out.println("Regra 1.1: " + wd.getGovernor() + " -> " + wd.getDependent() + " - " + dictionary.getScore(wd.getDependent()));
*/
					targetList.addTargetWord(wd.getGovernor(), dictionary.getScore(wd.getDependent()),
							"r11",
							dbSentence.getId(), sentenceList);
				}

				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if (targetList.contains(wd2.getDependent()))
						continue;

					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("NN") == 0) {
								if(!dictionary.contains(wd.getDependent()))
									continue;
								
								 /* System.out.println("Regra 1.2: " +
								  wd.getDependent() + " -> " + wd.getGovernor()
								  + " <- " + wd2.getDependent() + " - Score: " + dictionary.getScore(wd.getDependent().toString()));
								 */

								targetList.addTargetWord(wd2.getDependent(), dictionary.getScore(wd.getDependent()),
										"r12",
										dbSentence.getId(), sentenceList);

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

	private Dictionary extractOpinionsUsingTargets(Sentence dbSentence, edu.stanford.nlp.simple.Sentence sfSentence, Dictionary dictionary) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {


			if ((targetList.contains(wd.getGovernor())) && (!dictionary.contains(wd.getDependent()))) {

				// Regra 1.1
				if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0
						&& (DictionaryProperties.mrDependencies.contains(wd.getType()))) {

					
					/* System.out.println("Regra 2.1: " + wd.getGovernor() +
					  "-> " + wd.getDependent());*/
					 
					dictionary.addWord(wd.getDependent(), getWordScore(wd.getDependent(), wd.getGovernor(), targetList.getScore(wd.getGovernor()), dbSentence),
							"r21");
					continue;
				}
			}

			// Regra 1.2
			for (WordDependency wd2 : dbSentence.getWordDependencyList()) {

				if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
						&& (DictionaryProperties.mrDependencies.contains(wd.getType())) &&
						(targetList.contains(wd2.getDependent()))) {
					try {
						if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0) {
							if (dictionary.contains(wd.getDependent()))
								continue;

							
							 /* System.out.println("Regra 2.2: " +
							  wd.getDependent() + " -> " + wd.getGovernor() +
							  " <- " + wd2.getDependent());*/
							 

							dictionary.addWord(wd.getDependent(),
									getWordScore(wd.getDependent(), wd2.getDependent(), targetList.getScore(wd2.getDependent()), dbSentence),
									"r22");
						}
					} catch (IndexOutOfBoundsException e) {
						System.out.println(e.toString());
					}

				}

			}

		}
		return dictionary;
	}

	private Dictionary extractTargetsUsingTargets(Sentence dbSentence, edu.stanford.nlp.simple.Sentence sfSentence, Dictionary targetList) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if (targetList.contains(wd.getGovernor()) && !targetList.contains(wd.getDependent())) {

				// Regra 3.1
				if ((sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("NN") == 0)
						&& (DictionaryProperties.conjDependency.contains(wd.getType()))) {
				//	System.out.println("Regra 3.1: " + wd.getGovernor() + "-> " + wd.getDependent() + " - Score: " + targetList.getScore(wd.getGovernor()));
					targetList.addTargetWord(wd.getDependent(), targetList.getScore(wd.getGovernor()),
							"r31",
							dbSentence.getId(), sentenceList);

				}
			}
			if (targetList.contains(wd.getDependent())) {
				// Regra 3.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if (targetList.contains(wd2.getDependent()))
						continue;

					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (wd.getType().compareTo(wd2.getType()) == 0)) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("NN") == 0) {
								
								if(!targetList.contains(wd.getDependent()))
									continue;
								
							/*	  System.out.println(dbSentence.getSentence());
								  System.out.println("Regra 3.2: " +
								  wd.getDependent() + " -> " + wd.getGovernor()
								  + " <- " + wd2.getDependent() + " - Score:" + targetList.getScore(wd.getDependent()));*/
								 
								targetList.addTargetWord(wd2.getDependent(), targetList.getScore(wd.getDependent()), "r32",
										dbSentence.getId(), sentenceList);

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

	private Dictionary extractOpinionsUsingOpinions(Sentence dbSentence, edu.stanford.nlp.simple.Sentence sfSentence, Dictionary dictionary) {

		for (WordDependency wd : dbSentence.getWordDependencyList()) {
			if ((dictionary.contains(wd.getGovernor())) && (!dictionary.contains(wd.getDependent()))) {

				// Regra 1.1
				if (sfSentence.posTag(Integer.parseInt(wd.getDependentId()) - 1).compareTo("JJ") == 0
						&& (DictionaryProperties.conjDependency.contains(wd.getType()))) {

					
					 /* System.out.println("Regra 4.1: " + wd.getGovernor() +
					  " -> " + wd.getDependent() + ":" +
					  dictionary.getScore(wd.getGovernor()));*/
					 
					dictionary.addWord(wd.getDependent(), dictionary.getScore(wd.getGovernor()),
							"r41");
				}
			}

			if (dictionary.contains(wd.getDependent())) {
				// Regra 1.2
				for (WordDependency wd2 : dbSentence.getWordDependencyList()) {
					if (dictionary.contains(wd2.getDependent()))
						continue;


					if ((wd2.getGovernorId().compareTo(wd.getGovernorId()) == 0) && (wd2.getId() != wd.getId())
							&& (wd.getType().compareTo(wd2.getType()) == 0)) {
						try {
							if (sfSentence.posTag(Integer.parseInt(wd2.getDependentId()) - 1).compareTo("JJ") == 0) {

								
								/*  System.out.println("Regra 4.2: " +
								  wd.getDependent() + " -> " + wd.getGovernor()
								  + " <- " + wd2.getDependent() + ":" +
								  dictionary.getScore(wd.getDependent()));*/
								 

								dictionary.addWord(wd2.getDependent(), dictionary.getScore(wd.getDependent()),
										"r42");
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

	private Float getWordScore(String word, String target, Float score, Sentence sentence) {

		int initialPos = 0, finalPos = 0, ix = 0;

		String[] arr = sentence.getSentence().split(" ");
		for (String ss : arr) {
			if (ss.compareTo(word) == 0) {
				if (initialPos == 0)
					initialPos = ix;
				else
					finalPos = ix;
			} else if (ss.compareTo(target) == 0) {
				if (initialPos == 0)
					initialPos = ix;
				else
					finalPos = ix;
			}
			ix = ix + 1;
		}

		for (int i = initialPos; i < finalPos; i++) {

			if (arr[i].contains("’t")) {
				score = score * -1;
			}

			else {
				if (arr[i].equalsIgnoreCase("except"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("however") )
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("but"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("despite"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("though"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("except"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("although"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("oddly"))
					score = score * -1;
				else if (arr[i].equalsIgnoreCase("aside"))
					score = score * -1;

			}
		}
		// System.out.println("getWordScore:" + word + " -> " + target + ":" +
		// sentence.getSentence() + ":" + score);
		return score;
	}

}
