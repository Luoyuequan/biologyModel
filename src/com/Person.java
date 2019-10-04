package com;


import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;

/**
 * @implNote 继承 哺乳动物抽象类 并实现
 */
class Person extends AbstractMammalia<Person> {
//    O(∩_∩)O哈哈~master

    //    姓
    private String surname;
    //    名
    private String name;
    private int age;
    //    0:未结婚 1:已结婚
    private int marriageStatus;

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
        this.surname =
                father == null && mother == null ?
                        "Yuna" + sr.nextInt(10) :
                        father != null ? father.getSurname() : mother.getSurname();
        this.name = name;
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
        if (getSpouse() != null) {
            if (getChildren().size() < 5) {
                Person father = this.getSex() == 1 ? this : getSpouse();
                Person mother = this.getSex() == 0 ? this : getSpouse();
                Person child = new Person(System.currentTimeMillis(), father, mother, "Child" + getChildren().size());
                getChildren().add(child);
                getSpouse().getChildren().add(child);
                System.out.printf(
                        "%s与%s繁殖成功,新生子代为%s\n",
                        getSurnameName(), getSpouse().getSurnameName(), child.getSurnameName()
                );
                getPersonInfor();
                getSpouse().getPersonInfor();
            } else {
                System.out.printf(
                        "%s与%s的子代数量已达到%d，无法继续繁殖了!\n",
                        getSurnameName(), getSpouse().getSurnameName(), getChildren().size()
                );
            }
        } else {
            System.out.println(getSurnameName() + "暂无配偶,无法繁殖后代!");
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
//        两个人都未结婚，才可结婚
//        否则结婚失败
        if (spouse != null && getSpouse() == null && spouse.getSpouse() == null) {
            if (spouse.getSex() + getSex() == 1) {
//                如果对象的父亲 与 自己的父亲 相等，禁止 结婚
                if (getFather() != null && spouse.getFather() != null && spouse.getFather().getIdCard().equals(getFather().getIdCard())) {
                    System.out.printf("禁止%s与%s一代近亲结婚!\n", getSurnameName(), spouse.getSurnameName());
                } else {
                    this.setSpouse(spouse);
                    spouse.setSpouse(this);
                    System.out.printf("%s与%s结婚成功!\n", this.getSurnameName(), spouse.getSurnameName());
                    if (getMarriageStatus() != getSex() + getSpouse().getSex()) {
                        setMarriageStatus(getSex() + getSpouse().getSex());
                        getSpouse().setMarriageStatus(getSex() + getSpouse().getSex());
                    }
                    this.getPersonInfor();
                    this.getSpouse().getPersonInfor();
                }
            } else if (spouse.getSex() + getSex() != 1) {
                System.out.printf("%s与%s结婚失败,配偶必须为异性!\n", getSurnameName(), spouse.getSurnameName());
            }
        } else if (spouse != null) {
            if (getSpouse() != null) {
//                自身已结婚
                System.out.printf("%s已经与%s结婚了\n", getSurnameName(), getSpouse().getSurnameName());
            }
            if (spouse.getSpouse() != null) {
//                对方已结婚
                System.out.printf("%s已经与%s结婚了\n", spouse.getSurnameName(), spouse.getSpouse().getSurnameName());
            }
            System.out.printf("%s不能与%s结婚\n", getSurnameName(), spouse.getSurnameName());
//            改变结婚情况
            if (getSpouse() != null && getMarriageStatus() != getSex() + getSpouse().getSex()) {
                setMarriageStatus(getSex() + getSpouse().getSex());
                getSpouse().setMarriageStatus(getSex() + getSpouse().getSex());
            }
        } else {
            System.out.println("请传入有效对象!");
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
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.printf(
                "DNA:%s,姓名: %s,出生日期:%s,性别:%s,婚姻情况:%s,父亲:%s,母亲:%s,配偶:%s,子代:%d个,健康值:%d,死亡日期:%s\n",
                getIdCard(),
                getSurnameName(),
                sdf.format(getBirthDateTime()),
                getSexToString(),
                getMarriageStatusToString(),
                getFather() == null ? "无" : getFather().getSurnameName(),
                getMother() == null ? "无" : getMother().getSurnameName(),
                getSpouse() == null ? "无" : getSpouse().getSurnameName(),
                getChildren().size(),
                getHealth(),
                sdf.format(getDeathDateTime())
        );
    }

    /**
     * @return 姓名
     */
    private String getSurnameName() {
        return this.surname + "·" + this.name;
    }

    /**
     * @return 0：女，1：男
     */
    private String getSexToString() {
        return getSex() == 0 ? "女♀" : "男♂";
    }

    /**
     * @return 0:未结婚 ，1：已结婚
     */
    private String getMarriageStatusToString() {
        return getMarriageStatus() == 0 ? "未结婚" : "已结婚";
    }

    /**
     * 获取子代信息
     */
    void getChildrenInfor() {
        for (Person child : getChildren()) {
            child.getPersonInfor();
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public void setAge(int age) {
        this.age = age;
    }

    public int getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(int marriageStatus) {
        this.marriageStatus = marriageStatus;
    }
}
