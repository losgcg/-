package com.example.manage.controller;

import com.example.manage.pojo.Fruit;
import com.example.manage.service.FruitService;
import com.example.manage.utils.ResultMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class FruitController {
    @Autowired
    private FruitService fruitService;
    @Autowired
    private ResultMap resultMap;

    /*分页展示数据*/
    @RequestMapping("/selectAllFruitLimits")
    @ResponseBody
    public ResultMap selectAllFruitLimits(int page,int limit){
        try{
            PageHelper.startPage(page,limit);
            List<Fruit> list = fruitService.selectAllFruitLimits();
            PageInfo<Fruit> pageInfo = new PageInfo<Fruit>(list);
            long total = pageInfo.getTotal();
            resultMap.setCode(0);
            resultMap.setList(list);
            resultMap.setTotal(total);

        } catch (Exception e){
            resultMap.setCode(1);
            resultMap.setMessage(e.getMessage());
        }
        return resultMap;
    }
    /*通过id删除水果信息*/
    @RequestMapping("/deleteFruitById")
    @ResponseBody
    public ResultMap deleteVideoById(int id) {
        try {
            fruitService.deleteFruitById(id);
            resultMap.setStatus(true);
        } catch (Exception e) {
            resultMap.setStatus(false);
            resultMap.setMessage(e.getMessage());
        }
        return resultMap;
    }

    /*通过id修改水果信息*/
    @RequestMapping("/updateFruitById")
    @ResponseBody
    public ResultMap updateFruitById(int id,String jsonStr) {
        try {
            fruitService.updateFruitById(id,jsonStr);
            resultMap.setStatus(true);
        } catch (Exception e) {
            resultMap.setStatus(false);
            resultMap.setMessage(e.getMessage());
        }
        return resultMap;
    }


}
