package com.example.test11.adapter

import android.app.Activity
import android.app.AlertDialog
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.test11.databinding.LayoutViewBinding
import com.example.test11.model.Person
import com.google.firebase.firestore.FirebaseFirestore

class ContactAdapter(var activity: Activity, var data: ArrayList<Person>) :
    RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {
    class MyViewHolder(var binding: LayoutViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutViewBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvName.setText(data[position].name)
        holder.binding.tvNumber.setText(data[position].number)
        holder.binding.tvAddress.setText(data[position].address)
        var db=FirebaseFirestore.getInstance()
        holder.binding.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Delete")
            dialog.setMessage("do you want delete contact!")
            dialog.setPositiveButton("yes") { _, _ ->
                db.collection("contacts").document(data[position].id)
                    .delete()
                    .addOnSuccessListener {
                        data.removeAt(position)
                        notifyDataSetChanged()
                    }
                    .addOnFailureListener {
                        Toast.makeText(activity, "Failed delete", Toast.LENGTH_SHORT).show()

                    }
            }
            dialog.setNegativeButton("No") { dis, _ ->
                dis.dismiss()
            }
            dialog.create().show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}