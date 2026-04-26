package com.onpe.app.ui.actas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.onpe.app.R
import com.onpe.app.data.*

class ActasFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_actas, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar stats reales desde Firestore
        FirestoreService.getStats("peru") { stats ->
            activity?.runOnUiThread {
                view.findViewById<TextView>(R.id.tv_total_actas_f).text       = stats.totalActas
                view.findViewById<TextView>(R.id.tv_procesadas_actas_f).text  = stats.procesadas
                view.findViewById<TextView>(R.id.tv_contabilizadas_f).text    = stats.contabilizadas
            }
        }

        // Búsqueda por número
        val etNumero  = view.findViewById<EditText>(R.id.et_numero_acta)
        val btnBuscar = view.findViewById<Button>(R.id.btn_buscar_acta)
        val cardResult = view.findViewById<View>(R.id.card_resultado_acta)

        btnBuscar.setOnClickListener {
            val numero = etNumero.text.toString().trim()
            if (numero.length < 6) {
                Toast.makeText(context, "Ingrese los 6 dígitos del número de acta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            cardResult.visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tv_result_numero).text = numero
            view.findViewById<TextView>(R.id.tv_result_mesa).text   = "${numero.take(4)}-A"
        }
    }
}
