select
*
from
( select
    t.id id,
    t.name name,
    sum(t.amount) amounts,
    count(t.id) counts,
    (select sum(amount) from t_project_package_supplier where is_winning = 1 and supplier_id = t.id and status > 6) winAmounts,
    sum(t.winBid) winBids,
    (count(t.id)-sum(t.winBid)) missBids,
    concat(group_concat(distinct t.year order by t.year desc)) years
                    from
                         (select
                            p.id id,p.'name' name,pps.amount amount,pps.is_winning winBid,pps.year year
                           from
                           t_client_company p,t_project_package_supplier pps
                           where
                           pps.status > 6 and p.id = pps.supplier_id) t

    group by  t.id  order by  amounts desc ) sel_tab00 limit 0,20;