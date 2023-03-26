package it.polimi.ingsw;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class testGame extends TestCase{

    @Test
    public void testConstructor_1_exception(){
        try {
            Game g1=new Game(1);
            fail("Expected exception");
            assertTrue(g1.getGameTable()!=null && g1.getCardbox()!=null);
        }catch (Exception e){

        }
    }

    @Test
    public void testConstructor_5_exception(){
        try {
            Game g1=new Game(5);
            fail("Expected exception");
            assertTrue(g1.getGameTable()!=null && g1.getCardbox()!=null);
        }catch (Exception e){

        }
    }

    @Test
    public void testaddNewPlayer_3playersnumofPlayers2_exception() throws Exception{
        Game g1=new Game(2);
        Player p1=new Player("player",null);
        Player p2=new Player("player",null);
        Player p3=new Player("player",null);
        try {
            g1.addNewPlayer(p1);
            g1.addNewPlayer(p2);
            g1.addNewPlayer(p3);
            fail("Expected exception");
        }catch (Exception e){

        }
    }

    @Test
    public void testaddNewPlayer_5playersnumofPlayers4_exception() throws Exception{
        Game g1=new Game(4);
        Player p1=new Player("player",null);
        Player p2=new Player("player",null);
        Player p3=new Player("player",null);
        Player p4=new Player("player",null);
        Player p5=new Player("player",null);
        try {
            g1.addNewPlayer(p1);
            g1.addNewPlayer(p2);
            g1.addNewPlayer(p3);
            g1.addNewPlayer(p4);
            g1.addNewPlayer(p5);
            fail("Expected exception");
        }catch (Exception e){

        }
    }

    @Test
    public void testaddNewPlayer_2playersnumofPlayers4(){
        try {
            Game g1=new Game(4);
            Player p1=new Player("player",null);
            Player p2=new Player("player",null);
            g1.addNewPlayer(p1);
            g1.addNewPlayer(p2);
        }catch (Exception e){

        }

    }
}
