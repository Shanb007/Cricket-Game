package com.tekion.game.service;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface MatchService {
    String matchDeclaration(int overs) throws SQLException;
    ArrayList<String> startFirstInnings(int overs) throws SQLException, ClassNotFoundException;
    ArrayList<String> startSecondInnings(int overs) throws SQLException, ClassNotFoundException;
    void ShowResults() throws SQLException;
}
