package com.example.volei.model

class Partidas {
    var partidas: MutableList<Partida> = mutableListOf()

    constructor() : super() {}

    constructor(partidas: MutableList<Partida>) : super() {
        this.partidas = partidas
    }
}