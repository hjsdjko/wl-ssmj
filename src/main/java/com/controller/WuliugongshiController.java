package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.WuliugongshiEntity;

import com.service.WuliugongshiService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 物流公司表
 * 后端接口
 * @author
 * @email
 * @date 2021-02-25
*/
@RestController
@Controller
@RequestMapping("/wuliugongshi")
public class WuliugongshiController {
    private static final Logger logger = LoggerFactory.getLogger(WuliugongshiController.class);

    @Autowired
    private WuliugongshiService wuliugongshiService;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        logger.debug("Controller:"+this.getClass().getName()+",page方法");
        PageUtils page = wuliugongshiService.queryPage(params);
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("Controller:"+this.getClass().getName()+",info方法");
        WuliugongshiEntity wuliugongshi = wuliugongshiService.selectById(id);
        if(wuliugongshi!=null){
            return R.ok().put("data", wuliugongshi);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody WuliugongshiEntity wuliugongshi, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",save");
        Wrapper<WuliugongshiEntity> queryWrapper = new EntityWrapper<WuliugongshiEntity>()
            .eq("name", wuliugongshi.getName())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        WuliugongshiEntity wuliugongshiEntity = wuliugongshiService.selectOne(queryWrapper);
        if(wuliugongshiEntity==null){
            wuliugongshiService.insert(wuliugongshi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody WuliugongshiEntity wuliugongshi, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",update");
        //根据字段查询是否有相同数据
        Wrapper<WuliugongshiEntity> queryWrapper = new EntityWrapper<WuliugongshiEntity>()
            .notIn("id",wuliugongshi.getId())
            .eq("name", wuliugongshi.getName())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        WuliugongshiEntity wuliugongshiEntity = wuliugongshiService.selectOne(queryWrapper);
        if(wuliugongshiEntity==null){
            wuliugongshiService.updateById(wuliugongshi);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        logger.debug("Controller:"+this.getClass().getName()+",delete");
        wuliugongshiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}

