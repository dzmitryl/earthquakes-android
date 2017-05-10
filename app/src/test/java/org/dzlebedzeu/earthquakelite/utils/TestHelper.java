package org.dzlebedzeu.earthquakelite.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

class TestHelper {

    static String getStringFromResource(String resourceName) {
        String result = "";
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(resourceName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
