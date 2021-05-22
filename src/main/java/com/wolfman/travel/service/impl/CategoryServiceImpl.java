package com.wolfman.travel.service.impl;


import com.wolfman.travel.mapper.CategoryMapper;
import com.wolfman.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    @Qualifier("cacheManager")
    RedisCacheManager redisCacheManager;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查找所有目录
     *
     * @return
     */
    @Override
    public Object findAll() {
        //获取cacheManager
        Cache cacheManager = redisCacheManager.getCache("cacheManager");
        //获取缓存中的category数据
        Object category = redisTemplate.opsForValue().get("category");
        //如果缓存中的数据为空
        if (category == null) {
            //调用categoryMapper查询所有目录
            category = categoryMapper.findAll();
            //将查询结果放入缓存中
            cacheManager.put("category", category);
        }
        return category;
    }
}
