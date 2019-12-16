package com;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


/**
 * 哺乳动物抽象类
 *
 * @param <T> 子类
 * @author luoyuequan
 */
public abstract class AbstractMammalia<T> extends AbstractOrganism {
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
        this.sex = sr.nextInt(2);
        this.father = father;
        this.mother = mother;
    }

    AbstractMammalia() {
    }

    /**
     * 觅食
     */
    abstract void foraging();

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public T getFather() {
        return father;
    }

    public void setFather(T father) {
        this.father = father;
    }

    public T getMother() {
        return mother;
    }

    public void setMother(T mother) {
        this.mother = mother;
    }

    public T getSpouse() {
        return spouse;
    }

    public void setSpouse(T spouse) {
        this.spouse = spouse;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}

