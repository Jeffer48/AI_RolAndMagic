package com.pruebas.airolmagic.views.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import kotlinx.coroutines.launch

@Composable
fun ChatScaffold(navController: NavHostController, content: @Composable () -> Unit){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(res = LocalContext.current.resources,id = R.drawable.dark_matter)

    val invertColorMatrix = ColorMatrix(
        floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
    )

    val scaleFactor = 2.5f
    val alphaFactor = 0.3f
    val invertColorFilter = ColorFilter.colorMatrix(invertColorMatrix)
    val tiledBrush = remember(imageBitmap){
        ShaderBrush(ImageShader(image = imageBitmap, tileModeX = TileMode.Repeated, tileModeY = TileMode.Repeated))
    }

    ModalNavigationDrawer(
        drawerContent = {  },
        drawerState = drawerState
    ){
        Scaffold(
            topBar = { NavBarChat({}) },
            content = { innerPadding ->
                Box(
                    modifier = Modifier.fillMaxSize().background(colorResource(R.color.bg_black_purple))
                ){
                    Spacer(
                        modifier = Modifier.fillMaxSize()
                            .drawWithCache{
                                onDrawBehind {
                                    scale(scale = scaleFactor){
                                        drawRect(brush = tiledBrush, colorFilter = invertColorFilter, alpha = alphaFactor)
                                    }
                                }
                            }
                    )
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ){ content() }
                }
            }
        )
    }
}

@Composable
fun NavBarChat(onMenuClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF161B22),
        shadowElevation = 4.dp
    ) {
        Column{
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        painter = painterResource(R.drawable.ico_list),
                        contentDescription = "Abrir menú",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Campaña Nocturna",
                        color = Color(0xFFF1F5F9),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 20.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFF30363D)
            )
        }
    }
}