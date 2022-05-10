package com.example.manage.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.manage.mapper.FruitMapper;
import com.example.manage.pojo.Fruit;
import com.example.manage.service.FruitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    @Autowired
    FruitMapper fruitMapper;

    @Override
    public List<Fruit> selectAllFruitLimits(){

        return fruitMapper.selectAllFruitLimits();
    }

    @Override
    public int deleteFruitById(int id) {
        if(id==0) {
            throw new RuntimeException("未知！");
        }
        int num = fruitMapper.deleteFruitById(id);
        if(num == 0) {//如果受影响行数为0，证明sql肯定出问题了，要将错误信息存储到日志里
            throw new RuntimeException("程序发生重大错误，请联系管理员！");
        }
        return num;
    }

    @Override
    public int updateFruitById(int id,String jsonStr) {
        if(id==0) {
            throw new RuntimeException("未知！");
        }
        Fruit fruit = JSON.parseObject(jsonStr,Fruit.class);
        fruit.setId(id);
        int num = fruitMapper.updateFruitById(fruit);
        if(num == 0) {
            throw new RuntimeException("程序发生重大错误，请联系管理员！");
        }
        num = fruitMapper.updateFruitById(fruit);
        if(num == 0) {
            throw new RuntimeException("程序发生重大错误，请联系管理员！");
        }
        return num;

    }
}
