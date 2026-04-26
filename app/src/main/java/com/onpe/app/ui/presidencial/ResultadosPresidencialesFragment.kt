package com.onpe.app.ui.presidencial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onpe.app.R
import com.onpe.app.adapter.CandidatoPresAdapter
import com.onpe.app.data.*

class ResultadosPresidencialesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_resultados_presidenciales, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rv_candidatos_pres)
        rv.layoutManager = LinearLayoutManager(context)

        cargarDatos(view, false)

        view.findViewById<Button>(R.id.btn_peru_pres).setOnClickListener { cargarDatos(view, false) }
        view.findViewById<Button>(R.id.btn_ext_pres).setOnClickListener  { cargarDatos(view, true) }
    }

    private fun cargarDatos(view: View, extranjero: Boolean) {
        val ambito = if (extranjero) "extranjero" else "peru"
        val mockStats = if (extranjero) statsExtranjero else statsPeru
        val mockCands = if (extranjero) candidatosExtranjero else candidatosPeru

        actualizarToggle(view, extranjero)
        actualizarStats(view, mockStats)

        val rv = view.findViewById<RecyclerView>(R.id.rv_candidatos_pres)
        rv.adapter = CandidatoPresAdapter(mockCands)

        FirestoreService.getStats(ambito) { stats ->
            activity?.runOnUiThread { actualizarStats(view, stats) }
        }
        FirestoreService.getCandidatos(ambito) { lista ->
            activity?.runOnUiThread { rv.adapter = CandidatoPresAdapter(lista) }
        }
    }

    private fun actualizarToggle(view: View, extranjero: Boolean) {
        val btnPeru = view.findViewById<Button>(R.id.btn_peru_pres)
        val btnExt  = view.findViewById<Button>(R.id.btn_ext_pres)
        if (extranjero) {
            btnExt.backgroundTintList  = resources.getColorStateList(R.color.navy_dark, null)
            btnExt.setTextColor(resources.getColor(R.color.white, null))
            btnPeru.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            btnPeru.setTextColor(resources.getColor(R.color.grey_text, null))
        } else {
            btnPeru.backgroundTintList = resources.getColorStateList(R.color.navy_dark, null)
            btnPeru.setTextColor(resources.getColor(R.color.white, null))
            btnExt.backgroundTintList  = android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            btnExt.setTextColor(resources.getColor(R.color.grey_text, null))
        }
    }

    private fun actualizarStats(view: View, stats: RegionalStats) {
        view.findViewById<TextView>(R.id.tv_total_actas_pres).text    = stats.totalActas
        view.findViewById<TextView>(R.id.tv_procesadas_pres).text     = stats.procesadas
        view.findViewById<TextView>(R.id.tv_contabilizadas_pres).text = stats.contabilizadas
        view.findViewById<TextView>(R.id.tv_electores_pres).text      = stats.electoresHabiles
        view.findViewById<TextView>(R.id.tv_participantes_pres).text  = stats.participantes
        view.findViewById<TextView>(R.id.tv_pct_pres).text            = stats.porcentajeFinal
        view.findViewById<TextView>(R.id.tv_validos_pres).text        = stats.votosValidos
        view.findViewById<TextView>(R.id.tv_blancos_pres).text        = stats.votosBlancos
        view.findViewById<TextView>(R.id.tv_nulos_pres).text          = stats.votosNulos
        view.findViewById<TextView>(R.id.tv_emitidos_pres).text       = stats.totalEmitidos
    }
}
