package P2;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class FriendshipGraphTest {
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    @Test
    public void addVertexTest() {
        FriendshipGraph graph = new FriendshipGraph();
        Set<Person> correctPersons = new HashSet<>();
        //初始化人物
        Person Nayeon = new Person("Nayeon");

        Person MOMO = new Person("MOMO");

        Person MOMOki = new Person("MOMO");

        Person SANA = new Person("SANA");

        assertEquals(correctPersons, graph.vertices());
        //test
        correctPersons.add(Nayeon);
        graph.addVertex(Nayeon);
        assertEquals(correctPersons, graph.vertices());
        //test
        correctPersons.add(MOMO);
        graph.addVertex(MOMO);
        assertEquals(correctPersons, graph.vertices());
        //test
        graph.addVertex(MOMOki);
        assertEquals(correctPersons, graph.vertices());
        //test
        correctPersons.add(SANA);
        graph.addVertex(SANA);
        assertEquals(correctPersons, graph.vertices());
    }
    @Test
    public void addEdgeTest()
    {
        FriendshipGraph graph = new FriendshipGraph();
        Person Kanye = new Person("Kanye");

        Person lil_wayne = new Person("lil_wayne");

        Person lil_Nas_X = new Person("lil_Nas_X");

        Person Tyga = new Person("Tyga");

        graph.addVertex(Kanye);
        graph.addVertex(lil_wayne);
        graph.addVertex(lil_Nas_X);
        graph.addVertex(Tyga);
        Map<Person, Integer> KanyeM = new HashMap<>();
        Map<Person, Integer> lil_wayneM= new HashMap<>();
        Map<Person, Integer> lil_Nas_XM= new HashMap<>();
        graph.addEdge(Kanye,lil_wayne);
        graph.addEdge(lil_wayne, Kanye);
        KanyeM.put(lil_wayne, 1);
        lil_wayneM.put(Kanye,1);

        assertEquals(KanyeM, graph.targets(Kanye));
        assertEquals(lil_wayneM, graph.targets(lil_wayne));


        graph.addEdge(lil_Nas_X, Tyga);
        graph.addEdge(Tyga, lil_Nas_X);
        lil_Nas_XM.put(Tyga, 1);
       assertEquals(lil_Nas_XM, graph.targets(lil_Nas_X));

        graph.addEdge(Kanye, Kanye);
        assertEquals(KanyeM, graph.targets(Kanye));


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
