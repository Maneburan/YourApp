package com.example.yourapp.ui.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun ImagePicker(
    remoteUri: Uri?,
    onImgLoaded: (btm: Bitmap?) -> Unit
) {
    val context = LocalContext.current
    var localUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            localUri = uri
        }
    )

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    launcher.launch("image/*")
                },
                content = {
                    Text("Изображение")
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    onImgLoaded(null)
                    localUri = null
                },
                content = {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                },
                enabled = localUri != null
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        var model: Any? = remoteUri

        if (localUri != null) {
            model = localUri

            val request = ImageRequest.Builder(context).data(localUri).target { drawable ->
                onImgLoaded((drawable as BitmapDrawable).bitmap)
            }.build()

            val disposable = context.imageLoader.enqueue(request)
        }

        if(model != null) {
            AsyncImage(
                model = model,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }

    }
}