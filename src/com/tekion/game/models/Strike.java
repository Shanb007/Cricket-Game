package com.tekion.game.models;

public class Strike {
private int onStrike = 0;
private int notOnStrike = 1;

int OnStrike(){
    return onStrike;
}

int NotOnStrike(){
    return notOnStrike;
}

void changeStrike(int currentStrike, int currentNonStrike){
notOnStrike = currentStrike;
onStrike = currentNonStrike;
}
void changeCurrentOnStrike(int newPlayerNumber){
    onStrike = newPlayerNumber;
}
}
