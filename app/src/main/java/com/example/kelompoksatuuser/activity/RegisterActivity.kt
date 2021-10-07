package com.example.kelompoksatuuser.activity

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kelompoksatuuser.databinding.ActivityRegisterBinding
import com.example.kelompoksatuuser.model.Member
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    var selectedImageProfil: Uri? = null
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = "Daftar Member"
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.setTitle(title).toString()

        binding.progressCircular.visibility = View.INVISIBLE

        auth.signOut()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("member")

        binding.imgProfil.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityImage.launch(intent)
        }

        binding.btnRegister.setOnClickListener {
            simpanDataRegister()
            binding.progressCircular.visibility = View.VISIBLE
        }
    }

    var activityImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageProfil = data?.data
            val image = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageProfil)
            val bitmap = BitmapDrawable(image)
            binding.imgLogoProfil.visibility = View.GONE
            binding.imgProfil.setBackgroundDrawable(bitmap)
        }
    }

    private fun registerMember(imageProfil: String) {
        if (
            binding.edtNamaDepan.text.toString().isEmpty() &&
            binding.edtNamaBelakang.text.toString().isEmpty() &&
            binding.edtNamaNomor.text.toString().isEmpty() &&
            binding.edtEmail.text.toString().isEmpty() &&
            binding.edtPassword.text.toString().isEmpty() &&
            binding.edtAlamat.text.toString().isEmpty() &&
            imageProfil.isEmpty()) {

            binding.edtNamaDepan.error = "Form Tidak Boleh Kosong"
            binding.edtNamaBelakang.error = "Form Tidak Boleh Kosong"
            binding.edtNamaNomor.error = "Form Tidak Boleh Kosong"
            binding.edtEmail.error = "Form Tidak Boleh Kosong"
            binding.edtPassword.error = "Form Tidak Boleh Kosong"
            binding.edtAlamat.error = "Form Tidak Boleh Kosong"
        }

        binding.btnRegister.isEnabled
        auth.createUserWithEmailAndPassword(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference?.child(currentUser?.uid!!)
//                    val ref = FirebaseDatabase.getInstance().getReference("member")
                    val memberId = databaseReference!!.push().key
                    val member = Member(
                        memberId,
                        binding.edtNamaDepan.text.toString(),
                        binding.edtNamaBelakang.text.toString(),
                        binding.edtNamaNomor.text.toString(),
                        binding.edtEmail.text.toString(),
                        binding.edtPassword.text.toString(),
                        binding.edtAlamat.text.toString(),
                        imageProfil
                    )

                    if (memberId != null) {
                        currentUserDb?.setValue(member)
                            ?.addOnSuccessListener {
                                Toast.makeText(applicationContext, "Berhasil Mendaftar Sebagai Member", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            ?.addOnFailureListener {
                                Toast.makeText(applicationContext, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(applicationContext, "Registrasi Gagal, Coba lagi dan pastikan data terisi dengan benar", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun simpanDataRegister() {
        if (selectedImageProfil == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/member/profil/$fileName")
        ref.putFile(selectedImageProfil!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Register", "Berhasil Upload Gambar. Lokasi: $it")
                    registerMember(it.toString())
                }
            }
            .removeOnFailureListener {
                Log.d("Register", "Gagal Upload Gambar")
            }
    }
}