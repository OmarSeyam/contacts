package com.example.test11.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.test11.MainActivity
import com.example.test11.R
import com.example.test11.databinding.FragmentAddContactsBinding
import com.example.test11.model.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddContactsFragment : Fragment() {
    private var progressDialog: ProgressDialog? = null
    private var _binding: FragmentAddContactsBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var d: Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        d = (activity as MainActivity)
        binding.btnAdd.setOnClickListener {
            if (binding.txtName.text.isNotEmpty() && binding.txtAddress.text.isNotEmpty() && binding.txtNumber.text.isNotEmpty()) {
                showDialog()
                val name = binding.txtName.text.toString()
                val address = binding.txtAddress.text.toString()
                val number = binding.txtNumber.text.toString()
                val student = Person("", name, number, address)
                db.collection("contacts")
                    .add(student)
                    .addOnSuccessListener {
                        hideDialog()
                        binding.txtName.text.clear()
                        binding.txtAddress.text.clear()
                        binding.txtNumber.text.clear()
                        Toast.makeText(d, "Success Add", Toast.LENGTH_SHORT).show()
                        (d as MainActivity).makeCurrentFragment(ViewContactsFragment())
                    }
                    .addOnFailureListener {
                        hideDialog()
                        Toast.makeText(d, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(d, "Complete info!", Toast.LENGTH_SHORT).show()
            }
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