package com.example.tinderswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinderswipe.ui.theme.TinderSwipeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TinderSwipeTheme {
                TinderLikeUI()

            }
        }
    }
}


data class UserProfile(
    val name: String,
    val age: Int,
    val location: String,
    val distance: Int,
    val imageRes: Int
)

/*
@Composable
fun SwipeableCard(
    userProfile: UserProfile,
    onSwiped: (Boolean) -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
            .offset { IntOffset(offsetX.value.toInt(), offsetY.value.toInt()) }
            .graphicsLayer(
                rotationZ = rotation.value,
                scaleX = scale.value,
                scaleY = scale.value
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (abs(offsetX.value) < size.width / 4) {
                                offsetX.animateTo(0f, tween(400))
                                offsetY.animateTo(0f, tween(400))
                                rotation.animateTo(0f, tween(400))
                                scale.animateTo(1f, tween(400))
                            } else {
                                val swipedRight = offsetX.value > 0
                                offsetX.animateTo(
                                    if (swipedRight) size.width.toFloat() * 2
                                    else -size.width.toFloat() * 2,
                                    tween(400)
                                )
                                onSwiped(swipedRight)
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        coroutineScope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y)
                            rotation.snapTo(offsetX.value / 60)
                            scale.snapTo(1f - abs(offsetX.value) / size.width / 8)
                        }
                        change.consume()
                    }
                )
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = userProfile.imageRes),
                contentDescription = "Profile picture of ${userProfile.name}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "${userProfile.name}, ${userProfile.age}",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Live in ${userProfile.location}",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = "${userProfile.distance} km away",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}
?*/
@Composable
fun ProfileCard(
    userProfile: UserProfile,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onSuperLike: () -> Unit,
    onRefresh: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = userProfile.imageRes),
                contentDescription = "Profile picture of ${userProfile.name}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            // Gradient overlay to improve text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 300f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // User info
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "${userProfile.name}, ${userProfile.age}",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Live in ${userProfile.location}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${userProfile.distance} km away",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }


                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(Icons.Default.Refresh, Color(0xFFFF69B4), onClick = onRefresh)
                    ActionButton(Icons.Default.Close, Color(0xFFFF69B4), onClick = onDislike)
                    ActionButton(Icons.Default.Star, Color(0xFF87CEFA), onClick = onSuperLike)
                    ActionButton(Icons.Default.Favorite, Color(0xFF90EE90), onClick = onLike)
                    ActionButton(Icons.Default.ThumbUp, Color(0xFF90EE90))
                }
            }
        }
    }
}


@Composable
fun ActionButton(icon: ImageVector, color: Color, onClick: () -> Unit = {}) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp)
            .background(color, CircleShape)
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TinderLikeUI() {
    val profiles = listOf(
        UserProfile("Laura Josephine", 31, "Unknown", 10, R.drawable.allef_vinicius),
        UserProfile("John Doe", 28, "Nairobi", 5, R.drawable.brooke_cagle),
        UserProfile("Jane Smith", 35, "Mombasa", 15, R.drawable.jordan_whitfield),
        UserProfile("Ayo Ogunseinde", 20, "Kisii", 12, R.drawable.ayo_ogunseinde),
        UserProfile("Gabriel Silverio", 19, "Mombasa", 5, R.drawable.gabriel_silverio)
    )
    val pagerState = rememberPagerState(pageCount = { profiles.size })
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Discover",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF69B4)
                    )
                    Text(
                        text = "Nairobi",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Sort")
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                ProfileCard(
                    userProfile = profiles[page],
                    onLike = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < profiles.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    onDislike = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < profiles.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    onSuperLike = {

                    },
                    onRefresh = {
                        coroutineScope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                )
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TinderUIPerv() {
    TinderSwipeTheme {
        TinderLikeUI()
    }
}