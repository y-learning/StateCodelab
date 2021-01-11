package com.why.codelabs.state.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animate
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSizeConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction.Done
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun SelectableIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    isSelected: Boolean,
    onIconSelected: () -> Unit
) {
    val tint = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }
    TextButton(
        onClick = { onIconSelected() },
        shape = CircleShape,
        modifier = modifier
    ) {
        Column {
            Icon(icon, tint = tint)
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .preferredWidth(icon.defaultWidth)
                        .preferredHeight(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.preferredHeight(4.dp))
            }
        }
    }
}

@Composable
fun IconRow(
    modifier: Modifier = Modifier,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit
) = Row(modifier) {
    for (todoIcon in TodoIcon.values())
        SelectableIconButton(
            icon = todoIcon.imageVector,
            isSelected = todoIcon == icon
        ) { onIconChange(todoIcon) }
}

@Preview(showBackground = true)
@Composable
fun PreviewIconRow() = IconRow(icon = TodoIcon.Square) {}

@Composable
fun TodoEditButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) = TextButton(
    modifier = modifier,
    enabled = enabled,
    shape = CircleShape,
    onClick = onClick
) {
    Text(text)
}

@Preview(showBackground = true)
@Composable
fun TodoEditButtonPreview() = TodoEditButton("Edit") { /*TODO*/ }

@ExperimentalAnimationApi
@Composable
fun AnimatedIconRow(
    modifier: Modifier = Modifier,
    icon: TodoIcon,
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    onIconChange: (TodoIcon) -> Unit
) {
    // remember these specs so they don't restart if recomposing during
    // the animation, this is required since TweenSpec restarts on interruption
    val enter = remember {
        fadeIn(
            animSpec = TweenSpec(
                300,
                easing = FastOutLinearInEasing
            )
        )
    }
    val exit = remember {
        fadeOut(
            animSpec = TweenSpec(
                100,
                easing = FastOutSlowInEasing
            )
        )
    }
    Box(modifier.defaultMinSizeConstraints(minHeight = 16.dp)) {
        AnimatedVisibility(
            visible = visible,
            initiallyVisible = initialVisibility,
            enter = enter,
            exit = exit,
        ) {
            IconRow(icon = icon, onIconChange = onIconChange)
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun AnimatedIconRowPreview() = AnimatedIconRow(
    icon = TodoIcon.Done
) { /*TODO*/ }


/** Draw a background that animates resizing and elevation changes.*/
@Composable
fun TodoItemInputBackground(
    modifier: Modifier = Modifier,
    elevate: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    val animatedElevation = animate(if (elevate) 1.dp else 0.dp, TweenSpec(500))
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        shape = RectangleShape,
        elevation = animatedElevation,
    ) {
        Row(
            modifier = modifier.animateContentSize(animSpec = TweenSpec(300)),
            content = content
        )
    }
}

@Composable
fun TodoInputText(
    text: String,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {},
    onTextChange: (String) -> Unit
) = TextField(
    value = text,
    modifier = modifier,
    maxLines = 1,
    backgroundColor = Color.Transparent,
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = Done),
    onValueChange = onTextChange,
    onImeActionPerformed = { action, softKeyboardController ->
        if (action == Done) {
            onImeAction()
            softKeyboardController?.hideSoftwareKeyboard()
        }
    }
)

@Preview(showBackground = true)
@Composable
fun TodoInputTextPreview() = TodoInputText("Buy tea.") { }
