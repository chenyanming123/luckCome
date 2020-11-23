queryMylove
===
* 我喜欢的，通过用户id查询用户喜欢的

    SELECT
        @pageTag(){
            p1.id,
            p1.other_id,
            p2.nick_name,
            p2.birth_year,
            ROUND( DATEDIFF( CURDATE( ), p2.birth_year ) / 365.2422 ) AS age,
            p2.address,
            p2.domicile,
            p3.education AS education_name,
            p2.WORK,
            p5.image_name,
            p1.STATUS
        @}
    FROM
        app_data_appointment p1
        LEFT JOIN app_data_userinfo p2 ON p1.other_id = p2.user_id
        LEFT JOIN app_base_education p3 ON p2.education = p3.id
        LEFT JOIN app_base_income p4 ON p2.income = p4.id
        left join (
            select s.* from (
                    select *, row_number() over (partition by user_id order by user_id) as user_id_num  from app_data_images
            ) s where s.user_id_num = 1
        ) p5 on p1.other_id = p5.user_id
    where 1=1
    @if(!isEmpty(userId) && userId != ''){
        and p1.user_id = #userId#
    @}
    @if(status == 0){
        and ( p1.status = 0 or p1.status = 2)
    @}else if(status == 1){
        and  p1.status = 1
    @}else if(status == 3){
        and  p1.status = 3
    @}
    and p1.data_status = 1
    order by p1.status asc , p1.register_time desc
    
queryLoveme
===
* 喜欢我的，通过用户id查询用户喜欢的

    SELECT
        @pageTag(){
            p1.id,
            p1.user_id,
            p2.nick_name,
            p2.birth_year,
            ROUND( DATEDIFF( CURDATE( ), p2.birth_year ) / 365.2422 ) AS age,
            p2.address,
            p2.domicile,
            p3.education AS education_name,
            p2.WORK,
            p5.image_name,
            p1.STATUS
       @}
    FROM
        app_data_appointment p1
        LEFT JOIN app_data_userinfo p2 ON p1.user_id = p2.user_id
        LEFT JOIN app_base_education p3 ON p2.education = p3.id
        LEFT JOIN app_base_income p4 ON p2.income = p4.id
        left join (
            select s.* from (
                    select *, row_number() over (partition by user_id order by user_id) as user_id_num  from app_data_images
            ) s where s.user_id_num = 1
        ) p5 on p1.user_id = p5.user_id
    where 1=1
    @if(!isEmpty(userId) && userId != ''){
        and p1.other_id = #userId#
    @}
    @if(!isEmpty(status) && status != ''){
        and p1.status = #status#
    @}
    and p1.data_status = 1
    order by p1.status asc ,p1.register_time desc
    
queryLoveEachOther
===
* 相互喜欢，通过用户id查询用户喜欢的
    SELECT
        @pageTag(){
            p1.id,
            p1.newUserId,
            p2.nick_name,
            p2.birth_year,
            ROUND( DATEDIFF( CURDATE( ), p2.birth_year ) / 365.2422 ) AS age,
            p2.address,
            p2.domicile,
            p3.education AS education_name,
            p2.WORK,
            p5.image_name,
            p1.STATUS 
    	@}
    FROM
    	(
            SELECT
                *,
            IF
                ( user_id = #userId#, other_id, user_id ) AS newUserId 
            FROM
                app_data_appointment 
            WHERE
                1 = 1 
                @if(!isEmpty(status) && status != ''){
                    AND STATUS = #status#
                @}
                @if(!isEmpty(userId) && userId != ''){
                    AND ( user_id = #userId# OR other_id = #userId# ) 
                @}
                and data_status = 1
                order by register_time desc
                
    	) p1
    	LEFT JOIN app_data_userinfo p2 ON p1.newUserId = p2.user_id
    	LEFT JOIN app_base_education p3 ON p2.education = p3.id
    	LEFT JOIN app_base_income p4 ON p2.income = p4.id
    	LEFT JOIN (
            SELECT
                s.* 
            FROM
                ( SELECT *, row_number ( ) over ( PARTITION BY user_id ORDER BY user_id ) AS user_id_num FROM app_data_images ) s 
            WHERE
            s.user_id_num = 1 
    	) p5 ON p1.newUserId = p5.user_id
    	
queryOnlyOneByUserIdAndOtherId
===
* 根据发起人和对方查询最新的一条数据
    select * from app_data_appointment 
    where  user_id = #userId# and other_id = #otherId# and  (status = 0 or status = 1) 
    order by register_time desc
    limit 1
    
getAppDataAppointment
===
* 获取约会记录的部分新
    select id,user_id,other_id,place_id,appointment_time from app_data_appointment where  id = #id#