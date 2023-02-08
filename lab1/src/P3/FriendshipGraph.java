package P3;

import java.util.*;


public class FriendshipGraph
{
    public List<Person> persons = new ArrayList<Person>(); // Graph中的节点列表
    private final List<String> names = new ArrayList<String>(); // 保存名字，用于判断是否重复
    public Vector<Vector<Integer>> relationmatrix = new Vector<Vector<Integer>>(); //邻接矩阵，表示人物关系
    public boolean addVertex(Person p)
    {
        if(names.contains(p.getName()))
        {
            System.out.println("此人已存在");
            return false;
        }
        else
        {
            persons.add(p);  //节点表加入新人
            names.add(p.getName());
            int num= persons.size();
            Vector<Integer> newperson = new Vector<Integer>(); //在关系矩阵中加入
            relationmatrix.add(newperson);
            for (int i = 0; i < num-1; i++)
            {
                relationmatrix.elementAt(i).add(0);  //每个人的关系向量中加个位置
            }
            for (int i = 0; i < num; i++) {
                relationmatrix.elementAt(num-1).add(0);  //初始化newperson的关系向量
            }
            return true;
        }
    }
    public boolean addEdge(Person p1, Person p2)  //仅建立单向联系
    {
        if (!names.contains(p1.getName()))
        {
            System.out.println(p1.getName() + "不在关系图中");
            return false;
        }
        if (!names.contains(p2.getName())) {
            System.out.println(p2.getName() + "不在关系图中");
            return false;
        }
        int search1=persons.indexOf(p1);
        int search2=persons.indexOf(p2);
       // Vector<Integer> temp = relationmatrix.elementAt(search1);
       // temp.set(search2, 1);
        relationmatrix.elementAt(search1).set(search2, 1);
        return true;
    }
    public int getDistance(Person p1, Person p2) {
        // 异常情况处理
        // p1与p2有其一不在关系图中
        if (!persons.contains(p1)) {
            System.out.print(p1.getName() + "不在关系图中");
            return -1;
        }
        if (!persons.contains(p2)) {
            System.out.print(p2.getName() + "不在关系图中");
            return -1;
        }
        int search1=persons.indexOf(p1);
        int search2=persons.indexOf(p2);
        // p1与p2相等
        if (search1 == search2) {
            return 0;
        }
        Queue<Person> queue = new LinkedList<Person>(); // 队列，用于BFS搜素
        int distance = 0;
        Person temp = new Person("");
        Person queueEnd = new Person("");
        Vector<Integer> tempCol = new Vector<Integer>();
        // visit数组（visit为标志是否访问过的数组,访问过为1，否则为0）
        int[] visit = new int[persons.size()];
        // isQueueEnd标志节点i是否是某轮bfs深搜的终点，若是，其为true，,需要使distance++
        boolean[] isQueueEnd = new boolean[persons.size()];


        // 初始化，对p1进行设定
        queue.add(p1);
        visit[search1] = 1;
        isQueueEnd[search1]=true;

        while (queue.peek() != p2) {
            temp = queue.poll(); // 弹出并保存queue的头元素
            int weizhi = persons.indexOf(temp);
            // 将与queue头元素直接相连，且未访问过的元素入队
            tempCol = relationmatrix.get(weizhi); // tempCol保存头元素对应的关系矩阵行
            for (int i = 0; i < tempCol.size(); i++)
            { // 头元素对应的关系矩阵行，遍历此行中的所有元素，找到与头元素直接相邻的元素
                if (tempCol.get(i) == 1) {
                    // 查找index为i的person，并将其加入队列,同时把其标记为访问过
                    for (Person t : persons)
                    {
                        int weizhi_t = persons.indexOf(t);
                        if (weizhi_t == i && visit[i] == 0) {
                            queue.add(t);
                            visit[i] = 1;
                            queueEnd = t; // 记录当前队尾
                            break;
                        }
                    }
                }
            }

            // 最后队列空，说明没有p1到p2的直接通路
            if (queue.isEmpty())
                return -1;

            // 记录当前队尾，并使distance++
            if (isQueueEnd[persons.indexOf(temp)]) {
                isQueueEnd[persons.indexOf(queueEnd)]=true;
                distance++;
            }
        }
        return distance;
    }
    public static void main(String[] args)
    {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        //should print 1
        System.out.println(graph.getDistance(rachel, ben));
        //should print 2
        System.out.println(graph.getDistance(rachel, rachel));
        //should print 0
        System.out.println(graph.getDistance(rachel, kramer));
        //should print -1
    }


}
