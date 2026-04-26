package com.onpe.app.ui.presidencial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onpe.app.R
import com.onpe.app.data.Candidato
import com.onpe.app.data.RegionalStats
import java.text.NumberFormat
import java.util.Locale

class FullTableBottomSheet(
    private val candidatos: List<Candidato>,
    private val stats: RegionalStats
) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.bottom_sheet_full_table, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btn_close_sheet).setOnClickListener { dismiss() }

        val container = view.findViewById<LinearLayout>(R.id.ll_candidatos_table)
        container.removeAllViews()

        candidatos.forEach { c ->
            val row = LayoutInflater.from(context).inflate(R.layout.item_candidato_table, container, false)
            row.findViewById<TextView>(R.id.tv_nombre_table).text = c.nombre
            row.findViewById<TextView>(R.id.tv_pct_validos_table).text = "${"%.3f".format(c.porcentaje)}%"
            row.findViewById<TextView>(R.id.tv_pct_emitidos_table).text = "${"%.3f".format(c.porcentajeEmitidos)}%"
            row.findViewById<TextView>(R.id.tv_votos_table).text = fmt(c.votos)
            if (c.porcentaje > 50) {
                row.setBackgroundColor(resources.getColor(R.color.gold_bg, null))
            }
            container.addView(row)
        }

        // Stats
        view.findViewById<TextView>(R.id.tv_stat_electores).text   = stats.electoresHabiles
        view.findViewById<TextView>(R.id.tv_stat_particip).text    = stats.participantes
        view.findViewById<TextView>(R.id.tv_stat_pct_particip).text = stats.porcentajeFinal
        view.findViewById<TextView>(R.id.tv_stat_validos).text     = stats.votosValidos
        view.findViewById<TextView>(R.id.tv_stat_blancos).text     = stats.votosBlancos
        view.findViewById<TextView>(R.id.tv_stat_nulos).text       = stats.votosNulos
        view.findViewById<TextView>(R.id.tv_stat_emitidos).text    = stats.totalEmitidos
        view.findViewById<TextView>(R.id.tv_stat_total_actas).text = stats.totalActas
        view.findViewById<TextView>(R.id.tv_stat_procesadas).text  = stats.procesadas
        view.findViewById<TextView>(R.id.tv_stat_contabilizadas).text = stats.contabilizadas
    }

    private fun fmt(n: Int) = NumberFormat.getNumberInstance(Locale.US).format(n)
}
