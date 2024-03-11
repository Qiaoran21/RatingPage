package com.example.ratingbar

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingbar.ui.theme.Pink40

/* This demo is to create a rating page that allows the users to rate their experience/product.
   There are two parts of the page: five starts for the users to rate,
   and a text box for them to write a review (optional). */

/* inspired by https://medium.com/@imitiyaz125/star-rating-bar-in-jetpack-compose-5ae54a2b5b23 */

@Composable
fun RatingScreen() {
    var isButtonClicked by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
                text = "HOW WAS YOUR EXPERIENCE?",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(bottom = 50.dp)
            )

        var rating by remember { mutableIntStateOf(0) }

        RatingBar(
            modifier = Modifier.size(50.dp),
            rating = rating
        ) {
            rating = it
        }

        if (rating != 0) {
            val emoji = when {
                rating == 1 -> "🥺"
                rating == 2 -> "😐"
                rating == 3 -> "😊"
                rating == 4 -> "😃"
                else -> "😍"
            }
            Text(
                text = emoji,
                fontSize = 150.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            CommentField(isButtonClicked) {isButtonClicked = it}

            SubmitButton(isButtonClicked) {isButtonClicked = it}
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    starNumber: Int = 5,
    starColour: Color = Pink40,
    onRatingChange: (Int) -> Unit
) {
    Row {
        for (index in 1 .. starNumber) {
            Icon(
                modifier = modifier.clickable { onRatingChange(index) },
                contentDescription = "",
                tint = starColour,
                imageVector = if (rating >= index) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarOutline
                }
            )
        }
    }
}

@Composable
fun CommentField(isButtonClicked: Boolean, onButtonClicked:(Boolean) -> Unit) {
    var comment by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.padding(30.dp),
        value = comment,
        onValueChange = {comment = it},
        placeholder = { Text(text = "Write a review", fontFamily = FontFamily.Monospace)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Pink40,
            focusedBorderColor = Pink40
        ),
        enabled = !isButtonClicked
    )
    if (isButtonClicked) {
        onButtonClicked(true)
    }
}

@Composable
fun SubmitButton(isButtonClicked: Boolean, onButtonClicked:(Boolean) -> Unit) {
    var isEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (isButtonClicked) {
        isEnabled = false
    }

    Button(
        onClick = {
            Toast.makeText(context, "Thank you for the feedback!", Toast.LENGTH_LONG).show()
            onButtonClicked(true)
        },
        enabled = isEnabled,
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(Pink40)
    ) {
        Text(text = "Submit", color = Color.Black, fontFamily = FontFamily.Monospace)
    }
}