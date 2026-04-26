package com.onpe.app.adapter

import android.graphics.Color
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

class CandidatoPresAdapter(private val lista: List<Candidato>) :
    RecyclerView.Adapter<CandidatoPresAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val foto: ImageView      = view.findViewById(R.id.img_foto_pres)
        val partido: TextView    = view.findViewById(R.id.tv_partido_pres)
        val nombre: TextView     = view.findViewById(R.id.tv_nombre_pres)
        val votos: TextView      = view.findViewById(R.id.tv_votos_pres)
        val porcentaje: TextView = view.findViewById(R.id.tv_pct_pres_item)
        val container: View      = view.findViewById(R.id.card_candidato_pres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_candidato_pres, parent, false))

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = lista[position]
        holder.foto.setImageResource(c.fotoRes)
        holder.partido.text = c.partido.uppercase()
        holder.nombre.text = c.nombre
        holder.votos.text = "${NumberFormat.getNumberInstance(Locale.US).format(c.votos)} votos"
        holder.porcentaje.text = "${"%.3f".format(c.porcentaje)}%"

        val ctx = holder.itemView.context
        if (c.porcentaje > 50) {
            holder.container.setBackgroundColor(Color.WHITE)
        }
    }
}
