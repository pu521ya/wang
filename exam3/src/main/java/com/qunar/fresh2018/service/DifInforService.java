package com.qunar.fresh2018.service;

import com.qunar.fresh2018.dao.DifInforDao;
import com.qunar.fresh2018.model.DifInfor;
import com.qunar.fresh2018.util.DiffCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DifInforService {
    //private static final Logger LOGGER = LoggerFactory.getLogger(DifInforService.class);
    public static final int FILE_MAX_SIZE = 1024;

    @Resource
    private DifInforDao diffDao;


    public Integer gettDiffCount() {
        return diffDao.getDiffCount();
    }


    public List<DifInfor> selectDiffForPage(int page) {
        //只搜寻前5条记录  0<=page<=2
        if(page>2)
            return null;
        else if(page == 2)
            return diffDao.selectDiffForPage(new RowBounds(page * 2, 1));
        else return diffDao.selectDiffForPage(new RowBounds(page * 2, 2));
    }


    @Transactional
    public int insertDiff(DifInfor diff) {
        if (diff.getSourceContent() == null || diff.getSourceContent().length() > DifInforService.FILE_MAX_SIZE) {
            throw new IllegalArgumentException("源文件大小不符合条件!");
        }
        if (diff.getTargetContent() == null || diff.getTargetContent().length() > DifInforService.FILE_MAX_SIZE){
            throw new IllegalArgumentException("目标文件大小不符合条件!");
        }
        if (diff.getDifference() == null || diff.getDifference().length() > DifInforService.FILE_MAX_SIZE) {
            throw new IllegalArgumentException("差异数据大小异常!");
        }
        return diffDao.insertDiff(diff);
    }

    @Transactional
    public boolean deleteDiff(Integer id) {
        return diffDao.deleteDiffById(id) > 0;
    }

    @Transactional
    public boolean createDiffAndInsert(MultipartFile source, MultipartFile target) {
        try {
            DifInfor diff = DiffCreateUtil.createDiff(source, target);
            insertDiff(diff);
        } catch (IOException e) {
            log.error("文件对比失败!", e);
            return false;
        }
        return true;
    }
}