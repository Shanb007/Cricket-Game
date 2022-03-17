package com.tekion.game.service;

import com.tekion.game.Repository.BallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BallServiceImpl implements BallService{

    @Autowired
    private BallRepository ballRepository;


    public List<Map<String, Object>> ballInfoService(int matchID, String innings, double overNo){
        return ballRepository.getBallInfo(matchID,innings,overNo);
    }

    public List<Map<String, Object>> overInfoService(int matchID, String innings, int overNo){
        return ballRepository.getOverInfo(matchID,innings,overNo);
    }

}
