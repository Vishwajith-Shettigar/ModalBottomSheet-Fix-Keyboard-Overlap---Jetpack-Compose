package com.example.modalbottomsheetworkaround

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.modalbottomsheetworkaround.ui.theme.ModalBottomSheetWorkaroundTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ModalBottomSheetWorkaroundTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          EditProfileScreen(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
  modifier: Modifier,
) {

  val bottomSheetState =
    rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val view = LocalView.current
  var isImeVisible by remember { mutableStateOf(false) }

  DisposableEffect(LocalWindowInfo.current) {
    val listener = ViewTreeObserver.OnPreDrawListener {
      isImeVisible = ViewCompat.getRootWindowInsets(view)
        ?.isVisible(WindowInsetsCompat.Type.ime()) == true
      true
    }
    view.viewTreeObserver.addOnPreDrawListener(listener)
    onDispose {
      view.viewTreeObserver.removeOnPreDrawListener(listener)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Transparent),
    verticalArrangement = Arrangement.Bottom
  ) {
    ModalBottomSheet(
      windowInsets = WindowInsets.ime,
      onDismissRequest = {
      },
      sheetState = bottomSheetState,
      modifier = Modifier.then(
        if (isImeVisible)
          Modifier.fillMaxHeight(1.0F)
        else
          Modifier.fillMaxHeight(0.73F)
      )
    ) {
      EditProfileContent()
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
) {

  var userName by remember {
    mutableStateOf("shiroXhima")
  }

  var fullName by remember {
    mutableStateOf("Shinchan nohara")
  }

  var aboutMe by remember {
    mutableStateOf("Its me shinchan")
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .padding(16.dp)
      .imePadding()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
      ) {

        IconButton(
          modifier = Modifier
            .size(40.dp)
            .border(
              width = 1.dp,
              shape = CircleShape,
              color = MaterialTheme.colorScheme.primaryContainer
            ), onClick = {
          }, content = {
            Icon(
              painter = painterResource(id = R.drawable.ic_launcher_foreground),
              contentDescription = "edit profile picture"
            )
          })

        Image(
          painter = painterResource(id = R.drawable.ic_launcher_foreground),
          contentDescription = "Profile Picture",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
        )

        IconButton(
          modifier = Modifier
            .size(40.dp)
            .border(
              width = 1.dp,
              shape = CircleShape,
              color = MaterialTheme.colorScheme.primaryContainer
            ), onClick = {
          }, content = {
            Icon(
              painter = painterResource(id = R.drawable.ic_launcher_foreground),
              contentDescription = "edit profile picture"
            )
          })
      }
      TextField(
        modifier = Modifier
          .wrapContentSize()
          .width(150.dp),
        value = userName,
        readOnly = true,
        onValueChange = {
          userName = it
        },
        label = {
          Text(text = "username")
        },
        colors = TextFieldDefaults.textFieldColors(
          containerColor = Color.Transparent,
          unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,

          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
          errorTextColor = Color.Red,
          errorContainerColor = Color.Transparent,
          errorIndicatorColor = Color.Transparent,
          errorSupportingTextColor = Color.Red,
          focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,

          focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        maxLines = 1,
        textStyle = MaterialTheme.typography.titleMedium.copy(
          textAlign = TextAlign.Center
        ),
        isError = false
      )
    }
    TextField(
      modifier = Modifier
        .wrapContentSize()
        .padding(vertical = 10.dp),
      value = fullName,
      onValueChange = {
        fullName = it
      },
      label = {
        Text(text = "full name")
      },
      colors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
      maxLines = 1,
      textStyle = MaterialTheme.typography.titleMedium.copy(
        textAlign = TextAlign.Start
      ),
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_launcher_foreground),
          contentDescription = "",
          modifier = Modifier.size(30.dp)
        )
      },
    )

    TextField(
      modifier = Modifier
        .wrapContentSize()
        .padding(vertical = 10.dp)
        .imePadding(),
      value = aboutMe,
      onValueChange = {
        aboutMe = it
      },
      label = {
        Text(text = "about me")
      },
      colors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,

        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
      singleLine = false,
      maxLines = 4,
      textStyle = MaterialTheme.typography.titleMedium.copy(
        textAlign = TextAlign.Start
      ),
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_launcher_foreground),
          contentDescription = "",
          modifier = Modifier.size(30.dp)
        )
      },
    )

    Button(
      modifier = Modifier
        .wrapContentSize()
        .padding(vertical = 10.dp)
        .imePadding(),
      onClick = {
      },
    ) {
      Text(
        text = "save",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
