package com.tws.hunt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tws.hunt.stages.ActiveProductByCategoryStage;

@Service
public class HuntGameService {

    @Autowired
    private ActiveProductByCategoryStage stage;

    public void play() throws Exception {
        stage.play();
    }
}
