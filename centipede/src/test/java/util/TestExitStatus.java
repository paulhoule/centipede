package util;

import com.ontology2.centipede.util.ExitStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestExitStatus {
    @Test
    public void testOK() {
        assertEquals(0, ExitStatus.OK);
    }

}
