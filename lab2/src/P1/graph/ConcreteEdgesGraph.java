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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    public ConcreteEdgesGraph() {
    }
    // TODO checkRep
    // checkRep
    private void checkRep() {
        for (Edge edge : edges) {
            assert edge.getWeight() > 0;
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
        }
    }
    @Override
    public boolean add(L vertex) {
        //检测是否已经含有这个元素
        if(vertices.contains(vertex))
        {
            System.out.println("添加失败，该元素已存在");
            return false;
        }
        vertices.add(vertex);
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        //若输入的权重小于0，不符合要求，返回-1
        if(weight<0)
            return -1;
        //如果之前没有这些点，先给加上去
        if (weight != 0) {
            vertices.add(source);
            vertices.add(target);
        }
        //判断这两个点是否已经存在边，若存在则需要先删除再添加
        Iterator<Edge<L>> it = edges.iterator();  //使用迭代器
        while (it.hasNext()) {
            Edge temp = it.next();
            if (temp.getSource().equals(source) && temp.getTarget().equals(target)) {
                int oldWeight = temp.getWeight();
                it.remove();
                if(weight!=0) {  //如果权重为0则删除旧边即可，若大于0则需要添加新的边
                Edge newEdge = new Edge(source, target, weight); // 因为Edge是不可变的，所以用这种方式修改边权
                edges.add(newEdge);
                }
                return oldWeight;
            }
        }
        //若不存在旧边，若新的权重值不为0，则直接添加新边；若新的权重仍为0，则不添加边直接返回0
        if (weight == 0)
        {
            return 0;
        } else
        {
            Edge newEdge = new Edge(source, target, weight);
            edges.add(newEdge);
            return 0;
        }
    }
    
    @Override
    public boolean remove(L vertex) {
        if (vertices.contains(vertex))
        {
            vertices.remove(vertex);
            edges.removeIf(temp -> temp.getSource().equals(vertex) || temp.getTarget().equals(vertex)); //使用removeif过滤集合元素
            checkRep();
            return true;
        } else
            return false;
    }
    
    @Override public Set<L> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        checkRep();
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        checkRep();
        return targets;
    }
    
    // TODO toString()
    public String toString() {
        StringBuilder NA = new StringBuilder();
        for (Edge<L> edge : edges) {
            NA.append(edge.toString());
        }
        checkRep();
        return NA.toString();
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
    private final L source;
    private final L target;
    private final int weight;
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    // TODO checkRep
    public void checkRep() {
        assert this.weight > 0;
        assert this.source != null;
        assert this.target != null;
    }
    // TODO methods
    public L getSource() {
        checkRep();
        return source;
    }

    public L getTarget() {
        checkRep();
        return target;
    }

    public int getWeight() {
        checkRep();
        return weight;
    }
    // TODO toString()
    @Override
    public String toString() {
        String kiki = this.source + "->" + this.target + " (" + this.weight + ")" + '\n';
        checkRep();
        return kiki;
    }
}
