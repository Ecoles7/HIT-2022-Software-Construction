package P1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagicSquare
{
    public static void main(String[] args) throws IOException
    {
        for (int i = 1; i <= 5; i++)
        {
            System.out.println(Integer.toString(i) + ".txt:");
            System.out.println("鉴定为"+isLegalMagicSquare(i + ".txt"));
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要生成幻方方阵的行列数n");
        int n = sc.nextInt();
        boolean flag = generateMagicSquare(n);
        if (flag)
        {
            System.out.println(Integer.toString(6) + ".txt:");
            System.out.println("鉴定为"+isLegalMagicSquare(6 + ".txt"));
        }
        else
            System.out.println(flag);

    }
    public static boolean isLegalMagicSquare(String filename) throws IOException
    {
        File file = new File("src/P1/txt/" + filename);
        BufferedReader reader;
        //读取异常处理
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e2) {
            System.err.println("读入失败~OvO");
            return false;
        }
        String ppp;
        ArrayList<String> Nayeon=new ArrayList<>();
        while ((ppp = reader.readLine()) != null)
        {
            Nayeon.add(ppp);
        }
        reader.close();
        int row,column;
        row = Nayeon.size(); // 字符串数组元素数量即矩阵的行数
        //判断是否通过/t分割
        for (int i = 0; i < row; i++)
        {
            if (Nayeon.get(i).contains(" ")) {
                System.out.println("数字之间并非使用\\t分割~OvO");
                return false;
            }
        }
        column = Nayeon.get(0).split("\t+").length; //取第一行的列数
        //判断行列数是否一致
        if(column != row)
        {
            System.out.println("行列数不一致~OvO");
            return false;
        }
        //判断是否为矩阵
        for(int i=1;i<row;i++)
        {
            int mina= Nayeon.get(i).split("\t+").length;
            if(mina != column)
            {
                System.out.println("不是矩阵~OvO!");
                return false;
            }
        }
        //检查是否都为整数
        int[][] magicsquare = new int[row][row];
        int i,j;
        for (i = 0; i < row; i++)
        {
            for ( j = 0; j < row; j++) {
                String s = Nayeon.get(i).split("\t+")[j];
                //包含除数字外的其他字符，如负号，小数点，字母，则直接返回false
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher matcher = pattern.matcher(s);
                if (!matcher.matches())
                {
                    System.out.println("这个矩阵不全是正整数~OvO");
                    return false;
                }
                // 为square赋值
                magicsquare[i][j] = Integer.parseInt(s);
            }
        }
        //判断幻方
        int vavalue=0;
        int value_row=0,value_column=0,slash_value1=0,slash_value2=0;
        for(i=0;i<row;i++)
        {
            vavalue+=magicsquare[0][i];
        }
        //幻方检查-行列检查
        for(i=0;i<row;i++)
        {
            for(j=0;j<row;j++)
            {
                value_row+= magicsquare[i][j];
                value_column+=magicsquare[j][i];
            }
            if(value_row!=vavalue || value_column!= vavalue)
            {
                System.out.println("不是幻方~OvO");
                return false;
            }
            else
            {
                value_row = 0;
                value_column = 0;
            }
        }
        //幻方检查-对角线
        for(i=0;i<row;i++)
        {
            slash_value1 += magicsquare[i][i];
            slash_value2 += magicsquare[row-i-1][i];
        }
        if(slash_value1!=vavalue || slash_value2!= vavalue)
        {
            System.out.println("不是幻方~OvO");
            return false;
        }
        return true;
    }
    public static boolean generateMagicSquare(int n)
    {
        //负数检测
        if (n < 0) {
            System.out.println("n不能为负数");
            return false;
        }
        //偶数检测
        if (n % 2 == 0) {
            System.out.println("n不能为偶数");
            return false;
        }
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++)
        {
            magic[row][col] = i;
            if (i % n == 0)  //
                row++;
            else
            {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
                System.out.println();
        }
        //写入文件
        String txtPath = "src/P1/txt/6.txt";
        try
        {
            File writeName = new File(txtPath);
            writeName.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
            for (int k = 0; k < n; k++)
            {
                for (int l = 0; l < n; l++)
                {
                    String s = Integer.toString(magic[k][l]);
                    if (l != n - 1)
                        out.write(s + '\t');
                    else
                        out.write(s + '\n');
                }
            }
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
