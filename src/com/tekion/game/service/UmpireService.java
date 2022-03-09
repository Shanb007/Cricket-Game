package com.tekion.game.service;


import org.springframework.stereotype.Service;

@Service
public interface UmpireService {
    int SetFirstUmpire();
    int SetSecondUmpire();
    int SetThirdUmpire();
}
