package com.anurag.newsly.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.anurag.newsly.R

@Composable
fun CoilPlaygroundScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // URL input
    var imageUrl by remember { mutableStateOf("https://picsum.photos/600/400") }
    var currentUrl by remember { mutableStateOf(imageUrl) }

    // Fake feed images for LazyColumn
    val demoImages = remember { List(20) { "https://picsum.photos/600/400?random=$it" } }

    // Preload first 5 images for feed performance
    LaunchedEffect(Unit) {
        val imageLoader = context.imageLoader
        demoImages.take(5).forEach { url ->
            imageLoader.enqueue(
                ImageRequest.Builder(context)
                    .data(url)
                    .build()
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // ===== Header =====
        Text(
            "Coil Playground",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        // ===== URL input =====
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = { currentUrl = imageUrl }) {
            Text("Load Image")
        }
        Spacer(Modifier.height(32.dp))

        // ===== SECTION 1: AsyncImage =====
        Text("AsyncImage (High-level API)", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(currentUrl)
                .crossfade(true)
                .transformations(RoundedCornersTransformation(24f))
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.error),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(Modifier.height(32.dp))

        // ===== SECTION 2: SubcomposeAsyncImage =====
        Text("SubcomposeAsyncImage (Custom loading & error)", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(currentUrl)
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            loading = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            error = {
                Image(
                    painterResource(R.drawable.error),
                    contentDescription = null
                )
            }
        )

        Spacer(Modifier.height(32.dp))

        // ===== SECTION 3: LazyColumn Feed =====
        Text("LazyColumn + Coil (Feed best practices)", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            items(items = demoImages, key = { it }) { url ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(url)
                        .crossfade(true)
                        .transformations(RoundedCornersTransformation(16f))
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(bottom = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        // ===== Back Button =====
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
