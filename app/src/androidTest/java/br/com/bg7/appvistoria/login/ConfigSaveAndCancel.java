package br.com.bg7.appvistoria.login;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.bg7.appvistoria.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ConfigSaveAndCancel {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void configSaveAndCancel() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_login), withText("LOGIN"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkBox_wifi),
                        withParent(withId(R.id.linear_wifi)),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_confirm), withText("CONFIRMAR"),
                        withParent(withId(R.id.linear_buttons)),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.linear_language_top), isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_language),
                        withParent(withId(R.id.linear_language)),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Inglês"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.checkBox_wifi),
                        withParent(withId(R.id.linear_wifi)),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_cancel), withText("CANCELAR"),
                        withParent(withId(R.id.linear_buttons)),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.linear_language_top), isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_language),
                        withParent(withId(R.id.linear_language)),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinner_language),
                        withParent(withId(R.id.linear_language)),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Inglês"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.checkBox_wifi),
                        withParent(withId(R.id.linear_wifi)),
                        isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_confirm), withText("CONFIRMAR"),
                        withParent(withId(R.id.linear_buttons)),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.checkBox_wifi),
                        withParent(withId(R.id.linear_wifi)),
                        isDisplayed()));
        appCompatCheckBox4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button_cancel), withText("CANCEL"),
                        withParent(withId(R.id.linear_buttons)),
                        isDisplayed()));
        appCompatButton5.perform(click());

    }

}
