package pab.lop.illustrashopandroid.ui.view.admin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.spacing_compact
import pab.lop.illustrashopandroid.ui.view_model.AdminViewModel
import pablo_lonav.android.utils.ScreenNav

@Composable
fun Image_Upload(navController: NavController, adminViewModel: AdminViewModel, context: Context) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val byteArray = remember { mutableStateOf<ByteArray?>(null) }
    val uriLoad = remember { mutableStateOf(false) }
    val bitmapLoad = remember { mutableStateOf(false) }
    val buttonOK = remember { mutableStateOf(false) }
    val customName = remember { mutableStateOf("") }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(MaterialTheme.spacing_compact.small))

        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing_compact.small))

        imageUri?.let {
            if (!uriLoad.value) {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                uriLoad.value = true
            }

            bitmap.value?.let { btm ->

                Logger.i(btm.asImageBitmap().toString())
                Logger.i(imageUri.toString())
                bitmapLoad.value = true
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing_compact.small))

        Button(onClick = {
            adminViewModel.multipartImageUpload(
                byteArray = byteArray,
                context = context,
                bitmap = bitmap,
                customName = customName
            //todo crear objeto para la bbdd con info de la imagen
            ) {
                Logger.i("*** *** *** *** SUCCESSS!!!!!!!")
                //todo indicar que la imagen se ha cargado
                buttonOK.value = true;
            }
        }
        ) {
            Text("Upload")
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing_compact.small))

        Button(
            enabled = buttonOK.value,
            onClick = {

                navController.navigate(ScreenNav.LoginScreen.route)
            }
        ) {
            Text("Return")
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing_compact.small))


        OutlinedTextField(
            value = customName.value,
            onValueChange = {
                it.also {
                    customName.value = it
                    //TODO validar nombre no repetido ni caracteres raros
                }
            },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.name),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.email),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Email
                Icon(
                    imageVector = image,
                    stringResource(R.string.email)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}





