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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShouldCancelMapDialogWhenClickInCancel extends WorkorderBaseMatcher {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void shouldCancelMapDialogWhenClickInCancel() {
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
                .inAdapterView(withId(R.id.listview));

        dataInteraction.onChildView(withId(R.id.more_info)).perform(click());
        dataInteraction.onChildView(withId(R.id.bt_maps)).perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_cancel), withText("CANCELAR"), isDisplayed()));
        appCompatButton2.perform(click());

    }

}
