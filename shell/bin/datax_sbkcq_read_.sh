#!/bin/bash

#**support currency*#
#**add by liuxuan***#
#**at 2016/04/18****#

###currency####
tmp_fifofile="/tmp/$.fifo"
mkfifo $tmp_fifofile 
exec 15<>$tmp_fifofile
rm -rf $tmp_fifofile # 将fd15指向fifo类型
thread=15 # 此处定义线程数

#fd15中放置了$thread个回车符,用于控制线程数,fd13起到管道的作用
for ((n=1;n<=$thread;n++));do
	echo >&15
done

base_dir=$(cd $(dirname $0);pwd)
base_dir=$base_dir/..
source $base_dir/shell/log.sh "datax_mysql_to_hive.log"

if [ $# == 1 ]; then
     day=$1
else
     day=`date +%Y%m%d`
fi

yday=`date +%Y%m%d -d"${day}-1day"`
ydayf=`date +%Y-%m-%d -d"${day}-1day"`
ydaym=`date +%y%m%d -d"${day}-1day"`

##删除 previous data.
rm -rf $base_dir/sbkcq_data/*

channel='1'
username='read_user'
password='1AD_g3TjtW2'
defaultFS=''

fileType='text'
writeMode='append'
fieldDelimiter='`'
compress='GZIP'
#BOS_CMD="bce bos"

##初始化异常状态文件
error_status_file=${base_dir}/status/sbkcq.error.status.${yday}
>$error_status_file

##初始化alter partition sql文件.
echo "use db_stat_sbkcq;" > ${base_dir}/sql/alter_add_partition_${yday}.sql

#job_datax_json_f write_type region_id server_id column_r rds_Url mysql_db mysql_table hive_table column_w
job_datax_json_f() {
local write_type=$1
local region_id=$2
local server_id=$3
local column_r=$4
local mysql_db=$6
local jdbcUrl='jdbc:mysql://'${5}':3306/'${mysql_db}''
local mysql_table=$7
local hive_table=$8
local column_w=$9
local sql_where=${10}
local path_w='/user/hive/warehouse/db_stat_sbkcq.db/'${hive_table}'/ds='${yday}'/region_id='${region_id}''
local fileName_w=''${hive_table}'_'${yday}''

hadoop fs -mkdir -p ${path_w}

local job_datax_json=''
if [ "hdfs" = ${write_type} ]; then
job_datax_json_mysql_to_hdfs='
{
    "setting": {},
    "job": {
        "setting": {
            "speed": {
                "channel": '${channel}'
            }
        },
        "content": [
            {
                 "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "'${username}'",
                        "password": "'${password}'",
                        "column": ['${column_r}'],
                        "connection": [
                            {
                                "table": [
                                    "'${mysql_table}'"
                                ],
                                "jdbcUrl": ["'${jdbcUrl}'"]
                            }
                        ]
                    }
                },
                "writer": {
                    "name": "hdfswriter",
                    "parameter": {
                        "defaultFS": "'${defaultFS}'",
                        "fileType": "'${fileType}'",
                        "path": "'${path_w}'",
                        "fileName": "'${fileName_w}'",
                        "column": ['${column_w}'],
                        "writeMode": "'${writeMode}'",
                        "fieldDelimiter": "'${fieldDelimiter}'",
                        "compress":"'${compress}'"
                    }
                }            
       
            }
        ]
    }
}'
job_datax_json=${job_datax_json_mysql_to_hdfs}
elif [ "hdfs_sql" = ${write_type}  ]; then
job_datax_json_mysql_querySql_to_hdfs='
{
    "setting": {},
    "job": {
        "setting": {
            "speed": {
                "channel": '${channel}'
            }
        },
        "content": [
            {
                 "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "'${username}'",
                        "password": "'${password}'",
                        "connection": [
                            {
                                "querySql": [
                                    "'${column_r}' from '${mysql_table}' where '${sql_where}';"
                                ],
                                "jdbcUrl": ["'${jdbcUrl}'"]
                            }
                        ]
                    }
                },
                "writer": {
                    "name": "hdfswriter",
                    "parameter": {
                        "defaultFS": "'${defaultFS}'",
                        "fileType": "'${fileType}'",
                        "path": "'${path_w}'",
                        "fileName": "'${fileName_w}'",
                        "column": ['${column_w}'],
                        "writeMode": "'${writeMode}'",
                        "fieldDelimiter": "'${fieldDelimiter}'",
                        "compress":"'${compress}'"
                    }
                }            
       
            }
        ]
    }
}'
job_datax_json=${job_datax_json_mysql_querySql_to_hdfs}
elif [ "textfile" = ${write_type}  ]; then
job_datax_json_mysql_to_textfile='
{
    "setting": {},
    "job": {
        "setting": {
            "speed": {
                "channel": '${channel}'
            }
        },
        "content": [
            {
                 "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "'${username}'",
                        "password": "'${password}'",
                        "column": ['${column_r}'],
                        "connection": [
                            {
                                "table": [
                                    "'${mysql_table}'"
                                ],
                                "jdbcUrl": ["'${jdbcUrl}'"]
                            }
                        ]
                    }
                },
                  "writer": {
                    "name": "txtfilewriter",
                    "parameter": {
                        "path": "'${base_dir}'/sbkcq_data",
                        "fileName": "datax_txt_'${mysql_table}'_'${ydaym}'_'${region_id}'_'${server_id}'",
                        "writeMode": "truncate",
                        "fileFormat":"csv",
                        "encoding":"utf-8",
                        "fieldDelimiter": "'${fieldDelimiter}'"
                    }
                }
            }
        ]
    }
}'
job_datax_json=${job_datax_json_mysql_to_textfile}
elif [ "textfile_sql" = ${write_type}  ]; then
job_datax_json_mysql_querySql_to_textfile='
{
    "setting": {},
    "job": {
        "setting": {
            "speed": {
                "channel": '${channel}'
            }
        },
        "content": [
            {
                 "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "'${username}'",
                        "password": "'${password}'",
                        "connection": [
                            {
                                "querySql": [
                                    "'${column_r}' from '${mysql_table}' where '${sql_where}';"
                                ],
                                "jdbcUrl": ["'${jdbcUrl}'"]
                            }
                        ]
                    }
                },
                  "writer": {
                    "name": "txtfilewriter",
                    "parameter": {
                        "path": "'${base_dir}'/sbkcq_data",
                        "fileName": "datax_txt_'${mysql_table}'_'${ydaym}'_'${region_id}'_'${server_id}'",
                        "writeMode": "truncate",
                        "fileFormat":"csv",
                        "encoding":"utf-8",
                        "fieldDelimiter": "'${fieldDelimiter}'"
                    }
                }
            }
        ]
    }
}'
job_datax_json=${job_datax_json_mysql_querySql_to_textfile}
else
write_log ERROR "error parameter!!!"
fi

echo $job_datax_json > ${base_dir}/data_tmp/job_datax_${region_id}_${server_id}_${mysql_db}_${mysql_table}_${yday}.json

python ${base_dir}/datax/bin/datax.py ${base_dir}/data_tmp/job_datax_${region_id}_${server_id}_${mysql_db}_${mysql_table}_${yday}.json
if [ $? -ne 0 ];then
	echo "datax.py process : ${region_id}_${server_id}_${mysql_db}_${mysql_table}_${yday}" >> $error_status_file
	write_log ERROR "datax.py process : ${region_id}_${server_id}_${mysql_db}_${mysql_table}_${yday} failed"
	return 1
else
	write_log INFO "datax.py process : ${region_id}_${server_id}_${mysql_db}_${mysql_table}_${yday} success"
fi

###
alter_hive='
alter table '${hive_table}' add partition (ds='${yday}',region_id='${region_id}');
'
echo ${alter_hive} >> ${base_dir}/sql/alter_add_partition_${yday}.sql
if [ "textfile" == ${write_type} -o "textfile_sql" == ${write_type} ]; then
	hadoop fs -D speed.limit.kb=10240 -copyFromLocal ${base_dir}/sbkcq_data/datax_txt_${mysql_table}_${ydaym}_${region_id}_${server_id}*  ${path_w}
	if [ $? -ne 0 ];then
		echo "hadoop fs copyfrom local:${base_dir}/sbkcq_data/datax_txt_${mysql_table}_${ydaym}_${region_id}_${server_id}*  ${path_w}" >> $error_status_file
		write_log ERROR "hadoop fs copyfrom local:${base_dir}/sbkcq_data/datax_txt_${mysql_table}_${ydaym}_${region_id}_${server_id}*  ${path_w} failed"
		return 1
	else
		write_log INFO "hadoop fs copyfrom local:${base_dir}/sbkcq_data/datax_txt_${mysql_table}_${ydaym}_${region_id}_${server_id}*  ${path_w} success"
	fi
fi
} ##function

mysqlinfo='mysql -h10.10.144.108 -udt_gata_dw_w -p'xxxxx''
${mysqlinfo} --local-infile=1   -e"select distinct region_id,server_id,rds_url from gata_sbkcq.server_relation where service_time != '0000-00-00' and status = 1;" | sed 's/\t/:/g' > ${base_dir}/table_list/cn_sbkcq_region_server_list.table.${yday}
sed -i '1d' ${base_dir}/table_list/cn_sbkcq_region_server_list.table.${yday}
${mysqlinfo} --local-infile=1   -e"
select distinct region_id,rds_url from gata_sbkcq.region_relation;
" | sed 's/\t/:/g' > ${base_dir}/table_list/cn_sbkcq_region_list.table.${yday}
sed -i '1d' ${base_dir}/table_list/cn_sbkcq_region_list.table.${yday}

##############################################################
#######################accountDB##############################
##############################################################
for keystr  in `cat ${base_dir}/table_list/cn_sbkcq_region_list.table.${yday}`
do
region_id=`echo ${keystr}|awk -F [:] '{print $1}'`
regoin_rds=`echo ${keystr}|awk -F [:] '{print $2}'`
#'accountdb' 'gaea_cn_sbkcq_account'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${region_id} '"id","name","password","privilege","login_status","world_name_crc","forbid_mask","guard","mibao","ip","time","special_flag",'\"\'${region_id}\'\"',"reg_time"' ${regoin_rds} 'accountdb' 'account' 'gaea_cn_sbkcq_account' ''
echo >&15
}&
#'accountdb' 'gaea_cn_sbkcq_account_third'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${region_id} '"account_id","code","third_type","third_id","accountname","password",'\"\'${region_id}\'\"',"reg_time"' ${regoin_rds} 'accountdb' 'account_third' 'gaea_cn_sbkcq_account_third' ''
echo >&15
}&
done

#############################################################
#########################gameDB##############################
#############################################################
for keystr  in `cat ${base_dir}/table_list/cn_sbkcq_region_server_list.table.${yday}`
do
region_id=`echo ${keystr}|awk -F [:] '{print $1}'`
server_id=`echo ${keystr}|awk -F [:] '{print $2}'`
server_rds=`echo ${keystr}|awk -F [:] '{print $3}'`
#'gamedb' 'gaea_cn_sbkcq_role_data'
read -u15
{
job_datax_json_f 'textfile_sql' ${region_id} ${server_id} 'select account_id,role_id,role_name,role_name_crc,sex,hair_model_id,hair_color_id,face_model_id,face_detail_id,dress_model_id,visualizeid,display_set,map_id,x,y,z,face_x,face_y,face_z,reborn_map_id,class,classex,level,exp_cur_level,hp,mp,rage,endurance,vitality,injury,knowledge,morale,morality,culture,credit,identity,vip_point,att_avail,talent_avail,physique_added,strength_added,pneuma_added,innerforce_added,technique_added,agility_added,talent_type1,talent_type2,talent_type3,talent_type4,talent_val1,talent_val2,talent_val3,talent_val4,safe_guard_flag,pk_value,close_safe_guard_time,bag_size,bag_gold,bag_silver,bag_copper,bag_bind_gold,bag_bind_silver,bag_bind_copper,bag_yuanbao,exchange_volume,guild_id,team_id,total_tax,remote_open_set,cur_title_id,get_mall_free_time,create_time,login_time,logout_time,online_time,cur_online_time,leave_guild_time,remove_flag,remove_time,treasure_sum,stall_level,stall_daily_exp,stall_cur_exp,stall_last_time,send_mail_num,master_id,masterprentice_forbid_time,map_limit_num,own_instance_id,own_instance_map_id,instance_create_time,hang_num,is_exp,is_brotherhood,leave_exp,leave_brotherhood,pet_packet_num,total_mastermoral,kill_num,gift_group_id,gift_step,gift_id,gift_leaving_time,gift_get,role_camp,paimailimit,banklimit,exbagstep,exwarestep,vigour,today_online_tick,history_vigour_cost,ware_size,ware_gold,ware_silver,ware_copper,signature_name,yuanbao_exchange_num,achievemetn_point,forbid_talk_start,forbid_talk_end,change_name,graduate_num,destory_equip_count,cur_1v1_score,day_1v1_score,day_1v1_num,week_1v1_score,score_1v1_award,last_change_name_time,delete_role_guard_time,exploits,circle_quest_refresh,exploitslimit,active_num,purpuredec,circle_quest_perdaynumber,cooldownrevive_cd,circle_quest_refresh_daymax,shihun,perday_hang_getexp_timems,achievemetn_num,pet_xiulian_size,perday_vigour_get_total,guild_active_num,god_level,master_moral,perday_prentice_num,master_repution_reward,cur_title_id2,cur_title_id3,instance_pass,shaodang_begin_time,shaodang_index,spouse_id,vip_level,vip_exp,tilizhi,silver_box_time,gold_box_time,fusion_degrees,flowerexp,doublepractieexp,redenvelopeexp,expself,into_palace_time,enemy_id,card_begin_time,card_end_time,guild_contribution,kill_monster_num,canaccept_huantask_id,isEnterTower,isgetexp,offlineExp_StartTime,isfirstispay,isfirstexpense,'\'${server_id}\'',month_card_time,justice' ${server_rds} 'gamedb' 'role_data' 'gaea_cn_sbkcq_role_data' '' '1=1'
echo >&15
}&
#'gamedb' 'gaea_cn_sbkcq_account_common'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"account_id","account_name","safecode_crc","reset_time","bag_password_crc","baibao_yuanbao","ware_size","ware_silver","warestep","yuanbao_recharge","IsReceive","total_recharge","receive_type","receive_type_ex","web_type","score","robort","recharge_feedback","feedback_times","recharge_period","\"\"","\"\"","third_active_state","third_id",'\"\'${server_id}\'\"',"third_string","sr_kinds"' ${server_rds} 'gamedb' 'account_common' 'gaea_cn_sbkcq_account_common' ''
echo >&15
}&

#'gamedb' 'gaea_cn_sbkcq_guild'
read -u15
{
job_datax_json_f 'textfile_sql' ${region_id} ${server_id} 'select id,guild_name,creater_name_id,leader_id,special_state,level,hold_city0,hold_city1,hold_city2,fund,material,reputation,daily_cost,peace,rank,create_time,formal,sign_num,uplevel_time,league_id,unleague_time,prosperity,change_dkp,change_guild_symbol,symbol_value,group_purchase,remain_spread_times,commendation,change_name,jujue_time,act_instance_info,attack_SBK,competitive_money,competitive_time,experience,'\'${server_id}\' ${server_rds} 'gamedb' 'guild' 'gaea_cn_sbkcq_guild' '' '1=1'
echo >&15
}&
done
write_log INFO "*********************Complete to import gamedb to localfile to hive.*********************"
write_log INFO "*********************Begin to import logdb to localfile to hive.**********************"

##################################################################
#############################logDB################################
##################################################################
for keystr  in `cat ${base_dir}/table_list/cn_sbkcq_region_server_list.table.${yday}`
do
region_id=`echo ${keystr}|awk -F [:] '{print $1}'`
server_id=`echo ${keystr}|awk -F [:] '{print $2}'`
server_rds=`echo ${keystr}|awk -F [:] '{print $3}'`

#'logdb' 'gaea_cn_sbkcq_log_yuanbao'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"id","account_id","role_id","cmd_id","container_type","num","total_num","time","role_id_relation",'\"\'${server_id}\'\" ${server_rds} 'logdb' 'log_yuanbao_'${ydaym}'' 'gaea_cn_sbkcq_log_yuanbao' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_money'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"id","account_id","role_id","cmd_id","container_type","num","total_num","role_id_relation","time",'\"\'${server_id}\'\" ${server_rds} 'logdb' 'log_money_'${ydaym}'' 'gaea_cn_sbkcq_log_money' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_login_log'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"account_id","account_name","ip","action","time",'\"\'${server_id}\'\",'"device_id"' ${server_rds} 'logdb' 'login_log_'${ydaym}'' 'gaea_cn_sbkcq_login_log' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_mallsell'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"id","cmd_id","serial","type_id","role_id","number","first_gain_time","exist_time","max_use_times","cost_yuanbao","cost_ex_volume","time",'\"\'${server_id}\'\" ${server_rds} 'logdb' 'log_mallsell_'${ydaym}'' 'gaea_cn_sbkcq_log_mallsell' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_bill_yuanbao_log'
read -u15
{
job_datax_json_f 'textfile_sql' ${region_id} ${server_id} 'select token_id,account_id,yuanbao,time,'\'${server_id}\' ${server_rds} 'logdb' 'bill_yuanbao_log' 'gaea_cn_sbkcq_bill_yuanbao_log' '' "substring(time,1,10)='${ydayf}'"
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_serial_reward'
read -u15
{
job_datax_json_f 'textfile_sql' ${region_id} ${server_id} 'select id,account_id,role_id,type,serial,time,kind,pici,'\'${server_id}\' ${server_rds} 'logdb' 'log_serial_reward_'${ydaym}'' 'gaea_cn_sbkcq_log_serial_reward' '' '1=1'
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_dragon_challenge'
#备注：比百度与同步多一个server_id，且是在schema的最后一个字段，所以应该直接用百度云的数据覆盖不会有什么影响.
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"role_id","role_name","level","type","cost","open_num","all_num","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_dragon_challenge_'${ydaym}'' 'gaea_cn_sbkcq_log_dragon_challenge' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_item'
#备注：比百度与同步多一个server_id，且是在schema的最后一个字段，所以应该直接用百度云的数据覆盖不会有什么影响.
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"id","role_id","cmd_id","type_id","optnum","serial1","container_type1","result_num1","serial2","container_type2","result_num2","role_id_relation" ,"time",'\'${server_id}\' ${server_rds} 'logdb' 'log_item_'${ydaym}'' 'gaea_cn_sbkcq_log_item' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_gmcmd'
#备注：比百度与同步多一个server_id，且是在schema的最后一个字段，所以应该直接用百度云的数据覆盖不会有什么影响.
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"id","role_id","gm_cmd","error_code","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_gmcmd_'${ydaym}'' 'gaea_cn_sbkcq_log_gmcmd' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_gemsell'
#备注：比百度与同步多一个server_id，且是在schema的最后一个字段，所以应该直接用百度云的数据覆盖不会有什么影响.
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"id","money_type","cmd_id","serial","type_id","role_id","number","cost_money","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_gemsell_'${ydaym}'' 'gaea_cn_sbkcq_log_gemsell' ''
echo >&15
}&

#'logdb' 'gaea_cn_sbkcq_log_sbk_guild_competitive'
#备注：比百度与同步多一个server_id，且是在schema的最后一个字段，所以应该直接用百度云的数据覆盖不会有什么影响.
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"guild_id","guild_name","leader_id","leader_name","money","rank","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_sbk_guild_competitive_'${ydaym}'' 'gaea_cn_sbkcq_log_sbk_guild_competitive' ''
echo >&15
}&

#'logdb' gaea_cn_sbkcq_log_sbk_guild_kill'
#备注：比百度与同步多一个server_id，且是在schema的最后一个字段，所以应该直接用百度云的数据覆盖不会有什么影响.
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"guild_id","guild_name","kill_num","win","rank","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_sbk_guild_kill_'${ydaym}'' 'gaea_cn_sbkcq_log_sbk_guild_kill' ''
echo >&15
}&

#'logdb' 'log_warn_item'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"role_id","role_name","item_id","num","cmd","cmd_name","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_warn_item_'${ydaym} 'gaea_cn_sbkcq_log_warn_item' ''
echo >&15
}&

#'logdb' 'Log_warn_money'
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"role_id","role_name","type","num","cmd","cmd_name","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_warn_money_'${ydaym} 'gaea_cn_sbkcq_log_warn_money' ''
echo >&15
}&

#logdb.log_openserveractivity
read -u15
{
job_datax_json_f 'textfile' ${region_id} ${server_id} '"role_id","act_id","act_type","act_name","score","rewardscore","rewardstate","item1","itemnum1","item2","itemnum2","item3","itemnum3","time",'\'${server_id}\' ${server_rds} 'logdb' 'log_openserveractivity_'${ydaym} 'gaea_cn_sbkcq_log_openserveractivity' ''
echo >&15
}&

done

wait # 等待所有的后台子进程结束
exec 15>&- # 关闭df15

write_log INFO "***************************Complete to import logdb to bos.*************************"
write_log INFO "All tables have been to be imported to localfile to hive."

write_log INFO "==========================================BEGIN TO MSCK REPARI HIVE TABLE --- ADD PARTITION OF ${ydayf} ================================================="
#hive -f ${base_dir}/sql/msck_repair_hive_table.sql
sort -r ${base_dir}/sql/alter_add_partition_${yday}.sql | uniq > ${base_dir}/sql/alter_add_partition_${yday}.uniq.sql
hive -f ${base_dir}/sql/alter_add_partition_${yday}.uniq.sql
if [ $? -eq 0 ];then
	write_log INFO "msck repair hive table -- ${ydayf} success."
else
	write_log ERROR "msck repair hive table -- ${ydayf} failed."
fi

error_num=`wc -l $error_status_file | awk '{print $1}'`
if [ -f "$error_status_file" ] && [ "X$error_num" != "X0" ];then
        write_log ERROR "##############Has $error_num occured#############"
        curl -d "content=异常信息请见具体同步日志信息.&subject=沙巴克日志同步HIVE异常&tos=xxxxx@gaeamobile.com" http://mailserver:8080/mailservice/send/mail
fi

exit 0
