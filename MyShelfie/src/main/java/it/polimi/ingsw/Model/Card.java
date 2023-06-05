package it.polimi.ingsw.Model;

public enum Card {
    WHITE1 {
        @Override
        public String toString() {

            return "\033[0;97mWHITE\033[0m";
        }
    }, WHITE2 {
        @Override
        public String toString() {

            return "\033[0;97mWHITE\033[0m";
        }
    }, WHITE3 {
        @Override
        public String toString() {

            return "\033[0;97mWHITE\033[0m";
        }
    }, BLUE1 {
        @Override
        public String toString() {

            return "\033[0;34mBLUE\033[0m";
        }
    }, BLUE2 {
        @Override
        public String toString() {

            return "\033[0;34mBLUE\033[0m";
        }
    }, BLUE3 {
        @Override
        public String toString() {

            return "\033[0;34mBLUE\033[0m";
        }
    }, LIGHTBLUE1 {
        @Override
        public String toString() {
            return "\033[0;96mLIGHTBLUE\033[0m";
        }
    }, LIGHTBLUE2 {
        @Override
        public String toString() {
            return "\033[0;96mLIGHTBLUE\033[0m";
        }
    }, LIGHTBLUE3 {
        @Override
        public String toString() {
            return "\033[0;96mLIGHTBLUE\033[0m";
        }
    }, YELLOW1 {
        @Override
        public String toString() {
            return "\033[0;93mYELLOW\033[0m";
        }
    }, YELLOW2 {
        @Override
        public String toString() {
            return "\033[0;93mYELLOW\033[0m";
        }
    }, YELLOW3 {
        @Override
        public String toString() {
            return "\033[0;93mYELLOW\033[0m";
        }
    }, GREEN1 {
        @Override
        public String toString() {
            return "\033[0;92mGREEN\033[0m";
        }

    }, GREEN2 {
        @Override
        public String toString() {
            return "\033[0;92mGREEN\033[0m";
        }

    }, GREEN3 {
        @Override
        public String toString() {
            return "\033[0;92mGREEN\033[0m";
        }

    }, PURPLE1 {
        @Override
        public String toString() {
            return "\033[0;95mPURPLE\033[0m";
        }
    }, PURPLE2 {
        @Override
        public String toString() {
            return "\033[0;95mPURPLE\033[0m";
        }
    }, PURPLE3 {
        @Override
        public String toString() {
            return "\033[0;95mPURPLE\033[0m";
        }
    }, NONE, NOT;

    public boolean isEqualTo(Card other) {
        if (((this == WHITE1 || this == WHITE2 || this == WHITE3) && (other == WHITE1 || other == WHITE2 || other == WHITE3)) ||
                ((this == BLUE1 || this == BLUE2 || this == BLUE3) && (other == BLUE1 || other == BLUE2 || other == BLUE3)) ||
                ((this == LIGHTBLUE1 || this == LIGHTBLUE2 || this == LIGHTBLUE3) && (other == LIGHTBLUE1 || other == LIGHTBLUE2 || other == LIGHTBLUE3))
                || ((this == GREEN1 || this == GREEN2 || this == GREEN3) && (other == GREEN1 || other == GREEN2 || other == GREEN3)) ||
                ((this == YELLOW1 || this == YELLOW2 || this == YELLOW3) && (other == YELLOW1 || other == YELLOW2 || other == YELLOW3)) ||
                ((this == PURPLE1 || this == PURPLE2 || this == PURPLE3) && (other == PURPLE1 || other == PURPLE2 || other == PURPLE3))) {
            return true;
        }
        return false;

    }
}
