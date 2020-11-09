package stage4.HomeWork.Work1;

public class Student {
    private int id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws IdExceptionTest {
        if (id >0 && id<10000){
            this.id = id;
        }
        else {
            throw new IdExceptionTest();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws AgeExceptionTest {
        if (age>0){
            this.age = age;
        }
        else {
            throw new AgeExceptionTest();
        }
    }
}