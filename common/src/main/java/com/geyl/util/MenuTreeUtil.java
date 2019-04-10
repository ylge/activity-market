package com.geyl.util;

import com.geyl.bean.model.system.SysMenu;
import com.geyl.bean.model.system.SysRoleMenu;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author geyl
 * date 2018/10/21 0021 下午 15:58
 */
public class MenuTreeUtil {

    private List<SysMenu> nodes;

    private List<SysRoleMenu> checknodes;

    /**
     * 创建一个新的实例 Tree.
     *
     * @param nodes   将树的所有节点都初始化进来。
     */
    public MenuTreeUtil(List<SysMenu> nodes, List<SysRoleMenu> checknodes){
        this.nodes = nodes;
        this.checknodes = checknodes;
    }


    /**
     * buildTree
     * 描述:  创建树
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    public List<Map<String, Object>> buildTree(){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(SysMenu node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (node.getParentId()==0) {
                Map<String, Object> map = buildTreeChildsMap(node);
                list.add(map);
            }
        }
        return list;
    }


    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private List<Map<String, Object>> buildTreeChilds(SysMenu node){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        List<SysMenu> childNodes = getChilds(node);
        for(SysMenu childNode : childNodes){
            //System.out.println("childNode"+childNode.getMenuName());
            Map<String, Object> map = buildTreeChildsMap(childNode);
            list.add(map);
        }
        return list;
    }

    /**
     * buildChildMap
     * 描述:生成Map节点
     * @param childNode
     * @return Map<String, Object>
     */
    private Map<String, Object> buildTreeChildsMap(SysMenu childNode){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> stateMap = new HashMap<>();
        stateMap.put("checked", false);
        for(SysRoleMenu checknode : checknodes){
            if(checknode.getMenuId().equals(childNode.getId())){
                stateMap.put("checked", true);
            }
        }
        stateMap.put("disabled", false);
        stateMap.put("expanded", false);
        stateMap.put("selected", false);
        map.put("id", childNode.getId());
        map.put("text", childNode.getName());
        map.put("url", childNode.getUrl());
        map.put("state", stateMap);
        List<Map<String, Object>> childs = buildTreeChilds(childNode);
        if(childs.isEmpty() || childs.size() == 0){
            //map.put("state","open");
        }else{
            map.put("nodes", childs);
        }
        return map;
    }


    /**
     * getChilds
     * 描述:  获取子节点
     * @param parentNode
     * @return List<Resource>
     * @exception
     * @since  1.0.0
     */
    public List<SysMenu> getChilds(SysMenu parentNode) {
        List<SysMenu> childNodes = new ArrayList<>();
        for(SysMenu node : nodes){
            //System.out.println(node.getParentId()+"-------"+parentNode.getId());
            if (node.getParentId().equals(parentNode.getId())) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }

    /**
     * buildTree
     * 描述:  创建树
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    public List<SysMenu> buildTreeGrid(){
        List<SysMenu> list = new ArrayList<SysMenu>();
        for(SysMenu node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (StringUtils.equals(node.getParentId().toString(), "0")) {
                List<SysMenu> childs = buildTreeGridChilds(node);
                node.setChild(childs);
                list.add(node);
            }
        }
        return list;
    }

    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private List<SysMenu> buildTreeGridChilds(SysMenu node){
        List<SysMenu> list = new ArrayList<SysMenu>();
        List<SysMenu> childNodes = getChilds(node);
        for(SysMenu childNode : childNodes){
            //System.out.println("childNode"+childNode.getMenuName());
            List<SysMenu> childs = buildTreeGridChilds(childNode);
            childNode.setChild(childs);
            list.add(childNode);
        }
        return list;
    }




}
