package com.onpe.app.data

import com.onpe.app.R

val candidatosPeru = listOf(
    Candidato("Pedro Pablo Kuczynski", "Peruanos por el Kambio", 50.120, 46.868, 8596937, R.drawable.ppk),
    Candidato("Keiko Fujimori", "Fuerza Popular", 49.880, 46.644, 8555880, R.drawable.keiko)
)

val candidatosExtranjero = listOf(
    Candidato("Pedro Pablo Kuczynski", "Peruanos por el Kambio", 52.180, 48.524, 189016, R.drawable.ppk),
    Candidato("Keiko Fujimori", "Fuerza Popular", 47.820, 44.451, 173146, R.drawable.keiko)
)

val statsPeru = RegionalStats(
    totalActas = "77,307", procesadas = "77,307", contabilizadas = "77,307",
    electoresHabiles = "22,901,954", participantes = "18,342,896",
    porcentajeFinal = "80.093%", ausentismo = "19.907%",
    votosValidos = "17,152,817", pctValidos = 93.512,
    votosBlancos = "149,577", pctBlancos = 0.815,
    votosNulos = "1,040,502", pctNulos = 5.673,
    totalEmitidos = "18,342,896"
)

val statsExtranjero = RegionalStats(
    totalActas = "5,847", procesadas = "5,847", contabilizadas = "5,847",
    electoresHabiles = "884,924", participantes = "389,529",
    porcentajeFinal = "44.018%", ausentismo = "55.982%",
    votosValidos = "362,162", pctValidos = 92.975,
    votosBlancos = "15,820", pctBlancos = 4.062,
    votosNulos = "11,547", pctNulos = 2.965,
    totalEmitidos = "389,529"
)
