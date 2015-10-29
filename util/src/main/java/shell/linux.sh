#!/bin/bash
#This script is used to check the server
#system info
system_info() {
echo "**********************************************"
echo "system info:"
echo
echo "   System-release : `cat /etc/RedHat-release`"
echo "   Kernel-release : `uname -a|awk '{print $1,$3}'`"
echo "   Server-Model : `dmidecode | grep "Product Name:"|sed -n '1p'|awk -F': ' '{print $2}'`"
echo
}




#CPU info
cpu_info() {
echo "**********************************************"
echo "CPU info:"
echo
echo "    Frequency : `cat /proc/cpuinfo | grep "model name" | uniq |awk -F': ' '{print $2}'`"
echo "    CPU cores:  `cat /proc/cpuinfo | grep "cpu cores" | uniq |awk -F': ' '{print $2}'`"
echo "    Logic Count : `cat /proc/cpuinfo | grep "processor" | sort -u| wc -l `"
echo "    Physical Count : `cat /proc/cpuinfo | grep "physical" | sort -u| wc -l`"
echo "    Cache size : `cat /proc/cpuinfo| grep "cache size"|uniq|awk '{print $4,$5}'`"
echo
}





#memory info
mem_info() {
memory=`dmidecode |grep "Range Size"|head -1|awk '{print $3$4}'`
mem_size=`echo "This server has ${memory} memory."`

echo "**********************************************"
echo "Memory info:"
echo
echo "   Total : ${mem_size}"
echo "   Count : `dmidecode |grep -A16 "Memory Device$"|grep Size|awk '{if($2!~/No/) print $0}'|wc -l`"
dmidecode |grep -A20 "Memory Device$"|grep Size|sed '{s/^       */   /g};{/No/d}'
echo
}





#disk and partitions
swap_pos=`cat /proc/swaps|sed -n '2p'|awk '{print $1}'`
partition_info() {
echo "**********************************************"
echo "Hard disk info:"
echo
echo "`fdisk -l|grep Disk|awk -F, '{print $1}'`"
echo "**********************************************"
echo "Partition info:"
echo
df -h | grep -v Filesystem | sed "s:none:${swap_pos}:"
echo
}


#network adapter info
adapter_info() {

duplex_eth0=`ethtool eth0 | grep Duplex | awk '{if($2~/Full/) print "Full"};{if($2~/Half/)print "Half"};{if($2~/Uknown!/) print "unknown"}'`

duplex_eth1=`ethtool eth1 | grep Duplex | awk '{if($2~/Full/) print "Full"};{if($2~/Half/)print "Half"};{if($2~/Uknown!/) print "unknown"}'`

Negotiation_eth0=`ethtool eth0 | grep "Advertised auto-negotiation"|awk -F': ' '{if($2~/No/) print "Non-negotiation."};{if($2~/Yes/) print "Negotiation"}'`

Negotiation_eth1=`ethtool eth1 | grep "Advertised auto-negotiation"|awk -F': ' '{if($2~/No/) print "Non-negotiation"};{if($2~/Yes/) print "Negotiation"}'`

IP_eth0=`cat /etc/sysconfig/network-scripts/ifcfg-eth0|grep IPADDR|awk -F= '{print $2}'`

IP_eth1=`cat /etc/sysconfig/network-scripts/ifcfg-eth1|grep IPADDR|awk -F= '{print $2}'`

speed_eth0=`ethtool eth0|grep Speed|awk '{print $2}'`
speed_eth1=`ethtool eth1|grep Speed|awk '{print $2}'`

echo "**********************************************"
echo "Network adapter info:"
echo
echo "  IP_eth0 : ${IP_eth0}        IP_eth0 : ${IP_eth1}"
echo "  Speed_eth0 : ${speed_eth0}          Speed_eth1 : ${speed_eth1}"
echo "  Duplex_eth0 : ${duplex_eth0}            Duplex_eth1 : ${duplex_eth1}"
echo "  Negotiation_eth0 : ${Negotiation_eth0}  Negotiation_eth1 : ${Negotiation_eth1}"
echo
}




#software package
software_info() {
echo "**********************************************"
echo "SELinux is `cat /etc/selinux/config |grep SELINUX=disabled|awk -F= '{print $2}'||echo "enabled"`"
echo "`service iptables status|sed 's/Firewall/Iptables/g'`"
echo
echo "**********************************************"
sed -n '/%packages/,/%post/p;' /root/anaconda-ks.cfg|sed '/%post/d;/^$/d'
echo "**********************************************"
}



#del mac-addr
#sed -i '/HWADDR/d' /etc/sysconfig/network-scripts/ifcfg-eth0
#sed -i '/HWADDR/d' /etc/sysconfig/network-scripts/ifcfg-eth1


system_info
cpu_info
mem_info
partition_info
adapter_info
software_info