package P3;


import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import static org.junit.Assert.*;

public class FriendshipGraphTest
{

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    @Test
    public void addVertexTest() {
        FriendshipGraph graph = new FriendshipGraph();
        List<Person> correctPersons = new ArrayList<Person>();
        //初始化人物
        Person Nayeon = new Person("Nayeon");

        Person MOMO = new Person("MOMO");

        Person MOMOki = new Person("MOMO");

        Person SANA = new Person("SANA");

        assertEquals(correctPersons, graph.persons);
        //test
        correctPersons.add(Nayeon);
        graph.addVertex(Nayeon);
        assertEquals(correctPersons, graph.persons);
        //test
        correctPersons.add(MOMO);
        graph.addVertex(MOMO);
        assertEquals(correctPersons, graph.persons);
        //test
        graph.addVertex(MOMOki);
        assertEquals(correctPersons, graph.persons);
        //test
        correctPersons.add(SANA);
        graph.addVertex(SANA);
        assertEquals(correctPersons, graph.persons);
    }
    @Test
    public void addEdgeTest()
    {
        FriendshipGraph graph = new FriendshipGraph();
        Vector<Vector<Integer>> relationmartix= new Vector<Vector<Integer>>(); //用于检验的矩阵
        Vector<Integer> a = new Vector<Integer>();
        Vector<Integer> b = new Vector<Integer>();
        Vector<Integer> c = new Vector<Integer>();
        Vector<Integer> d = new Vector<Integer>();
        Vector<Integer> temp = new Vector<Integer>();

        Person Kanye = new Person("Kanye");

        Person lil_wayne = new Person("lil_wayne");

        Person lil_Nas_X = new Person("lil_Nas_X");

        Person lil_Uzi_Vert = new Person("lil_Uzi_Vert");

        graph.addVertex(Kanye);
        graph.addVertex(lil_wayne);
        graph.addVertex(lil_Nas_X);
        for (int i = 0; i < 3; i++) {
            a.add(0);
            b.add(0);
            c.add(0);
            d.add(0);
        }
        relationmartix.add(a);
        relationmartix.add(b);
        relationmartix.add(c);
        //test
        assertEquals(relationmartix, graph.relationmatrix);
        graph.addEdge(Kanye, lil_wayne);
        graph.addEdge(lil_wayne, Kanye);
        temp = relationmartix.get(0);
        temp.set(1, 1);
        temp = relationmartix.get(1);
        temp.set(0, 1);
        assertEquals(relationmartix, graph.relationmatrix);
        graph.addVertex(lil_Uzi_Vert);
        graph.addEdge(lil_Uzi_Vert, lil_wayne);
        graph.addEdge(lil_wayne, lil_Uzi_Vert);
        for (int i = 0; i < graph.persons.indexOf(lil_Uzi_Vert); i++) {
            temp = relationmartix.get(i);
            temp.add(0);
        }
        d.add(0);
        relationmartix.add(d);
        temp = relationmartix.get(1);
        temp.set(3, 1);
        temp = relationmartix.get(3);
        temp.set(1, 1);
        assertEquals(relationmartix, graph.relationmatrix);
    }
    @Test
    public void getDistanceTest() {
        FriendshipGraph graph = new FriendshipGraph();

        Person Kris = new Person("Kris");

        Person Kim = new Person("Kim");

        Person Khloe = new Person("Khloe");

        Person Kourtney = new Person("Kourney");

        Person Kendall = new Person("Kendall");

        Person Kylie = new Person("Kylie");

        Person Scott = new Person("Scott");

        Person Kanye = new Person("Kanye");

        Person Travis = new Person("Travis");

        Person Pete = new Person("Pete");

        graph.addVertex(Kris);
        graph.addVertex(Khloe);
        graph.addVertex(Kim);
        graph.addVertex(Kourtney);
        graph.addVertex(Kendall);
        graph.addVertex(Kylie);
        graph.addVertex(Kanye);
        graph.addVertex(Scott);
        graph.addVertex(Travis);


        graph.addEdge(Kris, Kim);
        graph.addEdge(Kim, Kris);

        graph.addEdge(Kris, Khloe);
        graph.addEdge(Khloe, Kris);

        graph.addEdge(Kris, Kourtney);
        graph.addEdge(Kourtney, Kris);

        graph.addEdge(Kris, Kendall);
        graph.addEdge(Kendall, Kris);

        graph.addEdge(Kris, Kylie);
        graph.addEdge(Kylie, Kris);

        graph.addEdge(Kim, Kanye);
        graph.addEdge(Kanye, Kim);

        graph.addEdge(Kourtney, Travis);
        graph.addEdge(Travis, Kourtney);

        //相通
        assertEquals(1, graph.getDistance(Kris, Kim));
        assertEquals(2, graph.getDistance(Kris, Kanye));
        assertEquals(3, graph.getDistance(Kanye, Kourtney));
        assertEquals(4, graph.getDistance(Kanye, Travis));
        assertEquals(3, graph.getDistance(Kendall, Kanye));
        //不通
        assertEquals(-1, graph.getDistance(Scott, Kanye));
        assertEquals(-1, graph.getDistance(Scott, Kourtney));
        // 输入两者为同一点
        assertEquals(0, graph.getDistance(Kim, Kim));
        assertEquals(0, graph.getDistance(Kanye, Kanye));
        // 不在图中的点
        assertEquals(-1, graph.getDistance(Pete, Kanye));
    }
}
