package toby_spring.learningtest.junit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitTest {
    @Autowired
    ApplicationContext context;

    static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
    static ApplicationContext contextObject = null;

    @Test
    public void test1() {
//        assertThat(testObjects, not(hasItem(this)));
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

//        assertThat(contextObject == null || contextObject == this.context, is(true));
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    public void test2() {
//        assertThat(testObjects, not(hasItem(this)));
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

//        assertTrue(contextObject == null || contextObject == this.context);
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    public void test3() {
//        assertThat(testObjects, not(hasItem(this)));
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

//        assertThat(contextObject, either(is(nullValue())).or(is(this.contextObject)));
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }
}
