package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES

@Composable
fun Main(
    navController: NavController,
    mainViewModel: MainViewModel,
    context: Context,
    customSpacing: Spacing
) {

    val imagesByFamily: HashMap<String, MutableList<String>> = HashMap()
    imagesByFamily["Primera Familia"] = mutableListOf("MonaLisa.jpg", "American_Gothic.jpg")
    imagesByFamily["Segunda Familia"] =
        mutableListOf("Meisje_met_de_parel.jpg", "American_Gothic.jpg", "StarryNight.jpg")
    imagesByFamily["Tercera Familia"] = mutableListOf("The_Kiss.jpg", "Guernica.jpg")

    MainStart(
        navController = navController,
        snackbarHostState = remember { SnackbarHostState() },
        scope = rememberCoroutineScope(),
        scaffoldState = rememberScaffoldState(),
        applicationContext = context,
        mainViewModel = mainViewModel,
        imagesByFamily = imagesByFamily
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainStart(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    applicationContext: Context,
    mainViewModel: MainViewModel,
    imagesByFamily: HashMap<String, MutableList<String>>,
) {

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )

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
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
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
                                    Icon(
                                        Icons.Filled.HighlightOff,
                                        contentDescription = "Ver comentario",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
        drawerBackgroundColor = MaterialTheme.colors.primaryVariant,
        drawerShape = customShape(),
        drawerContent = { /* TODO DRAWER CONTENT */ },
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                modifier = Modifier.background(verticalGradient),
                title = {
                    Text(
                        text = "DEV TITLE DEFAULT",
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
                        color = Color.White
                    )
                },
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
                        Icon(
                            Icons.Filled.HighlightOff,
                            contentDescription = "Cerrar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(imagesByFamily.keys.toMutableList()) { index, item ->

                Column(
                ) {
                    Text(item)
                    HorizontalPager(
                        count = imagesByFamily.get(item)?.size ?: 2,
                        state = rememberPagerState(),
                    ) { page ->
                        Card(
                            backgroundColor = MaterialTheme.colors.secondary,
                            modifier = Modifier
                              //  .fillMaxWidth(0.8f)
                                .padding(5.dp)
                                .clickable(onClick = {
                                    mainViewModel.getAllUsers {
                                        Logger.i("Loading complete on item click")
                                    }
                                    //TODO POPUP DE OPCIONES

                                })
                        ) {
                            AsyncImage(
                                model = "${URL_HEAD_IMAGES}${imagesByFamily.get(item)?.get(page)}",
                                contentDescription = null,
                                placeholder = painterResource(id = R.drawable.loading_image),
                                contentScale = ContentScale.Fit,
                             //   modifier = Modifier.fillMaxSize(0.8f)
                            )

                            /*      CoilImage(
                            imageModel = "${URL_HEAD_IMAGES}${
                                imagesByFamily.get(item)?.get(page)
                            }",
                            contentScale = ContentScale.Inside,
                            previewPlaceholder = R.drawable.red,

                            error = ImageBitmap.imageResource(R.drawable.broken_image),
                            modifier = Modifier.fillMaxHeight(0.8f),

                            )*/

                            /* Image(
                                 painter = rememberAsyncImagePainter("${URL_HEAD_IMAGES}${imagesByFamily.get(item)?.get(page)}"),
                                 contentDescription = null,
                                 modifier = Modifier.size(1128.dp)
                            )*/

                            Logger.d(
                                """
                            item  =>> $item
                            index =>> $index
                            array =>> ${imagesByFamily[item]}
                            page  =>> $page
                            """
                                    .trimIndent()
                            )
                        }
                    }
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
        return Outline.Rectangle(
            Rect(
                left = 0f,
                top = 0f,
                right = size.width * 2 / 3,
                bottom = size.height
            )
        )
    }
}


