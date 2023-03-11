package com.example.test11.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.test11.activity.MainActivity
import com.example.test11.activity.RealTimeActivity
import com.example.test11.databinding.FragmentAddRealTimeBinding
import com.example.test11.model.Person
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class AddRealTimeFragment : Fragment() {
    private var _binding: FragmentAddRealTimeBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var d: Activity
    var count =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRealTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.database
        myRef=db.getReference()
        d = (activity as RealTimeActivity)
        binding.btnAdd.setOnClickListener {
            if (binding.txtName.text.isNotEmpty() && binding.txtAddress.text.isNotEmpty() && binding.txtNumber.text.isNotEmpty()) {
                val name = binding.txtName.text.toString()
                val address = binding.txtAddress.text.toString()
                val number = binding.txtNumber.text.toString()
                val contact = Person("", name, number, address)
                myRef.child("contacts").child(count.toString())
                    .setValue(contact)
                    .addOnSuccessListener {
                        count++
                        binding.txtName.text.clear()
                        binding.txtAddress.text.clear()
                        binding.txtNumber.text.clear()
                        Toast.makeText(d, "Success Add", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(d, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(d, "Complete info!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnGet.setOnClickListener {
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                    binding.tvData.setText(value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(d,error.toString(),Toast.LENGTH_SHORT).show()
                }
            })
        }
        }
    }
