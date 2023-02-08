/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    public ConcreteVerticesGraph() {
    }
    // TODO checkRep
    public void checkRep() {
        Set<Vertex<L>> testSet = new HashSet<>(vertices);
        assert testSet.size() == vertices.size();
        for (Vertex<L> v : testSet) {
            assert vertices.contains(v);
        }
    }

    @Override
    public boolean add(L vertex) {
        for (Vertex<L> v : vertices) {
            if (v.getName().equals(vertex)) {
                return false;
            }
        }
        vertices.add(new Vertex<>(vertex));
        checkRep();
        return true;
    }
    
    @Override public int set(L source,L target, int weight) {
        this.add(source);
        this.add(target);
        int oriweight=0;
        if(weight<0)
            return -1;
        Iterator<Vertex<L>> it = vertices.iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            if (v.getName().equals(source)) {
                oriweight=v.addTarget(target, weight);
            }
            if (v.getName().equals(target)) {
                v.addSouce(source, weight);
            }
        }
        checkRep();
        return oriweight;
}
    
    @Override
    public boolean remove(L vertex) {
        //throw new RuntimeException("not implemented");
        boolean flag = false;
        Iterator<Vertex<L>> it = vertices.iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            if (v.getName() == vertex) {
                it.remove();
                flag = true;
            } else {
                if (v.getSources().containsKey(vertex)) {
                    v.removeSource(vertex);
                }
                if (v.getTargets().containsKey(vertex)) {
                    v.removeTarget(vertex);
                }
            }
        }
        checkRep();
        return flag;
    }
    
    @Override public Set<L> vertices() {
        Set set = new HashSet<>();
        for (Vertex v : vertices) {
            set.add(v.getName());
        }
        checkRep();
        return set;
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        //throw new RuntimeException("not implemented");
        Map<L, Integer> map = new HashMap<>();
        for (Vertex<L> v : vertices) {
            if (v.getName() == target) {
                map = v.getSources();
                break;
            }
        }
        return new HashMap<L, Integer>(map);
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> map = new HashMap<>();
        for (Vertex<L> v : vertices) {
            if (v.getName() == source) {
                map = v.getTargets();
                break;
            }
        }
        checkRep();
        return new HashMap<L, Integer>(map);
    }
    
    // TODO toString()
    @Override
    public String toString() {
        StringBuilder re = new StringBuilder();
        for (Vertex<L> v : vertices) {
            re.append(v.toString());
        }
        return re.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
    private final L name; // 保存该点的名字
    private Map<L, Integer> sources = new HashMap<>(); // 保存该点的父节点
    private Map<L, Integer> targets = new HashMap<>(); // 保存该点的子节点
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    public Vertex(L name) {
        this.name = name;
        this.sources = new HashMap<>();
        this.targets = new HashMap<>();
    }
    // TODO checkRep
    
    // TODO methods
    public L getName() {
        return name;
    }

    public Map<L, Integer> getSources() {
       return new HashMap<L, Integer>(sources);
    }

    public Map<L, Integer> getTargets() {
        return new HashMap<L, Integer>(targets);
    }

    public int addTarget(L target, int weight) {
        Integer OriWeight = 0;
        if (weight == 0) {
            OriWeight = this.removeTarget(target);
        } else if (weight > 0) {
            OriWeight = targets.put(target, weight);
            if(OriWeight == null )
                return 0 ;
            else
                return OriWeight;
        }
        return OriWeight;
    }

    public int removeTarget(L target) {
        Integer weight = targets.remove(target);
        if(weight == null)
            return 0;
        else
            return weight;
    }

    public int addSouce(L source, int weight) {
        Integer OriWeight = 0;
        if (weight == 0) {
            OriWeight = this.removeSource(source);
        } else if (weight > 0) {
            OriWeight = sources.put(source, weight);
            if(OriWeight == null )
                return 0 ;
            else
                return OriWeight;
        }
        return OriWeight;
    }

    public int removeSource(L source) {
        Integer weight = sources.remove(source);
        if(weight == null)
            return 0;
        else
            return weight;
    }
    // TODO toString()
    @Override
    public String toString() {
        return String.format("Vertex %s has %d sources and %d targets", this.getName().toString(),
                this.getSources().size(), this.getTargets().size());
    }
    
}
