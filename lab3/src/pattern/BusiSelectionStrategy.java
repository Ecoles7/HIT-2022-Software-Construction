package pattern;

import auxiliary.Dish;
import auxiliary.Person;
import auxiliary.Proposal;

import java.util.*;

public class BusiSelectionStrategy implements SelectionStrategy<Proposal> {
    public Map<Proposal, Double> selection(Map<Proposal, Double> statistics, int maximum){
        Map<Proposal, Double> results = new HashMap<>();
        double rank = 0.0;
        for(Proposal key : statistics.keySet()){
            Double value = statistics.get(key);
            if(value > 2.0/3.0){
                results.put(key,++rank);
            }
        }
        return results;

    }

}
