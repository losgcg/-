package com.example.manage.service;

import com.example.manage.pojo.Fruit;


import java.util.List;

public interface FruitService {
    public List<Fruit> selectAllFruitLimits();

    //根据id删除水果信息
    public int deleteFruitById(int id);
    //根据id修改水果信息
    public int updateFruitById(int id,String jsonStr);
}
