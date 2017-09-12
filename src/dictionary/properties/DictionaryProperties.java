package dictionary.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryProperties {
	public static final List<String> mrDependencies = new ArrayList();
	public static final List<String> conjDependency = new ArrayList();
    static
    {
    	mrDependencies.add("nsubj");
    	mrDependencies.add("nsubjpass");
    	mrDependencies.add("obj");
    	mrDependencies.add("iobj");
    	mrDependencies.add("nummod");
    	mrDependencies.add("appos");
    	mrDependencies.add("nmod");
    	mrDependencies.add("nmod:npmod");
    	mrDependencies.add("nmod:tmod");
    	mrDependencies.add("nmod:poss");
    	mrDependencies.add("vocative");
    	mrDependencies.add("discourse");
    	mrDependencies.add("expl");
    	conjDependency.add("conj");
    }
}
