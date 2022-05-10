package com.example.manage.mapper;

import com.example.manage.pojo.Fruit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FruitMapper {

    //分页查询所有水果信息
    public List<Fruit> selectAllFruitLimits();
    //根据id删除水果信息
    public int deleteFruitById(int id);
    //根据id修改水果信息
    public int updateFruitById(Fruit fruit);
}
