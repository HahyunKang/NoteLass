package com.app.note_lass.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun RotatingIcon(
    icon : Int,
    tint : Color,
    modifier : Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Icon(
        painter =  painterResource(id = icon),
        tint = tint,
        contentDescription = null, // decorative element
        modifier = modifier
            .rotate(angle)
    )

}