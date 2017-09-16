package br.com.bg7.appvistoria.workorder;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.login.LoginActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShouldChangeColorIconWhenOpenOtherStatusWorkorder extends WorkorderBaseMatcher {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void shouldChangeColorIconWhenOpenOtherStatusWorkorder() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_login), withText("ENTRAR"), isDisplayed()));
        appCompatButton.perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView = onView(
            allOf(withId(R.id.menu_visita), isDisplayed()));
        bottomNavigationItemView.perform(click());


        DataInteraction dataInteraction = onData(withItemValue("PROJETO 1"))
                .inAdapterView(withId(R.id.product_list));
        dataInteraction.onChildView(withId(R.id.more_info)).perform(click());
        dataInteraction.onChildView(withId(R.id.image_more_info)).check(matches(withDrawable(R.drawable.ic_hide_info_in_progress)));

        DataInteraction dataInteraction2 = onData(withItemValue("PROJETO 4"))
                .inAdapterView(withId(R.id.product_list));
        dataInteraction2.onChildView(withId(R.id.more_info)).perform(click());
        dataInteraction2.onChildView(withId(R.id.image_more_info)).check(matches(withDrawable(R.drawable.ic_hide_info_completed)));

        DataInteraction dataInteraction3 = onData(withItemValue("PROJETO 7"))
                .inAdapterView(withId(R.id.product_list));
        dataInteraction3.onChildView(withId(R.id.more_info)).perform(click());
        dataInteraction3.onChildView(withId(R.id.image_more_info)).check(matches(withDrawable(R.drawable.ic_hide_info_not_started)));


    }

    }
