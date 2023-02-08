/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph

    @Test   //Add测试 1.测试Add后。元素列表中是否完成添加 2.测试重复add是否能够返回false
    public void testAdd() {
        Graph<String> graph =emptyInstance();
        String a="SANA";
        String b="MINA";
        graph.add(a);
        graph.add(b);
        assertEquals(true,graph.vertices().contains(a));
        assertEquals(true,graph.vertices().contains(b));
        assertEquals(false,graph.add(a));
    }

    @Test   //Set测试 1.测试添加边时，若之前存在边，能否正常返回旧边的值 2.添加权重不符合输入要求的边，能否返回-1
    public void testSet() {
        Graph<String> graph =emptyInstance();
        assertEquals(0,graph.set("a", "b", 5));
        assertEquals(0,graph.set("b", "c", 4));
        assertEquals(5,graph.set("a", "b", 6));
        assertEquals(6,graph.set("a", "b", 0));
        assertEquals(0,graph.set("a", "c", 0));
        assertEquals(-1,graph.set("a", "b", -3));
    }

    @Test  //remove测试 1.删除表中存在的一个元素，查看能否返回true 2.删除不存在的一个元素，查看能否返回false
    public void testremove() {
        Graph<String> graph =emptyInstance();
        String a="Take";
        String b="what";
        String c="you";
        String d="want";
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.set(a, b, 5);
        graph.set(b, c, 4);
        assertEquals(true,graph.remove(a));
        assertEquals(false,graph.remove(d));
    }

    @Test  //Sources测试 查看能否正确返回Map表
    public void testSources() {
        testAdd();
        testSet();
        Graph<String> graph =emptyInstance();
        String a="kiki";
        String b="doyou";
        String c="loveme";
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.set(a, b, 5);
        graph.set(b, c, 4);
        graph.set(a, c, 3);
        Map<String,Integer> map=new HashMap<String,Integer>();
        map.put(a,3);
        map.put(b,4);
        assertEquals(map,graph.sources(c));
    }

    @Test  //Targets测试 查看能否正确返回Map表
    public void testTargets() {
        testAdd();
        testSet();
        Graph<String> graph =emptyInstance();
        String a="No";
        String b="Child";
        String c="leftbehind";
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.set(a, b, 5);
        graph.set(b, c, 4);
        graph.set(a, c, 3);
        Map<String,Integer> map=new HashMap<String,Integer>();
        map.put(c,3);
        map.put(b,5);
        assertEquals(map,graph.targets(a));
    }

    @Test  //
    public void testVertices() {
        testAdd();
        Graph<String> graph =emptyInstance();
        String a="yu";
        String b="tao";
        String c="shuai";
        graph.add(a);
        graph.add(b);
        graph.add(c);
        assertEquals(true,graph.vertices().contains(a));
        assertEquals(true,graph.vertices().contains(b));
        assertEquals(true,graph.vertices().contains(c));
    }

}
