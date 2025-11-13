package com.google.exhibitioqrvalidator.presentation.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.exhibitioqrvalidator.R
import com.google.exhibitioqrvalidator.domain.models.Page
import com.google.exhibitioqrvalidator.domain.models.pages
import com.google.exhibitioqrvalidator.presentation.common.QrCodeValidatorTextBtn
import kotlinx.coroutines.launch


@Composable
fun OnBoardingScreen(event: (OnBoardingEvent) -> Unit )  {
    Column(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val buttonState = remember {
            derivedStateOf {
                when(pagerState.currentPage){
                    0-> listOf("", "Next")
                    1-> listOf("back", "Next")
                    2-> listOf("back", "Get Started")
                    else -> listOf("", "")

                }
            }
        }
        HorizontalPager(state = pagerState) {index ->
            OnBoardingPage(page = pages[index])

        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PagerIndicator(pageSize = pages.size, SelectedPage =pagerState.currentPage, modifier = Modifier.width(52.dp) )
            Spacer(modifier = Modifier.width(10.dp))
            Row(modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()
                if(buttonState.value[0].isNotEmpty()){
                    QrCodeValidatorTextBtn(text = buttonState.value[0], onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    })
                }
                QrCodeValidatorTextBtn(text = buttonState.value[1], onClick = {
                    scope.launch {
                        if(pagerState.currentPage == 2){
                            event(OnBoardingEvent.SaveAppEntry)
                        }
                        else{
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                })
            }
        }
    }
}

@Composable
fun OnBoardingPage(modifier: Modifier = Modifier, page: Page) {
    Column(modifier = Modifier.fillMaxWidth()
    ) {
        Image(painter = painterResource(id = page.image), contentDescription = null,
            contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.6f))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = page.title, modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small))
        Text(text = page.description, modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium))
    }

}

@Composable
fun PagerIndicator(modifier: Modifier = Modifier,
                   pageSize:Int,
                   SelectedPage:Int,
                   selectedColor:Color= MaterialTheme.colorScheme.primary,
                   unSelectedColor: Color = MaterialTheme.colorScheme.inversePrimary
) {
    Row(modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize){page ->
            Box(modifier = Modifier
                .size(26.dp).padding(8.dp)
                .clip(CircleShape)
                .background(color = if(page == SelectedPage) selectedColor else unSelectedColor  ))

        }

    }

}

@Composable
fun OnBoardingPage( page: Page) {
    Column(modifier = Modifier.fillMaxWidth()
    ) {
        Image(painter = painterResource(id = page.image), contentDescription = null,
            contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.6f))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = page.title, modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small))
        Text(text = page.description, modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium))
    }


}