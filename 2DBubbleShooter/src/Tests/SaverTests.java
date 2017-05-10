package Tests;

import GameObjects.*;
import Utils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Artem Solomatin on 10.05.17.
 * 2DBubbleShooter
 */
public class SaverTests {
    //FIELDS
    static Player player;
    static Saver profile;

    ArrayList<Saver> profiles;

    @Before
    public void setUp() {
        player = new Player(Color.white, 0);
        profile = new Saver("TEST", 10_000);
        //Saver.setFileName("Test.ser");
    }

    @Test
    public void saverConstructorTest(){
        new Saver("lol", 0);
    }

    @Test
    public void serializationTest(){
        profiles = new ArrayList<>();
        for(int i = 0;i < 10; i++){
            profiles.add(new Saver("Test" + i, i));
        }

        Saver.serData(profiles);
        System.out.println(profiles != null);
    }

    @Test
    public void deserializationTest(){
        ArrayList<Saver> actualProfiles = Saver.deserData();

        for(int i = 0;i < 10;i++){
            Assert.assertEquals(i, actualProfiles.get(i).getScore());
            Assert.assertEquals("Test" + i, actualProfiles.get(i).getName());
        }
    }
}
