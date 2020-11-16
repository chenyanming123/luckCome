package com.luck.dataService.common;

public enum AreaName {
    河北省("河北"),
    山西省("山西"),
    吉林省("吉林"),
    辽宁省("辽宁"),
    黑龙江省("黑龙江"),
    陕西省("陕西"),
    甘肃省("甘肃"),
    青海省("青海"),
    山东省("山东"),
    福建省("福建"),
    浙江省("浙江"),
    台湾省("台湾"),
    河南省("河南"),
    湖北省("湖北"),
    湖南省("湖南"),
    江西省("江西"),
    江苏省("江苏"),
    安徽省("安徽"),
    广东省("广东"),
    海南省("海南"),
    四川省("四川"),
    贵州省("贵州"),
    云南省("云南"),
    北京市("北京"),
    上海市("上海"),
    天津市("天津"),
    重庆市("重庆"),
    内蒙古自治区("内蒙"),
    新疆维吾尔自治区("新疆"),
    宁夏回族自治区("宁夏"),
    广西壮族自治区("广西"),
    西藏自治区("西藏");
    private String areaNameDy;

    AreaName(String areaNameDy) {
        this.areaNameDy = areaNameDy;
    }

    public static String getNewAreaName(String areaNameDy) {
        if(areaNameDy != null && !"".equals(areaNameDy)){
            String[] name = areaNameDy.split("-",-1);
            if(name.length >0){
                for (AreaName areaName : AreaName.values()) {
                    if (name[0] == areaName.getAreaNameDy()) {
                        name[0] = areaName.name();
                        for(int i=0;i<name.length;i++){
                            if(i==0){
                                areaNameDy = name[i];
                            }else{
                                areaNameDy = areaNameDy+"-"+name[i];
                            }
                        }
                        return areaNameDy;
                    }
                }
            }
        }
        return areaNameDy;
    }

    public String getAreaNameDy() {
        return areaNameDy;
    }

    public void setAreaNameDy(String areaNameDy) {
        this.areaNameDy = areaNameDy;
    }
}
