package com.onpe.app.data

import com.google.firebase.firestore.FirebaseFirestore
import com.onpe.app.R

object FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    fun getCandidatos(ambito: String, onResult: (List<Candidato>) -> Unit) {
        db.collection("candidatos")
            .whereEqualTo("ambito", ambito)
            .addSnapshotListener { snap, error ->
                if (error != null || snap == null) return@addSnapshotListener
                val lista = snap.documents
                    .sortedBy { (it.getLong("orden") ?: 0).toInt() }
                    .map { doc ->
                        Candidato(
                            nombre = doc.getString("nombre") ?: "",
                            partido = doc.getString("partido") ?: "",
                            porcentaje = doc.getDouble("porcentaje") ?: 0.0,
                            porcentajeEmitidos = doc.getDouble("porcentajeEmitidos") ?: 0.0,
                            votos = (doc.getLong("votos") ?: 0).toInt(),
                            fotoRes = if (doc.id.startsWith("ppk")) R.drawable.ppk else R.drawable.keiko
                        )
                    }
                if (lista.isNotEmpty()) onResult(lista)
            }
    }

    fun getStats(ambito: String, onResult: (RegionalStats) -> Unit) {
        db.collection("stats").document(ambito)
            .addSnapshotListener { doc, error ->
                if (error != null || doc == null || !doc.exists()) return@addSnapshotListener
                val stats = RegionalStats(
                    totalActas       = doc.getString("totalActas") ?: "",
                    procesadas       = doc.getString("procesadas") ?: "",
                    contabilizadas   = doc.getString("contabilizadas") ?: "",
                    electoresHabiles = doc.getString("electoresHabiles") ?: "",
                    participantes    = doc.getString("participantes") ?: "",
                    porcentajeFinal  = doc.getString("porcentajeFinal") ?: "",
                    ausentismo       = doc.getString("ausentismo") ?: "",
                    votosValidos     = doc.getString("votosValidos") ?: "",
                    pctValidos       = doc.getDouble("pctValidos") ?: 0.0,
                    votosBlancos     = doc.getString("votosBlancos") ?: "",
                    pctBlancos       = doc.getDouble("pctBlancos") ?: 0.0,
                    votosNulos       = doc.getString("votosNulos") ?: "",
                    pctNulos         = doc.getDouble("pctNulos") ?: 0.0,
                    totalEmitidos    = doc.getString("totalEmitidos") ?: ""
                )
                onResult(stats)
            }
    }
}
