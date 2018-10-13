package com.qunar.fresh2018.util;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.qunar.fresh2018.model.DifInfor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
public class DiffCreateUtil {


    //获取文件源并解析, 然后创建Diff并将解析到的source, target, difference添加

    public static DifInfor createDiff(MultipartFile source, MultipartFile target) throws IOException {
        DifInfor diff = new DifInfor();
        StringBuilder sourceStrBuilder = new StringBuilder();
        StringBuilder targetStrBuilder = new StringBuilder();
        Map<String, String> sourceMap = com.qunar.fresh2018.util.FileUtil.parseFile(source, sourceStrBuilder);
        Map<String, String> targetMap = com.qunar.fresh2018.util.FileUtil.parseFile(target, targetStrBuilder);

        diff.setSourceContent(sourceStrBuilder.toString());
        diff.setTargetContent(targetStrBuilder.toString());
        diff.setDifference(makeDiff(sourceMap, targetMap));
        diff.setTime(new Timestamp(System.currentTimeMillis()));
        return diff;
    }

    /**
     * 分为三种情况
     * 源存在、目标不存在 => -a2=y
     * 源不存在、目标存在 => +a4=n
     * 源和目标存在、但不同 => -a3=z;+a3=x
     */
    private static String makeDiff(Map<String, String> sourceMap, Map<String, String> targetMap) {
        StringBuilder sb = new StringBuilder();
        MapDifference<String, String> diffHandle = Maps.difference(sourceMap, targetMap);
        Map<String, String> diffOnLeft = diffHandle.entriesOnlyOnLeft();
        Map<String, String> diffOnRight = diffHandle.entriesOnlyOnRight();
        Map<String, MapDifference.ValueDifference<String>> diffOnBoth = diffHandle.entriesDiffering();

        for (Map.Entry<String, String> entry : diffOnLeft.entrySet()) {
            String key = entry.getKey();
            sb.append("-").append(key).append("=").append(entry.getValue()).append(com.qunar.fresh2018.util.FileUtil.LINE_SEPARATOR);
        }

        for (Map.Entry<String, String> entry : diffOnRight.entrySet()) {
            String key = entry.getKey();
            sb.append("+").append(key).append("=").append(entry.getValue()).append(com.qunar.fresh2018.util.FileUtil.LINE_SEPARATOR);
        }

        for (Map.Entry<String, MapDifference.ValueDifference<String>> entry : diffOnBoth.entrySet()) {
            String key = entry.getKey();
            MapDifference.ValueDifference<String> valueDifference = entry.getValue();
            sb.append("-").append(key).append("=").append(valueDifference.leftValue()).append(";").append("+")
                    .append(key).append("=").append(valueDifference.rightValue()).append(com.qunar.fresh2018.util.FileUtil.LINE_SEPARATOR);
        }
        return sb.toString();
    }

}