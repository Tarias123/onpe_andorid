package com.onpe.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onpe.app.R
import com.onpe.app.data.Candidato
import java.text.NumberFormat
import java.util.Locale

class CandidatoDesgloseAdapter(private val lista: List<Candidato>) :
    RecyclerView.Adapter<CandidatoDesgloseAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val foto: ImageView     = view.findViewById(R.id.img_foto)
        val partido: TextView   = view.findViewById(R.id.tv_partido)
        val nombre: TextView    = view.findViewById(R.id.tv_nombre)
        val porcentaje: TextView = view.findViewById(R.id.tv_porcentaje)
        val votos: TextView     = view.findViewById(R.id.tv_votos)
        val divider: View       = view.findViewById(R.id.divider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_candidato_desglose, parent, false))

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = lista[position]
        holder.foto.setImageResource(c.fotoRes)
        holder.partido.text = c.partido.uppercase()
        holder.nombre.text = c.nombre
        holder.porcentaje.text = "${"%.3f".format(c.porcentaje)}%"
        holder.votos.text = "${fmt(c.votos)} votos"
        holder.divider.visibility = if (position == lista.size - 1) View.GONE else View.VISIBLE
    }

    private fun fmt(n: Int): String =
        NumberFormat.getNumberInstance(Locale.US).format(n)
}
