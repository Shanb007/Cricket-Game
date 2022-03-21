package com.tekion.game.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BallService {

    List<Map<String, Object>> overInfoService(int matchID, String innings, int overNo) throws SQLException;
    List<Map<String, Object>> ballInfoService(int matchID, String innings, double overNo) throws SQLException;
}
