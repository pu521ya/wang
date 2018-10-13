package com.qunar.fresh2018.service;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class UserService {
    //private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final Map<String,String> accountsMap = Maps.newHashMap();
    public static final Splitter SPLITTER = Splitter.on("=").omitEmptyStrings().trimResults();
    public static final String ACCOUNT_PROPERTIES_PATH = "/account.properties";

    static {
        try {
            Files.readLines(new File(UserService.class.getResource(ACCOUNT_PROPERTIES_PATH).getPath()), Charsets.UTF_8,
                    new LineProcessor<String>() {
                        @Override
                        public boolean processLine(String line) throws IOException {
                            List<String> params = SPLITTER.splitToList(line);
                            if (params.size() == 2) {
                                accountsMap.put(params.get(0), params.get(1));
                                return true;
                            } else {
                                throw new IOException("Illegal account properties : " + line);
                            }
                        }

                        @Override
                        public String getResult() {
                            return "";
                        }
                    });
        } catch (IOException e) {
            log.error("Get account properties failed!", e);
        }
    }
    public static boolean checkUser(String username, String password) {
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            return false;
        }
        return password.equals(accountsMap.get(username));
    }

}