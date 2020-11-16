getImagesByUserId
===
* 通过userId查询图片信息，返回id和imageName

	select id,image_name from app_data_images where 1=1
	@if(!isEmpty(userId) && userId != ''){
        and user_id = #userId#
    @}
    order by id asc