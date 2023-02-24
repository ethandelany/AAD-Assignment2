package me.ethandelany.assignment2compose

import android.app.AlertDialog
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import me.ethandelany.assignment2compose.ui.theme.Assignment2ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column {
                            ConstraintLayout {
                                val (button, text) = createRefs()
                                val openDialog = remember { mutableStateOf(false) }

                                if (openDialog.value) {
                                    AlertDialog(
                                        onDismissRequest = {
                                            openDialog.value = false
                                        },
                                        title = {
                                            Text("ConstraintLayouts!")
                                        },
                                        text = {
                                            Column {
                                                Text("This alert was rendered within a ConstraintLayout, for some reason.")
                                                Text("I find JetPack Compose' layout system much nicer to use, very similar to web technologies.")
                                                Text("I also find the default behavior of just overlaying elements very annoying.")
                                                Text("It would make much more sense to stack them like in HTML.")
                                            }
                                        },
                                        confirmButton = {
                                            Button(onClick = { openDialog.value = false }) {
                                                Text("Confirm button")
                                            }
                                        },
                                        dismissButton = {
                                            Button(onClick = { openDialog.value = false }) {
                                                Text("Dismiss button")
                                            }
                                        }
                                    )
                                }

                                Button(
                                    onClick = {
                                              openDialog.value = true
                                    },
                                    modifier = Modifier.constrainAs(button) {
                                        top.linkTo(parent.top, margin = 16.dp)
                                    }
                                ) {
                                    Text("Test Alert")
                                }

                                Text(
                                    "Welcome to the Dice Roller!",
                                    Modifier.constrainAs(text) {
                                        top.linkTo(button.bottom, margin = 16.dp)
                                    }
                                )
                            }

                            DiceRoller()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DieImage(number: Int) {
    val imageResource = when(number) {
        1 -> R.drawable.inverted_dice_1
        2 -> R.drawable.inverted_dice_2
        3 -> R.drawable.inverted_dice_3
        4 -> R.drawable.inverted_dice_4
        5 -> R.drawable.inverted_dice_5
        6 -> R.drawable.inverted_dice_6
        else -> R.drawable.inverted_dice_6
    }

    Image(
        painter = painterResource(imageResource),
        contentDescription = "Logo representing the die state",
        Modifier.width(16.dp)
    )
}


@Composable
fun DiceRoller() {
    val rolls = remember {
        mutableStateListOf<Int>()
    }

    Button(onClick = {
        rolls.add((1..6).random())
    }) {
        Text("Roll the dice!")
    }

    Spacer(modifier = Modifier.padding(10.dp))

    if (rolls.isEmpty()) {
        Text("No rolls yet.")
    } else {
        LazyColumn() {
            items(
                items = rolls,
                itemContent = {
                    Row {
                        DieImage(number = it)
                        Spacer(Modifier.padding(4.dp))
                        Text(text = it.toString())
                    }
                }
            )
        }
    }

}
