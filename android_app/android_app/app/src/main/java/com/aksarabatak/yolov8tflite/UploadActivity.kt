package com.aksarabatak.yolov8tflite

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.aksarabatak.yolov8tflite.databinding.UploadBinding

class UploadActivity : AppCompatActivity(), Detector.DetectorListener {
    private lateinit var binding: UploadBinding
    private lateinit var detector: Detector
    private var currentUploadedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detector = Detector(baseContext, Constants.MODEL_PATH, Constants.LABELS_PATH, this)
        detector.setup()

        binding.btnUpload.setOnClickListener {
            openGallery()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    displayImage(bitmap)
                }
            }
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(intent)
    }

    private fun displayImage(bitmap: Bitmap) {
        // Menskalakan bitmap ke ukuran yang lebih kecil
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true)  // Ganti 200 dengan ukuran yang diinginkan

        binding.uploadedImage.setImageBitmap(scaledBitmap)
        binding.overlay.visibility = View.VISIBLE
        detector.detect(scaledBitmap)
        currentUploadedBitmap = scaledBitmap
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.clear()
    }

    override fun onEmptyDetect() {
        binding.overlay.invalidate()
    }

    override fun onDetect(boundingBoxes: List<BoundingBox>, inferenceTime: Long) {
        runOnUiThread {
            binding.inferenceTime.text = "${inferenceTime}ms"
            binding.overlay.setResults(boundingBoxes)
            binding.overlay.invalidate()

            // Gabungkan semua nama kelas menjadi satu string
            val classNames = boundingBoxes.joinToString(separator = "") { it.clsName }

            // Set teks clsName dengan nama-nama kelas yang digabungkan
            binding.clsName.text = classNames
        }
    }



}
