package com.example.volei.model

class Partida {
    var id: Int = 0
    var time1: String = ""
    var time2: String = ""
    var score1: Int = 0
    var score2: Int = 0

    constructor() : super() {}

    constructor(id: Int, time1: String, time2: String, score1: Int, score2: Int) : super() {
        this.id = id
        this.time1 = time1
        this.time2 = time2
        this.score1 = score1
        this.score2 = score2
    }
}