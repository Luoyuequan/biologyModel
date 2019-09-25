package com;

import java.util.UUID;

/**
 * 生物抽象类
 */
abstract class AbstractOrganism extends AbstractEcosystem {
    private long birthDateTime;
    private long deathDateTime;
    private int health;
    private String idCard;

    /**
     * @param birthDateTime 出生日期
     */
    AbstractOrganism(long birthDateTime) {
        this.birthDateTime = birthDateTime;
        this.health = 100;
        this.idCard = UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * json格式数据模型 转为 对象 时 必备
     */
    AbstractOrganism() {

    }

    /**
     * 繁殖
     */
    abstract void breeding();

    /**
     * 运动
     */
    abstract void movement();

    public long getBirthDateTime() {
        return birthDateTime;
    }

    public void setBirthDateTime(long birthDateTime) {
        this.birthDateTime = birthDateTime;
    }

    public long getDeathDateTime() {
        return deathDateTime;
    }

    public void setDeathDateTime(long deathDateTime) {
        this.deathDateTime = deathDateTime;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
