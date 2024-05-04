package org.freedu.threadsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import org.freedu.threadsapp.R
import org.freedu.threadsapp.navigations.Routes

@Composable
fun Splash(navController: NavController){
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val(image) = createRefs()
        Image(painter = painterResource(id = R.drawable.threads), contentDescription = "Threads" ,
            modifier = Modifier.constrainAs(image){
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }.size(120.dp))
    }
    LaunchedEffect(true ) {
        delay(3000)
        if(FirebaseAuth.getInstance().currentUser != null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
        else{
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }

}