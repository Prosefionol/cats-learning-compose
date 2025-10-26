package com.example.catslearningcompose.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.catslearningcompose.R
import com.example.catslearningcompose.model.DuplicateException
import com.example.catslearningcompose.model.LoadDataException
import com.example.catslearningcompose.model.LoadResult

@Composable
fun <T> LoadResultContent(
    loadResult: LoadResult<T>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onTryAgainAction: () -> Unit = {},
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.Default
) {
    when (loadResult) {
        LoadResult.Loading -> Box(modifier = modifier) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        is LoadResult.Success -> content(loadResult.data)
        is LoadResult.Error -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = exceptionToMessageMapper.getUserMessage(
                        exception = loadResult.exception,
                        context = LocalContext.current
                    ),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onTryAgainAction
                ) {
                    Text(stringResource(R.string.button_exception_title))
                }
            }
        }
    }
}

fun interface ExceptionToMessageMapper {
    fun getUserMessage(exception: Exception, context: Context): String
    companion object {
        val Default = ExceptionToMessageMapper { exception, context ->
            when (exception) {
                is LoadDataException -> context.getString(R.string.failed_to_load_data_error_text)
                is DuplicateException -> context.getString(
                    R.string.already_exists_error_text,
                    exception.duplicatedValue
                )
                else -> context.getString(R.string.unknown_error_text)
            }
        }
    }
}
