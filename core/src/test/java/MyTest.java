import BattleCity_independent_code.Tank;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by kuznetsov_pavel on 18.09.16.
 */
public class MyTest {
    @Test
    public  void Test1 () {
        Tank myTank = new Tank();
        Tank botTank = new Tank();


        myTank.setDamages(Tank.getArmorPlayerTank());
        myTank.setCoordX(100);
        myTank.setCoordY(100);
        myTank.setDerection(0);
        myTank.set_speed(0);

        botTank.setDamages(Tank.getArmorPlayerTank());
        botTank.setCoordX(100);
        botTank.setCoordY(200);
        botTank.setDerection(1);
        botTank.set_speed(0);




    }



}
