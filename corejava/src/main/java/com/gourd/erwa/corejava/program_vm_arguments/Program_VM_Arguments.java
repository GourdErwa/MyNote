package com.gourd.erwa.corejava.program_vm_arguments;

/**
 * Created by lw on 14-7-4.
 * <p>
 * 实际上 program arguments 中的值作为 args[] 的参数传入的，而 VM Arguments 是设置的虚拟机的属性。
 * program arguments 是要传给你的应用程序的，它通过主函数中的 args 来传值。 VM arguments 是系统的属性，要传给 java 虚拟机的。
 */
public class Program_VM_Arguments {
    /*
      program arguments 输入
      -f analyzier.conf -s /var/log/ezsonar/analyzier/stats_benchmark.log -sf 10
     */

    /**
     * 例如： VM arguments:
     * -DldUserName=rex -DldPassword=amaxgs -Xmx512m -Dcom.datasweep.plantops.j2eevendor=JBoss -Djava.library.path=D:/work/RNNativeLibsWin32.jar;Y:/PlantOperations/bldPlantOperations/code/lib/jdic -Djava.ext.dirs=Y:/PlantOperations/bldPlantOperations/CODE/lib/jboss;Y:/PlantOperations/bldPlantOperations/code/bld/SUN_JDK/jre/lib/ext;Y:/PlantOperations/bldPlantOperations/code/lib/xerces;E:/FTPCDestination/jboss-eap-5.0.0.GA/jboss-eap-5.0/jboss-as/client -Djava.endorsed.dirs=Y:/PlantOperations/bldPlantOperations/code/lib/jboss/endorsed
     * 这些系统属性都以—D开头。
     *
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("------------ Program arguments ------------");
        System.out.println("Program arguments args size:" + args.length);
        for (String arg : args) {
            System.out.println(arg);
        }

        System.out.println("------------ VM arguments ------------");
        String syspro1 = "syspro1";
        System.out.println(System.getProperty(syspro1));
        String syspro2 = "syspro2";
        System.out.println(System.getProperty(syspro2));

    }
}
