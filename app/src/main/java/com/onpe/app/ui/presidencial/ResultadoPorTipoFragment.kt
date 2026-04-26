package com.onpe.app.ui.presidencial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.onpe.app.R
import com.onpe.app.data.*

class ResultadoPorTipoFragment : Fragment() {

    private var esExtranjero = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_resultado_por_tipo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_peru_tipo).setOnClickListener  { cambiarAmbito(view, false) }
        view.findViewById<Button>(R.id.btn_ext_tipo).setOnClickListener   { cambiarAmbito(view, true) }

        cambiarAmbito(view, false)
    }

    private fun cambiarAmbito(view: View, extranjero: Boolean) {
        esExtranjero = extranjero
        val ambito = if (extranjero) "extranjero" else "peru"
        val mockStats = if (extranjero) statsExtranjero else statsPeru

        actualizarToggle(view, extranjero)
        actualizarUI(view, mockStats)

        FirestoreService.getStats(ambito) { stats ->
            activity?.runOnUiThread { actualizarUI(view, stats) }
        }
    }

    private fun actualizarUI(view: View, stats: RegionalStats) {
        val validos  = parseNum(stats.votosValidos)
        val blancos  = parseNum(stats.votosBlancos)
        val nulos    = parseNum(stats.votosNulos)
        val total    = validos + blancos + nulos

        val pctV = if (total > 0) validos.toDouble() / total * 100 else 0.0
        val pctB = if (total > 0) blancos.toDouble() / total * 100 else 0.0
        val pctN = if (total > 0) nulos.toDouble()   / total * 100 else 0.0

        view.findViewById<TextView>(R.id.tv_pct_validos_tipo).text  = "${"%.3f".format(pctV)}%"
        view.findViewById<TextView>(R.id.tv_pct_blancos_tipo).text  = "${"%.3f".format(pctB)}%"
        view.findViewById<TextView>(R.id.tv_pct_nulos_tipo).text    = "${"%.3f".format(pctN)}%"
        view.findViewById<TextView>(R.id.tv_num_validos_tipo).text  = stats.votosValidos
        view.findViewById<TextView>(R.id.tv_num_blancos_tipo).text  = stats.votosBlancos
        view.findViewById<TextView>(R.id.tv_num_nulos_tipo).text    = stats.votosNulos
        view.findViewById<TextView>(R.id.tv_total_emitidos_tipo).text = stats.totalEmitidos

        view.findViewById<ProgressBar>(R.id.prog_validos_tipo).progress  = pctV.toInt()
        view.findViewById<ProgressBar>(R.id.prog_blancos_tipo).progress  = pctB.toInt()
        view.findViewById<ProgressBar>(R.id.prog_nulos_tipo).progress    = pctN.toInt()
    }

    private fun actualizarToggle(view: View, extranjero: Boolean) {
        val btnPeru = view.findViewById<Button>(R.id.btn_peru_tipo)
        val btnExt  = view.findViewById<Button>(R.id.btn_ext_tipo)
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

    private fun parseNum(s: String) = s.replace(",", "").toLongOrNull() ?: 0L
}
