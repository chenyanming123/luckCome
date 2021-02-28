getUserinfo
===
* 根据userId查询用户资料信息

    select 
        p1.user_id,
        p1.nick_name,
        p1.sex,
        p1.birth_year,
        ROUND(DATEDIFF(CURDATE(), p1.birth_year)/365.2422) as age,
        p1.address,
        p1.domicile,
        p1.education,
        p4.education as education_name,
        p1.stature,
        p1.work,
        p1.income,
        p3.income as income_value,
        p1.description,
        p1.choose_require 
    from app_data_userinfo p1
    left join app_base_education p4 on p1.education = p4.id 
    left join app_base_income p3 on p1.income = p3.id
    where 1=1
    @if(!isEmpty(userId) && userId != ''){
        and p1.user_id = #userId#
    @}

getMyUserinfo
===
* 根据userId查询用户资料信息-我的资料含微信等重要信息

    select 
        p1.user_id,
        p1.nick_name,
        p1.wechat,
        p1.sex,
        p1.birth_year,
        ROUND(DATEDIFF(CURDATE(), p1.birth_year)/365.2422) as age,
        p1.address,
        p1.domicile,
        p1.education,
        p4.education as education_name,
        p1.stature,
        p1.work,
        p1.income,
        p3.income as income_value,
        p1.description,
        p1.choose_require 
    from app_data_userinfo p1
    left join app_base_education p4 on p1.education = p4.id 
    left join app_base_income p3 on p1.income = p3.id
    where 1=1
    @if(!isEmpty(userId) && userId != ''){
        and p1.user_id = #userId#
    @}

getUserinfo2(暂时不用)
===
* 根据userId查询用户资料信息

    select 
        p1.user_id,
        p1.nick_name,
        p1.wechat,
        p1.sex,
        p1.birth_year,
        ROUND(DATEDIFF(CURDATE(), p1.birth_year)/365.2422) as age,
        p1.address,
        p1.domicile,
        p1.education,
        p4.education as education_name,
        p1.stature,
        p1.work,
        p1.income,
        p3.income as income_value,
        p1.description,
        p1.choose_require 
    from app_data_userinfo p1
    left join app_base_education p4 on p1.education = p4.id 
    left join app_base_income p3 on p1.income = p3.id
    where 1=1
    @if(!isEmpty(userId) && userId != ''){
        and p1.user_id = #userId#
    @}


queryUserInfo
===
* 分页列表查看用户资料信息

    select 
        @pageTag(){
            p1.user_id,
            p1.sex,
            p1.birth_year,
            ROUND(DATEDIFF(CURDATE(), p1.birth_year)/365.2422) as age,
            p1.nick_name,
            p1.work,
            p1.address,
            p1.domicile,
            p2.image_name,
            p3.education AS education_name
        @}
    from  app_data_userinfo p1 
    left join (
    		select s.* from (
    				select *, row_number() over (partition by user_id order by user_id) as user_id_num  from app_data_images
    		) s where s.user_id_num = 1
    ) p2 on p1.user_id = p2.user_id
    left join app_base_education p3 ON p1.education = p3.id
    where 1=1	
    @if(!isEmpty(sex) && sex != ''){
        and p1.sex = #sex#
    @}
    @if(!isEmpty(address) && address != ''){
        and p1.address like #address+'%'#
    @}
    @if(!isEmpty(age_start) && age_start != ''){
        and ROUND(DATEDIFF(CURDATE(), p1.birth_year)/365.2422) >= #age_start#
    @}
    @if(!isEmpty(age_end) && age_end != ''){
        and ROUND(DATEDIFF(CURDATE(), p1.birth_year)/365.2422) <= #age_end#
    @}
    order by RAND()
    
queryUserInfo2
===
* 分页列表查看用户资料信息(暂不用)

    select 
        @pageTag(){
            p1.user_id,
            p1.nick_name,
            p1.wechat,
            p1.sex,
            p1.birth_year,
            p1.address,
            p2.area_name as address_name,
            p1.domicile,
            p3.area_name as domicile_name,
            p1.education,
            p4.education as education_name,
            p1.stature,
            p1.work,
            p1.income_start,
            p1.income_end,
            p1.description,
            p1.choose_require,
            p5.image_name as image_name1,
            p6.image_name as image_name2,
            p7.image_name as image_name3
        @}
    from app_data_userinfo p1
    left join app_base_area p2 on p1.address = p2.area_code 
    left join app_base_area p3 on p1.domicile = p3.area_code 
    left join app_base_education p4 on p1.education = p4.id
    left join (
    		select s.* from (
    				select *, row_number() over (partition by user_id order by user_id) as user_id_num  from app_data_images
    		) s where s.user_id_num = 1
    ) p5 on p1.user_id = p5.user_id
    left join (
    		select s.* from (
    				select *, row_number() over (partition by user_id order by user_id) as user_id_num  from app_data_images
    		) s where s.user_id_num = 2
    ) p6 on p1.user_id = p6.user_id	
    left join (
    		select s.* from (
    				select *, row_number() over (partition by user_id order by user_id) as user_id_num  from app_data_images
    		) s where s.user_id_num = 3
    ) p7 on p1.user_id = p7.user_id	
    where 1=1	
    @if(!isEmpty(sex) && sex != ''){
        and p1.sex = #sex#
    @}
    @if(!isEmpty(address) && address != ''){
        and p1.address = #address#
    @}
    @if(!isEmpty(birth_year_start) && birth_year_start != ''){
        and p1.birth_year >= #birth_year_start#
    @}
    @if(!isEmpty(birth_year_end) && birth_year_end != ''){
        and p1.birth_year <= #birth_year_end#
    @}
    order by RAND()



