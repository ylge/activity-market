package com.geyl.base.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.BaseService;
import com.geyl.bean.PageResult;
import com.geyl.bean.ShiroUser;
import com.geyl.util.CamelCaseUtil;
import com.geyl.util.ReflectHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author geyl
 * @date 2017/12/13.
 */
public abstract class BaseServiceImpl<T, E extends Serializable> implements BaseService<T, E> {

    /**
     * general field(通用字段)
     */
    private static final String CREATE_BY = "createBy";

    private static final String CREATE_TIME = "createTime";

    private static final String UPDATE_BY = "updateBy";

    private static final String UPDATE_TIME = "updateTime";

    public abstract BaseMapper<T, E> getMappser();

    @Override
    public int deleteByPrimaryKey(E id) {
        return getMappser().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        record = addValue(record, true);
        return getMappser().insert(record);
    }

    /**
     * 通用注入创建 更新信息 可通过super调用
     *
     * @param record
     * @param flag
     * @return
     */
    public T addValue(T record, boolean flag) {
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //统一处理公共字段
        Class<?> clazz = record.getClass();
        String operator, operateDate;
        try {
            if (flag) {
                operator = CREATE_BY;
                operateDate = CREATE_TIME;
            } else {
                operator = UPDATE_BY;
                operateDate = UPDATE_TIME;
            }
            Field fieldDate = ReflectHelper.getTargetField(clazz, operateDate);
            if (!ObjectUtils.isEmpty(fieldDate)) {
                fieldDate.set(record, new Date());
            }
            Field field = ReflectHelper.getTargetField(clazz, operator);
            if (!ObjectUtils.isEmpty(field)) {
                field.set(record, currentUser.getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return record;
    }

    @Override
    public int insertSelective(T record) {
        record = addValue(record, true);
        return getMappser().insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(E id) {
        return getMappser().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        record = addValue(record, false);
        return getMappser().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        record = addValue(record, false);
        return getMappser().updateByPrimaryKey(record);
    }

    @Override
    public List<T> selectAll(T record) {
        return getMappser().selectAll(record);
    }

    /**
     * 公共展示类
     *
     * @param t 实体
     * @return
     */
    public PageResult<T> pageList(T t) {
        Integer offset = (Integer) ReflectHelper.getFieldValue(t, "offset");
        Integer limit = (Integer) ReflectHelper.getFieldValue(t, "limit");
        String order = (String) ReflectHelper.getFieldValue(t, "order");
        String sort = (String) ReflectHelper.getFieldValue(t, "sort");
        PageHelper.startPage(offset / limit + 1, limit, CamelCaseUtil.toUnderlineName(sort+" "+order));
        List<T> tList = getMappser().selectListByPage(t);
        PageResult<T> result = new PageResult<>(new PageInfo<>(tList));
        return result;
    }

}
