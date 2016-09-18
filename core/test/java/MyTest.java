import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by kuznetsov_pavel on 18.09.16.
 */
public class MyTest {
    @Test
    public  void Test (){
        int k = 0;
        for (int i = 0; i < 10; i++)
            k++;
        assertEquals(10,k);
    }

    @Test
    public  void Test1 (){
        int k = 0;
        for (int i = 0; i < 20; i++)
            k++;
        assertEquals(10,k);
    }

}
