package util;

import com.google.common.base.CharMatcher;
import com.ontology2.centipede.util.Version;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestVersion {
    @Test
    public void iHaveAVersionNumber() {
        String number= Version.get("com.ontology2.centipede");
        assertTrue(CharMatcher.DIGIT.matchesAnyOf(number));
    }
}
