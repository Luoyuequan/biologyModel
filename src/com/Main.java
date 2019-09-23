package com;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

public class Main implements Runnable {
    public static void main(String[] args) {
        Main my = new Main();
        my.initPersonList();
        Person person = new Person(System.currentTimeMillis(), null, null, "One");
//        person.getPersonInfor();
        System.out.println(JSON.toJSONString(person,true));
        my.randomMarriage(AbstractEcosystem.ecosystem);
        my.randomBreeding(AbstractEcosystem.ecosystem);
        my.savePersonList();
    }

    private void randomBreeding(List<Person> ecosystem) {
        if (ecosystem.size() != 0) {
            SecureRandom sr = null;
            try {
                sr = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
            } catch (NoSuchAlgorithmException e) {
                sr = new SecureRandom(); // 获取普通的安全随机数生成器
            }
            ecosystem.get(sr.nextInt(ecosystem.size())).breeding();
        }
    }

    /**
     * 从现有的人类集合 随机取两名异性 进行配对
     *
     * @param ecosystem 人类集合
     */
    private void randomMarriage(List<Person> ecosystem) {
        int[] family = new int[2];
        if (ecosystem.size() >= family.length) {
            SecureRandom sr = null;
            try {
                sr = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
            } catch (NoSuchAlgorithmException e) {
                sr = new SecureRandom(); // 获取普通的安全随机数生成器
            }
            family[0] = sr.nextInt(ecosystem.size());
            family[1] = sr.nextInt(ecosystem.size());
            if (family[0] == family[1]) {
                randomMarriage(ecosystem);
            } else {
                ecosystem.get(family[0]).marriage(ecosystem.get(family[1]));
            }
        }
    }

    /**
     * 加载本地 已存在 人类集合
     */
    private void initPersonList() {
        try {
            File file = new File("./personList.json");
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                String personListJson = in.readUTF();
                AbstractEcosystem.ecosystem = JSON.parseArray(personListJson, Person.class);
                in.close();
                fileIn.close();
                for (Person p : AbstractEcosystem.ecosystem) {
                    p.getPersonInfor();
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * 保存人类集合 到 本地
     */
    private void savePersonList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("./personList.json");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            String personListJson = JSON.toJSONString(AbstractEcosystem.ecosystem);
            System.out.println(personListJson);
            out.writeUTF(personListJson);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void run() {
//        Person Person = new Person(System.currentTimeMillis(), null, null, "One");
//        Person Person1 = new Person(System.currentTimeMillis(), null, null, "Two");
    }
}
