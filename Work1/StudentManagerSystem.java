package stage4.HomeWork.Work1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class StudentManagerSystem {
    //学生信息储存在集合类中
    static ArrayList<Student> array = new ArrayList<Student>();

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        //启动应用便开始从硬盘加载数据
        loadData();
        while (true) {
            System.out.println("----欢迎来到学生管理系统，请选择操作----");
            System.out.println("1 查看所有学生");
            System.out.println("2 添加学生信息");
            System.out.println("3 查找学生信息");
            System.out.println("4 修改学生信息");
            System.out.println("5 删除学生信息");
            System.out.println("6 退出系统");
            //输入选项
            String choice = sc.nextLine();
            //判断
            switch (choice) {
                case "1":
                    query();
                    break;
                case "2":
                    add();
                    storeData();//修改数据之后立即更新硬盘文件
                    break;
                case "3":
                    findstudent();
                    break;
                case "4":
                    modify();
                    storeData();//修改数据之后立即更新硬盘文件
                    break;
                case "5":
                    delete();
                    storeData();//修改数据之后立即更新硬盘文件
                    break;
                case "6":
                    System.out.println("感谢您的使用，再见！");
                    System.exit(0);
                default:
                    System.out.println("您的输入有误，请重新选择！");
            }
        }
    }
    private static void loadData() throws Exception {
        //初次打开应用会创建文件
        File file= new File("D:\\output\\a.txt");
        if(!file.exists()){
                file.createNewFile();
            System.out.println("初次启动初始化完成！");
        }
        //从文件中读取数据进集合
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine())!=null){
            String [] str = line.split("\t");
            Student s = new Student();
            s.setId(Integer.parseInt(str[0]));
            s.setName(str[1]);
            s.setAge(Integer.parseInt(str[2]));
            array.add(s);
        }
        br.close();
    }
    //储存数据到文件中
    private static void storeData() throws IOException{
        File file= new File("D:\\output\\a.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //直接将数据全部存储到文件中，不用追加方法，也方便增删查改
        //如何信息量比较大就不适应
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        //将数据存储进文件
        for (Student s :array){
            StringBuilder sb = new StringBuilder();
            sb.append(s.getId()).append("\t").append(s.getName()).append("\t").append(s.getAge());
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }
    //查询学生信息方法
    private static void query(){

        if(array.size() == 0){
            System.out.println("暂时未存储任何学生信息，请重新选择。");
            return;
        }
        System.out.println("学号\t姓名\t年龄");
        for(int i=0;i<array.size();i++){
            Student s = array.get(i);
            System.out.println(s.getId()+"\t"+s.getName()+"\t"+s.getAge());
        }
    }
    //添加学生信息方法
    private static void add() throws Exception {
        int q=0;//此处q作为检验的标志，如果发现了重复元素，则q自加
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学号");
        int id =sc.nextInt();
        for(int i=0;i<array.size();i++){//与已经添加集合的元素进行对比，如果相同则输出相应内容
            Student s=array.get(i);
            if(s.getId()==(id)){//元素的sid变量与刚获取的变量利用方法进行对比
                System.out.println("输入的学号有重复，请检查后重新输入！");
                q++;
                break;
            }
        }
        if(q==0){
            System.out.println("请输入姓名");
            String name = sc.next();
            System.out.println("请输入年龄");
            int age =sc.nextInt();
            Student s = new Student();
            s.setId(id);
            s.setName(name);
            s.setAge(age);
            array.add(s);
            System.out.println("添加学生成功！");
        }

    }
        //查找学生信息方法
        public static void findstudent() throws Exception{
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入要查找学生的姓名");
            String name = sc.next();
            for (int i=0;i<array.size();i++){
                Student s= array.get(i);
                if (s.getName().equals(name)){
                    System.out.println("学号:"+s.getId()+",姓名:"+s.getName()+",年龄:"+s.getAge());
                    continue;
                }
                else if (i==array.size()-1/* && s.getName().equals(name)*/) {
                    if (s.getName().equals(name)) {
                        System.out.println("没有找到该学生，请检查姓名是否正确！");
                    }
                }

            }
        }
    //修改学生信息方法
    private static void modify() throws Exception {
        Scanner sc = new Scanner(System.in);
        int index = -1;//查找标志
        System.out.println("请输入需要修正的学生学号：");
        int id = sc.nextInt();

        for(int i=0;i<array.size();i++){
            Student s = array.get(i);
            if(s.getId()==(id)){
                index = i;
                break;
            }
        }
        if(index == -1){//未找到学生直接返回
            System.out.println("未找到相应学号所对应学生！");
            return;
        }else{
            System.out.println("请输入需要更改的姓名：");
            String name = sc.next();
            System.out.println("请输入需要更改的年龄：");
            int age = sc.nextInt();
            Student s = array.get(index);
            s.setName(name);
            s.setAge(age);
            System.out.println("学生信息更新成功！");
        }

    }
    //删除学生信息方法
    private static void delete(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要删除的学生学号：");
        int id = sc.nextInt();
        for(int i=0;i<array.size();i++){
            Student s = array.get(i);
            if(s.getId()==(id)){
                System.out.println("请确认删除信息(Y/N)：");
                System.out.println("学号\t\t姓名\t年龄");
                System.out.println(s.getId()+"\t"+s.getName()+"\t"+s.getAge());
                String confirm = sc.next();
                //判断确认删除
                if(confirm.equals("y") || confirm.equals("Y")){
                    if(array.remove(s)){
                        System.out.println("删除成功！");
                        return;
                    }
                }else{
                    System.out.println("已取消删除！");
                    return;
                }
            }
        }
        System.out.println("未找到所对应学生学号，请确认学号信息是否有误!");
    }

}
