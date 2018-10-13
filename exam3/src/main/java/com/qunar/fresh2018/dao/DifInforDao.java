package com.qunar.fresh2018.dao;
import com.qunar.fresh2018.model.DifInfor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
public interface DifInforDao {

    //插入
    int insertDiff(DifInfor difInfor);

    //删除
    int deleteDiffById(@Param("id") int id);


    Integer getDiffCount();

    //分页查询
    List<DifInfor> selectDiffForPage(RowBounds rowBounds);

}