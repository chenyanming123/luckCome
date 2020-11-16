getAppUserBymobile_WX
===
* 通过手机号码获取用户信息——微信

	select nick_name,gender,mobile,country,province,city,avatar_url_wx from app_user where 1=1
	@if(!isEmpty(mobile) && mobile != ''){
        and mobile = #mobile#
    @}

getAppUserBymobile_DY
===
* 通过手机号码获取用户信息——抖音

	select nick_name,gender,mobile,country,province,city,avatar_url_dy from app_user where 1=1
	@if(!isEmpty(mobile) && mobile != ''){
        and mobile = #mobile#
    @}
    
getAppUserBymobile
===
* 通过手机号码获取用户信息

	select * from app_user where 1=1
	@if(!isEmpty(mobile) && mobile != ''){
        and mobile = #mobile#
    @}
