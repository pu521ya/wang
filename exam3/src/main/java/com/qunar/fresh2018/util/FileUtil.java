package com.qunar.fresh2018.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
@Slf4j
public class FileUtil {
    //private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    private static final String SEPARATOR = "=";
    public static final String LINE_SEPARATOR = "<br>";

    public static Map<String, String> parseFile(MultipartFile file, StringBuilder sourceStrBuilder) throws IOException {
        Map<String, String> map = Maps.newHashMap();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(SEPARATOR);
                if (split.length == 2){
                    map.put(split[0],split[1]);
                    sourceStrBuilder.append(line).append(LINE_SEPARATOR);
                }
                else {
                    throw new IOException("文件格式异常.");
                }
            }
        } catch (IOException e) {
            log.error("解析文件异常!", file);
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("IO流关闭异常!", br);
                    throw e;
                }
            }
        }
        return map;
    }
}