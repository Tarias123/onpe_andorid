package com.onpe.app.data

data class Candidato(
    val nombre: String = "",
    val partido: String = "",
    val porcentaje: Double = 0.0,
    val porcentajeEmitidos: Double = 0.0,
    val votos: Int = 0,
    val fotoRes: Int = 0
)

data class RegionalStats(
    val totalActas: String = "",
    val procesadas: String = "",
    val contabilizadas: String = "",
    val electoresHabiles: String = "",
    val participantes: String = "",
    val porcentajeFinal: String = "",
    val ausentismo: String = "",
    val votosValidos: String = "",
    val pctValidos: Double = 0.0,
    val votosBlancos: String = "",
    val pctBlancos: Double = 0.0,
    val votosNulos: String = "",
    val pctNulos: Double = 0.0,
    val totalEmitidos: String = ""
)
