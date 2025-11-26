package com.pruebas.airolmagic.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.views.layout.NavBar
import com.pruebas.airolmagic.views.layout.SideBar

@Composable
fun MainScaffold(navController: NavHostController,sessionViewModel: SessionViewModel = SessionViewModel(), content: @Composable () -> Unit){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showTopBar = currentRoute == GamesListScreen::class.qualifiedName
    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(res = LocalContext.current.resources,id = R.drawable.dark_matter)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
        drawerContent = { SideBar(navController,sessionViewModel) },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { if(showTopBar) NavBar(scope = scope, drawerState = drawerState) },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.bg_black_purple))
                ) {
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(15.dp)
                    ){ content() }
                }
            }
        )
    }
}