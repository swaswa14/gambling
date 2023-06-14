package ph.cdo.backend.request.registration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    public Util util = new Util();

    @Test
    void shouldBeTwo(){
        int answer = util.climbStairs(2);

        Assertions.assertEquals(2, answer);
    }

    @Test
    void shouldBeThree(){
        int answer = util.climbStairs(8);

        Assertions.assertEquals(3, answer);
    }

    @Test
    void shouldBeFive(){
        int answer = util.climbStairs(4);

        Assertions.assertEquals(5, answer);
    }
}