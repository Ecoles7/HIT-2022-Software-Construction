package pattern;

import auxiliary.Person;

import java.util.*;

public class ElectionSelectionStrategy implements SelectionStrategy<Person> {
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics, int maximum){
        Map<Person, Double> results = new HashMap<>();

        Set<Person> set = new TreeSet<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if(statistics.get(o1) > statistics.get(o2)) return -1;
                else if(statistics.get(o1) < statistics.get(o2)) return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        set.addAll(statistics.keySet());

        double rank = 0.0, score = Integer.MIN_VALUE;
        Iterator<Person> iterator = set.iterator();

         //看第k个和第k+1个是否相等；如果不相等(或仅有k个人)直接取前k个，否则设相等得分为s,不选前k个中与s相等的候选者。这次循环结束后score为第k个人的分数
        for(int i = 0; i < Math.min(maximum, statistics.size()); i++) {
            score = statistics.get(iterator.next());
        }

         //仅有k个人(或者少于k个),或第k个和第k+1个不相等
        if(!iterator.hasNext() || statistics.get(iterator.next()) != score) {
            iterator = set.iterator();
            for(int i = 0; i < Math.min(maximum, statistics.size()); i++) {
                results.put(iterator.next(), ++rank);
            }
        }
        else {
            iterator = set.iterator();
            Person person = iterator.next();
            while(statistics.get(person) != score) {
                results.put(person, ++rank);
                person = iterator.next();
            }
        }

        return results;


    }
}
