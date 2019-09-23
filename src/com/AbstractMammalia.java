package com;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;


/**
 * 哺乳动物抽象类
 *
 * @param <T> 子类
 */
abstract class AbstractMammalia<T> extends AbstractOrganism {
    //            0:母 1:公
    private int sex;
    private T father;
    private T mother;
    //    配偶
    private T spouse;
    //    子代
    private List<T> children = new ArrayList<>();

    /**
     * @param birthDateTime 出生日期 用于父类 构造函数
     * @param father        父
     * @param mother        母
     */
    AbstractMammalia(long birthDateTime, T father, T mother) {
        super(birthDateTime);
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
        } catch (NoSuchAlgorithmException e) {
            sr = new SecureRandom(); // 获取普通的安全随机数生成器
        }
        this.setSex(sr.nextInt(2));
        this.setFather(father);
        this.setMother(mother);
    }


    AbstractMammalia() {
    }

    /**
     * 觅食
     */
    abstract void foraging();

    int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    T getFather() {
        return father;
    }

    T getMother() {
        return mother;
    }

    public void setFather(T father) {
        this.father = father;
    }

    public void setMother(T mother) {
        this.mother = mother;
    }

    T getSpouse() {
        return spouse;
    }

    void setSpouse(T spouse) {
        this.spouse = spouse;
    }

    List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}

