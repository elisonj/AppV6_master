package br.com.bg7.appvistoria.workorder;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.bg7.appvistoria.data.WorkOrder;

/**
 * Created by: elison
 * Date: 2017-08-29
 */
public class WorkorderBaseMatcher {

    public Matcher<Object> withItemValue(final String value) {
        return new BoundedMatcher<Object, WorkOrder>(WorkOrder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(value);
            }

            @Override
            public boolean matchesSafely(WorkOrder item) {
                return item.getName().toUpperCase().equals(String.valueOf(value));
            }
        };
    }


    Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }
}
