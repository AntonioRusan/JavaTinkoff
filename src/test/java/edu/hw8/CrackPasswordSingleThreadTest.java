package edu.hw8;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CrackPasswordSingleThreadTest {
    @Test
    void testPassword() {
        CrackPasswordSingleThread.crackPassword();
    }
}
