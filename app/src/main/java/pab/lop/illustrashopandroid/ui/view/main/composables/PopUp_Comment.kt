package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing

@Composable
fun PopUpComment(
    verticalGradient: Brush,
    openPopUpComment: MutableState<Boolean>,
    customSpacing: Spacing,
    comment: MutableState<String>
) {

    val currentComment = remember { mutableStateOf(comment.value) }


    Dialog(
        onDismissRequest = { openPopUpComment.value = false }
    ) {
        Surface(
            modifier = Modifier
                .padding(customSpacing.small)
                .wrapContentHeight(),
            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(brush = verticalGradient)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {



                    /************ TITLE ************/
                    Text(
                        text = stringResource(R.string.add_comment),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                            .clickable(onClick = { })
                    )



                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = {
                            openPopUpComment.value = false

                        },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = stringResource(R.string.Close)
                        )
                    }
                }



                Column(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = currentComment.value,
                        onValueChange = { currentComment.value = it },
                        maxLines = 5,
                        textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(20.dp).height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(customSpacing.small))

                }

                /************ SAVE ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            comment.value = currentComment.value
                            openPopUpComment.value = false
                        })
                ) {

                    Text(
                        text = stringResource(R.string.save),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                    )
                }


            }
        }
    }
}

