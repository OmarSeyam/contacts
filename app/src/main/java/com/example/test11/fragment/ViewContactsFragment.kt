package com.example.test11.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test11.activity.MainActivity
import com.example.test11.activity.RealTimeActivity
import com.example.test11.adapter.ContactAdapter
import com.example.test11.databinding.ActivityRealTimeBinding
import com.example.test11.databinding.FragmentViewContactsBinding
import com.example.test11.model.Person
import com.google.firebase.firestore.FirebaseFirestore

class ViewContactsFragment : Fragment() {
    private var progressDialog: ProgressDialog? = null
    private var _binding: FragmentViewContactsBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var d: Activity
    lateinit var data:ArrayList<Person>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        d = (activity as MainActivity)
        data= ArrayList<Person>()
        getAllContact()
        binding.add.setOnClickListener {
            (d as MainActivity).makeCurrentFragment(AddContactsFragment())
        }
        binding.button.setOnClickListener {
            val i=Intent(d,RealTimeActivity::class.java)
            startActivity(i)
        }
    }
    fun getAllContact() {
        showDialog()
        db.collection("contacts")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (doc in it) {
                        val person = Person(
                            doc.id,
                            doc.getString("name")!!,
                            doc.getString("number")!!,
                            doc.getString("address")!!
                        )
                        data.add(person)
                    }
                    var contactAdapter = ContactAdapter(d, data)
                    binding.rv.layoutManager = LinearLayoutManager(d)
                    binding.rv.adapter = contactAdapter
                }
                hideDialog()
            }
            .addOnFailureListener {
                Toast.makeText(d,it.message, Toast.LENGTH_SHORT).show()
                hideDialog()
            }
    }
    private fun showDialog() {
        progressDialog = ProgressDialog(d)
        progressDialog!!.setMessage("Wait ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun hideDialog() {
        if (progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }
}