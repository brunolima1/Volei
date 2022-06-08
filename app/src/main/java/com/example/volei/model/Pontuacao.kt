package com.example.volei.model

class Pontuacao {
    var score1: Int = 0
    var score2: Int = 0
    var sets1: Int = 0
    var sets2: Int = 0

    constructor() : super() {}

    constructor(score1: Int, score2: Int, sets1: Int, sets2: Int) : super() {
        this.score1 = score1
        this.score2 = score2
        this.sets1 = sets1
        this.sets2 = sets2
    }
}