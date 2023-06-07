package com.myproject.twitch;

import com.myproject.twitch.external.TwitchService;
import com.myproject.twitch.external.model.Clip;
import com.myproject.twitch.external.model.Game;
import com.myproject.twitch.external.model.Stream;
import com.myproject.twitch.external.model.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevelopmentTester implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);


    public DevelopmentTester() {
    }


    @Override
    public void run(ApplicationArguments args) {
    }
}


