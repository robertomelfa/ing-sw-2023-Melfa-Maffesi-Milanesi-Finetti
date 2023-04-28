package it.polimi.ingsw.Model;

public enum Card {
    WHITE{
        @Override
        public String toString(){

            return "\033[0;97mWHITE\033[0m";
        }
    }, BLUE{
        @Override
        public String toString(){

            return "\033[0;34mBLUE\033[0m";
        }
    }, LIGHTBLUE{
        @Override
        public String toString(){
            return "\033[0;96mLIGHTBLUE\033[0m";
        }
    }, YELLOW{
        @Override
        public String toString(){
            return "\033[0;93mYELLOW\033[0m";
        }
    }, GREEN{
        @Override
        public String toString(){
            return "\033[0;92mGREEN\033[0m";
        }

    }, PURPLE{
        @Override
        public String toString(){
            return "\033[0;95mPURPLE\033[0m";
        }
    }, NONE, NOT;
}
