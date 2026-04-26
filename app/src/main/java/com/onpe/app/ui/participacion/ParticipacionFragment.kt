package com.onpe.app.ui.participacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.onpe.app.R
import com.onpe.app.data.*

class ParticipacionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_participacion, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarDatos(view, false)

        view.findViewById<Button>(R.id.btn_nacional_part).setOnClickListener   { cargarDatos(view, false) }
        view.findViewById<Button>(R.id.btn_extranjero_part).setOnClickListener { cargarDatos(view, true) }
    }

    private fun cargarDatos(view: View, extranjero: Boolean) {
        val ambito = if (extranjero) "extranjero" else "peru"
        val mockStats = if (extranjero) statsExtranjero else statsPeru

        actualizarToggle(view, extranjero)
        actualizarUI(view, mockStats)

        FirestoreService.getStats(ambito) { stats ->
            activity?.runOnUiThread { actualizarUI(view, stats) }
        }
    }

    private fun actualizarUI(view: View, stats: RegionalStats) {
        val pct = stats.porcentajeFinal.replace("%", "").toDoubleOrNull() ?: 0.0
        val electores   = parseNum(stats.electoresHabiles)
        val participan  = parseNum(stats.participantes)
        val ausentes    = electores - participan

        view.findViewById<TextView>(R.id.tv_electores_part).text      = stats.electoresHabiles
        view.findViewById<TextView>(R.id.tv_pct_part).text            = stats.porcentajeFinal
        view.findViewById<TextView>(R.id.tv_participantes_part).text  = stats.participantes
        view.findViewById<TextView>(R.id.tv_ausentismo_part).text     = fmt(ausentes)
        view.findViewById<TextView>(R.id.tv_pct_ausentismo_part).text = stats.ausentismo
        view.findViewById<ProgressBar>(R.id.prog_participacion).progress = pct.toInt()
    }

    private fun actualizarToggle(view: View, extranjero: Boolean) {
        val btnNac = view.findViewById<Button>(R.id.btn_nacional_part)
        val btnExt = view.findViewById<Button>(R.id.btn_extranjero_part)
        if (extranjero) {
            btnExt.backgroundTintList = resources.getColorStateList(R.color.navy_dark, null)
            btnExt.setTextColor(resources.getColor(R.color.white, null))
            btnNac.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            btnNac.setTextColor(resources.getColor(R.color.grey_text, null))
        } else {
            btnNac.backgroundTintList = resources.getColorStateList(R.color.navy_dark, null)
            btnNac.setTextColor(resources.getColor(R.color.white, null))
            btnExt.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            btnExt.setTextColor(resources.getColor(R.color.grey_text, null))
        }
    }

    private fun parseNum(s: String) = s.replace(",", "").toLongOrNull() ?: 0L
    private fun fmt(n: Long): String {
        var result = ""
        val str = n.toString()
        str.reversed().forEachIndexed { i, c -> if (i > 0 && i % 3 == 0) result = ",$result"; result = "$c$result" }
        return result
    }
}
