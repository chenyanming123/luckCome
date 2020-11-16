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