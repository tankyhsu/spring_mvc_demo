package com.springmvc.spring;

/**
 * Created by Administrator on 2016/4/1.
 */
public class Profiles {
    public static final String ACTIVE_PROFILE = "spring.profiles.active";
    public static final String DEFAULT_PROFILE = "spring.profiles.default";

    public static final String PRODUCTION = "production";
    public static final String DEVELOPMENT = "development";
    public static final String UNIT_TEST = "test";
    public static final String FUNCTIONAL_TEST = "functional";

    /**
     * 在Spring启动前，设置profile的环境变量。
     */
    public static void setProfileAsSystemProperty(String profile) {
        System.setProperty(ACTIVE_PROFILE, profile);
    }
}
