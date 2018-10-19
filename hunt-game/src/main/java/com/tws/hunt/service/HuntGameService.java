package com.tws.hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tws.hunt.stages.ActiveProductCountStage;

@Service
public class HuntGameService {

    @Autowired
    private ActiveProductCountStage stage;

    public void play() throws Exception {
        stage.play();
    }
}
