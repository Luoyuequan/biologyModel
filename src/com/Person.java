package com;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;

/**
 * @implNote 继承 哺乳动物抽象类 并实现
 */
class Person extends AbstractMammalia<Person> {
    //    姓
    private String surname;
    //    名
    private String name;
    private int age;

    /**
     * @param birthDateTime 出生日期
     * @param father        父亲
     * @param mother        母亲
     * @param name          名
     */
    Person(long birthDateTime, Person father, Person mother, String name) {
        super(birthDateTime, father, mother);
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
        } catch (NoSuchAlgorithmException e) {
            sr = new SecureRandom(); // 获取普通的安全随机数生成器
        }
        String surname =
                father == null && mother == null ?
                        "Yuna" + sr.nextInt(10) :
                        father != null ? father.getSurname() : mother.getSurname();
        this.setSurname(surname);
        this.setName(name);
        //        加入生物集
        ecosystem.add(this);
        this.getPersonInfor();
    }

    /**
     * json格式数据模型 转为 对象 时 必备
     */
    public Person() {
    }

    /**
     * 觅食
     */
    @Override
    void foraging() {

    }

    /**
     * 繁殖
     */
    @Override
    void breeding() {
        if (this.getSpouse() != null) {
            if (getChildren().size() < 5) {
                Person father = this.getSex() == 1 ? this : this.getSpouse();
                Person mother = this.getSex() == 0 ? this : this.getSpouse();
                Person child = new Person(System.currentTimeMillis(), father, mother, "Child" + this.getChildren().size());
                this.getChildren().add(child);
                this.getSpouse().getChildren().add(child);
                System.out.printf(
                        "%s与%s繁殖成功,子代为%s\n",
                        this.getSurnameName(), this.getSpouse().getSurnameName(), child.getSurnameName()
                );
                this.getPersonInfor();
                this.getSpouse().getPersonInfor();
            } else {
                System.out.printf(
                        "%s与%s的子代数量已达到%d，无法继续繁殖了!\n",
                        this.getSurnameName(), this.getSpouse().getSurnameName(), this.getChildren().size()
                );
            }
        } else {
            System.out.println(this.getSurnameName() + "暂无配偶,无法繁殖后代!");
        }

    }

    /**
     * 运动
     */
    @Override
    void movement() {

    }

    /**
     * 结婚
     *
     * @param spouse 配偶
     */
    void marriage(Person spouse) {
        if (this.getSpouse() != null) {
            System.out.printf("%s已经与%s结婚了\n", this.getSurnameName(), spouse.getSurnameName());
        } else if (spouse == null) {
            System.out.println("请传入有效对象!");
        } else if (spouse.getSex() + this.getSex() == 1) {
            this.setSpouse(spouse);
            spouse.setSpouse(this);
            System.out.printf("%s与%s结婚成功!\n", this.getSurnameName(), spouse.getSurnameName());
            this.getPersonInfor();
            this.getSpouse().getPersonInfor();
        } else if (spouse.getSex() + this.getSex() != 1) {
            System.out.printf("%s与%s结婚失败,配偶必须为异性!\n", this.getSurnameName(), spouse.getSurnameName());
        }
    }

    /**
     * 讲话
     */
    void talking() {

    }

    /**
     * 听话
     */
    void listen() {

    }

    /**
     * 获取个人基本信息
     */
    void getPersonInfor() {
//        System.out.println(this.birthDateTime);
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.printf(
                "DNA:%s,姓名: %s,出生日期:%s,死亡日期:%s,性别:%s,健康值:%d,父亲:%s,母亲:%s,配偶:%s,子代:%d个\n",
                getIdCard(),
                this.getSurnameName(),
                sdf.format(getBirthDateTime()),
                sdf.format(getDeathDateTime()),
                this.getSexToString(),
                getHealth(),
                this.getFather() == null ? "无" : this.getFather().getSurnameName(),
                this.getMother() == null ? "无" : this.getMother().getSurnameName(),
                this.getSpouse() == null ? "无" : this.getSpouse().getSurnameName(),
                this.getChildren().size()
        );
    }

    /**
     * @return 姓名
     */
    private String getSurnameName() {
        return this.getSurname() + "·" + this.getName();
    }

    /**
     * @return 0：女，1：男
     */
    private String getSexToString() {
        return super.getSex() == 0 ? "女♀" : "男♂";
    }

    /**
     * 获取子代信息
     */
    void getChildrenInfor() {
        for (Person child : this.getChildren()) {
            child.getPersonInfor();
        }
    }

    private String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
