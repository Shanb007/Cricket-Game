package com.tekion.game.service;

import com.tekion.game.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public String teamName(int teamID){
        return teamRepository.getTeamName(teamID);
    }

    public List<Map<String, Object>> allTeams(){
        return teamRepository.getAllTeams();
    }

    public List<String> teamPlayingMatch(int matchID){
        return teamRepository.getTeamsInMatch(matchID);
    }
}
