package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.utils.admob.composables.AdaptiveBanner
import pab.lop.illustrashopandroid.utils.admob.composables.InlineBanner
import pab.lop.illustrashopandroid.utils.excludedFamilies

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Body(
    familyProducts: HashMap<String, List<product_stock_response>>,
    popUpDetailsOpen: MutableState<Boolean>
) {
    LazyColumn{
        itemsIndexed(familyProducts.keys.toMutableList()) { index, family ->
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .border(0.5.dp, MaterialTheme.colors.primaryVariant)
            ) {
                if (!excludedFamilies.contains(family)) {
                    Text(
                        text = family,
                        fontFamily = FontFamily.Cursive,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 5.dp)
                    )
                    HorizontalPager(
                        count = familyProducts.get(family)?.size ?: 2,
                        contentPadding = PaddingValues(horizontal = 64.dp, vertical = 10.dp),
                        //flingBehavior = PagerDefaults.flingBehavior(),

                    state = rememberPagerState(),
                        modifier = Modifier
                            .height(400.dp) // Image Size
                    ) { page ->
                        ImageContent(
                            page = page,
                            familyProducts = familyProducts,
                            family = family,
                            index = index,
                            popUpDetailsOpen = popUpDetailsOpen
                        )
                    }
                }
            }
        }
        item{ InlineBanner() }
    }
}