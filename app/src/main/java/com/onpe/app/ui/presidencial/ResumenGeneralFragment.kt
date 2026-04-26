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
import com.onpe.app.adapter.CandidatoDesgloseAdapter
import com.onpe.app.data.*

class ResumenGeneralFragment : Fragment() {

    private var esExtranjero = false
    private var statsActuales = statsPeru
    private var candidatosActuales = candidatosPeru

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_resumen_general, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPeru       = view.findViewById<Button>(R.id.btn_peru)
        val btnExtranjero = view.findViewById<Button>(R.id.btn_extranjero)
        val rv            = view.findViewById<RecyclerView>(R.id.rv_candidatos_desglose)
        val tvFullTable   = view.findViewById<TextView>(R.id.tv_view_full_table)

        rv.layoutManager = LinearLayoutManager(context)

        // Cargar datos iniciales (Perú)
        cargarDatos(view, false)

        btnPeru.setOnClickListener {
            btnPeru.backgroundTintList = resources.getColorStateList(R.color.navy_dark, null)
            btnPeru.setTextColor(resources.getColor(R.color.white, null))
            btnExtranjero.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            btnExtranjero.setTextColor(resources.getColor(R.color.grey_text, null))
            cargarDatos(view, false)
        }

        btnExtranjero.setOnClickListener {
            btnExtranjero.backgroundTintList = resources.getColorStateList(R.color.navy_dark, null)
            btnExtranjero.setTextColor(resources.getColor(R.color.white, null))
            btnPeru.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            btnPeru.setTextColor(resources.getColor(R.color.grey_text, null))
            cargarDatos(view, true)
        }

        tvFullTable.setOnClickListener {
            FullTableBottomSheet(candidatosActuales, statsActuales)
                .show(parentFragmentManager, "full_table")
        }
    }

    private fun cargarDatos(view: View, extranjero: Boolean) {
        esExtranjero = extranjero
        val ambito = if (extranjero) "extranjero" else "peru"
        val mockStats = if (extranjero) statsExtranjero else statsPeru
        val mockCandidatos = if (extranjero) candidatosExtranjero else candidatosPeru

        statsActuales = mockStats
        candidatosActuales = mockCandidatos
        actualizarUI(view, mockStats, mockCandidatos)

        FirestoreService.getStats(ambito) { stats ->
            statsActuales = stats
            activity?.runOnUiThread { actualizarUI(view, stats, candidatosActuales) }
        }

        FirestoreService.getCandidatos(ambito) { lista ->
            candidatosActuales = lista
            activity?.runOnUiThread {
                val rv = view.findViewById<RecyclerView>(R.id.rv_candidatos_desglose)
                rv.adapter = CandidatoDesgloseAdapter(lista)
            }
        }
    }

    private fun actualizarUI(view: View, stats: RegionalStats, candidatos: List<Candidato>) {
        view.findViewById<TextView>(R.id.tv_actas_estado).text =
            "${stats.contabilizadas} / ${stats.totalActas}"
        view.findViewById<TextView>(R.id.tv_porcentaje_final).text = stats.porcentajeFinal
        view.findViewById<TextView>(R.id.tv_electores).text = stats.electoresHabiles
        view.findViewById<TextView>(R.id.tv_participantes).text = stats.participantes

        val rv = view.findViewById<RecyclerView>(R.id.rv_candidatos_desglose)
        rv.adapter = CandidatoDesgloseAdapter(candidatos)

        // Progreso
        view.findViewById<View>(R.id.prog_total).let {
            it.findViewById<TextView>(R.id.tv_prog_label).text = "Total Actas (${stats.totalActas})"
            it.findViewById<TextView>(R.id.tv_prog_value).text = "100.000%"
        }
        view.findViewById<View>(R.id.prog_procesadas).let {
            it.findViewById<TextView>(R.id.tv_prog_label).text = "Actas Procesadas"
            it.findViewById<TextView>(R.id.tv_prog_value).text = "100.000%"
        }
        view.findViewById<View>(R.id.prog_contabilizadas).let {
            it.findViewById<TextView>(R.id.tv_prog_label).text = "Actas Contabilizadas"
            it.findViewById<TextView>(R.id.tv_prog_value).text = "100.000%"
        }
    }
}
