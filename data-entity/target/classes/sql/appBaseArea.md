queryAreaByAreaCode
===
* 根据行政区划查询下级行政区划，行政区划为空是返回省级行政区划

	select area_code,area_name from app_base_area where 1=1
	@if(isEmpty(areaCode) || areaCode == ''){
        and area_level = 1
    @}else{
        and parent_code = #areaCode#
    @}
