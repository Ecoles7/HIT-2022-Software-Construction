package pattern;

import java.util.Map;

public interface SelectionStrategy<C> {

	// TODO
    public Map<C, Double> selection(Map<C, Double> statistics, int maximum);

}
