package pattern;

import auxiliary.Dish;
import auxiliary.Person;

import java.util.*;

public class DishSelectionStrategy implements SelectionStrategy<Dish> {
    public Map<Dish, Double> selection(Map<Dish, Double> statistics, int maximum){
        Map<Dish, Double> results = new HashMap<>();
        Map<Dish, Double> waitresults = new HashMap<>();
        Set<Dish> set = new TreeSet<>(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                if(statistics.get(o1) > statistics.get(o2)) return -1;
                else if(statistics.get(o1) < statistics.get(o2)) return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        set.addAll(statistics.keySet());

        double rank = 0.0, score = Integer.MIN_VALUE;
        Iterator<Dish> iterator = set.iterator();

//看第k个和第k+1个是否相等；如果不相等(或仅有k个人)直接取前k个，否则设相等得分为s,不选前k个中与s相等的候选者。这次循环结束后score为第k个人的分数
        for(int i = 0; i < Math.min(maximum, statistics.size()); i++) {
            score = statistics.get(iterator.next());
        }
        if(!iterator.hasNext() || statistics.get(iterator.next()) != score) {
            iterator = set.iterator();
            for(int i = 0; i < Math.min(maximum, statistics.size()); i++) {
                results.put(iterator.next(), ++rank);
            }
        }
        else{
            iterator = set.iterator();
            Dish dish= iterator.next();
            for (int i=0;i<maximum;i++){
                results.put(dish, ++rank);
                dish = iterator.next();
            }


        }
        return results;
    }
}
