package com.wolfman.travel.Component;

import com.wolfman.travel.bean.PageBean;
import com.wolfman.travel.bean.Route;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PageComponent {

    //无参构造
    public PageComponent() {
    }

    /**
     * 分页封装工具
     *
     * @param currentPage
     * @param totalCount
     * @param list
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageUtil(int currentPage, int totalCount, List<Route> list, int pageSize) {
        PageBean<Route> pb = new PageBean<>();
        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        pb.setList(list);
        //设置总页数
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * 数据转换
     *
     * @param oldData
     * @param expectedData
     * @return
     */
    public int dataTransfer(String oldData, int expectedData) {
        //判断原有数据是否为空
        if (oldData != null && oldData.length() > 0 && !"null".equals(oldData)) {
            return Integer.parseInt(oldData);
        } else {
            return expectedData;
        }
    }

}
