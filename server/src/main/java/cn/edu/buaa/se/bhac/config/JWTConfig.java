package cn.edu.buaa.se.bhac.config;

import cn.edu.buaa.se.bhac.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class JWTConfig {

    public final static String secretKey = "bhac@)@!)#(@!_SKAL@_@SLA";
    public final static Long expirationTime = 7 * 24 * 60 * 60 * 1000L;

}
