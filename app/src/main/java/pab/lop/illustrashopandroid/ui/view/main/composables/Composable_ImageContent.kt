package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.android.material.math.MathUtils.lerp
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES
import pab.lop.illustrashopandroid.utils.productSelected

import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScope.ImageContent(
    page: Int,
    familyProducts: HashMap<String, List<product_stock_response>>,
    family: String,
    index: Int,
    popUpDetailsOpen: MutableState<Boolean>
) {
    Card(
        Modifier
            //  .padding(5.dp)
            .clickable(onClick = {
                productSelected = familyProducts
                    .get(family)
                    ?.get(page)
                popUpDetailsOpen.value = true
            })
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    0.85f,
                    1f,
                    (1f - pageOffset.coerceIn(0f, 1f))
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    0.5f,
                    1f,
                    1f - pageOffset.coerceIn(0f, 1f)
                )

            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$URL_HEAD_IMAGES${familyProducts.get(family)?.get(page)?.image}")
                .crossfade(true)
                .crossfade(1000)
                .build(),
            contentDescription = null,
            //placeholder = painterResource(id = R.drawable.loading_image),
            //   modifier = Modifier.fillMaxSize(0.8f)
        )


        Logger.d(
            """
                            family =>> $family
                            index ==>> $index
                            page ===>> $page
                            url ====>> ${URL_HEAD_IMAGES}${familyProducts.get(family)?.get(page)?.image}
                            """
                .trimIndent()
        )
    }
}