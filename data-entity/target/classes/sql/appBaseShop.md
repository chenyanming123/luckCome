getShopList
===
* 查询商铺列表
    
    SELECT
        @pageTag(){
        *
        @}
    FROM app_base_shop
    where 1=1	
    @if(!isEmpty(shopName) && shopName != ''){
        and shop_name like #'%'+shopName+'%'#
    @}
    @if(!isEmpty(shopType) && shopType != ''){
        and shop_type = #shopType#
    @}

getAppBaseShop
===
* 获取商店的部分信息
    select id,shop_name,shop_type,shop_phone,area_name,shop_address from app_base_shop where  id = #id#