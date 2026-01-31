@file:OptIn(ExperimentalMaterial3Api::class)

package com.anurag.newsly.presentation.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import java.io.File
import androidx.compose.foundation.lazy.items

@Composable
fun ToolsScreen(
    onBack: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var multiImages by remember { mutableStateOf<List<String>>(emptyList()) }
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var permissionResult by remember { mutableStateOf("") }
    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            selectedImageUri = uri?.toString()
        }

    val multiImageLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetMultipleContents()
        ) { uris ->
            multiImages = uris.map { it.toString() }
        }

    val cameraPreviewLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            previewBitmap = bitmap
        }

    val imageUri = remember {
        val file = File(context.cacheDir, "temp_photo.jpg")

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    val takePictureLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                Toast.makeText(context, "Photo captured!", Toast.LENGTH_SHORT).show()
            }
        }

    Button(onClick = {
        takePictureLauncher.launch(imageUri)
    }) {
        Text("Take Photo")
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            permissionResult = if (granted) "Granted" else "Denied"
            if (granted) {
                takePictureLauncher.launch(imageUri)
            }
        }

    Button(onClick = {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }){
        Text("Request Camera Permission")
    }

    val multiPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->

            val allGranted = result.values.all { it }

            permissionResult =
                if (allGranted) "All granted"
                else "Some denied"
        }


    val customLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            permissionResult = "Result code = ${result.resultCode}"
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contracts Demo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {

                Button(onClick = {
                    imagePickerLauncher.launch("image/*")
                }) {
                    Text("Pick Image")
                }

                selectedImageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp)
                    )
                }
            }

            item {
                Button(onClick = {
                    multiImageLauncher.launch("image/*")
                }) {
                    Text("Pick Multiple Images")
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(multiImages) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }

            }

            item {
                Button(onClick = {
                    cameraPreviewLauncher.launch(null)
                }) {
                    Text("Camera Preview (Bitmap)")
                }

                previewBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            item {
                Button(onClick = {

                    val granted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED

                    if (granted) {
                        takePictureLauncher.launch(imageUri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }

                }) {
                    Text("Take Full Photo")
                }
            }

            item {
                Button(onClick = {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Request Camera Permission")
                }
            }

            item {
                Button(onClick = {
                    multiPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                    )
                }) {
                    Text("Request Multiple Permissions")
                }
            }

            item {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    customLauncher.launch(intent)
                }) {
                    Text("Start Custom Activity")
                }

                Text(permissionResult)
            }
        }
    }
}
