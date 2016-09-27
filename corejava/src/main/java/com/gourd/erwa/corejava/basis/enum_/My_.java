package com.gourd.erwa.util.corejava.basis.enum_;

/**
 * @author lw by 14-5-29.
 */
public enum My_ {

    YIWA {
        @Override
        public String toString() {
            System.out.println(YIWA.name() + "MY_");
            return super.toString();
        }
    },
    ERWA {
        @Override
        public String toString() {
            System.out.println(ERWA.name() + "MY_");
            return super.toString();
        }

        public String getdemo_1() {

            return "";
        }
    },
    SANWA {
        @Override
        public String toString() {
            System.out.println(SANWA.name() + "MY_");
            return super.toString();
        }
    }
}

