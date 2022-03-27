package pab.lop.illustrashopandroid.ui.view.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel
import pab.lop.illustrashopandroid.ui.view_model.MainViewModel

@Composable
fun Main(
    navController: NavController,
    mainViewModel: MainViewModel,
    context: Context
) {

    MainStart(
        navController = navController,
        snackbarHostState = remember { SnackbarHostState() },
        scope = rememberCoroutineScope(),
        scaffoldState = rememberScaffoldState(),
        applicationContext = context,
        mainViewModel = mainViewModel
    )
}


@Composable
fun MainStart(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    applicationContext: Context,
    mainViewModel: MainViewModel,
) {
    val listaImages : MutableList<String> = mutableListOf("MonaLisa.jpg", "American_Gothic.jpg")
    for( i in 0..200) listaImages.add("Ejemplo.jpg")
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { snackbarHostState.currentSnackbarData?.dismiss() },
                        backgroundColor = MaterialTheme.colors.primary
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "Snack Bar Default",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(30.dp)
                                        .fillMaxWidth(0.8f)
                                )
                                IconButton(
                                    //modifier = Modifier.size(20.dp).padding(20.dp),
                                    onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                                ) {
                                    Icon(Icons.Filled.HighlightOff, contentDescription = "Ver comentario", tint = Color.White)
                                }
                            }
                        }
                    }
                }
            )
        },
        drawerBackgroundColor = MaterialTheme.colors.primary,
        drawerShape = customShape(),
        drawerContent =  { /* TODO DRAWER CONTENT */ },
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "DEV TITLE DEFAULT",
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
                        color = MaterialTheme.colors.onError,
                    ) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            Logger.d("Click en options icon")
                            scope.launch { scaffoldState.drawerState.open() }
                        }) {
                        Icon(Icons.Filled.Menu, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* TODO ICON BUTTON ACTION */ }
                    ) {
                        Icon(Icons.Filled.HighlightOff, contentDescription = "Cerrar", tint = Color.White)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(listaImages){ index, item ->
                Card(
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(5.dp)
                ) {
                    //TODO FIRST PART CONTENT
                    //sera una label y un bloque de imagenes que corran hacia la derecha.

                    Text(item)

                }

            }




        }
    }
}

@Composable
fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(
            left = 0f,
            top = 0f,
            right = size.width * 2 / 3,
            bottom = size.height
        ))
    }
}

@Composable
fun NetworkImage(url: String?, modifier: Modifier) {

/*    var image by remember { mutableStateOf<ImageAsset?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    onCommit(url) {
        val picasso = Picasso.get()

        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                drawable = placeHolderDrawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageAsset()
            }
        }

        picasso
            .load(imagePath)
            .placeHolder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(target)

        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }

    if (image != null) {
        Image(asset = image, modifier = modifier)
    } else if (theDrawable != null) {
        Canvas(modifier = modifier) {
            drawIntoCanvas { canvas ->
                drawable.draw(canvas.nativeCanvas)
            }
        }
    }*/
}
