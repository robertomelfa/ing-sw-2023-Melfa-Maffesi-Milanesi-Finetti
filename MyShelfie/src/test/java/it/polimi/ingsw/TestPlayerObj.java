package it.polimi.ingsw;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPlayerObj {

    PlayerObj obj;
    Library lib;
    Player player;

    @Before
    public void setUp() throws Exception {
        obj = new PlayerObj(1);
        player = new Player("Nick");
        obj.resetAvaliable();
    }

//        avaliable si resetta ... (da capire dove metterlo) (esiste metodo resetAvaliable)

    @Test
    public void testConstructor_attributes() throws Exception {

     //   Assert.assertEquals(0, obj.getI());
        Assert.assertEquals(1, obj.getPOINT()[0]);
        Assert.assertEquals(2, obj.getPOINT()[1]);
        Assert.assertEquals(4, obj.getPOINT()[2]);
        Assert.assertEquals(6, obj.getPOINT()[3]);
        Assert.assertEquals(9, obj.getPOINT()[4]);
        Assert.assertEquals(12, obj.getPOINT()[5]);
    }

    @Test
    public void testConstructor_checkSingleObjList(){

        Assert.assertEquals(6, obj.getPlayerObjs().size());
        for (int i=0; i<6; i++){
            Assert.assertTrue (obj.getPlayerObjs().get(i).getXPosition()>=0 && obj.getPlayerObjs().get(i).getXPosition()<6);
            Assert.assertTrue (obj.getPlayerObjs().get(i).getYPosition()>=0 && obj.getPlayerObjs().get(i).getYPosition()<5);
            Assert.assertTrue (obj.getPlayerObjs().get(i).getType()!=Card.NONE && obj.getPlayerObjs().get(i).getType()!=Card.NOT && obj.getPlayerObjs().get(i).getType()!=null);
        }
    }

    @Test
    public void testCheckObj_numObj_12Points () throws Exception {

        for (int k=0; k<12; k++) {
            lib = new Library();
            obj = new PlayerObj(k+1);

            Assert.assertEquals(6, obj.getPlayerObjs().size());

            for (int i = 0; i < 6; i++) {
                lib.setCard(obj.getPlayerObjs().get(i).getXPosition(), obj.getPlayerObjs().get(i).getYPosition(), obj.getPlayerObjs().get(i).getType());
            }

            Assert.assertEquals(12, obj.checkObj(player, lib));
        }
    }

    @Test
    public void testCheckObj_numObj_9Points () throws Exception {

        lib = new Library();
        obj= new PlayerObj(1);

        for (int i=0; i<5; i++){
            lib.setCard(obj.getPlayerObjs().get(i).getXPosition(),obj.getPlayerObjs().get(i).getYPosition(),obj.getPlayerObjs().get(i).getType());
        }

        Assert.assertEquals(9, obj.checkObj(player, lib));
    }

    @Test
    public void testCheckObj_numObj_6Points () throws Exception {

        lib = new Library();
        obj= new PlayerObj(1);

        for (int i=0; i<4; i++){
            lib.setCard(obj.getPlayerObjs().get(i).getXPosition(),obj.getPlayerObjs().get(i).getYPosition(),obj.getPlayerObjs().get(i).getType());
        }

        Assert.assertEquals(6, obj.checkObj(player, lib));
    }

    @Test
    public void testCheckObj_numObj_4Points () throws Exception {

        lib = new Library();
        obj= new PlayerObj(1);

        for (int i=0; i<3; i++){
            lib.setCard(obj.getPlayerObjs().get(i).getXPosition(),obj.getPlayerObjs().get(i).getYPosition(),obj.getPlayerObjs().get(i).getType());
        }

        Assert.assertEquals(4, obj.checkObj(player, lib));
    }

    @Test
    public void testCheckObj_numObj_2Points () throws Exception {

        lib = new Library();
        obj= new PlayerObj(1);

        for (int i=0; i<2; i++){
            lib.setCard(obj.getPlayerObjs().get(i).getXPosition(),obj.getPlayerObjs().get(i).getYPosition(),obj.getPlayerObjs().get(i).getType());
        }

        Assert.assertEquals(2, obj.checkObj(player, lib));
    }

    @Test
    public void testCheckObj_numObj_1Points () throws Exception {

        lib = new Library();
        obj= new PlayerObj(1);

        lib.setCard(obj.getPlayerObjs().get(0).getXPosition(),obj.getPlayerObjs().get(0).getYPosition(),obj.getPlayerObjs().get(0).getType());

        Assert.assertEquals(1, obj.checkObj(player, lib));

    }

    @Test
    public void testResetAvaliable_chekReset(){

        obj.resetAvaliable();
        Assert.assertEquals(12, PlayerObj.getAvailable().size());
    }
// abbiamo messo che controlla solo alla fine della partita
//
//    @Test
//    public void testCheckObj_numObj_4to12Points() throws Exception {
//
//        testCheckObj_numObj_4Points();
//
//        for (int i=0; i<6; i++){
//            lib.setCard(obj.getPlayerObjs().get(i).getXPosition(),obj.getPlayerObjs().get(i).getYPosition(),obj.getPlayerObjs().get(i).getType());
//        }
//
//        Assert.assertEquals(12-4 ,obj.checkObj(player, lib));
//    }
//
//    @Test
//    public void testCheckObj_numObj_4to6Points() throws Exception {
//
//        lib = new Library();
//        obj= new PlayerObj(1);
//
//        for (int i=0; i<3; i++){
//            lib.setCard(obj.getPlayerObjs().get(i).getXPosition(),obj.getPlayerObjs().get(i).getYPosition(),obj.getPlayerObjs().get(i).getType());
//        }
//
//        obj.checkObj(player, lib);
//      //  Assert.assertEquals(2,obj.getI());
//
//        lib.setCard(obj.getPlayerObjs().get(3).getXPosition(),obj.getPlayerObjs().get(3).getYPosition(),obj.getPlayerObjs().get(3).getType());
//
//        Assert.assertEquals(6-4 ,obj.checkObj(player, lib));
//    }
//
//    @Test
//    public void testCheckObj_numObj_12to12Points() throws Exception {
//
//        testCheckObj_numObj_12Points();
//
//        Assert.assertEquals(12-12 ,obj.checkObj(player, lib));
//    }

//    non so se serve a qualcosa
//    @After
//    public void reset(){
//        obj=null;
//        lib=null;
//    }
}
