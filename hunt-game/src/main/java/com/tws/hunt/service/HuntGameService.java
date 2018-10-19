package com.tws.hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tws.hunt.stages.ProductCountStage;

@Service
public class HuntGameService {

    @Autowired
    private ProductCountStage stage;

    public void play() throws Exception {
        stage.play();
    }
}
