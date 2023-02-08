/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {

        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    @Test  //文本只有一行
    public void testPoem1() throws IOException {
        String filePath = "Spring2022_HITCS_SC_Lab2/src/P1/poet/mugar-omni-theater.txt";
        String input = "Test the system.";
        String AccOutput = "Test of the system.";
        GraphPoet graphPoet = new GraphPoet(new File(filePath));
        assertEquals(AccOutput, graphPoet.poem(input));
    }
    @Test  //文本多行
    public void testPoem2() throws IOException {
        String filePath = "Spring2022_HITCS_SC_Lab2/src/P1/poet/Emotionally_Scarred.txt";
        String input = "I know emotions with lies..";
        String AccOutput = "I know emotions come with lies..";
        GraphPoet graphPoet = new GraphPoet(new File(filePath));
        assertEquals(AccOutput, graphPoet.poem(input));
    }
    @Test  //空文本
    public void testPoem3() throws IOException {
        String filePath = "Spring2022_HITCS_SC_Lab2/src/P1/poet/Street_Lights.txt";
        String input = "Expect same.";
        String AccOutput = "Expect same.";
        GraphPoet graphPoet = new GraphPoet(new File(filePath));
        assertEquals(AccOutput, graphPoet.poem(input));
    }
    @Test  //边的权重都为1
    public void testPoem4() throws IOException {
        String filePath = "Spring2022_HITCS_SC_Lab2/src/P1/poet/Need_To_Know.txt";
        String input = "I you.";
        String AccOutput = "I see you.";
        GraphPoet graphPoet = new GraphPoet(new File(filePath));
        assertEquals(AccOutput, graphPoet.poem(input));
    }
    @Test  //边的权重不同
    public void testPoem5() throws IOException {
        String filePath = "Spring2022_HITCS_SC_Lab2/src/P1/poet/CityofGods.txt";
        String input = "I you.";
        String AccOutput = "I miss you.";
        GraphPoet graphPoet = new GraphPoet(new File(filePath));
        assertEquals(AccOutput, graphPoet.poem(input));
    }
    @Test //ToString 空
    public void testtoStringempty() throws IOException {
        final GraphPoet nimoy = new GraphPoet(new File("Spring2022_HITCS_SC_Lab2/src/P1/poet/Street_Lights.txt"));
        System.out.println(nimoy.toString());
        assertEquals("", nimoy.toString());
    }
    @Test
    public void testtoString() throws IOException {
        final GraphPoet nimoy = new GraphPoet(new File("Spring2022_HITCS_SC_Lab2/src/P1/poet/mugar-omni-theater.txt"));
        assertTrue(nimoy.toString().contains("this->is (1)"));
        assertTrue(nimoy.toString().contains("is->a (1)"));
    }
    
}
