package br.com.bg7.appvistoria.workorder;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.login.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShouldChangeColorIconWhenOpenOtherStatusWorkorder {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void shouldChangeColorIconWhenOpenOtherStatusWorkorder() {
        ViewInteraction appCompatButton = onView(
allOf(withId(R.id.button_login), withText("ENTRAR"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
allOf(withId(R.id.menu_visita), isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction linearLayout = onView(
allOf(withId(R.id.more_info), isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction linearLayout2 = onView(
allOf(withId(R.id.more_info), isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction linearLayout3 = onView(
allOf(withId(R.id.more_info), isDisplayed()));
        linearLayout3.perform(click());

        }

    }
